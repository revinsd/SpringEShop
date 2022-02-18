package web.spring.SpringEShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import web.spring.SpringEShop.models.Item;
import web.spring.SpringEShop.models.User;
import web.spring.SpringEShop.repo.UserRepository;
import web.spring.SpringEShop.services.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class LkController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/lk")
    public String lk(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("title", "Личный кабинет");
        return "lk";
    }

    @PostMapping("/lk")
    public String edit(@Valid User user,
                       @RequestParam("oldPassword") String oldPassword,
                       @RequestParam("password") String password,
                       @RequestParam("passwordConfirm") String passwordConfirm,
                       @RequestParam("id") long id,
                       BindingResult bindingResult,
                       Model model) {
        if(!password.equals(passwordConfirm)){
            model.addAttribute("passwordError","Пароли не совпадают");
            return  "lk";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordError","Произошла ошибка");
            return "lk";
        } else if(bCryptPasswordEncoder.matches(user.getOldPassword(), userRepository.findById(id).orElseThrow().getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);

        }else{
            model.addAttribute("passwordError","Неверный текущий пароль");
            return "lk";
        }
        model.addAttribute("confirm","Пароль успешно изменен");
        return "lk";
    }
}
