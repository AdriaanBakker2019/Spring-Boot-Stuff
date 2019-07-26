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
public class ActorFrontEndController {
    @Autowired
    ActorService service;

    @Autowired
    FilmService filmService;


    private String processActorInfo(Model model, String name) {

        List<Actor> actors = service.findActorsByName( name);
        model.addAttribute("actorlist", actors);

        Getstring sv = new Getstring(name);
        model.addAttribute("message", sv);
        List<Film> films;
        if (actors.size() == 1) {
            String ncode = actors.get(0).getNconst();
            Getstring svncode = new Getstring(ncode);
            films = filmService.findFilmsBy(ncode);
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
        //return "result";
    }


    @RequestMapping("/actorinfo")
    public String listActors(@RequestParam("name") String name, Model model) {

        return processActorInfo(model, name);

/*        List<Actor> actors = service.findActorsByName( name);
        model.addAttribute("actorlist", actors);

        Getstring sv = new Getstring(name);
        model.addAttribute("message", sv);
        List<Film> films;
        if (actors.size() == 1) {
            String ncode = actors.get(0).getNconst();
            Getstring svncode = new Getstring(ncode);
             films = filmService.findFilmsBy(ncode);
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
  */
    }


    @RequestMapping("/getfilm")
    public String findFilmBy(@RequestParam("ncode") String aNcode, Model model) {
        List<Film> films = filmService.findFilmsBy(aNcode);
        Getstring svmessage;
        Getstring svncode = new Getstring(aNcode);

        if (films.isEmpty()) {
            svmessage = new Getstring(  "film list is empty");
        } else {
            svmessage = new Getstring(  "film " +  films.get(1).getPrimaryTitle());
        }

        model.addAttribute("message", svmessage);
        model.addAttribute( "films", films);
        model.addAttribute("ncode", svncode);

        return "filmresult";
    }



}

