package com.example.server.schedule;

import com.example.server.dao.NewsRepo;
import com.example.server.model.News;
import lombok.RequiredArgsConstructor;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class NewsCollector {

    public static final String NEWS_WEBSITE_URL = "https://www.pravda.com.ua/news/";

    private final Logger LOGGER = LoggerFactory.getLogger(NewsCollector.class);

    private final NewsRepo newsRepo;

    @Scheduled(cron = "0 0/20 * * * ?")
    private void fetchNewsFromTheSite() {
        LOGGER.info("Method fetchNews begins");
        List<News> newsList = new ArrayList<>();
        try {
            LOGGER.info("News collecting begins");
            newsRepo.deleteAllForCurrentDay(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
            Document document = Jsoup.connect(NEWS_WEBSITE_URL).get();
            Elements elements = document.getElementsByClass("article_news_list");
            for (Element element : elements) {
                String publicationTime = element.getElementsByClass("article_time").text();
                if (!publicationTime.isEmpty()) {
                    News news = new News();
                    news.setHeadline(element.select("a").text());

                    String newsLink = element.select("a").attr("href");
                    if (newsLink.startsWith("https://")) {
                        LOGGER.info(newsLink);
                        fetchNewsDescription(newsLink, news);
                    } else {
                        String correctConnectionString = new StringBuilder(newsLink).insert(0, "https://www.pravda.com.ua").toString();
                        fetchNewsDescription(correctConnectionString, news);
                    }
                    news.setPublicationTime(LocalDateTime.of(LocalDate.now(),
                            LocalTime.parse(publicationTime)));
                    newsList.add(news);
                    LOGGER.info("News added");
                }
            }
            LOGGER.info("News collecting ends");
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("Method fetchNews ends");
        newsRepo.saveAll(newsList);
    }

    private String buildDescription(Elements elements) {
        String result;
        StringBuilder sb = new StringBuilder();
        for (Element element : elements) {
            sb.append(element.text());
        }
        result = sb.toString();
        sb.delete(0, sb.length());
        return result;
    }

    private void fetchNewsDescription(String url, News news) {
        try {
            Document doc = Jsoup.connect(url).get();
            Elements el = doc.getElementsByTag("p");
            String descriptionText = buildDescription(el);
            LOGGER.info("Length: " + descriptionText.length());
            news.setDescription(descriptionText);
        } catch (HttpStatusException e) {
            news.setDescription("Error 403");
        } catch (IllegalArgumentException e) {
            news.setDescription("Error 404");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
