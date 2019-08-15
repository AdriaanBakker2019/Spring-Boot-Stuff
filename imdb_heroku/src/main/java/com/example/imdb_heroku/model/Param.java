package com.example.imdb_heroku.model;

import org.springframework.stereotype.Component;

@Component
public class Param {
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
