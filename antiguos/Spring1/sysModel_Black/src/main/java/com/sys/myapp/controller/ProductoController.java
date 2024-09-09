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
import com.sys.myapp.modelo.Producto;
import com.sys.myapp.servicio.ProductoService;
//@Controller es un Singleton usa una instancia-solo una:carrito, que permanece durante toda la ejecucion de la App

@Controller
@RequestMapping("/Producto")
public class ProductoController {
	@Autowired       
    private ProductoService proService; 		       
	
	@RequestMapping(value="/pro_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){              
        map.put("baraja",proService.findAll());  
        return "/Producto/Listar";   
    }    
	
	@RequestMapping(value="/pro_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map){        
		Producto productoModel=new Producto();     //vacio sus propiedades=null
        model.addAttribute("producto",productoModel);              
        return "/Producto/Registrar";   
    }    
	@RequestMapping(value="/pro_registrar",method=RequestMethod.POST)
    public String registrar_POST(Producto producto){        //habita llega cargado al servidor  
		proService.insert(producto);
        return "redirect:/Producto/pro_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/{productoId}/pro_editar",method=RequestMethod.GET) 
    public String editar_GET(Model model,@PathVariable Integer productoId,Map map){        
		Producto automod=proService.findById(productoId); 
        model.addAttribute("producto", automod);                 
        return "/Producto/Editar";   
    }
	@RequestMapping(value="/{productoId}/pro_editar",method=RequestMethod.POST) 
    public String editar_POST(Producto producto,@PathVariable Integer productoId){  
		producto.setIdproducto(productoId);
		proService.update(producto);
        return "redirect:/Producto/pro_listar";   
    }
	
	@RequestMapping(value="/{productoId}/pro_borrar",method=RequestMethod.GET) //llamado desde listar:recibe el id dela hab escogido
    public String borrar_GET(Model model,@PathVariable Integer productoId){        
		Producto automod=proService.findById(productoId); //automod esta cargado 
        model.addAttribute("producto", automod);//"producto" va a viajar a la pag.borrar
        return "/Producto/Eliminar";   
    }
    @RequestMapping(value="/{productoId}/pro_borrar",method=RequestMethod.POST) 
    public String borrar_POST(Producto producto,@PathVariable Integer productoId){          
        //antes de borrar busca en el hijo Consumo para lanzar error
    	ArrayList<Integer> listado1= (ArrayList<Integer>) proService.productoID_Consumo();
		for (Integer autoid: listado1  ) {
		        if (autoid==productoId)
			return "redirect:/producto_errorborrar";
		}    	
		 	//si esta todo ok, uso el id de la hab recibido como clave del dicc pa borrar
		proService.delete(productoId);        
        return "redirect:/Producto/pro_listar";   
    }
    
    @RequestMapping(value="/producto_errorborrar",method=RequestMethod.GET)
    public String aerrorborrar_GET(){ 
		return "/Producto/ErrorBorrar";   
    }    
          
            
    
}
