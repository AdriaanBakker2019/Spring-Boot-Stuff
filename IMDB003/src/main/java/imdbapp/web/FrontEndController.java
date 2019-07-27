package imdbapp.web;


import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.repo.Getstring;
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
public class FilmActorFrontEndController {
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

    private String processActorInfo(Model model, String name) {

        List<Actor> actors = service.findActorsByName( name);
        model.addAttribute("actorlist", actors);

        Getstring sv = new Getstring(name);
        model.addAttribute("message", sv);
        List<Film> films;
        if (actors.size() == 1) {
            String ncode = actors.get(0).getNconst();
            Getstring svncode = new Getstring(ncode);
            films = filmService.findFilmsByActorKey(ncode);
            model.addAttribute( "films", films);
            model.addAttribute("ncode", svncode);
            String sTypecast = "error";
            try {
                sTypecast = Utils.getTypecast(films);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Getstring svTypecode = new Getstring(sTypecast);
            model.addAttribute("typecast", svTypecode);
        } else {
            films = new ArrayList<Film>();
            model.addAttribute( "films", films);
            model.addAttribute("ncode", new Getstring("unknown"));
        }
        return "actorinfo";
    }

    @GetMapping("/getactorinfo")
    public String getActorForm(Model model) {
        model.addAttribute("getstring", new Getstring("actor name here"));
        return "getactorinfo";
    }

    @PostMapping("/getactorinfo")
    public String actorSubmit(@ModelAttribute Getstring getstring, Model model) {
        return processActorInfo(model, getstring.getContent());
    }

    //@RequestMapping("/actorinfo")
    //public String listActors(@RequestParam("name") String name, Model model) {
    //    return processActorInfo(model, name);
    //}


    /*
     *   /getcoincidence
     *
     *   Get coincident films for two actors
     *   Parameters: for now, one actor name. The other actor is Kevin Bacon.
     */
    @GetMapping("/getcoincidence")
    public String getCoincidenceForm(Model model) {
        model.addAttribute("getstring", new Getstring("actor name  here"));
        return "getcoincidence";
    }


    @PostMapping("/getcoincidence")
    public String coincidenceSubmit(@ModelAttribute Getstring getstring, Model model) {
        String name1 = getstring.getContent();
        List<Actor> actor1list = service.findActorsByName(name1);
        List <Film> filmlist = new ArrayList<Film>();
        if (actor1list.size() == 1) {
            Actor actor1 = actor1list.get(0);
            String ncode1 = actor1.getNconst();
            String ncode2 = "nm0000102" ;
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
        return "filminfo";
    }


}

