package com.example.imdb_heroku.model;

import org.springframework.stereotype.Component;

@Component
public class Request {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
