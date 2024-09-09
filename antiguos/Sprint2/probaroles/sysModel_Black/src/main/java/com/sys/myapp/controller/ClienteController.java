package com.sys.myapp.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Cliente")
public class ClienteController {
	@Autowired       
    private ClienteService cliService; //servira para cualkier hijo q defina en la configuracion
	@Autowired       
    private PersonaService perService;
	@Autowired       
    private TrabajadorService trabaService;
     	
	@GetMapping(value="/cliente_listar")
    public String listar_GET(Map map, HttpSession session, Model model){        //Map permite enviar variables a una pagina web    
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
		}
        map.put("baraja",cliService.findAll());  
        return "/Cliente/Listar";   
    }    
	
	@GetMapping(value="/cliente_registrar")
    public String registrar_GET(Model model,Map map, HttpSession session){        
		Cliente habitaModel=new Cliente();     //vacio sus propiedades=null
        model.addAttribute("cliente",habitaModel);      
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Cliente/Registrar";   
    }    
	
	@PostMapping(value="/cliente_registrar")
    public String registrar_POST(Cliente cliente){        //cliente llega cargado(sin id) pero debo insertar 1ro una persona 
		//falta validar no dupliquen unique
		perService.insert(cliente.getPersona());
		Integer idpersona= perService.obtenerultimo();
		cliente.setIdpersona(idpersona);     //ope en memoria
		cliService.insert(cliente);
        return "redirect:/Cliente/cliente_listar";   
    }    
	
	@GetMapping(value="/cliente_error1")
    public String error1_GET(){ 
		return "/Cliente/Error1";   
    }    
	
	//la url se recibe mas corta:
	@GetMapping(value="/{idpersona}/cliente_editar") 
    public String editar_GET(Model model,@PathVariable Integer idpersona,Map map, HttpSession session){        
		Cliente automod=cliService.findById(idpersona); 
        model.addAttribute("cliente", automod);    //envio cliente completo  
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Cliente/Editar";   
    }
	@PostMapping(value="/{idpersona}/cliente_editar") 
    public String editar_POST(Cliente cliente,@PathVariable Integer idpersona){  
		Persona persona= cliente.getPersona();        //llega del html sin id
		persona.setIdpersona(idpersona);
		perService.update(persona);		
		cliService.update(cliente);
        return "redirect:/Cliente/cliente_listar";   
    }
	
    
    @GetMapping(value="/{idpersona}/cliente_detalle") 
    public String detalle_GET(Model model,@PathVariable Integer idpersona, HttpSession session){        
    	Cliente cliente=cliService.findById(idpersona);      // esta cargado 
    	model.addAttribute("clientecompleto",cliente);  
    	//
    	Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Cliente/Detalle";   
    }           
    
}
