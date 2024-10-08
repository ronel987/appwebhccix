package com.sys.myapp.controller;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.sys.myapp.BaeldungProperties;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ReservaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Reserva")
public class ReservaController {
	@Autowired       
    private ReservaService resService; 
	@Autowired       
    private PersonaService perService; 
	@Autowired       
    private HabitacionService habitaService; 
	@Autowired       
    private ClienteService clienteService;
	@Autowired       
    private TrabajadorService trabaService;
		
				
	@RequestMapping(value="/reserva_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){        //Map permite enviar variables a una pagina web        
        map.put("baraja",resService.findAll());  
        return "/Reserva/Listar";   
    }    
	
	@GetMapping("/registrar/paso-uno")
    public String getRegistrarResePasoUno(@RequestParam(value = "cliente-doc", required = false)String doc,Model model) {    	              
        Persona persona=perService.findByDocum(doc);
        if (persona != null)
        	model.addAttribute("persona", persona);
        else
        	model.addAttribute("persona", new Persona());
        return "/Reserva/Buscarcliente";
    }
	 	  
	@RequestMapping(value="/reserva_registrar",method=RequestMethod.GET)
    public String registrar_GET(@RequestParam(value="idper",required = true)Integer idper,Model model,Map map,HttpSession session){        
		     
		if (idper>0) {
			Integer idusuario= (Integer) session.getAttribute("idpersona");
			Reserva productoModel=new Reserva();
		    Cliente ocliente = clienteService.findById(idper);
		    
		    productoModel.setCliente(ocliente);
		    model.addAttribute("reserva",productoModel);      
            map.put("idtrabajador", idusuario);       //si funca enviando 1 solo object al select:aca 
            map.put("obcliente", ocliente); 
            return "/Reserva/Registrar"; 
		}
		else {
			return "/Reserva/Buscarcliente";
		}			
    }    
	
	@RequestMapping(value="/reserva_registrar",method=RequestMethod.POST)
    public String registrar_POST(Reserva reserva){        
		resService.insert(reserva);
        return "redirect:/Reserva/reserva_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/{reservaId}/reserva_editar",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer reservaId, Map map){        
		Reserva automod=resService.findById(reservaId); 
        model.addAttribute("reserva", automod);    //envio reserva completo   
        map.put("bHabita",habitaService.findAll());  
        map.put("bCliente", clienteService.findAll()); 
        map.put("bTrabajador", trabaService.findAll()); 
        return "/Reserva/Editar";   
    }
	@RequestMapping(value="/{reservaId}/reserva_editar",method=RequestMethod.POST) 
    public String editar_POST(Reserva reserva ,@PathVariable Integer reservaId){  
		reserva.setIdreserva(reservaId);
		resService.update(reserva);
        return "redirect:/Reserva/reserva_listar";   
    }
	
	    
    @RequestMapping(value="/{idreserva}/reserva_detalle",method=RequestMethod.GET) 
    public String detalle_GET(Model model,@PathVariable Integer idreserva){        //@Path..se usa pa Integer o cadena
    	Reserva productomod=resService.findById(idreserva); 
        model.addAttribute("reserva", productomod);
        return "/Reserva/Detalle";   
    }
	        
        
            
    
}
