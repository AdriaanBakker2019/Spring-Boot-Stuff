package imdbapp.web;

import imdbapp.service.ActorService;
import imdbapp.repo.Stringvalue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@Controller
public class ActorFrontEndController {
    @Autowired
    ActorService service;


    @RequestMapping("/list")
    public String listActors(@RequestParam("name") String name, Model model) {

        model.addAttribute("actorlist", service.findActorsByName( name));
        Stringvalue sv = new Stringvalue(name);
        model.addAttribute("message", sv);
        return "result";
    }



}

