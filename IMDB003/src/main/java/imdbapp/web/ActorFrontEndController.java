package imdbapp.web;

import imdbapp.repo.Actor;
import imdbapp.repo.Film;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;
import imdbapp.repo.Stringvalue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Controller
public class ActorFrontEndController {
    @Autowired
    ActorService service;

    @Autowired
    FilmService filmService;


    @RequestMapping("/list")
    public String listActors(@RequestParam("name") String name, Model model) {
        List<Actor> actors = service.findActorsByName( name);
        model.addAttribute("actorlist", actors);

        Stringvalue sv = new Stringvalue(name);
        model.addAttribute("message", sv);
        return "result";
    }


    @RequestMapping("/getfilm")
    public String findFilmBy(@RequestParam("ncode") String ncode, Model model) {
        List<Film> films = filmService.findFilmsBy(ncode);
        Stringvalue svmessage;
        Stringvalue svncode = new Stringvalue(ncode);

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

