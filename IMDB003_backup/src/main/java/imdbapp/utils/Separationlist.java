package imdbapp.utils;

import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.repo.FilmActor;

import java.util.*;

public class Separationlist {


    private HashSet<String> actorkeylist;
    private HashMap<String, FilmActor> filmactorlist;

    public HashSet<String> getActorkeylist() {
        return actorkeylist;
    }

    public String printActorkeylist() {
        String result = "Actorkeylist: ";
        for (String s: actorkeylist) {  result+= s+  "-";
        }
        return result;
    }

    public Separationlist() {

        actorkeylist = new HashSet<>();
        filmactorlist = new HashMap<>();
    }



    public void addFilmActor(String fromActor, String filmkey, String actorkey, boolean logging) {
        if (!actorkeylist.contains(actorkey)) {
            addActor(actorkey, logging);
            FilmActor fa = new FilmActor(filmkey, fromActor);
            filmactorlist.put(actorkey, fa);
        }
    }

// externally used to add a single actor to the first list.

    public void addActor(String aActorkey, boolean logging) {
            actorkeylist.add(aActorkey);
            if ((logging) && (actorkeylist.size() % 1000 == 0)) {
                System.out.println("actorkey list size:" + actorkeylist.size());
            }
    }

    // find original actor (present in previous list) who introduced actorkey to
    // this list plus the key of the film
    // that was generated by this original actor
    public FilmActor findActorOrigin(String aActorkey) {
        return filmactorlist.get(aActorkey);
    }



    String printSize () {
        return " nr of Actors:" + actorkeylist.size();
    }

    boolean containsActorkey(String aActorkey) {
        return actorkeylist.contains(aActorkey);
    }


}
