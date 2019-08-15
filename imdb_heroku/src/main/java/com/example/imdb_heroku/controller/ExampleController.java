package com.example.imdb_heroku.controller;



import com.example.imdb_heroku.model.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExampleController {
    //@Autowired
    //private DogsService dogsService;



    @GetMapping("/getmessage")
    public String greetingForm(Model model) {
        model.addAttribute("param", new Param());
        return "getmessage";
    }

    @PostMapping("/getmessage")
    public String greetingSubmit(@ModelAttribute Param param) {
        return "showmessage";
    }



}