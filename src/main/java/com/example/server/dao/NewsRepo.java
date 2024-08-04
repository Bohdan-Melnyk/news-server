package com.example.server.dao;

import com.example.server.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NewsRepo extends JpaRepository<News, Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from News n where n.publicationTime >= :startOfTheDay AND n.publicationTime < :endOfTheDay")
    void deleteAllForCurrentDay(@Param("startOfTheDay") LocalDateTime startOfTheDay, @Param("endOfTheDay") LocalDateTime endOfTheDay);

    @Transactional
    @Modifying
    @Query(value = "select n from News n where n.publicationTime >= :startOfTheDay AND n.publicationTime < :endOfTheDay")
    List<News> fetchAllForCurrentDay(@Param("startOfTheDay") LocalDateTime startOfTheDay, @Param("endOfTheDay") LocalDateTime endOfTheDay);
}
