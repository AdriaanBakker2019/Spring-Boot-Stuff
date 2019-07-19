package dogapps.web;

import dogapps.repo.Dog;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("dog", new Dog());
        return "dog";
    }

    @PostMapping("/greeting")
    public String greetingSubmit(@ModelAttribute Dog dog) {
        return "result";
    }

}
