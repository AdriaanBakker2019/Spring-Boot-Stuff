package imdbapp.service;

import imdbapp.repo.Film;
import imdbapp.repo.FilmRepository;
import java.util.ArrayList;
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

    public Film findFilm(String tconst) {
        return  repository.findFilm(tconst);
    }

    public List<Film> findFilmsBy(String ncode) {
        return  repository.findFilmsBy(ncode);
    }

    public List<String> findTitleKeysInCommon(String ncode1, String ncode2) {
        return  repository.findTitlesInCommon(ncode1, ncode2);
    }

    public List<Film> findTitlesInCommon(String ncode1, String ncode2) {
        List <String> titlekeys = findTitleKeysInCommon(ncode1, ncode2);
        List <Film> filmlist = new ArrayList<Film>();

        for (String key: titlekeys) {
            filmlist.add(findFilm(key));
        }
        return filmlist;
    }


}