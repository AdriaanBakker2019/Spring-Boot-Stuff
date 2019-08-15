package com.example.imdb_heroku.service;

import com.example.imdb_heroku.repo.Actor;
import com.example.imdb_heroku.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public  class ActorService {
    @Autowired
    ActorRepository repository;

     public List<Actor> findByName(String name) {
         return repository.findByName(name);
     }

}