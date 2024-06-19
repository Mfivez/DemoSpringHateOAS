package com.example.demo.api.models;

import java.util.List;

public record UpdateBookRequest(
        String title,
        String newTitle,
        List<String> authorNames
)
{
}
