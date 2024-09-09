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
import com.sys.myapp.modelo.Pago;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.PagoService;
import com.sys.myapp.servicio.TrabajadorService;
//@Controller es un Singleton usa una instancia-solo una:carrito, que permanece durante toda la ejecucion de la App

@Controller
@RequestMapping("/Pago")
public class PagoController {
	@Autowired       
    private PagoService paService; 	
	@Autowired       
    private TrabajadorService trabaService; 
	
	@RequestMapping(value="/pago_listar",method=RequestMethod.GET)
    public String listar_GET(Map map,Model model,HttpSession session){        //Map permite enviar variables a una pagina web     
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        map.put("baraja",paService.findAll());  
        return "/Pago/Listar";   
    }    
	
	@RequestMapping(value="/pago_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map,HttpSession session){        
		Pago productoModel=new Pago();     //vacio sus propiedades=null
        model.addAttribute("pago",productoModel);   
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Pago/Registrar";   
    }    
	@RequestMapping(value="/pago_registrar",method=RequestMethod.POST)
    public String registrar_POST(Pago pago){         
		paService.insert(pago);
        return "redirect:/pago_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/pago_editar/{pagoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer pagoId,Map map,HttpSession session){        
		Pago automod=paService.findById(pagoId); // esta cargado 
        model.addAttribute("pago", automod);    //envio pago completo  
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Pago/Editar";   
    }
	@RequestMapping(value="/pago_editar/{pagoId}",method=RequestMethod.POST) 
    public String editar_POST(Pago pago){  
		paService.update(pago);
        return "redirect:/pago_listar";   
    }
	     
    
    @RequestMapping(value="/pago_detalle/{idpago}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String detalle_GET(Model model,@PathVariable Integer idpago,HttpSession session){        //@Path..se usa pa Integer o cadena
    	Pago productomod=paService.findById(idpago); // esta cargado 
        model.addAttribute("pago", productomod);
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Pago/Detalle";   
    }
	        
        
            
    
}
