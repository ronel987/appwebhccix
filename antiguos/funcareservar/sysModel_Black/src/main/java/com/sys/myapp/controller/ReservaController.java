package com.sys.myapp.controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.ReservaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Reserva")
public class ReservaController {
	@Autowired       
    private ReservaService resService; 
	
	@Autowired       
    private HabitacionService habitaService; 
	@Autowired       
    private ClienteService clienteService;
	@Autowired       
    private TrabajadorService trabaService;
	//@Value("${}")  
	private Trabajador bTrabajador;	
	
	@RequestMapping(value="/reserva_listar",method=RequestMethod.GET)
    public String listar_GET(Map map){        //Map permite enviar variables a una pagina web        
        map.put("baraja",resService.findAll());  
        return "/Reserva/Listar";   
    }    
	
	@RequestMapping(value="/reserva_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map){        
		Reserva productoModel=new Reserva();     
        model.addAttribute("reserva",productoModel);   
        map.put("bHabita",habitaService.findAll());  
        map.put("bCliente", clienteService.findAll()); 
        map.put("bTrabajador", trabaService.findAll()); 
        return "/Reserva/Registrar";   
    }    
	@RequestMapping(value="/reserva_registrar",method=RequestMethod.POST)
    public String registrar_POST(Reserva reserva){        
		resService.insert(reserva);
        return "redirect:/Reserva/reserva_listar";   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/{reservaId}/reserva_editar",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer reservaId, Map map){        
		Reserva automod=resService.findById(reservaId); 
        model.addAttribute("reserva", automod);    //envio reserva completo   
        map.put("bHabita",habitaService.findAll());  
        map.put("bCliente", clienteService.findAll()); 
        map.put("bTrabajador", trabaService.findAll()); 
        return "/Reserva/Editar";   
    }
	@RequestMapping(value="/{reservaId}/reserva_editar",method=RequestMethod.POST) 
    public String editar_POST(Reserva reserva ,@PathVariable Integer reservaId){  
		reserva.setIdreserva(reservaId);
		resService.update(reserva);
        return "redirect:/Reserva/reserva_listar";   
    }
	
	    
    @RequestMapping(value="/{idreserva}/reserva_detalle",method=RequestMethod.GET) 
    public String detalle_GET(Model model,@PathVariable Integer idreserva){        //@Path..se usa pa Integer o cadena
    	Reserva productomod=resService.findById(idreserva); 
        model.addAttribute("reserva", productomod);
        return "/Reserva/Detalle";   
    }
	        
        
            
    
}
