package com.sys.myapp.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sys.myapp.modelo.Pago;
import com.sys.myapp.servicio.PagoService;
//@Controller es un Singleton usa una instancia-solo una:carrito, que permanece durante toda la ejecucion de la App

@RestController
public class PagoController {
	@Autowired       
    private PagoService paService; //servira para cualkier hijo q defina en la configuracion		       
	
	@RequestMapping(value="/pago_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){        //Map permite enviar variables a una pagina web        
        map.put("baraja",paService.findAll());  
        return "/Pago/Listar";   
    }    
	
	@RequestMapping(value="/pago_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map){        
		Pago productoModel=new Pago();     //vacio sus propiedades=null
        model.addAttribute("pago",productoModel);              
        return "/Pago/Registrar";   
    }    
	@RequestMapping(value="/pago_registrar",method=RequestMethod.POST)
    public String registrar_POST(Pago pago){        //habita llega cargado al servidor  
		paService.insert(pago);
        return "redirect:/pago_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/pago_editar/{pagoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer pagoId,Map map){        
		Pago automod=paService.findById(pagoId); // esta cargado 
        model.addAttribute("pago", automod);    //envio habita completo              
        return "/Pago/Editar";   
    }
	@RequestMapping(value="/pago_editar/{pagoId}",method=RequestMethod.POST) 
    public String editar_POST(Pago pago){  
		paService.update(pago);
        return "redirect:/pago_listar";   
    }
	
	@RequestMapping(value="/pago_borrar/{pagoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id dela hab escogido
    public String borrar_GET(Model model,@PathVariable Integer pagoId){        
		Pago automod=paService.findById(pagoId); //automod esta cargado con la Hab completo
        model.addAttribute("pago", automod);//"pago" va a viajar a la pag.borrar
        return "/Pago/Borrar";   
    }
    @RequestMapping(value="/pago_borrar/{pagoId}",method=RequestMethod.POST) 
    public String borrar_POST(Pago pago,@PathVariable Integer pagoId){          
         	//si esta todo ok, uso el id de la hab recibido como clave del dicc pa borrar
		paService.delete(pago.getIdpago());        
        return "redirect:/pago_listar";   
    }
    
    @RequestMapping(value="/pago_errorborrar",method=RequestMethod.GET)
    public String paerrorborrar_GET(){ 
		return "/Pago/ErrorBorrar";   
    }    
    
    @RequestMapping(value="/pago_detalle/{idpago}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String detalle_GET(Model model,@PathVariable Integer idpago){        //@Path..se usa pa Integer o cadena
    	Pago productomod=paService.findById(idpago); // esta cargado 
        model.addAttribute("pago", productomod);
        return "/Pago/Detalle";   
    }
	        
        
            
    
}
