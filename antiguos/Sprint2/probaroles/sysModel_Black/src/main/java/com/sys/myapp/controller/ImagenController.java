package com.sys.myapp.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sys.myapp.modelo.Habitacion;
import com.sys.myapp.modelo.Imagen;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.HabitacionService;
import com.sys.myapp.servicio.ImagenService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
@RequestMapping("/Imagen")
public class ImagenController {
	@Autowired
    private ImagenService imagenService;
	@Autowired
    private TrabajadorService trabaService;
	@Autowired       
    private HabitacionService habitaService;
    
    @RequestMapping(value="/imagen_listar",method=RequestMethod.GET)
    public String listar_GET(Map map,HttpSession session,Model model){
        map.put("bImagenes",imagenService.findAll());
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Imagen/Listar";
    }
    
    @RequestMapping(value="/imagen_registrar",method = RequestMethod.GET)
    public String registrar_GET(Model model,Map map,HttpSession session){
        model.addAttribute("imagen",new Imagen());
        map.put("bHabita",habitaService.findAll());  //se envia la lista de habita al combobox
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Imagen/Registrar";
    }
    @RequestMapping(value="/imagen_registrar",method = RequestMethod.POST)//@RequestPart para atrapar el archivo
    public String registrar_POST(@RequestPart("picture")MultipartFile picture,Imagen imagen) throws IOException
    {   //picture es el name de la etiketa input
        imagen.setNombre(picture.getOriginalFilename());
        imagen.setFile(picture.getBytes());
        imagenService.insert(imagen);  //guarda en BD
        return "redirect:/Imagen/imagen_listar";
    }
    
    @RequestMapping(value="/{imagenId}/imagen_editar",method=RequestMethod.GET)
    public String editar_GET(Model model,@PathVariable Integer imagenId, Map map,HttpSession session){
        
        Imagen imaModel=imagenService.findById(imagenId);    //  imaModel esta cargado
        model.addAttribute("imagen", imaModel);
        map.put("bHabita", habitaService.findAll());
        //"imagen" viaja cargada a la pagina "editar"
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Imagen/Editar";
    }
    
    @RequestMapping(value="/{imagenId}/imagen_editar",method=RequestMethod.POST)
    public String editar_POST(@RequestPart("picture")MultipartFile picture,Imagen imagen) throws IOException
    {     
        imagen.setNombre(picture.getOriginalFilename());
        imagen.setFile(picture.getBytes());
        imagenService.update(imagen);  
        return "redirect:/Imagen/imagen_listar";
    }
    
    @RequestMapping(value="/{imagenId}/imagen_borrar",method=RequestMethod.GET)
    public String borrar_GET(Model model,@PathVariable Integer imagenId,HttpSession session){
       
        Imagen imaModel=imagenService.findById(imagenId);    
        model.addAttribute("imagen", imaModel);  //imaModel lo pongo en un modelo
        Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		if (trabajador.isPresent()) {			
			model.addAttribute("trabajador", trabajador.get());
		}
        return "/Imagen/Borrar";   
    }
    
    @RequestMapping(value="/{imagenId}/imagen_borrar",method=RequestMethod.POST)
    public String borrar_POST(Imagen imagen,@PathVariable Integer imagenId){      //llega al servidor la imagen cargada
            	
    	//se usa el servicio borrar
        imagenService.delete(imagen.getImagenId());
        //el post ya proceso los datos:borro
        return "redirect:/Imagen/imagen_listar";
    }
            
    
}
