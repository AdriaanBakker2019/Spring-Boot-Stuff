package com.example.imdb_heroku.controller;

import com.example.imdb_heroku.model.Param;
import com.example.imdb_heroku.repo.Actor;
import com.example.imdb_heroku.service.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ActorController {
    @Autowired
    private ActorService actorService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/getactorinfo")
    public String getActorInfo(Model model) {
        model.addAttribute("param", new Param());
        return "getactorinfo";
    }

    @PostMapping("/getactorinfo")
    public String getActorInfoSubmit(@ModelAttribute Param param, Model model) {
        List<Actor> actors = new ArrayList<>();


            System.out.println("find actor " + param.getContent());
            actors = actorService.findByName(param.getContent());
            System.out.println("find actor list length:" + actors.size());


        model.addAttribute("actorlist", actors);
        model.addAttribute("findactor", param);


        return "showactorinfo";
    }



}
