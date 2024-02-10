package com.sys.myapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ReservaService;

@Controller
@RequestMapping("/Clients")
public class ClientsController {
	@Autowired       
    private ClienteService cliService; 
	@Autowired       
    private PersonaService perService;
	@Autowired       
    private HabitacionService habitaService;
	@Autowired       
    private ReservaService resService;
     	
	@GetMapping(value="/misdatos")
    public String datospersonales_GET(Model model, HttpSession session,RedirectAttributes atribute){          
		Optional <Cliente> cliente= cliService.findById2((Integer) session.getAttribute("idpersona")) ;	
		model.addAttribute("cliente", cliente);				
        return "/Clients/Misdatos";   
    }    	
	//RedirectAtributes:para mandar mensajes flash
	@PostMapping(value="/misdatos") 
    public String editar_POST(Cliente cliente,HttpSession session,RedirectAttributes attribute){  
		Persona persona= cliente.getPersona();            //cliente y persona esta en memoria sin id
		Integer idper = (Integer) session.getAttribute("idpersona");
		persona.setIdpersona(idper);
		cliente.setIdpersona(idper);
		perService.update(persona);		
		cliService.update(cliente);
		attribute.addFlashAttribute("success", "Cliente actualizado con exito!");
        return "redirect:/Clients/misdatos";   
    }		
	
	@GetMapping(value="/habitaciones") 
    public String habitaciones_GET(Model model){        
    	model.addAttribute("baraja", habitaService.findAll());
        return "/Clients/Habitaciones";   
    }   
	
	@GetMapping(value="/{idhabitacion}/detalle") 
    public String detalles_GET(Model model,@PathVariable Integer idhabitacion ){        
    	Habitacion habita=habitaService.findById(idhabitacion);
    	model.addAttribute("habitacion", habita);
        return "/Clients/Detalle";   
    }   	
	
    
    @GetMapping(value="/reservar") 
    public String reservar_GET(HttpSession session,Model model,Map map){  
    	Integer idusuario= (Integer) session.getAttribute("idpersona");
		Reserva reservacia=new Reserva();
	    Cliente ocliente = cliService.findById(idusuario);
	    reservacia.setCliente(ocliente);
	    model.addAttribute("reserva", reservacia);      
        map.put("idtrabajador", idusuario);       //si funca enviando 1 solo object al select:aca 
        map.put("obcliente", ocliente); 
        
        return "/Clients/Reservar";   
    }		
    @PostMapping(value="/reservar")
    public String reservar_POST(Reserva reserva,Model model){        
		resService.insert(reserva);
		Integer ultimo= resService.obtenerultimo();
		Reserva reser= resService.findById(ultimo);
		model.addAttribute("reserva",reser);
        return "redirect:/Clients/constancia";   
    }  
    
    @GetMapping(value = "/Clients/costo")
    public String getCostodeAlojamiento(@RequestParam("fecha") LocalDate fechasalida, Model model) {    	
    	
    	Double costo=120.5*2;
        model.addAttribute("costos", costo);   //esto is ok=pasa a un html   
        
        return "/Components/opciones :: costo-alojamiento";           
    }
    
    @GetMapping(value="/constancia") 
    public String constancia_GET(Model model){        
    	
        return "/Clients/Constancia";   
    }   
    
}
