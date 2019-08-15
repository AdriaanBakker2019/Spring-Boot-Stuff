package com.example.imdb_heroku.repo;

import org.springframework.stereotype.Component;

@Component
public class ActorRequest {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
