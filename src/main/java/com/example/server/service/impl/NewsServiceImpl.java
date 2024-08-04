package com.example.server.service.impl;

import com.example.server.dao.NewsRepo;
import com.example.server.dto.NewsDto;
import com.example.server.model.News;
import com.example.server.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepo newsRepo;

    /**
     * Creates a new news entry.
     *
     * @param newsDto the data transfer object containing the details of the news to be created
     */
    @Override
    public void createNews(NewsDto newsDto) {
        News newNews = new News();
        newNews.setHeadline(newsDto.headline());
        newNews.setDescription(newsDto.description());
        newNews.setPublicationTime(newsDto.publicationTime());
        newsRepo.save(newNews);
    }

    /**
     * Reads and retrieves the details of a news entry.
     *
     * @param id the unique identifier of the news to be read
     */
    @Override
    public News readNews(Long id) {
        return newsRepo.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
    }

    /**
     * Updates an existing news entry.
     *
     * @param id the unique identifier of the news to be updated
     */
    @Override
    public void updateNews(Long id, NewsDto newsDto) {
        var news = newsRepo.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        news.setHeadline(newsDto.headline());
        news.setDescription(newsDto.description());
        news.setPublicationTime(newsDto.publicationTime());
        newsRepo.save(news);
    }

    /**
     * Deletes a news entry.
     *
     * @param id the unique identifier of the news to be deleted
     */
    @Override
    public void deleteNews(Long id) {
        newsRepo.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        newsRepo.deleteById(id);
    }

    @Override
    public List<News> getAllForCurrentDay() {
        return newsRepo.fetchAllForCurrentDay(LocalDate.now().atStartOfDay(), LocalDate.now().atTime(LocalTime.MAX));
    }
}
