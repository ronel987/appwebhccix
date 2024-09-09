package com.sys.myapp.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sys.myapp.modelo.Alquiler;
import com.sys.myapp.modelo.Consumo;
import com.sys.myapp.modelo.Pago;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.AlquilerService;
import com.sys.myapp.servicio.ConsumoService;
import com.sys.myapp.servicio.PagoService;
import com.sys.myapp.servicio.TrabajadorService;
//@Controller es un Singleton usa una instancia-solo una:carrito, que permanece durante toda la ejecucion de la App

@Controller
@RequestMapping("/Pago")
public class PagoController {
	@Autowired       
    private PagoService paService; 	
	@Autowired       
    private TrabajadorService trabaService; 
	@Autowired       
    private ConsumoService consumoService; 	
	@Autowired       
    private AlquilerService alquilerService;
	
	@RequestMapping(value="/pago_listar",method=RequestMethod.GET)
    public String listar_GET(Map map,Model model,HttpSession session){        //Map permite enviar variables a una pagina web     
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        map.put("baraja",paService.findAll());  
        return "/Pago/Listar";   
    }    
	//pagos de alojamiento
	@RequestMapping(value="/{idalquiler}/pagoaloja",method=RequestMethod.GET)
    public String registrar_GET(Model model,Map map,HttpSession session,@PathVariable Integer idalquiler){        
		Pago pagoModel=new Pago();     //vacio sus propiedades=null
		        
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
        Alquiler alquiler=alquilerService.findById(idalquiler);
        pagoModel.setFecha_pago(LocalDate.now());
		pagoModel.setMontopagado(alquiler.getCosto_alojamiento());
        
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());	
			 model.addAttribute("pago",pagoModel);  
			model.addAttribute("alquiler", alquiler);				
			model.addAttribute("idalquiler", idalquiler);
		}
        return "/Pago/PagarAloja";   
    }    
	@RequestMapping(value="/{idalquiler}/pagoaloja",method=RequestMethod.POST)
    public String registrar_POST(Pago pago,@PathVariable Integer idalquiler,RedirectAttributes flash){         
		paService.insert(pago);
		flash.addFlashAttribute("success", "Pago Guardado Exitosamente");
		alquilerService.bajardeudaaloja(pago.getMontopagado(), idalquiler);
		alquilerService.bajardeuda(pago.getMontopagado(), idalquiler);
        return "redirect:/Pago/{idalquiler}/pagoaloja";   
    }    
	
	//recepcionista: pagos de consumos:
		@RequestMapping(value="/{idconsumo}/pagar",method=RequestMethod.GET)
	    public String pagar_GET(Model model,Map map,HttpSession session,@PathVariable Integer idconsumo){        
			Pago pagoModel=new Pago();   					
	           
	        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
	        Consumo consumo=consumoService.findById(idconsumo);
	        pagoModel.setFecha_pago(LocalDate.now());
	        pagoModel.setMontopagado(consumo.getPreciototal());
	        model.addAttribute("pago",pagoModel);
	        Alquiler alquiler=consumo.getAlquiler();
	        Integer alquilerid=alquiler.getIdalquiler();
			if (trabajador.isPresent()) {			
				model.addAttribute("trabajador", trabajador.get());				
				model.addAttribute("consumo", consumo);
				model.addAttribute("alquiler", alquiler);				
				model.addAttribute("idalquiler", alquilerid);
			}
	        return "/Pago/PagarConsumo";   
	    } 
		
		@RequestMapping(value="/{idconsumo}/pagar",method=RequestMethod.POST)
	    public String pagar_POST(Model model,Pago pago,@PathVariable Integer idconsumo,HttpSession session){         
			paService.insert(pago);
			//cambia estado a pagado
			consumoService.actualizarestado(idconsumo);
			
			Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
			model.addAttribute("trabajador", trabajador.get());
			Consumo consumo=consumoService.findById(idconsumo);
			Alquiler alquiler=consumo.getAlquiler();
			Integer idalquiler=alquiler.getIdalquiler();
			//actualiza deuda:
			alquilerService.bajardeuda(pago.getMontopagado(), idalquiler);
			
	        return "redirect:/Alquiler/"+idalquiler+"/consulconsumos";   
	        
	    }    
	
	
	
	//la url se recibe mas corta:
	@RequestMapping(value="/pago_editar/{pagoId}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String editar_GET(Model model,@PathVariable Integer pagoId,Map map,HttpSession session){        
		Pago automod=paService.findById(pagoId); // esta cargado 
        model.addAttribute("pago", automod);    //envio pago completo  
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Pago/Editar";   
    }
	@RequestMapping(value="/pago_editar/{pagoId}",method=RequestMethod.POST) 
    public String editar_POST(Pago pago){  
		paService.update(pago);
        return "redirect:/pago_listar";   
    }
	     
    
    @RequestMapping(value="/pago_detalle/{idpago}",method=RequestMethod.GET) //llamado desde listar:recibe el id escogido
    public String detalle_GET(Model model,@PathVariable Integer idpago,HttpSession session){        //@Path..se usa pa Integer o cadena
    	Pago productomod=paService.findById(idpago); // esta cargado 
        model.addAttribute("pago", productomod);
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
			model.addAttribute("admin", session.getAttribute("admin"));
		}
        return "/Pago/Detalle";   
    }
	        
        
            
    
}
