package imdbapp.repo;




import java.util.HashMap;
import java.util.List;

/*
*
* This class is used to persist the whole TitlePrincipals table, it serves an attempt
* to speed up the creation of sets of separation between actors
*
* For any film key, the set of actors in this film according to the TitlePrincipals is stored.
*
* See also the CalculateSeparation class in the utils package
* ActorByFilm will be used in the inner loop of  generateNextSeparationlist: it replaces the
* call to actorService.findActorKeysBy(filmkey);
* */
public class ActorkeysByFilmkey {
    private HashMap<String, List<String>> filmMap = new HashMap<>();
    private  boolean initialized = false;

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }


    public ActorkeysByFilmkey() {
    }



    void add(String aTitlekey,  List<String> aActorlist ) {
        if (!filmMap.containsKey(aTitlekey))
            filmMap.put(aTitlekey, aActorlist);
    }

    List<String> findActorKeysBy(String aFilmkey) {
        if (!initialized) return null;
        return filmMap.get(aFilmkey);
    }
}
