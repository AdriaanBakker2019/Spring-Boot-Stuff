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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DogsFrontEndController {
    @Autowired
    DogsService service;


    @GetMapping("/adddog")
    public String greetingForm(Model model) {
        model.addAttribute("dog", new Dog());
        return "add_dog";
    }

    @PostMapping("/adddog")
    public String greetingSubmit(@ModelAttribute Dog dog) {
        DogDto dogdto = new DogDto();
        dogdto.setAge(dog.getAge());
        dogdto.setId(dog.getId());
        dogdto.setName(dog.getName());
        service.add(dogdto);
        return "result";
    }


    @RequestMapping("/list")
    public String countsList(Model model) {
        model.addAttribute("counts", service.getDogs());
        return "list";
    }

    @RequestMapping("/olddogs")
    public String countsOldList(Model model) {
        model.addAttribute("counts", service.getOldDogs());
        return "list";
    }


}
