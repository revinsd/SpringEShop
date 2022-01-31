package web.spring.SpringEShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import web.spring.SpringEShop.models.Item;
import web.spring.SpringEShop.repo.ItemRepository;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class CatalogController {
    @Autowired
    private ItemRepository itemRepository;
    @GetMapping("/catalog")
    public String catalog(Model model){
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        model.addAttribute("title","Каталог");
        return "catalog";
    }

    @GetMapping("/catalog/{id}")
    public String catalogItem(@PathVariable(value="id") long id, Model model){
        if(!itemRepository.existsById(id)) return "redirect:/catalog";
        Optional<Item> item = itemRepository.findById(id);
        ArrayList<Item> res = new ArrayList<>();
        item.ifPresent(res::add);
        model.addAttribute("item", res);
        model.addAttribute("title", res.get(0).getName());
        return "itemCard";
    }
}
