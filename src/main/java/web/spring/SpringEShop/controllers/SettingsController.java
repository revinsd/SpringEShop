package web.spring.SpringEShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import web.spring.SpringEShop.models.User;
import web.spring.SpringEShop.repo.UserRepository;
import web.spring.SpringEShop.services.FileService;
import web.spring.SpringEShop.services.UserService;

import javax.validation.Valid;
import java.io.IOException;

@Controller
public class SettingsController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    FileService fileService;

    @GetMapping("/settings")
    public String lk(Model model,@AuthenticationPrincipal User user) {
        model.addAttribute("user", user);
        model.addAttribute("title", "Настройки");
        model.addAttribute("id", user.getId());
        return "settings";
    }

    @PostMapping("/settings")
    public String edit(@Valid User user,
                       @RequestParam("oldPassword") String oldPassword,
                       @RequestParam("password") String password,
                       @RequestParam("passwordConfirm") String passwordConfirm,
                       @RequestParam("id") long id,
                       BindingResult bindingResult,
                       Model model) {
        if(!password.equals(passwordConfirm)){
            model.addAttribute("passwordError","Пароли не совпадают");
            return  "settings";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("passwordError","Произошла ошибка");
            return "settings";
        } else if(bCryptPasswordEncoder.matches(user.getOldPassword(), userRepository.findById(id).orElseThrow().getPassword())){
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userRepository.save(user);

        }else{
            model.addAttribute("passwordError","Неверный текущий пароль");
            return "settings";
        }
        model.addAttribute("confirm","Пароль успешно изменен");
        return "settings";
    }
    @PostMapping("/settings/addPhoto")
    public String addPhoto(@RequestParam("file") MultipartFile file,@RequestParam("id") long id)throws IOException {
        User user=userRepository.getById(id);
        fileService.CheckFileDirectoryAndAddFileNameForUser(user, file);
        userRepository.save(user);
        return "redirect:/settings";
    }
}
