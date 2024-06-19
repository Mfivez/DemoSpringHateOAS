package com.example.demo.api.models;

import java.util.List;

public record BookResponse(
        String title,
        List<String> author
)
{ }
