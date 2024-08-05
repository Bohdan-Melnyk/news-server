package com.example.server.controller;

import com.example.server.model.News;
import com.example.server.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/news")
    public ResponseEntity<News[]> fetchNews() {
        var currentDayNewsList = newsService.getAllForCurrentDay();
        return ResponseEntity.ok().body(currentDayNewsList.toArray(new News[currentDayNewsList.size()]));
    }

}
