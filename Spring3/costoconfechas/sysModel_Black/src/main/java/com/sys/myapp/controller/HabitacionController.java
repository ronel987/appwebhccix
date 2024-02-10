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
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.ImagenService;
import com.sys.myapp.servicio.TrabajadorService;


@Controller
@RequestMapping("/Habita")
public class HabitacionController {
	private final Logger logger= LoggerFactory.getLogger(HabitacionController.class);
	
	@Autowired       
    private HabitacionService habitaService; //servira para cualkier hijo q defina en la configuracion
		
    @Autowired
    private ImagenService imagenService;   
    @Autowired
    private TrabajadorService trabaService;
    
  //final:es constante /set : no se repita
   //public final  static Set<Habitacion> listad = new HashSet<>() ; 
	
	@RequestMapping(value="/habita_listar",method=RequestMethod.GET)
    public String listar_GET(Map map,Model model,HttpSession session){        //Map permite enviar variables a una pagina web   
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        map.put("baraja",habitaService.findAll());  
        return "/Habitacion/Listar";   
    }    
	
	@RequestMapping(value="/habita_registrar",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map,HttpSession session){        
		Habitacion habitaModel=new Habitacion();     //vacio sus propiedades=null
        model.addAttribute("habita",habitaModel); 
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}        
        return "/Habitacion/Registrar";   
    }  
	
	@RequestMapping(value="/habita_registrar",method=RequestMethod.POST)
    public String registrar_POST(Habitacion habita){        //habita llega cargado al servidor  
				//no podes duplicar numero
		ArrayList<Habitacion> listado=(ArrayList<Habitacion>) habitaService.findAll();
		for (Habitacion aut: listado  ) {
		if(aut.getNumero().equals(habita.getNumero()) )
			return "redirect:/habita_error1";
		}
		habitaService.insert(habita);
        return "redirect:/Habita/habita_listar";   
    }    
	
	@RequestMapping(value="/habita_error1",method=RequestMethod.GET)
    public String error1_GET(){ 
		return "/Habitacion/Error1";   
    }    
	
	//la url se recibe mas corta:
	@RequestMapping(value="/{habitaId}/habita_editar",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer habitaId,Map map,HttpSession session){        
		Habitacion automod=habitaService.findById(habitaId); // esta cargado 
        model.addAttribute("habita", automod);    //envio habita completo 
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Habitacion/Editar";   
    }
	
	@RequestMapping(value="/{habitaId}/habita_editar",method=RequestMethod.POST) 
    public String editar_POST(Habitacion habita, @PathVariable Integer habitaId){  
		habita.setIdhabitacion(habitaId);
		ArrayList<Habitacion> listado=(ArrayList<Habitacion>) habitaService.findAll();
		Integer conta=0;
		for (Habitacion aut: listado  ) {
		     if(aut.getNumero().equals(habita.getNumero()) ) {
		    	 conta++;
		     }
		     if(conta==2) {
			      return "redirect:/habita_error1";
		     }
		}	
		
        habitaService.update(habita);
        return "redirect:/Habita/habita_listar";   
    }	
    
    @RequestMapping(value="/habita_errorborrar",method=RequestMethod.GET)
    public String aerrorborrar_GET(){ 
		return "/Habitacion/ErrorBorrar";   
    }    
    
    @RequestMapping(value="/{idhabita}/habita_detalle",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String detalle_GET(Model model,@PathVariable Integer idhabita,HttpSession session){        //@Path..se usa pa Integer o cadena
    	Habitacion productomod=habitaService.findById(idhabita); // esta cargado 
        model.addAttribute("habita", productomod);
        //
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Habitacion/Detalle";   
    }
	  
    @GetMapping(value = "/opciones")
    public String getOpcionesHabita(@RequestParam("fecha") Date fecha, Model model,HttpSession lista) {
    	
    	lista.setAttribute("listado", habitaService.habitaDisponiblesxfecha(fecha));
    	
        model.addAttribute("habitaciones", habitaService.habitaDisponiblesxfecha(fecha));   //esto is ok=pasa a un html   
        
        return "/Components/opciones :: opciones-habitadispo";    //esto is una direccion fisica        
    }
        
    //filtro al listado anterior por  piso 
    @GetMapping(value = "/fechaypiso")
    public String getOpcionesxFechayPiso(@RequestParam(value="piso") String piso,Model model,HttpSession lista) {    	
    	  
    	logger.info("Lista de Habitas Disponibles:{}", lista.getAttribute("listado"));  // pinta objetos habs
    	
		Set<Habitacion> listacompleta = (Set<Habitacion>) lista.getAttribute("listado");
    	Set <Habitacion> nuevolistado = new HashSet<>();        
             for (Habitacion aut: listacompleta  ) {
	             if(aut.getPiso().equals(piso) ) {
	    	         nuevolistado.add(aut);
	             }	    
	         }	             
    	model.addAttribute("habitaciones", nuevolistado);   //esto is ok  
        return "/Components/opciones :: opciones-habitadispo";    
    }
    
}
