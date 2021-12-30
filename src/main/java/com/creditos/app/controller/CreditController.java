package com.creditos.app.controller;

import javax.validation.Valid;

import com.creditos.app.models.entity.Credit;
import com.creditos.app.models.entity.Customer;
import com.creditos.app.models.service.credit.CreditServiceImpl;
import com.creditos.app.models.service.customer.CustomerServiceImpl;
import com.creditos.app.models.service.typesofcredit.TypesOfCreditServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/creditos")
@SessionAttributes("credit")
public class CreditController {
    
    
    @Autowired CustomerServiceImpl customerService;
    @Autowired CreditServiceImpl creditService;
    @Autowired TypesOfCreditServiceImpl typesOfCreditService;
    @GetMapping()
    public String index(Model model) {
        model.addAttribute("title","Listado de creditos");
        return "credits/index";
    }

    @GetMapping("/nuevo/{custometId}")
    public String create(@PathVariable(value="custometId") Long customerId, Model model,RedirectAttributes flash){
        Customer customer = customerService.findOne(customerId);
        if(customer == null){
            flash.addFlashAttribute("danger", "El cliente no existe en la base de datos.");
            return "redirect:/clientes/listar";
        }

        Credit credit = new Credit();
        credit.setCustomer(customer);

         model.addAttribute("title","Nuevo credito");
         model.addAttribute("credit",credit);
         model.addAttribute("typesofcredit",typesOfCreditService.findAll());
         model.addAttribute("customers",customerService.findAll());

        return "credits/create";
    }
    @PostMapping("/store")
    public String store(@Valid Credit credit,  BindingResult result, RedirectAttributes flash, Model model,SessionStatus status){
        if(result.hasErrors()){
            model.addAttribute("title","Nuevo credito");
            return "credits/create";
        }
        String msgFlash = (credit.getId()!=null)?"Credito editado con exito.":"Credito creado con exito.";
        flash.addFlashAttribute("success",msgFlash);
        creditService.save(credit);
        status.setComplete();
        return "redirect:/clientes/listar";
    }
}
