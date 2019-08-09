package imdbapp.service;

import imdbapp.repo.ActorRepository;
import imdbapp.repo.Film;
import imdbapp.repo.FilmRepository;
import java.util.ArrayList;

import imdbapp.repo.FilmWithCrew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FilmService {
    @Autowired
    FilmRepository repository;

    @Autowired
    ActorRepository actorRepository;

    public List<Film> findAll() {
        return  repository.findAll();
    }

    public Film findFilmByTconst(String tconst) {
        return  repository.findFilmByTconst(tconst);
    }

    public List<Film> findFilmsByActorKey(String ncode) {
        return repository.findFilmsByNconst(ncode);
    }

    public List<String> findFilmKeysByActorKey(String ncode) { return repository.findFilmKeysByActorKey(ncode); }

    public List<Film> findFilmsByPrimaryName(String primaryName) {
        return repository.findFilmsByPrimaryTitle(primaryName);
    }

    public List<Film> findFilmsInCommon(String ncode1, String ncode2) {
        List<String> keylist1 = findFilmKeysByActorKey(ncode1);
        List<String> keylist2 = findFilmKeysByActorKey(ncode2);

        List<Film> filmlist = new ArrayList<Film>();

        for (String key: keylist1) {
            if (keylist2.contains(key)) {
                Film film = repository.findFilmByTconst(key);
                filmlist.add( film );
            }
        }
        return  filmlist;
    }




}