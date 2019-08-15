package com.example.imdb_heroku.controller;

import com.example.imdb_heroku.model.Param;
import com.example.imdb_heroku.repo.Actor;
import com.example.imdb_heroku.model.Request;
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
        model.addAttribute("request", new Request());
        return "getactorinfo";
    }

    @PostMapping("/getactorinfo")
    public String getActorInfoSubmit(@ModelAttribute Request request, Model model) {
        List<Actor> actors = new ArrayList<>();


            System.out.println("find actor " + request.getName());
            actors = actorService.findByName(request.getName());
            System.out.println("find actor list length:" + actors.size());


        model.addAttribute("actorlist", actors);
        model.addAttribute("findactor", request);


        return "showactorinfo";
    }



}
