package imdbapp.service;

import imdbapp.repo.Actor;
import imdbapp.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

}