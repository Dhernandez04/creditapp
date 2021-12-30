package com.creditos.app.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.creditos.app.models.entity.Credit;
import com.creditos.app.models.entity.Payment;
import com.creditos.app.models.service.credit.ICreditService;
import com.creditos.app.models.service.payment.IPaymentService;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/pagos")
public class PaymentController {
    @Autowired
    private IPaymentService paymentService;
    @Autowired  ICreditService creditService;
    @RequestMapping(value ="/creditos/{id}",method = RequestMethod.GET)
    public @ResponseBody List<Payment> loadPayment(@PathVariable(value = "id") Long id) {
        return paymentService.findByCredit(id);
    }
 
    @RequestMapping(value ="/listar",method = RequestMethod.GET)
    public String index(@RequestParam Map<String,Object> params, Model model){
    //paginando registros
        int page = params.get("page") != null ? Integer.valueOf(params.get("page").toString())-1 :0;
        PageRequest pageRequest =PageRequest.of(page, 10);
        Page<Payment> PagePayment = paymentService.findAll(pageRequest);
        int totalPage = PagePayment.getTotalPages();
        if(totalPage>0){
            List<Integer> pages = IntStream.rangeClosed(1,totalPage).boxed().collect(Collectors.toList());
        	model.addAttribute("pages", pages);
        }
        model.addAttribute("title","Metodos de pago");
        model.addAttribute("payments", PagePayment.getContent());
		model.addAttribute("current", page + 1);
		model.addAttribute("next", page + 2);
		model.addAttribute("prev", page);
		model.addAttribute("last", totalPage);
        return "payments/index";
    }

    @RequestMapping(value ="/crear",method = RequestMethod.GET)
    public String create(Model model){
        Payment payment = new Payment();
        model.addAttribute("title","Agregar metodo de pago");
        model.addAttribute("payment",payment);
        return "payments/create";
    }

    @RequestMapping(value ="/crear",method = RequestMethod.POST)
    public String store(@Valid Payment payment, BindingResult result, RedirectAttributes flash, Model model){
      
        if(result.hasErrors()){
            model.addAttribute("title","Agregar metodo de pago");
            return "payments/create";
        }
        String msgFlash = (payment.getId()!=null)?"Metodo de pago editado con exito.":"Metodo de pago creado con exito.";
        
        flash.addFlashAttribute("success",msgFlash);
        paymentService.save(payment);
        return "redirect:/metodos-de-pago/listar";
    }

    @RequestMapping(value = "/editar/{id}")
    String editar(@PathVariable(value = "id") Long id,Model model,RedirectAttributes flash){
        Payment payment = null;
        if(id>0){
            payment = paymentService.findOne(id);
            if(payment==null){
                flash.addFlashAttribute("danger","El metodo de pago no existe en la base de datos.");
                return "redirect:/metodos-de-pago/listar";
            }
        }else{
            flash.addFlashAttribute("danger","El id del metodo de pago no puede ser cero.");
            return "redirect:/metodos-de-pago/listar";
        }
        model.addAttribute("title","Editar metodo de pago");
        model.addAttribute("payment",payment);
        return "payments/create";
    }

    @RequestMapping(value = "/eliminar/{id}" )
    public String eliminar(@PathVariable(value="id") Long id,RedirectAttributes flash){
        if(id>0){
             paymentService.delete(id);
             flash.addFlashAttribute("success","Metodo de pago eliminado con exito.");
        }
            return "redirect:/metodos-de-pago/listar";
    }
    @RequestMapping(value ="/realizar-pago/{id}",method = RequestMethod.GET)
    public @ResponseBody Map<String,String> makePayment(@PathVariable(value = "id") Long id,
                                            @RequestParam Map<String,Object> params){
            String paymentdate = params.get("paymentdate").toString();
            Map<String, String> msg = new HashMap<String, String>();
            Payment payment = null;
            Credit credit = null;                                   
            if(id>0){
                payment = paymentService.findOne(id);
                
                if(payment==null){
                    msg.put("msg", "El metodo de pago no existe en la base de datos.");
                    return msg;
                }
            }else{
                msg.put("msg", "El metodo de pago no existe en la base de datos.");
                return msg;
            }
            //creating a calendar of objects to update the payment date
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
                Date fecha = format.parse(paymentdate);   
                
                Calendar date = Calendar.getInstance();
                date.setTime(fecha);
                payment.setPaymentDate(date);
                payment.setStatus("PAGADO");
                credit = creditService.findOne(payment.getPaymentId());
                credit.setNumberOfInstallments(credit.getNumberOfInstallments()-1);
                creditService.save(credit);
                paymentService.save(payment);
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
            msg.put("msg", "Pago realizado con exito");
        return msg;
    }
}
