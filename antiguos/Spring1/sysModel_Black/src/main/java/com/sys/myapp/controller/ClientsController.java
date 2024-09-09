package com.sys.myapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.modelo.Trabajador;
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
    public String datospersonales_GET(Model model, HttpSession session){            
		Optional <Cliente> cliente= cliService.findById2((Integer) session.getAttribute("idpersona")) ;	
		model.addAttribute("cliente", cliente);				
        return "/Clients/Misdatos";   
    }    	
	
	@PostMapping(value="/misdatos") 
    public String editar_POST(Cliente cliente,HttpSession session){  
		Persona persona= cliente.getPersona();            //cliente y persona esta en memoria sin id
		Integer idper = (Integer) session.getAttribute("idpersona");
		persona.setIdpersona(idper);
		cliente.setIdpersona(idper);
		perService.update(persona);		
		cliService.update(cliente);
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
	
    
    @GetMapping(value="/{idpersona}/reservar") 
    public String reservar_GET(@PathVariable Integer idpersona,@RequestParam(value="idper",required = true)Integer idper,HttpSession session,Model model,Map map){  
    	Integer idusuario= (Integer) session.getAttribute("idpersona");
		Reserva productoModel=new Reserva();
	    Cliente ocliente = cliService.findById(idper);
	    productoModel.setCliente(ocliente);
	    model.addAttribute("reserva",productoModel);      
        map.put("idtrabajador", idusuario);       //si funca enviando 1 solo object al select:aca 
        map.put("obcliente", ocliente); 
        
        return "/Clients/Reservar";   
    }		
    @RequestMapping(value="/{idpersona}/reservar",method=RequestMethod.POST)
    public String reservar_POST(Reserva reserva,Model model){        
		resService.insert(reserva);
		model.addAttribute("reserva",reserva);
        return "redirect:/Clients/constancia";   
    }   
    
    @GetMapping(value="/constancia") 
    public String constancia_GET(Model model){        
    	
        return "/Clients/Constancia";   
    }   
    
}
