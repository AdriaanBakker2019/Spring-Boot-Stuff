package imdbapp.service;

import imdbapp.repo.Actor;
import imdbapp.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Component
public class ActorService {
    @Autowired ActorRepository repository;



    public List<Actor> findAll() {
        return  repository.findAll();
    }

    public List<Actor> findActorsByName(String aName) {
        return  repository.findActorsByName(aName);
    }




}