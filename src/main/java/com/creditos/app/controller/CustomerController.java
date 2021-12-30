package com.creditos.app.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;


import com.creditos.app.models.entity.Customer;
import com.creditos.app.models.service.customer.ICustomerService;
import com.creditos.app.models.service.payment.PaymentServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clientes")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @Autowired PaymentServiceImpl paymentService;
    @RequestMapping(value ={"/listar"},method = RequestMethod.GET)
    public String index(@RequestParam Map<String,Object> params, Model model){
    //paginando registros
        int page = params.get("page") != null? Integer.valueOf(params.get("page").toString())-1 :0;
        PageRequest pageRequest =PageRequest.of(page, 10);
        Page<Customer> Pagecustomer = customerService.findAll(pageRequest);
        int totalPage = Pagecustomer.getTotalPages();
        if(totalPage>0){
            List<Integer> pages = IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
        	model.addAttribute("pages", pages);
        }
        model.addAttribute("title","Listar clientes");
        model.addAttribute("customers", Pagecustomer.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
        return "customers/index";
    }
    @RequestMapping(value ="/crear",method = RequestMethod.GET)
    public String create(Model model){
        Customer customer = new Customer();
        model.addAttribute("title","Crear cliente");
        model.addAttribute("customer",customer);
        return "customers/create";
    }
    @RequestMapping(value ="/resumen-credito/{id}",method = RequestMethod.GET)
    public String view(@PathVariable(value = "id") Long id,Model model,RedirectAttributes flash){
        Customer customer = null;
        if(id>0){
            customer = customerService.findOne(id);
            if(customer==null){
                flash.addFlashAttribute("danger","El id del cliente no existe en la base de datos.");
                return "redirect:/clientes/listar";
            }
        }
        model.addAttribute("title","Resumen de creditos");
        model.addAttribute("customer",customer);
        // model.addAttribute("payments",paymentService.findByCustomer(customer.getDni()));
        return "customers/resume";
    }

    @RequestMapping(value ="/crear",method = RequestMethod.POST)
    public String store(@Valid Customer customer, BindingResult result, RedirectAttributes flash, Model model){
        if(result.hasErrors()){
            model.addAttribute("title","Crear cliente");
            return "customers/create";
        }
        String msgFlash = (customer.getDni()!=null)?"Cliente editado con exito.":"Cliente creado con exito.";
        customer.setCreatedAt(new Date());
        customer.setActive(true);
        flash.addFlashAttribute("success",msgFlash);
        customerService.save(customer);
        return "redirect:/clientes/listar";
    }

    @RequestMapping(value = "/editar/{id}")
    String editar(@PathVariable(value = "id") Long id,Model model,RedirectAttributes flash){
        Customer customer = null;
        if(id>0){
            customer = customerService.findOne(id);
            if(customer==null){
                flash.addFlashAttribute("danger","El id del cliente no existe en la base de datos.");
                return "redirect:/clientes/listar";
            }
        }else{
            flash.addFlashAttribute("danger","El id del cliente no puede ser cero.");
            return "redirect:/clientes/listar";
        }
        model.addAttribute("title","Editar cliente");
        model.addAttribute("customer",customer);
        return "customers/create";
    }

    @RequestMapping(value = "/eliminar/{id}" )
    public String eliminar(@PathVariable(value="id") Long id,RedirectAttributes flash){
        if(id>0){
             customerService.delete(id);
             flash.addFlashAttribute("success","Cliente eliminado con exito.");
        }
            return "redirect:/clientes/listar";
    }
}
