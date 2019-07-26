package imdbapp.service;

import imdbapp.repo.Film;
import imdbapp.repo.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilmService {
    @Autowired
    FilmRepository repository;



    public List<Film> findAll() {
        return  repository.findAll();
    }

    public Film findFilm() {
        return  repository.findFilm();
    }

    public List<Film> findFilmsBy(String ncode) {
        return  repository.findFilmsBy(ncode);
    }

    public List<Film> findTitlesInCommon(String ncode1, String ncode2) {
        return  repository.findTitlesInCommon(ncode1, ncode2);
    }


}