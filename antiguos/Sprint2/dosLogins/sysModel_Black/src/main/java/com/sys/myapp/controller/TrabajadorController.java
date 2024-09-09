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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Traba")
public class TrabajadorController {
	@Autowired       
    private TrabajadorService traService; //servira para cualkier hijo q defina en la configuracion
	@Autowired       
    private PersonaService perService;
	
	     	
	@RequestMapping(value="/traba_listar",method=RequestMethod.GET)
    public String listar_GET(Map map,Model model,HttpSession session){        //Map permite enviar variables a una pagina web        
        map.put("baraja",traService.findAll());  
        Optional <Trabajador> trabajador= traService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Trabajador/Listar";   
    }    
	
	@RequestMapping(value="/traba_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map,HttpSession session){  		   
        //pal menu:
        Optional <Trabajador> trabajador= traService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
		Trabajador habitaModel=new Trabajador();     //vacio sus propiedades=null
        model.addAttribute("traba",habitaModel);   
        return "/Trabajador/Registrar";   
    }    
	
	@RequestMapping(value="/traba_registrar",method=RequestMethod.POST)
    public String registrar_POST(Trabajador trabajador){        //tra llega cargado al servidor  
				//no podes duplicar login
		ArrayList<Trabajador> listado=(ArrayList<Trabajador>) traService.findAll();
		for (Trabajador aut: listado  ) {
		if(aut.getLogin().equals(trabajador.getLogin()) )
			return "redirect:/Traba/cliente_error1";
		}
		perService.insert(trabajador.getPersona());
		Integer idpersona= perService.obtenerultimo();
		trabajador.setIdpersona(idpersona);     //ope en memoria
		traService.insert(trabajador);
        return "redirect:/Traba/traba_listar";   
    }    
	
	
	//la url se recibe mas corta:
	@RequestMapping(value="/{trabajadorId}/traba_editar",method=RequestMethod.GET) 
    public String editar_GET(Model model,@PathVariable Integer trabajadorId,Map map,HttpSession session){        
		Optional <Trabajador> automod=traService.findById(trabajadorId); // esta cargado 
        model.addAttribute("traba", automod);    
        //pal menu:traba logueado
        Optional <Trabajador> trabajador= traService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Trabajador/Editar";   
    }
	@RequestMapping(value="/{idpersona}/traba_editar",method=RequestMethod.POST) 
    public String editar_POST(Trabajador trabajador ,@PathVariable Integer idpersona){  
		ArrayList<Trabajador> listado=(ArrayList<Trabajador>) traService.findAll();
		for (Trabajador aut: listado  ) {
		if(aut.getLogin().equals(trabajador.getLogin()) )  //no duplike login
			return "redirect:/Traba/trabajador_error1";
		}	
				
		Persona persona= trabajador.getPersona();        //llega del html sin id
		persona.setIdpersona(idpersona);
		perService.update(persona);	
		traService.update(trabajador);
        return "redirect:/Traba/traba_listar";   
    }
		
    
    @RequestMapping(value="/{idtrabajador}/traba_detalle",method=RequestMethod.GET) 
    public String detalle_GET(Model model,@PathVariable Integer idtrabajador,HttpSession session){        //@Path..se usa pa Integer o cadena
    	Trabajador productomod=traService.findById2(idtrabajador); // esta cargado 
        model.addAttribute("traba", productomod);
        //pa ver quien esta logueado:
        Optional <Trabajador> trabajador= traService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Trabajador/Detalle";   
    }
	   
        
            
    
}
