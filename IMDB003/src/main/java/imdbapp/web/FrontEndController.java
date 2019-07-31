package imdbapp.web;


import imdbapp.repo.*;
import imdbapp.service.ActorService;
import imdbapp.service.FilmService;
import imdbapp.utils.CalculateSeparation;
import imdbapp.utils.Resultstrings;
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
    ActorService actorService;

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
            actors = actorService.findActorsByName( findactor.getName());
           System.out.println("find actor with unknown birthday found:" + actors.size());
        } else {
           System.out.println("find actor with known birthday");
            actors = actorService.findActorsWithKnownBirthday( findactor.getName());
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

            List<FilmWithCrew> fwclist = actorService.generateFWClist(films);
            model.addAttribute("filmswithcrew", fwclist);
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
     *   Get coincident movies and tv shows for two actors
     *   Parameters: two actor names
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
        List<Actor> actor1list = actorService.findActorsWithKnownBirthday(name1);
        List<Actor> actor2list = actorService.findActorsWithKnownBirthday(name2);
        List <Film> filmlist = new ArrayList<Film>();
        String result = "ok";
        if ((actor1list.size() == 1) && (actor2list.size() == 1)) {
            Actor actor1 = actor1list.get(0);
            String ncode1 = actor1.getNconst();
            Actor actor2 = actor2list.get(0);
            String ncode2 =  actor2.getNconst();
            System.out.println("checking common films between " + ncode1 + " and " + ncode2);
            filmlist = filmService.findFilmsInCommon(ncode1, ncode2);

        } else {
            if (actor1list.size()==0)
                result = ("actor " + name1 + " not found");
            else if (actor2list.size()==0)
               result = ("actor " + name2 + " not found");
            else if (actor1list.size()>1)
                result = ("actor " + name1 + " has more than one occurrence");
            else if (actor1list.size()>1)
                result = ("actor " + name2 + " has more than one occurrence");

        }
        model.addAttribute("result", new Getstring(result));
        model.addAttribute( "twostrings", twostrings);
        model.addAttribute( "films", filmlist);
        return "coincidenceinfo";
    }


    /*
     *   /getfilminfo
     *
     *   Get movie info and crew info for a title name
     *   Parameters: movie name
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
            fwclist = actorService.generateFWClist(filmlist);
        }



        model.addAttribute( "filmswithcrew", fwclist);
        model.addAttribute( "filmlist", filmlist);
        model.addAttribute("name", svname);
        model.addAttribute("result", svresult);
        return "filminfo";
    }



    @GetMapping("/getseparation")
    public String getSeparation(Model model) {
        model.addAttribute("twostrings", new TwoStrings("actor 1 name  here", "actor 2 name here"));
        return "getseparation";
    }

    @PostMapping("/getseparation")
    public String getSeparationSubmit(@ModelAttribute TwoStrings twostrings, Model model) {
        String name1 = twostrings.getString1();
        String name2 = twostrings.getString2();
        List<Actor> actor1list = actorService.findActorsWithKnownBirthday(name1);
        List<Actor> actor2list = actorService.findActorsWithKnownBirthday(name2);
        Getstring errormessage = new Getstring("ok");

        Resultstrings result = new Resultstrings();

        if ((actor1list.size() == 1) && (actor2list.size() == 1)) {
            String actor1key = actor1list.get(0).getNconst();
            String actor2key = actor2list.get(0).getNconst();
            CalculateSeparation cs = new CalculateSeparation(actor1key, actor2key, actorService, filmService);
            cs.checkSeparation(actor1key, actor2key, result);


        } else {
            if (actor1list.size()==0)
               errormessage.setContent("actor " + name1 + " not found");
            else if (actor2list.size()==0)
                errormessage.setContent("actor " + name2 + " not found");
            else if (actor1list.size()>1)
                errormessage.setContent("actor " + name1 + " has more than one occurrence");
            else if (actor1list.size()>1)
                errormessage.setContent("actor " + name2 + " has more than one occurrence");
        }
        model.addAttribute("errormessage", errormessage);
        model.addAttribute("result", result);
        return "separationresult";
    }

}

