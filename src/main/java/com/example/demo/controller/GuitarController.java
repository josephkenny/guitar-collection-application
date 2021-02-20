package com.example.demo.controller;

import com.example.demo.entity.Guitar;
import com.example.demo.repository.GuitarRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class GuitarController {

    private GuitarRepository guitarRepository;

    public GuitarController(GuitarRepository guitarRepository) {
        this.guitarRepository = guitarRepository;
    }

    @GetMapping("/guitars")
    public String getAllGuitars(Model model) {
        model.addAttribute("guitars", guitarRepository.findAll());
        return "guitars";
    }

    @GetMapping("/guitar")
    public String showAddGuitarForm(Guitar guitar) {
        return "add-guitar";
    }

    @GetMapping("/guitars/{id}")
    public String showUpdateGuitarForm(@PathVariable Long id, Model model) {
        model.addAttribute("guitar", guitarRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid guitar ID: " + id)));
        return "update-guitar";
    }

    @PostMapping("/guitars")
    public String saveGuitar(@Valid Guitar guitar, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-guitar";
        }
        guitarRepository.save(guitar);
        model.addAttribute("guitars", guitarRepository.findAll());
        return "redirect:/guitars";
    }

    @PostMapping("/guitars/{id}")
    public String updateGuitar(@PathVariable Long id, @Valid Guitar guitar, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-guitar";
        }
        guitarRepository.save(guitar);
        model.addAttribute("guitars", guitarRepository.findAll());
        return "redirect:/guitars";
    }

    @DeleteMapping("/guitars/{id}")
    public String deleteGuitar(@PathVariable Long id, Model model) {
        guitarRepository.deleteById(id);
        model.addAttribute("guitars", guitarRepository.findAll());
        return "redirect:/guitars";
    }
}
