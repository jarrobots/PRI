package wmi.amu.edu.pl.pri;


import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vue/home")
public class MainController {
    @GetMapping("/hello")
    public String hello() {

        return "Hello";
    }

}
