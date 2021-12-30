package com.creditos.app.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value="error",required=false) String error, Model model,Principal principal,RedirectAttributes flahs){
        if(principal != null){
            flahs.addFlashAttribute("info", "Ya has iniciado sesion anteriormente.");
            return "redirect:/clientes/listar";
        }
        if(error != null){
            model.addAttribute("danger","Usuario o contraseña incorrectos, por favor vuelva a intentarlo!");
        }
    return "auth/login";
    }
}
