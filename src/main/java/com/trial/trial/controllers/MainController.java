package com.trial.trial.controllers;
import com.trial.trial.models.Catalog;
import com.trial.trial.repo.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private CatalogRepository catalogRepository;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Catalog> catalogs = catalogRepository.findAll();
        model.addAttribute("catalogs", catalogs);
        model.addAttribute("title", "Главная страница");
        return "main";
    }
    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("title","Описание задания");
        return "about-main";
    }
    @GetMapping("/main/add")
    public String add(Model model){
        return "add";
    }

    @PostMapping("/main/add")
    public String CatalogPostAdd(@RequestParam String title,@RequestParam String anons,@RequestParam String text,@RequestParam double price, Model model){
        Catalog catalog = new Catalog(title,anons,text,price);
        catalogRepository.save(catalog);
        return "redirect:/";
    }

    @GetMapping("/main/{id}")
    public String catalogDetails(@PathVariable(value = "id") long id, Model model) {
        if (!catalogRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Catalog> catalogs = catalogRepository.findById(id);
        ArrayList<Catalog> res = new ArrayList<>();
        catalogs.ifPresent(res::add);
        model.addAttribute("catalog", res);
        return "details";
    }
    @GetMapping("/main/{id}/edit")
    public String catalogEdit(@PathVariable(value = "id") long id, Model model) {
        if (!catalogRepository.existsById(id)) {
            return "redirect:/";
        }
        Optional<Catalog> catalogs = catalogRepository.findById(id);
        ArrayList<Catalog> res = new ArrayList<>();
        catalogs.ifPresent(res::add);
        model.addAttribute("catalog", res);
        return "edit";
    }
    @PostMapping("/main/{id}/edit")
    public String CatalogPostEdit(@PathVariable(value = "id") long id,@RequestParam String title,@RequestParam String anons,@RequestParam String text,@RequestParam double price, Model model){
        Catalog catalog = catalogRepository.findById(id).orElseThrow();
        catalog.setTitle(title);
        catalog.setAnons(anons);
        catalog.setText(text);
        catalog.setPrice(price);
        catalogRepository.save(catalog);
        return "redirect:/";

}
@PostMapping("/main/{id}/remove")
    public String CatalogPostEdit(@PathVariable(value = "id") long id, Model model){
        Catalog catalog = catalogRepository.findById(id).orElseThrow();

        catalogRepository.delete(catalog);
        return "redirect:/";

}
}