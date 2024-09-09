package com.sys.myapp.controller;
import java.util.ArrayList;
import java.util.Collection;
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
import com.sys.myapp.modelo.Consumo;
import com.sys.myapp.servicio.ConsumoService;
//@Controller es un Singleton usa una instancia-solo una:carrito, que permanece durante toda la ejecucion de la App

@Controller
@RequestMapping("/Consumo")
public class ConsumoController {
	@Autowired       
    private ConsumoService conService; 	       
	
	@RequestMapping(value="/consumo_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){        //Map permite enviar variables a una pagina web        
        map.put("baraja",conService.findAll());  
        return "/Consumo/Listar";   
    }    
	
	@RequestMapping(value="/consu_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map){        
		Consumo productoModel=new Consumo();     //vacio sus propiedades=null
        model.addAttribute("consumo",productoModel);              
        return "/Consumo/Registrar";   
    }    
	@RequestMapping(value="/consu_registrar",method=RequestMethod.POST)
    public String registrar_POST(Consumo consumo){        //habita llega cargado al servidor  
		conService.insert(consumo);
        return "redirect:/Consumo/consumo_listar";   
    }    
		
		   
    @RequestMapping(value="/consumoxalquilerId/{idalquiler}",method=RequestMethod.GET) 
    public String consumosxReservaId_GET(Model model,@PathVariable Integer idreserva){       
    	Collection productomod=conService.consumosxIdAlquiler(idreserva); // esta cargado 
        model.addAttribute("consumoxreservaid", productomod);
        return "/Consumo/Reporte";   
    }
        
            
    
}
