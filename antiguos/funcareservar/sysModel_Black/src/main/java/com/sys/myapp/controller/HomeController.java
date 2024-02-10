package com.sys.myapp.controller;

import org.apache.tomcat.util.http.parser.Vary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
public class HomeController {
	
	@Autowired
    private TrabajadorService trabaService;
	
	@GetMapping({"/"})
	public String goHome(Model model) {
		model.addAttribute("titulo", "Bienvenido, entraste sin Loguearte");
		return "home";
	}
	
	@GetMapping({"/home"})
	public String goLoHome(Model model) {
		model.addAttribute("titulo", "Bienvenido");
		return "home";
	}
	
	@GetMapping({"/login"})
	public String goLogin(Model model) {
		model.addAttribute("titulo", "Ingresa tus credenciales");
		model.addAttribute("trabajador", new Trabajador());
		return "login";
	}
	
	@PostMapping("/login")
    public String postIngresar(Trabajador trabajador,Model model) {        
        String path = "/login";        
        Trabajador usuarioautenticado=trabaService.findByLoginyPassword(trabajador.getLogin(),trabajador.getPassword());
        if (usuarioautenticado != null)   {
        	model.addAttribute("idpersona", usuarioautenticado.getIdpersona());
             path = "redirect:/home";               
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
