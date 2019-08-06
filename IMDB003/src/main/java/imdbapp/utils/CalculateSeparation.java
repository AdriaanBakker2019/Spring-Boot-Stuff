package imdbapp.utils;

import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.repo.FilmActor;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;

import java.util.ArrayList;
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


    public ArrayList<Separationlist> lists1 = new ArrayList<>();
    public ArrayList<Separationlist> lists2 = new ArrayList<>();


    public int checkLevelOfSeparation(Resultstrings aResultstrings) throws Exception {
        for (int i=0; i<=6; i++) {
            lists1.add(new Separationlist());
            lists2.add(new Separationlist());
        }

        if (actor1key.equals(actor2key)) {
            aResultstrings.add("actor 1 and actor 2 should not be the same");
            return -1;
        }
        aResultstrings.add("Degree of separation between "+ printActor(actor2key) + " and " + printActor(actor1key));
        lists1.get(0).addActor(actor1key, false);
        lists2.get(0).addActor(actor2key, false);



        //check level 1
        generateNext(true, 1);
        int result = checkNext(true, 1, 0, aResultstrings);
        if (result >0 ) return result;

        // check level 2
        generateNext(false, 1);
        result = checkNext(false, 1, 1, aResultstrings);
        if (result >0 ) return result;

        // check level 3
        generateNext(false, 2);
        result = checkNext(true, 1, 2, aResultstrings);
        if (result >0 ) return result;

        // check level 4
        generateNext(true, 2);
        result = checkNext(true, 2, 2, aResultstrings);
        if (result >0 ) return result;



        aResultstrings.add("Degree of separation is > 4");
        return -1;

    } // checkLevelOfSeparation



    // Report on the actor key in the separation list, return key of original actor
    String reportOn(Separationlist list, Resultstrings aResultstrings, String aActorkey) throws Exception {
        FilmActor fa = list.findActorOrigin(aActorkey);
        aResultstrings.add( printActor(aActorkey) + " and " +
                    printActor(fa.getActorkey()) +
                    " were in " + printFilm(fa.getFilmkey()));
        return fa.getActorkey();
    }

    /********************************************************************************************
     *    Generate the next level of separation set for an actor using the previous level
     *    Parameters: aFirstActor is true if sets for the first actor are extended,
     *                            false if the sets for the second actor are extended
     *
     * ******************************************************************************************/
    void generateNext(boolean aFirstActor, int aLevel)  throws Exception {
        // Determine sets to be processed, is it the set of lists for actor1 or for actor2?
        ArrayList<Separationlist> lists;
        int actornr = 0;

        if (aFirstActor) {
            lists = lists1;
            actornr = 1;
        } else {
            lists = lists2;
            actornr = 2;
        }
        System.out.println("Extend list to " + aLevel + " for actor " + actornr);
        lists.set(aLevel, createNextActorList(lists.get(aLevel - 1)));

        System.out.println("list size " + aLevel + " for actor " + actornr + lists.get(aLevel).printSize());
    }


    /********************************************************************************************
     *    Check actor overlap between the separation lists for both actors
     *    Parameter aFirstActor determines if aLevel applies to the set of the first or the second actor
     *    Parameter aLevel determines the level in the set of separationlists
     *    aLevel should be larger than 1
     *
     *    In case overlap is found, the level of separation is determined as the sum of both levels
     *    and the path between two actors is printed to the result string sets
     * ******************************************************************************************/
    int checkNext(boolean aFirstActor, int aLevel, int aOtherlevel, Resultstrings aResultstrings ) throws Exception {

        System.out.println("CheckNext: " + aFirstActor + "," + aLevel + "," + aOtherlevel);
        int seplevel = aLevel + aOtherlevel;
        // Determine sets to be processed, is it the set of lists for actor1 or for actor2?
        ArrayList<Separationlist> lists;

        ArrayList<Separationlist> otherlists;


        if (aFirstActor) {
            lists = lists1;
            otherlists = lists2;
        } else {
            lists = lists2;
            otherlists = lists1;
        }
        Separationlist list1 = lists.get(aLevel);
        Separationlist list2 = otherlists.get(aOtherlevel);

        System.out.println("list 1 is for " + lists.get(0).printActorkeylist());
        System.out.println("list 2 is for " + otherlists.get(0).printActorkeylist());
        for (String actor: list1.getActorkeylist()) {
            if (list2.getActorkeylist().contains(actor)) {
                aResultstrings.add("separation level is " + seplevel);
                String actor2 = actor;
                for (int i= aLevel; i>= 1; i--) {
                    actor2= reportOn(lists.get(i), aResultstrings, actor2 );
                }
                aResultstrings.add("*** on the other hand: ***");
                actor2=actor;
                for (int i= aOtherlevel; i>= 1; i--) {
                    actor2= reportOn(otherlists.get(i), aResultstrings, actor2 );
                }
                System.out.println("result  aLevel =: " + aLevel + " Otherlevel =" + aOtherlevel);
                return seplevel;
            }
        }
        return -1;
    }


}