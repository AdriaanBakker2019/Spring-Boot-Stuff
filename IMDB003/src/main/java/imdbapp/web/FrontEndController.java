package imdbapp.web;


import imdbapp.repo.*;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;
import imdbapp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
public class FrontEndController {
    @Autowired
    ActorService service;

    @Autowired
    FilmService filmService;

    /*
     *   /getactorinfo
     *
     *
     *   Retrieve actor information and films the actor was in
     *   Parameters input: actor name
     *   Output (model parameters):
     *           actorlist - list of actors by this name
     *
     *           in case there is only one actor with this name, the list holds only one actor:
     *           films - films by this actor
     *           message - actor name (string)
     *           typecast - typecast of actor (string)
     */

    private String processActorInfo(Model model, FindActor findactor) {
        List<Actor> actors = new ArrayList<>();

        if (findactor == null) {
            System.out.println("NO findactor!!!");
        }
       if (!findactor.getHasBirthDate().equals("Yes")) {
           System.out.println("find actor with unknown birthday");
            actors = service.findActorsByName( findactor.getName());
           System.out.println("find actor with unknown birthday found:" + actors.size());
        } else {
           System.out.println("find actor with known birthday");
            actors = service.findActorsWithKnownBirthday( findactor.getName());
           System.out.println("find actor with  birthday found:" + actors.size());
        }

        model.addAttribute("actorlist", actors);
        model.addAttribute("findactor", findactor);
        System.out.println("model attributes added");

        List<Film> films;
        if (actors.size() == 1) {
            String ncode = actors.get(0).getNconst();
            Getstring svncode = new Getstring(ncode);
            films = filmService.findFilmsByActorKey(ncode);
            model.addAttribute("films", films);
            model.addAttribute("ncode", svncode);
            String sTypecast = "error";
            try {
                sTypecast = Utils.getTypecast(films);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Getstring svTypecode = new Getstring(sTypecast);
            model.addAttribute("typecast", svTypecode);
        }
        System.out.println("going to actorinfo");
        return "actorinfo";
    }

    @GetMapping("/getactorinfo")
    public String getActorForm(Model model) {
        model.addAttribute("findactor", new FindActor("actor name here", "Yes"));
        return "getactorinfo";
    }

    @PostMapping("/getactorinfo")
    public String actorSubmit(@ModelAttribute FindActor findactor, Model model) {
        return processActorInfo(model, findactor);
    }



    /*
     *   /getcoincidence
     *
     *   Get coincident films for two actors
     *   Parameters: for now, one actor name. The other actor is Kevin Bacon.
     */
    @GetMapping("/getcoincidence")
    public String getCoincidenceForm(Model model) {
        model.addAttribute("twostrings", new TwoStrings("actor 1 name  here", "actor 2 name here"));
        return "getcoincidence";
    }


    @PostMapping("/getcoincidence")
    public String coincidenceSubmit(@ModelAttribute TwoStrings twostrings, Model model) {
        String name1 = twostrings.getString1();
        String name2 = twostrings.getString2();
        List<Actor> actor1list = service.findActorsWithKnownBirthday(name1);
        List<Actor> actor2list = service.findActorsWithKnownBirthday(name2);
        List <Film> filmlist = new ArrayList<Film>();
        if ((actor1list.size() == 1) && (actor2list.size() == 1)) {
            Actor actor1 = actor1list.get(0);
            String ncode1 = actor1.getNconst();
            Actor actor2 = actor2list.get(0);
            String ncode2 =  actor2.getNconst();
            System.out.println("checking common films between " + ncode1 + " and " + ncode2);
            filmlist = filmService.findFilmsInCommon(ncode1, ncode2);

        } else {
            System.out.println("Actor not found or more than 1 actor");
        }

        model.addAttribute( "films", filmlist);
        return "coincidenceinfo";
    }


    /*
     *   /getfilminfo
     *
     *   Get film info and crew info for a title name
     *   Parameters: film name
     */
    @GetMapping("/getfilminfo")
    public String getFilmForm(Model model) {
        model.addAttribute("getstring", new Getstring("film name here"));
        return "getfilminfo";
    }


    @PostMapping("/getfilminfo")
    public String submitFilmForm(@ModelAttribute Getstring getstring, Model model) {
        String name1 = getstring.getContent();
        List<Film> filmlist = filmService.findFilmsByPrimaryName(name1);


        Getstring svname = new Getstring(name1);
        Getstring svresult = new Getstring("film found");
        List<FilmWithCrew> fwclist = new ArrayList<>();

        if (filmlist.size() == 0) {
            svresult.setContent("geen film gevonden");
        } else if (filmlist.size() >= 1){
            svresult.setContent( filmlist.size() + " films found");


            for (int i = 0; i<= filmlist.size() - 1; i++) {
                Film film = filmlist.get(i);
                FilmWithCrew fwc = new FilmWithCrew(film);
                System.out.println("getting actorlist for film " + film.getPrimaryTitle() + ":" + film.getStartYear());
                List<String> keylist = service.findActorKeysByTitleKey(film.getTconst());
                System.out.println("keylist size is " + keylist.size());
                for (String key: keylist) {
                    Actor actor = service.findPersonByKey(key);

                    System.out.println("actor found: " + actor.getPrimaryName());
                    fwc.addActor(actor);
                }
                fwclist.add(fwc);
            }
        }



        model.addAttribute( "filmswithcrew", fwclist);
        model.addAttribute( "filmlist", filmlist);
        model.addAttribute("name", svname);
        model.addAttribute("result", svresult);
        return "filminfo";
    }


}

