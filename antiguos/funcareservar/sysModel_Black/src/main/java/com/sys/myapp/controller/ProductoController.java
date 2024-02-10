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

@RestController
public class ProductoController {
	@Autowired       
    private ProductoService proService; //servira para cualkier hijo q defina en la configuracion		       
	
	@RequestMapping(value="/producto_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){        //Map permite enviar variables a una pagina web        
        map.put("baraja",proService.findAll());  
        return "/Producto/Listar";   
    }    
	
	@RequestMapping(value="/producto_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map){        
		Producto productoModel=new Producto();     //vacio sus propiedades=null
        model.addAttribute("producto",productoModel);              
        return "/Producto/Registrar";   
    }    
	@RequestMapping(value="/producto_registrar",method=RequestMethod.POST)
    public String registrar_POST(Producto producto){        //habita llega cargado al servidor  
		proService.insert(producto);
        return "redirect:/producto_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/producto_editar/{productoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer productoId,Map map){        
		Producto automod=proService.findById(productoId); // esta cargado 
        model.addAttribute("producto", automod);    //envio habita completo              
        return "/Producto/Editar";   
    }
	@RequestMapping(value="/producto_editar/{productoId}",method=RequestMethod.POST) 
    public String editar_POST(Producto producto){  
		proService.update(producto);
        return "redirect:/producto_listar";   
    }
	
	@RequestMapping(value="/producto_borrar/{productoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id dela hab escogido
    public String borrar_GET(Model model,@PathVariable Integer productoId){        
		Producto automod=proService.findById(productoId); //automod esta cargado con la Hab completo
        model.addAttribute("producto", automod);//"producto" va a viajar a la pag.borrar
        return "/Producto/Borrar";   
    }
    @RequestMapping(value="/producto_borrar/{productoId}",method=RequestMethod.POST) 
    public String borrar_POST(Producto producto,@PathVariable Integer productoId){          
        //antes de borrar busca en el hijo Consumo para lanzar error
    	ArrayList<Integer> listado1= (ArrayList<Integer>) proService.productoID_Consumo();
		for (Integer autoid: listado1  ) {
		if (autoid==productoId)
			return "redirect:/producto_errorborrar";
		}    	
		 	//si esta todo ok, uso el id de la hab recibido como clave del dicc pa borrar
		proService.delete(producto.getIdproducto());        
        return "redirect:/producto_listar";   
    }
    
    @RequestMapping(value="/producto_errorborrar",method=RequestMethod.GET)
    public String aerrorborrar_GET(){ 
		return "/Producto/ErrorBorrar";   
    }    
    
    @RequestMapping(value="/producto_detalle/{idproducto}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String detalle_GET(Model model,@PathVariable Integer idproducto){        //@Path..se usa pa Integer o cadena
    	Producto productomod=proService.findById(idproducto); // esta cargado 
        model.addAttribute("producto", productomod);
        return "/Producto/Detalle";   
    }
	        
        
            
    
}
