package imdbapp.web;


import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;
import imdbapp.repo.Stringvalue;
import imdbapp.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ActorFrontEndController {
    @Autowired
    ActorService service;

    @Autowired
    FilmService filmService;


    @RequestMapping("/actorinfo")
    public String listActors(@RequestParam("name") String name, Model model) {
        List<Actor> actors = service.findActorsByName( name);
        model.addAttribute("actorlist", actors);

        Stringvalue sv = new Stringvalue(name);
        model.addAttribute("message", sv);
        List<Film> films;
        if (actors.size() == 1) {
            String ncode = actors.get(0).getNconst();
            Stringvalue svncode = new Stringvalue(ncode);
             films = filmService.findFilmsBy(ncode);
            model.addAttribute( "films", films);
            model.addAttribute("ncode", svncode);
            String sTypecast = "error";
            try {
                sTypecast = Utils.getTypecast(films);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            Stringvalue svTypecode = new Stringvalue(sTypecast);
            model.addAttribute("typecast", svTypecode);
        } else {
            films = new ArrayList<Film>();
            model.addAttribute( "films", films);
            model.addAttribute("ncode", new Stringvalue("unknown"));
        }
        return "actorinfo";
    }


    @RequestMapping("/getfilm")
    public String findFilmBy(@RequestParam("ncode") String aNcode, Model model) {
        List<Film> films = filmService.findFilmsBy(aNcode);
        Stringvalue svmessage;
        Stringvalue svncode = new Stringvalue(aNcode);

        if (films.isEmpty()) {
            svmessage = new Stringvalue(  "film list is empty");
        } else {
            svmessage = new Stringvalue(  "film " +  films.get(1).getPrimaryTitle());
        }

        model.addAttribute("message", svmessage);
        model.addAttribute( "films", films);
        model.addAttribute("ncode", svncode);

        return "filmresult";
    }



}

