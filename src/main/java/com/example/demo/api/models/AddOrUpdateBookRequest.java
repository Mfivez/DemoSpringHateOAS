package com.example.demo.api.models;

import java.util.List;

public record AddOrUpdateBookRequest(
        String title,
        List<String> authorNames
)
{
}
