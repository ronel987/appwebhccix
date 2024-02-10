package com.sys.myapp.controller;

import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
public class HomeController {
	
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
    private TrabajadorService trabaService;
	//public static Integer idpersona=0;
	
	@GetMapping({"/"})
	public String goHome(Model model, HttpSession session) {
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		String nombre= trabajador.get().getPersona().getNombreCompleto();
		model.addAttribute("titulo", "Bienvenido, entraste sin Loguearte");
		model.addAttribute("nombre", nombre);
		return "home";
	}
	
	@GetMapping({"/home"})
	public String goLoHome(Model model,HttpSession session) {		
		logger.info("Session del Usuario:{}",session.getAttribute("idpersona"));
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		String nombre= trabajador.get().getPersona().getNombreCompleto();
		model.addAttribute("titulo", "Bienvenido");
		model.addAttribute("nombre", nombre);
		return "home";
	}
	
	@GetMapping({"/login"})
	public String goLogin(Model model) {
		model.addAttribute("titulo", "Ingresa tus credenciales");
		model.addAttribute("trabajador", new Trabajador());
		return "login";
	}
	
	@PostMapping("/login")
    public String postIngresar(Trabajador trabajador,Model model,HttpSession session) {        
        String path = "/login";        
        Optional <Trabajador> usuarioautenticado=trabaService.findByLoginyPassword(trabajador.getLogin(),trabajador.getPassword());
        if (usuarioautenticado.isPresent())   {        	
        	logger.info("Usuario: {}", trabajador);
        	logger.info("Usuario de BD:{}",usuarioautenticado.get());
        	session.setAttribute("idpersona", usuarioautenticado.get().getIdpersona());
        	if (usuarioautenticado.get().getAcceso().equals("ADMIN")) {
        		path="redirect:/home";
        	}
        	else {
        		path = "redirect:/home"; 
        	}        	              
        }
        else {
        	logger.info("Usuario No Existe!");
        }
        return path;
    }
	
	
	@GetMapping({"/register"})
	public String goRegister(Model model) {
		model.addAttribute("titulo", "Registrate wey");
		return "register";
	}
	@GetMapping({"/password"})
	public String goPassword(Model model) {
		model.addAttribute("titulo", "Ingresa tu Password");
		return "password";
	}
}
