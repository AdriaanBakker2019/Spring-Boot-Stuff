package imdbapp.utils;

import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.repo.FilmActor;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class CalculateSeparation {


    private final String actor1key;
    private final String actor2key;

    private final ActorService actorService;
    private final FilmService filmService;

    private final int Maxlevel = 3;


    /*
    *   Utility object to calculate the degree of separation between two actors.
    *   actor1key and actor2key have already been checked to lead to an existing actor in the database.
    */
    public CalculateSeparation(String actor1key, String actor2key, ActorService service, FilmService filmService) {
        this.actor1key = actor1key;
        this.actor2key = actor2key;
        this.actorService = service;
        this.filmService = filmService;
    }


    String printFilm(String aFilmkey) throws Exception {
        Film film = filmService.findFilmByTconst(aFilmkey);
        if (film == null) {
            throw new Exception("printFilm:Film " + aFilmkey + " not found ");
        }
        return film.getPrimaryTitle() + " (" + film.getStartYear() + ", " + film.getTitleType() + ")";
    }

    String printActor(String aActorkey) throws Exception {
        Actor actor = actorService.findPersonByKey(aActorkey);
        if (actor == null) {
            throw new Exception("printActor:Actor " + aActorkey + " not found ");
        }
        return actor.getPrimaryName();
    }







    public Separationlist createNextActorList(Separationlist aActorlist) {
        Separationlist resultlist = new Separationlist();
        for (String actorkey: aActorlist.getActorkeylist()) {
            List<String> filmlist = filmService.findFilmKeysByActorKey(actorkey);
            for (String filmkey: filmlist) {
                List<String> newactorlist = actorService.findActorKeysBy(filmkey);
                for (String newactorkey: newactorlist) {
                    resultlist.addFilmActor( actorkey, filmkey, newactorkey, true);
                }
            }
        }
        return resultlist;
    }


    public ArrayList<Separationlist>  lists_1 = new ArrayList<>();
    public ArrayList<Separationlist>  lists_2 = new ArrayList<>();


    public int checkLevelOfSeparation(Resultstrings aResultstrings) throws Exception {
        for (int i=0; i<=6; i++) {
            lists_1.add(new Separationlist());
            lists_2.add(new Separationlist());
        }

        if (actor1key.equals(actor2key)) {
            aResultstrings.add("actor 1 and actor 2 should not be the same");
            return -1;
        }
        aResultstrings.add("Degree of separation between "+ printActor(actor2key) + " and " + printActor(actor1key));
        lists_1.get(0).addActor(actor1key, false);

        lists_1.set(1, createNextActorList(lists_1.get(0)));
        System.out.println("list 1.1 has " + lists_1.get(1).printSize());
        if (lists_1.get(1).containsActorkey(actor2key)) {
            aResultstrings.add("separation level is 1");

            /*FilmActor fa = lists_1.get(1).findActorOrigin(actor2key);
            aResultstrings.add( printActor(actor2key) + " and " +
                    printActor(fa.getActorkey()) +
                    " were in " + printFilm(fa.getFilmkey()));*/
            reportOn(lists_1.get(1), aResultstrings, actor2key);

            return 1;
        }

        lists_2.get(0).addActor(actor2key, false);
        lists_2.set(1, createNextActorList(lists_2.get(0)));
        System.out.println("list 2.1 has " + lists_2.get(1).printSize());
        for (String actor: lists_1.get(1).getActorkeylist()) {
            if (lists_2.get(1).getActorkeylist().contains(actor)) {
                aResultstrings.add("separation level is 2");
                reportOn(lists_1.get(1), aResultstrings, actor );
                reportOn(lists_2.get(1), aResultstrings, actor );
                return 2;
            }
        }

        lists_1.set(2, createNextActorList( lists_1.get(1)));
        System.out.println("list 1.2 has " + lists_1.get(2).printSize());
        for (String actor: lists_1.get(2).getActorkeylist()) {
            if (lists_2.get(1).getActorkeylist().contains(actor)) {
                aResultstrings.add("separation level is 3");
                reportOn(lists_2.get(1), aResultstrings, actor );
                String sOriginalActor = reportOn(lists_1.get(2), aResultstrings, actor );
                reportOn(lists_1.get(1), aResultstrings, sOriginalActor);
                return 2;
            }
        }


        lists_2.set(2, createNextActorList( lists_2.get(1)));
        System.out.println("list 2.2 has " + lists_2.get(2).printSize());
        for (String actor: lists_1.get(2).getActorkeylist()) {
            if (lists_2.get(2).getActorkeylist().contains(actor)) {
                aResultstrings.add("separation level is 4");
                String sOriginalActor = reportOn(lists_2.get(2), aResultstrings, actor );
                reportOn(lists_2.get(1), aResultstrings, sOriginalActor );
                aResultstrings.add(" on the other hand:");
                sOriginalActor = reportOn(lists_1.get(2), aResultstrings, actor );
                reportOn(lists_1.get(1), aResultstrings, sOriginalActor );
                return 2;
            }
        }

        aResultstrings.add("Degree of separation is > 3");
        return  -1;
    } // checkLevelOfSeparation



    // Report on the actor key in the separation list, return key of original actor
    String reportOn(Separationlist list, Resultstrings aResultstrings, String aActorkey) throws Exception {
        FilmActor fa = list.findActorOrigin(aActorkey);
        aResultstrings.add( printActor(aActorkey) + " and " +
                    printActor(fa.getActorkey()) +
                    " were in " + printFilm(fa.getFilmkey()));
        return fa.getActorkey();
    }



}