package com.example.server.dto;

import java.time.LocalDateTime;

public record NewsDto(String headline, String description, LocalDateTime publicationTime) {
}
