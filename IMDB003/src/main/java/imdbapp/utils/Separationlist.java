package imdbapp.utils;

import imdbapp.repo.Film;
import imdbapp.repo.FilmActor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Separationlist {

    HashSet<String> filmkeylist;
    HashSet<String> actorkeylist;
    HashSet<FilmActor> filmActorList;

    public Separationlist() {
        filmkeylist = new HashSet<>();
        actorkeylist = new HashSet<>();


        filmActorList = new HashSet<>();
    }


    void addFilmActor(FilmActor filmActor, boolean logging) {
        if (!filmActorList.contains(filmActor)) {
            filmActorList.add(filmActor);

            if ((logging) && (filmActorList.size() % 100 == 0)) {
                System.out.println("filmactor list size:" + filmActorList.size());
            }
        }
        if (!filmkeylist.contains(filmActor.getFilmkey())) {
            filmkeylist.add(filmActor.getFilmkey());
            if ((logging) && (filmkeylist.size())% 100 == 0) {
                System.out.println("filmkey list size:" + filmkeylist.size());
            }
        }
        if (!actorkeylist.contains(filmActor.getActorkey())) {
            actorkeylist.add(filmActor.getActorkey());
            if ((logging) && (actorkeylist.size() % 100 == 0)) {
                System.out.println("actorkey list size:" + actorkeylist.size());
            }
        }



    }

    public HashSet<String> getFilmkeylist() {
        return filmkeylist;
    }

    public HashSet<String> getActorkeylist() {
        return actorkeylist;
    }

    public HashSet<FilmActor> getFilmActorList() {
        return filmActorList;
    }

    public boolean hasActorKey(String actorkey) {
        return(actorkeylist.contains(actorkey));
    }

    public String getFilmkeyByActorkey(String actorkey) {
        for (FilmActor filmactor: filmActorList) {
            if (filmactor.getActorkey().equals(actorkey)) {
                return filmactor.getFilmkey();
            }
        }
        return null;
    }

    public boolean hasFilmKey(String filmkey) {
        return(filmkeylist.contains(filmkey));
    }


}
