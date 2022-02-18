package web.spring.SpringEShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import web.spring.SpringEShop.models.Item;
import web.spring.SpringEShop.repo.ItemRepository;
import web.spring.SpringEShop.services.FileService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class CatalogController {
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private FileService fileService;


    @GetMapping("/catalog")
    public String catalog(Model model){
        Iterable<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        model.addAttribute("title","Каталог");
        return "catalog";
    }


    //TODO add personal item cards
//    @GetMapping("/catalog/{id}")
//    public String catalogItem(@PathVariable(value="id") long id, Model model){
//        if(!itemRepository.existsById(id)) return "redirect:/catalog";
//        Optional<Item> item = itemRepository.findById(id);
//        ArrayList<Item> res = new ArrayList<>();
//        item.ifPresent(res::add);`
//        model.addAttribute("item", res);
//        model.addAttribute("title", res.get(0).getName());
//        return "itemCard";
//    }

    @GetMapping("/catalog/add")
    public String catalogAdd(Model model) {

        model.addAttribute("item", new Item());
        model.addAttribute("title", "Добавление товара");
        return "catalog-add";
    }

    @PostMapping("/catalog/add")
    public String addTovar(@Valid Item item,
                           BindingResult bindingResult,
                           Model model,
                           @RequestParam("file") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            return "catalog-add";
        } else {
            fileService.CheckFileDirectoryAndAddFileName(item, file);
            itemRepository.save(item);
        }
        return "redirect:/catalog";
    }



    @GetMapping("/catalog/{id}/edit")
    public String catalogEdit(@PathVariable(value = "id") long id, Model model) {
        Item tov = itemRepository.findById(id).orElseThrow();
        model.addAttribute("item", tov);
        model.addAttribute("title", "Изменение товара");
        return "catalog-edit";
    }

    @PostMapping("/catalog/{id}/edit")
    public String edit(@Valid Item item, BindingResult bindingResult, Model model,
                       @RequestParam("file") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            return "catalog-edit";
        } else {
            fileService.CheckFileDirectoryAndAddFileName(item, file);
            itemRepository.save(item);
        }
        return "redirect:/catalog";
    }

    @PostMapping("/catalog/{id}/delete")
    public String catalogDelete(@PathVariable(value = "id") long id, Model model) {
        Item item = itemRepository.findById(id).orElseThrow();
        itemRepository.delete(item);
        return "redirect:/catalog";
    }

    @PostMapping("/catalog")
    public String catalogDelete(@RequestParam("search") String search, Model model) {
        List<Item> items = itemRepository.findByNameContainingIgnoreCase(search);
        model.addAttribute("items",items);
        return "catalog";
    }
}
