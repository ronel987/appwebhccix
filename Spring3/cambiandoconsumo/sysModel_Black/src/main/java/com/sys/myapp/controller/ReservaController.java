package com.sys.myapp.controller;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.sql.Date;
import java.util.Map;
import java.util.Optional;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ReservaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Reserva")
public class ReservaController {
	@Autowired       
    private ReservaService resService; 
	@Autowired       
    private PersonaService perService; 
	@Autowired       
    private HabitacionService habitaService; 
	@Autowired       
    private ClienteService clienteService;
	@Autowired       
    private TrabajadorService trabaService;
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
				
	@RequestMapping(value="/reserva_listar",method=RequestMethod.GET)
    public String listar_GET(Map map, Model model, HttpSession session){            
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        map.put("baraja",resService.findAll());  
        return "/Reserva/Listar";   
    }    
	
	@GetMapping("/registrar/paso-uno")
    public String getRegistrarResePasoUno(@RequestParam(value = "cliente-doc", required = false)String doc,Model model,HttpSession session) {    	              
        Persona persona=perService.findByDocum(doc);
        if (persona != null)
        	model.addAttribute("persona", persona);
        else
        	model.addAttribute("persona", new Persona());
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Reserva/Buscarcliente";
    }
	 	  
	@RequestMapping(value="/reserva_registrar",method=RequestMethod.GET)
    public String registrar_GET(@RequestParam(value="idper",required = true)Integer idper,Model model,Map map,HttpSession session){        
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}    
		if (idper>0) {
			Integer idusuario= (Integer) session.getAttribute("idpersona");
			Reserva productoModel=new Reserva();
		    Cliente ocliente = clienteService.findById(idper);
		    productoModel.setFecha_reserva(LocalDate.now());
		    productoModel.setCliente(ocliente);
		    model.addAttribute("reserva",productoModel);      
            map.put("idtrabajador", idusuario);       //si funca enviando 1 solo object al select:aca 
            map.put("obcliente", ocliente); 
            //            
            return "/Reserva/Registrar"; 
		}
		else {
			return "/Reserva/Buscarcliente";
		}			
    }    
	
	@RequestMapping(value="/reserva_registrar",method=RequestMethod.POST)
    public String registrar_POST(Reserva reserva,RedirectAttributes flash){        
		resService.insert(reserva);	  //
		Integer idper=reserva.getCliente().getIdpersona();
		flash.addFlashAttribute("success", "Reserva Guardada Exitosamente");		
		LocalDate fecha =  reserva.getFecha_ingresa();                 
		if (fecha !=null)   
		       habitaService.ocupar(reserva.getHabitacion().getIdhabitacion(), fecha);		
		
        return "redirect:/Reserva/reserva_registrar?idper="+idper;   
    }    
		
	//la url se recibe mas corta:
	@RequestMapping(value="/{reservaId}/reserva_editar",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer reservaId, Map map, HttpSession session){        
		Reserva reserva=resService.findById(reservaId); 
		//habitaService.desocupareserva(reserva.getHabitacion().getIdhabitacion(), reserva.getFecha_ingresa());
		Integer idusuario= (Integer) session.getAttribute("idpersona");
        model.addAttribute("reserva", reserva);    //envio reserva completo          
          
        map.put("bCliente", clienteService.findAll()); 
        map.put("idtrabajador", idusuario); 
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Reserva/Editar";   
    }
	@RequestMapping(value="/{reservaId}/reserva_editar",method=RequestMethod.POST)
    public String editar_POST(Reserva reserva ,@PathVariable Integer reservaId,RedirectAttributes flash){  
		reserva.setIdreserva(reservaId);
		resService.update(reserva);
		flash.addFlashAttribute("success", "Reserva Actualizada Exitosamente");
		habitaService.ocupar(reserva.getHabitacion().getIdhabitacion(), reserva.getFecha_ingresa());
        return "redirect:/Reserva/{reservaId}/reserva_editar";   
    }
	
	 //es llamado desde el listar(ojito) y desde boton imprimir pdf 
    @RequestMapping(value="/{idreserva}/reserva_detalle",method=RequestMethod.GET) 
    public String detalle_GET(Model model,@PathVariable Integer idreserva,HttpSession session){        //@Path..se usa pa Integer o cadena
    	Reserva reserguardada=resService.findById(idreserva); 
    	Habitacion bHabita = reserguardada.getHabitacion();
    	Cliente ocliente=reserguardada.getCliente();
    	Persona opersotraba= perService.findById((Integer) session.getAttribute("idpersona"));
    	model.addAttribute("aidi", idreserva);
    	model.addAttribute("idtrabajador", session.getAttribute("idpersona"));
    	model.addAttribute("persona", opersotraba);
    	model.addAttribute("bHabita",bHabita);
    	model.addAttribute("bcliente",ocliente);
        model.addAttribute("reserva", reserguardada);
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Reserva/Constancia";   
    }
	        
        
            
    
}
