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
	
	@RequestMapping(value="/consumo_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map){        
		Consumo productoModel=new Consumo();     //vacio sus propiedades=null
        model.addAttribute("consumo",productoModel);              
        return "/Consumo/Registrar";   
    }    
	@RequestMapping(value="/consumo_registrar",method=RequestMethod.POST)
    public String registrar_POST(Consumo consumo){        //habita llega cargado al servidor  
		conService.insert(consumo);
        return "redirect:/consumo_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/consumo_editar/{consumoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer consumoId,Map map){        
		Consumo automod=conService.findById(consumoId); // esta cargado 
        model.addAttribute("consumo", automod);    //envio habita completo              
        return "/Consumo/Editar";   
    }
	@RequestMapping(value="/consumo_editar/{consumoId}",method=RequestMethod.POST) 
    public String editar_POST(Consumo consumo){  
		conService.update(consumo);
        return "redirect:/consumo_listar";   
    }
	
	@RequestMapping(value="/consumo_borrar/{consumoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id dela hab escogido
    public String borrar_GET(Model model,@PathVariable Integer consumoId){        
		Consumo automod=conService.findById(consumoId); //automod esta cargado con la Hab completo
        model.addAttribute("consumo", automod);//"pago" va a viajar a la pag.borrar
        return "/Consumo/Borrar";   
    }
    @RequestMapping(value="/consumo_borrar/{consumoId}",method=RequestMethod.POST) 
    public String borrar_POST(Consumo consumo,@PathVariable Integer consumoId){          
         	//si esta todo ok, uso el id de la hab recibido como clave del dicc pa borrar
    	conService.delete(consumo.getIdconsumo());        
        return "redirect:/consumo_listar";   
    }
    
    @RequestMapping(value="/consumo_errorborrar",method=RequestMethod.GET)
    public String coerrorborrar_GET(){ 
		return "/Consumo/ErrorBorrar";   
    }    
    
    @RequestMapping(value="/consumo_detalle/{idconsumo}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String detalle_GET(Model model,@PathVariable Integer idconsumo){        //@Path..se usa pa Integer o cadena
    	Consumo productomod=conService.findById(idconsumo); // esta cargado 
        model.addAttribute("consumo", productomod);
        return "/Consumo/Detalle";   
    }
	   
    @RequestMapping(value="/consumoxreservaId/{idreserva}",method=RequestMethod.GET) 
    public String consumosxReservaId_GET(Model model,@PathVariable Integer idreserva){       
    	Collection productomod=conService.consumosxIdReserva(idreserva); // esta cargado 
        model.addAttribute("consumoxreservaid", productomod);
        return "/Consumo/Reporte";   
    }
        
            
    
}
