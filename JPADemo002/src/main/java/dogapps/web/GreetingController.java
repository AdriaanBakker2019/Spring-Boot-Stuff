package dogapps.web;

import dogapps.model.DogDto;
import dogapps.repo.Dog;
import dogapps.service.DogsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {
    @Autowired
    DogsService service;


    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("dog", new Dog());
        return "dog";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Dog dog) {
        DogDto dogdto = new DogDto();
        dogdto.setAge(dog.getAge());
        dogdto.setId(dog.getId());
        dogdto.setName(dog.getName());
        service.add(dogdto);
        return "result";
    }

}
