package web.spring.SpringEShop.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.spring.SpringEShop.models.User;

@Controller
public class MainController {
    @GetMapping("/")
    public String main(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("title","ESHOP");
        model.addAttribute("user", user);
        return "home";
    }

}
