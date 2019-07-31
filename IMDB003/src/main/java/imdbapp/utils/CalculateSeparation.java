package imdbapp.utils;

import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.repo.FilmActor;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class CalculateSeparation {


    private final String actor1key;
    private final String actor2key;

    private final ActorService actorService;
    private final FilmService filmService;


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


    /*
     *    Calculate the first degree of separation of actor
     *    Parameter: aActorkey
     */
    private Separationlist calcFirstSeparation(String aActorkey) {
        Separationlist sep1 = new Separationlist();

        List<String> filmkeys = filmService.findFilmKeysByActorKey(aActorkey);

        for (String filmkey: filmkeys) {
            List <String> actorkeys2 = actorService.findActorKeysBy(filmkey);
            for (String akey: actorkeys2) {
                FilmActor fa = new FilmActor(filmkey, akey);
                sep1.addFilmActor(fa, false);
            }
        }

        return sep1;

    }

    /*
     *
     * Given a separationlist, generate the list corresponding to the next level of separation.
     *
     * for now, the previous separation lists are not yet checked so it is possible that previously found
     * actors and films occur in more than one separationlist.
     */

    private Separationlist calcNextSeparation(Separationlist aSeparationlist) {
        Separationlist resultlist = new Separationlist();
        for (String ak: aSeparationlist.getActorkeylist()) {
            Separationlist sl = calcFirstSeparation(ak);
            for (FilmActor fa: sl.filmActorList) {
                resultlist.addFilmActor(fa, true);
            }
        }
        return resultlist;
    }

/*
*    addLastInfo
*    Log info about the common film title between actor 1 and actor 2
*
*   aActor2Key was found in a separationlist aList of actor aActor
*   Retrieve a title in aList for aActor2. This title has aActor1 in its crew of actors.
*
* */
    public void addLastInfo(Separationlist aList, Resultstrings aResult, String aActor1Key, String aActor2Key) {
        String filmkey = aList.getFilmkeyByActorkey(aActor2Key);
        Film film = filmService.findFilmByTconst(filmkey);
        String filmtitle = film.getPrimaryTitle();

        Actor actor1 = actorService.findPersonByKey(aActor1Key);
        Actor actor2 = actorService.findPersonByKey(aActor2Key);
        aResult.add(actor1.getPrimaryName() + " was in " + filmtitle + " (" + film.getStartYear() + "," +
                 film.getTitleType() + ")" +
                " with " + actor2.getPrimaryName());
    }


    private Actor checkOverlap(Separationlist sep1, Separationlist sep1b) {
        // check overlap in actors between sep1a and sep1b


        HashSet<String> actorkeys = sep1.getActorkeylist();
        Iterator<String> myIterator = actorkeys.iterator();
        Boolean found = false;
        Actor actor = null;
        while (myIterator.hasNext() && !found) {
            String nextkey = myIterator.next();
            found = sep1b.getActorkeylist().contains(nextkey);
            if (found) {
                actor = actorService.findPersonByKey(nextkey);

            }
        }
        return actor;
    }



    /*
    *    checkSeparation
    *       Checks the degree of separation between the two actors.
    *
    */
    public int checkSeparation(String aActor1key, String aActor2key, Resultstrings result) {

        Actor actor1 = actorService.findPersonByKey(aActor1key);
        Actor actor2 = actorService.findPersonByKey(aActor2key);

        //result.add("Separation between " + actor1.getPrimaryName() + " and " + actor2.getPrimaryName() + ":");

        Separationlist sep1 = calcFirstSeparation(aActor1key);
        //result.add("   actorkeylist of sep1 has length " + sep1.getActorkeylist().size() + " key is " + aActor1key);

        if (sep1.getActorkeylist().contains(aActor2key)) {

            result.add("Degree of separation is 1");
            this.addLastInfo(sep1, result, aActor1key, aActor2key);
            return 1;

        } else {

            Separationlist sep1b = calcFirstSeparation(aActor2key);

            Actor actor = checkOverlap(sep1, sep1b);
            if (actor != null) {
                result.add("Degree of separation is 2");
                result.add("first degree lists have actor " + actor.getPrimaryName() + " in common");
                this.addLastInfo(sep1, result, aActor1key, actor.getNconst());
                this.addLastInfo(sep1b, result, aActor2key, actor.getNconst());
                return 2;
            } else {

                Separationlist sep2 = calcNextSeparation(sep1);
                actor = checkOverlap(sep1b, sep2);

                if (actor != null) {
                    result.add("Degree of separation is 3");
                    result.add("lists have actor " + actor.getPrimaryName() + " in common");

                    this.addLastInfo(sep1b, result, aActor2key, actor.getNconst());

                    // actor actor1 and this actor have degree of separation 2


                    return 1 + checkSeparation(aActor1key, actor.getNconst(), result);

                } else {
                    result.add("Degree of separation is greater than 3");
                    return -1;
                }

            }
        }

    }




}
