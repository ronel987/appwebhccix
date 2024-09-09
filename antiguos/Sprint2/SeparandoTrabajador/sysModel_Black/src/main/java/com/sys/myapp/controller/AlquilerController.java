package com.sys.myapp.controller;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.sql.Date;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sys.myapp.modelo.Alquiler;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.servicio.AlquilerService;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ReservaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Alquiler")
public class AlquilerController {
	@Autowired       
    private ReservaService resService; 
	@Autowired       
    private HabitacionService habitaService; 
	@Autowired       
    private PersonaService perService; 
	@Autowired       
    private AlquilerService alquilerService; 
	@Autowired       
    private ClienteService clienteService;
	@Autowired       
    private TrabajadorService trabaService;
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
				
	@RequestMapping(value="/alquiler_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){               
        map.put("baraja",alquilerService.findAll());  
        return "/Alquiler/Listar";   
    }    
	 	  
	@RequestMapping(value="/{idreserva}/alquiler_registrar",method=RequestMethod.GET)
    public String registrar_GET(@PathVariable Integer idreserva, Model model,HttpSession session){     	     		
			Integer idusuario= (Integer) session.getAttribute("idpersona");		//trabajador	
		    Reserva oreserva = resService.findById(idreserva);	
		    Alquiler alquiler=new Alquiler();		
		    model.addAttribute("alquiler", alquiler);
		    model.addAttribute("oreserva", oreserva);      
            model.addAttribute("idtrabajador", idusuario);               
            return "/Alquiler/Registrar"; 				
    }    
	
	@RequestMapping(value="/{idreserva}/alquiler_registrar",method=RequestMethod.POST)
    public String registrar_POST(Alquiler alquiler){        
		alquilerService.insert(alquiler);	  
				
		LocalDate fecha =  alquiler.getFecha_ingreso();                 
		if (fecha !=null)   
		       habitaService.ocupar(alquiler.getReserva().getHabitacion().getIdhabitacion(), fecha);		
		//aca falta quitar la reserva del listado o cambiarle de estado
        return "redirect:/Alquiler/alquiler_listar";   
    }    
		
	
	@RequestMapping(value="/{alquilerId}/alquiler_editar",method=RequestMethod.GET) 
    public String editar_GET(Model model,@PathVariable Integer alquilerId, Map map, HttpSession session){        
		Alquiler alquiler= alquilerService.findById(alquilerId); 
		//habitaService.desocupareserva(reserva.getHabitacion().getIdhabitacion(), reserva.getFecha_ingresa());
		Integer idusuario= (Integer) session.getAttribute("idpersona");
        model.addAttribute("alquiler", alquiler);    //envio alquiler completo          
        map.put("bHabita",habitaService.findAll());  
        map.put("bCliente", clienteService.findAll()); 
        map.put("idtrabajador", idusuario); 
        return "/Alquiler/Editar";   
    }
	
	@RequestMapping(value="/{alquilerId}/alquiler_editar",method=RequestMethod.POST)
    public String editar_POST(Alquiler alquiler ,@PathVariable Integer alquilerId){  
		alquiler.setIdalquiler(alquilerId);
		alquilerService.update(alquiler);
	//	habitaService.ocupar(reserva.getHabitacion().getIdhabitacion(), reserva.getFecha_ingresa());
        return "redirect:/Alquiler/alquiler_listar";   
    }
	         
    
}
