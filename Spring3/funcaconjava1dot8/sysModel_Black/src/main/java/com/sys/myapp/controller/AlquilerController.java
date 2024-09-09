package com.sys.myapp.controller;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.sql.Date;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

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

import com.sys.myapp.modelo.Alquiler;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Consumo;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Pago;
import com.sys.myapp.modelo.Persona;
import com.sys.myapp.modelo.Reserva;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.AlquilerService;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.ReservaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Alquiler")
public class AlquilerController {
	@Autowired       
    private ReservaService resService; 
	@Autowired       
    private HabitacionService habitaService; 
	@Autowired       
    private PersonaService perService; 
	@Autowired       
    private AlquilerService alquilerService; 
	@Autowired       
    private ClienteService clienteService;
	@Autowired       
    private TrabajadorService trabaService;
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
				
	@RequestMapping(value="/alquiler_listar",method=RequestMethod.GET)
    public String listar_GET(Map map, HttpSession session, Model model){        
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
		}
        map.put("baraja",alquilerService.findAll());  
        model.addAttribute("admin", session.getAttribute("admin"));
        return "/Alquiler/Listar";   
    }    
	 	  
	@RequestMapping(value="/{idreserva}/alquiler_registrar",method=RequestMethod.GET)
    public String registrar_GET(@PathVariable Integer idreserva, Model model,HttpSession session){     	     		
			Integer idusuario= (Integer) session.getAttribute("idpersona");		//trabajador	
		    Reserva oreserva = resService.findById(idreserva);	
		    Alquiler alquiler=new Alquiler();		
		    alquiler.setFecha_ingreso(LocalDate.now());
		    model.addAttribute("alquiler", alquiler);
		    model.addAttribute("oreserva", oreserva);      
            model.addAttribute("idtrabajador", idusuario);  
            //
            Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
    		if (trabajador.isPresent()) {    			
    			model.addAttribute("trabajador", trabajador.get());
    			model.addAttribute("admin", session.getAttribute("admin"));
    		}
            return "/Alquiler/Registrar"; 				
    }    
	
	@RequestMapping(value="/{idreserva}/alquiler_registrar",method=RequestMethod.POST)
    public String registrar_POST(Alquiler alquiler){        
		alquilerService.insert(alquiler);	  
				
		LocalDate fecha =  alquiler.getFecha_ingreso();                 
		if (fecha !=null)  { 
		       habitaService.ocupar(alquiler.getReserva().getHabitacion().getIdhabitacion(), fecha);		
		    // cambiarle de estado a la reserva para sea ConAlquiler en la BD
		       Integer idreserva= alquiler.getReserva().getIdreserva();
		       resService.cambiarestadoalalquilar(idreserva);
		}
        return "redirect:/Alquiler/alquiler_listar";   
    }    
		
	//solo podra cambiar manualmente fecha de salida y costo_alojamiento,el resto se calcula solo
	@RequestMapping(value="/{alquilerId}/alquiler_editar",method=RequestMethod.GET) 
    public String editar_GET(Model model,@PathVariable Integer alquilerId, Map map, HttpSession session){        
		Alquiler alquiler= alquilerService.findById(alquilerId); 		
		Integer idusuario= (Integer) session.getAttribute("idpersona");
        model.addAttribute("alquiler", alquiler);    //envio alquiler completo          
       //pa usarlo en actualiza costototal
        session.setAttribute("alquilerId", alquilerId);
        map.put("idtrabajador", idusuario); 
        //pa mostrar nombre de traba:
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Alquiler/Editar";   
    }
	
	@RequestMapping(value="/{alquilerId}/alquiler_editar",method=RequestMethod.POST)
    public String editar_POST(Alquiler alquiler ,@PathVariable Integer alquilerId){  
		alquiler.setIdalquiler(alquilerId);
		alquilerService.update(alquiler);
	
        return "redirect:/Alquiler/alquiler_listar";   
    }
	 //listar consumos de un idalquiler
	@RequestMapping(value="/{alquilerId}/consulconsumos",method=RequestMethod.GET) 
    public String conconsumos_GET(Model model,@PathVariable Integer alquilerId, Map map, HttpSession session){        
		Alquiler alquiler= alquilerService.findById(alquilerId); 			
        model.addAttribute("alquiler", alquiler);                          //envio alquiler completo          
        model.addAttribute("bConsumos", alquiler.getItemsCo());  
        double deudabaconsumos=0.0;
        Collection <Consumo> listado = alquiler.getItemsCo();
        for (Consumo  consumo: listado  ) {
    		if(consumo.getEstado().equals("NoPagado") )
    			 deudabaconsumos=deudabaconsumos+ consumo.getPreciototal();
    	}
        
        //pa mostrar nombre de traba
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {    			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("deudaBaconsumos", deudabaconsumos);
		}
        return "/Consumo/Consumosxidalquiler";   
    }
	
	@RequestMapping(value="/{alquilerId}/checkout",method=RequestMethod.GET) 
    public String checkout_GET(Model model,@PathVariable Integer alquilerId){        
		Alquiler alquiler= alquilerService.findById(alquilerId); 			
        alquiler.setEstado("Finalizado");
        alquilerService.update(alquiler);
        return "redirect:/Alquiler/alquiler_listar";   
    }
	//en registrar Alquiler para definir costototal y deuda inicial
	@GetMapping(value = "/opciones")
    public String definirCostototal(@RequestParam("costo") Double costo, Model model) {      	
        model.addAttribute("costototal", costo);   
        
        return "/Components/opciones :: opciones-costototal";    //esto is ok=retorna un combobox al metodo js         
    }
	
	//en editar Alquiler para actualizar costototal si la recepcionista cambia el costoaloja
	@GetMapping(value = "/acostototal")
	public String actualizaCostototal(@RequestParam("costoaloja") Double costoaloja, Model model,HttpSession session) {   
	   Integer idalquiler= 	(Integer) session.getAttribute("alquilerId");
	   Alquiler alquiler = alquilerService.findById(idalquiler);
	   Collection <Consumo> listado = alquiler.getItemsCo();
	   Double gconsumos=0.0;
       for (Consumo  consumo: listado  ) {
   			gconsumos=gconsumos + consumo.getPreciototal();
   	    }	   
	   Double costototal = costoaloja + gconsumos;
	   model.addAttribute("costototal", costototal);  	        
	   return "/Components/opciones :: opciones-costototal";             
	}
	//en editar Alquiler para actualizar deudatotal si la recepcionista cambia el costoaloja q cambia el costototal
		@GetMapping(value = "/adeudatotal")
		public String actualizaDeudatotal(@RequestParam("costoaloja") Double costoaloja, Model model,HttpSession session) {   
		   Integer idalquiler= 	(Integer) session.getAttribute("alquilerId");
		   Alquiler alquiler = alquilerService.findById(idalquiler);
		   Collection <Consumo> listado1 = alquiler.getItemsCo();
		   Double gconsumos=0.0;
	       for (Consumo  consumo: listado1  ) {
	   			gconsumos=gconsumos + consumo.getPreciototal();
	   	    }	   
		   Double costototal = costoaloja + gconsumos;
		   //		   
		   Collection <Pago> listado2 = alquiler.getItemsPa();
		   Double pagostotales =0.0;
	       for (Pago pago: listado2  ) {
	    	   pagostotales= pagostotales + pago.getMontopagado();
	   	    }	   
		   Double deuda = costototal - pagostotales;
		   model.addAttribute("deuda", deuda);  	        
		   return "/Components/opciones :: opciones-deuda";             
		}
		
		//en editar Alquiler para actualizar deudaaloja si la recepcionista cambia el costoaloja q cambia el costototal
				@GetMapping(value = "/adeudaloja")
				public String actualizaDeudaloja(@RequestParam("costoaloja") Double costoaloja, Model model,HttpSession session) {   
				   Integer idalquiler= 	(Integer) session.getAttribute("alquilerId");
				   Alquiler alquiler = alquilerService.findById(idalquiler);
				  
			       Collection <Pago> listado2 = alquiler.getItemsPa();
				   Double pagosaloja =0.0;
			       for (Pago pago: listado2  ) {
			    	   if (pago.getTipodepago().equals("Alojamiento"))
			    	        pagosaloja= pagosaloja + pago.getMontopagado();
			   	    }	   
				   Double deudaloja = costoaloja - pagosaloja;
				   model.addAttribute("deudaloja", deudaloja);  	        
				   return "/Components/opciones :: opciones-deudaloja";             
				}
		
		
}
