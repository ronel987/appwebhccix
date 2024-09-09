package com.sys.myapp.controller;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sys.myapp.modelo.Alquiler;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Consumo;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Producto;
import com.sys.myapp.servicio.AlquilerService;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.ConsumoService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ProductoService;
//@Controller es un Singleton usa una instancia-solo una:carrito, que permanece durante toda la ejecucion de la App

@Controller
@RequestMapping("/Consumo")
public class ConsumoController {
	@Autowired       
    private ConsumoService conService; 	       
	@Autowired       
    private ProductoService proService; 
	@Autowired       
    private AlquilerService alquiService;
	@Autowired       
    private PersonaService perService;
	@Autowired       
    private ClienteService cliService;
	
	@RequestMapping(value="/consumo_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){        //Map permite enviar variables a una pagina web        
        map.put("baraja",conService.findAll());  
        return "/Consumo/Listar";   
    }    
	//regla: puedo vender a credito(en el sistema) solo a clientes alojados=con IdAlquiler
	@GetMapping("/registrar/paso-uno")
    public String getRegistrarConPasoUno(@RequestParam(value = "cliente-docu", required = false)String doc,Model model,RedirectAttributes atribute) {    	              
       // Optional<Cliente> cliente=cliService.findByNumeroDoc(doc);nosale
		Persona persona= perService.findByDocum(doc);
        if (persona != null) {        	
        	model.addAttribute("persona", persona);            
        }
        else
        	model.addAttribute("persona", new Persona());
        return "/Consumo/BuscarIdcliente";
    }	
	
	//debo encontrar el ultimo idalquiler dado el num_doc de un cliente(si funca con un cliente con idalquiler)
	@RequestMapping(value="/consu_registrar",method=RequestMethod.GET)
    public String registrar_GET(@RequestParam(value="idper",required = true)Integer idper, Model model,Map map,RedirectAttributes attribute){        
		        
        Integer idalquiler= alquiService.consultaridalquilercliente(idper);
        if (idalquiler != null) {
        	Consumo productoModel=new Consumo();     
            model.addAttribute("consumo",productoModel);  
            Collection <Producto> productos=proService.findAll();
            model.addAttribute("productos", productos);
        	model.addAttribute("idalquiler", idalquiler);        	
        	
            return "/Consumo/Registrar";  
        }
        else { //cuando es trabajador o el idcliente no tiene idalquiler no me deja avanzar al sigte paso
        	model.addAttribute("persona", perService.findById(idper));
        	attribute.addFlashAttribute("success", "Persona No Tiene idAlquiler!");
        	return "redirect:/Consumo/registrar/paso-uno";
        }
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
