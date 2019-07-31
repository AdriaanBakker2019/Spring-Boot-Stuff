package imdbapp.service;

import imdbapp.repo.Actor;
import imdbapp.repo.ActorRepository;
import imdbapp.repo.Film;
import imdbapp.repo.FilmWithCrew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActorService {
    @Autowired ActorRepository repository;

    public List<Actor> findActorsByName(String aName) {
        return  repository.findActorsByName(aName);
    }

    public List<Actor> findActorsWithKnownBirthday(String aName) {
        return  repository.findActorsByPrimaryNameEqualsAndBirthYearNotNull(aName);
    }

    public Actor findPersonByKey(String aNconst) {
        return repository.findPersonByNconst(aNconst);
    }

    public List<String> findActorKeysByTitleKey(String aTconst) {
        return repository.findActorKeysByTitleKey(aTconst);
    }

    public List<String> findActorKeysBy(String tconst) {
        return repository.findActorKeysBy(tconst);
    }

    /*
     * Generate filmwithcrew list from filmlist
     *
     */
    public List<FilmWithCrew>  generateFWClist(List<Film> aFilmlist) {
        List<FilmWithCrew> fwclist = new ArrayList<>();
        for (Film film: aFilmlist) {

            FilmWithCrew fwc = new FilmWithCrew(film);
            //System.out.println("getting actorlist for film " + film.getPrimaryTitle() + ":" + film.getStartYear());
            List<String> keylist = findActorKeysByTitleKey(film.getTconst());
            //System.out.println("keylist size is " + keylist.size());
            for (String key: keylist) {
                Actor actor = findPersonByKey(key);

                System.out.println("actor found: " + actor.getPrimaryName());
                fwc.addActor(actor);
            }
            fwclist.add(fwc);
        }
        return fwclist;
    }

}