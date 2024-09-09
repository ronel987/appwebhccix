package com.sys.myapp.controller;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.AlquilerService;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.ConsumoService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ProductoService;
import com.sys.myapp.servicio.TrabajadorService;
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
	@Autowired       
    private TrabajadorService trabaService;
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/consumo_listar",method=RequestMethod.GET)
    public String listar_GET(Map map,HttpSession session,Model model){        //Map permite enviar variables a una pagina web 
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
		}
        map.put("baraja",conService.findAll());
        model.addAttribute("admin", session.getAttribute("admin"));
        
        return "/Consumo/Listar";   
    }    
	//regla: puedo vender a credito(en el sistema) solo a clientes alojados=con IdAlquiler
	@GetMapping("/registrar/paso-uno")
    public String getRegistrarConPasoUno(@RequestParam(value = "cliente-doc", required = false)String doc,Model model,RedirectAttributes atribute,HttpSession session) {    	              
       
		Collection <String> listanumdocus=alquiService.obtenerlistanumdocs();		
       
		Persona persona= perService.findByDocum(doc);
        if (persona != null) {        	
        	model.addAttribute("persona", persona);            
        }
        else {
        	model.addAttribute("persona", new Persona());
        	model.addAttribute("numdocus", listanumdocus);
        }
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Consumo/BuscarIdcliente";
    }	
	
	//debo encontrar el ultimo idalquiler dado el num_doc de un cliente(si funca con un cliente con idalquiler)
	@RequestMapping(value="/consu_registrar",method=RequestMethod.GET)
    public String registrar_GET(@RequestParam(value="idper",required = true)Integer idper, Model model,Map map,RedirectAttributes attribute,HttpSession session){        
		        
        Integer idalquiler= alquiService.consultaridalquilercliente(idper);
        if (idalquiler != null) {
        	Consumo productoModel=new Consumo();     
            model.addAttribute("consumo",productoModel);  
            Collection <Producto> productos=proService.findAll();
            model.addAttribute("productos", productos);
        	model.addAttribute("idalquiler", idalquiler);        	
        	//
        	Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
    		if (trabajador.isPresent()) {			
    			model.addAttribute("trabajador", trabajador.get());
    			model.addAttribute("admin", session.getAttribute("admin"));
    		}
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
		Integer idalquiler=consumo.getAlquiler().getIdalquiler();
		Double preciototal=consumo.getPreciototal();
		alquiService.subircostotxconsumo(preciototal,idalquiler);
		alquiService.subirdeudaconsumo(preciototal, idalquiler);
        return "redirect:/Consumo/consumo_listar";   
    }    
		
		   
    @RequestMapping(value="/consumoxalquilerId/{idalquiler}",method=RequestMethod.GET) 
    public String consumosxAlquilerId_GET(Model model,@PathVariable Integer idreserva,HttpSession session){       
    	Collection productomod=conService.consumosxIdAlquiler(idreserva); // esta cargado 
    	//
    	Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        model.addAttribute("consumoxreservaid", productomod);
        
        return "/Consumo/Reporte";   
    }
    //Llamado desde JS:calcular precio total: primero enviar idproducto
    @RequestMapping(value="/enviaidproducto}",method=RequestMethod.GET) 
    public String enviaIdproducto_GET(@RequestParam("idproducto") Integer idproducto,HttpSession pasarvalor){       
    	logger.info("IdProducto:{}", idproducto); 
    	
    	Producto producto= proService.findById(idproducto);
    	Double precio= producto.getPrecio_venta();
    	
    	pasarvalor.setAttribute("precioventa", 2.5) ;  
    	logger.info("Idproducto: ", idproducto);
        return "/Consumo/Registrar";   //en JS nadie recibe esto: no se hace
    }
    
    //luego hago calculo
    @RequestMapping(value="/preciototal}",method=RequestMethod.GET) 
    public String preciototal_GET(Model model,@RequestParam("cantidad")Integer cantidad,HttpSession pasarvalor){       
    	logger.info("Cantidad:{}", cantidad); 
    	Double precio= (Double) pasarvalor.getAttribute("precioventa");       	
    	Double total= cantidad * precio;
    	
        model.addAttribute("totalprecio", total);
        
        return "/Components/opciones::opciones-preciototal";       //dir fisica
    }
            
    
}
