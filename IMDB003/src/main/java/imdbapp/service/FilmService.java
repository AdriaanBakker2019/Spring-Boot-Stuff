package imdbapp.service;

import imdbapp.repo.Film;
import imdbapp.repo.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Component
public class FilmService {
    @Autowired
    FilmRepository repository;



    public List<Film> findAll() {
        return  repository.findAll();
    }


}