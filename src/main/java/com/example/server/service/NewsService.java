package com.example.server.service;

import com.example.server.dto.NewsDto;
import com.example.server.model.News;

import java.util.List;

public interface NewsService {

    /**
     * Creates a new news entry.
     *
     * @param newsDto the data transfer object containing the details of the news to be created
     */
    void createNews(NewsDto newsDto);

    /**
     * Reads and retrieves the details of a news entry.
     *
     * @param id the unique identifier of the news to be read
     * @return the news entry corresponding to the specified id
     */
    News readNews(Long id);

    /**
     * Updates an existing news entry.
     *
     * @param id the unique identifier of the news to be updated
     * @param newsDto the data transfer object containing the updated details of the news
     */
    void updateNews(Long id, NewsDto newsDto);

    /**
     * Deletes a news entry.
     *
     * @param id the unique identifier of the news to be deleted
     */
    void deleteNews(Long id);

    /**
     * Retrieves a list of all news entries for the current day.
     *
     * @return a list of news entries published on the current day
     */
    List<News> getAllForCurrentDay();
}
