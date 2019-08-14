package com.example.dbexample.controller;


import com.example.dbexample.model.DogDto;
import com.example.dbexample.model.GetString;
import com.example.dbexample.repo.Dog;
import com.example.dbexample.service.DogsService;
import com.example.dbexample.model.IdMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ExampleController {
    @Autowired
    private DogsService dogsService;

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }


    @GetMapping("/getmessage")
    public String greetingForm(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "getmessage";
    }

    @PostMapping("/getmessage")
    public String greetingSubmit(@ModelAttribute IdMessage idMessage) {
        return "showmessage";
    }

    @GetMapping("/add_dog")
    public String addDog(Model model) {
        model.addAttribute("dog", new Dog());
        return "add_dog";
    }


    @PostMapping("/add_dog")
    public String addDogSubmit(@ModelAttribute Dog dog) {
        DogDto dogdto = new DogDto();
        dogdto.setAge(dog.getAge());
        dogdto.setId(dog.getId());
        dogdto.setName(dog.getName());
        dogsService.add(dogdto);
        return "add_dog_result";
    }


    @GetMapping("/delete_dog")
    public String deleteDog(Model model) {
        model.addAttribute("idMessage", new IdMessage());
        return "delete_dog";
    }

    @PostMapping("/delete_dog")
    public String deleteSubmit(Model model, @ModelAttribute IdMessage idMessage) {
        String name = idMessage.getContent();
        idMessage.setContent("ok");
        List<Dog> doglist = dogsService.getDogsByName(name);
        Dog dog = new Dog();
        if ( doglist.size() ==0 ) {
            idMessage.setContent("dog with name not found:" + name );
        } else {
            dog = doglist.get(0);
            dogsService.delete(dog.getId());
        };

        model.addAttribute("dog", dog);
        model.addAttribute("message", idMessage);
        return "delete_dog_confirm";
    }

    @RequestMapping("/list")
    public String countsList(Model model) {
        model.addAttribute("counts", dogsService.getDogs());
        return "list";
    }

    @RequestMapping("/olddogs")
    public String countsOldList(Model model) {
        model.addAttribute("counts", dogsService.getOldDogs());
        return "list";
    }



}