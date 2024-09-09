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

import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
public class HomeController {
	
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
    private TrabajadorService trabaService;
	@Autowired
    private ClienteService cliService;
	
	@GetMapping({"/"})     //temporal pa entrar rapido
	public String goHome(Model model, HttpSession session) {		
		
		model.addAttribute("titulo", "Bienvenido, entraste sin Loguearte");		
		return "home";
	}
	
	@GetMapping({"/home"})     //session viene cargada
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
		model.addAttribute("cliente", new Cliente());
		return "login";
	}
	
	@PostMapping("/login")
    public String postIngresar(Trabajador trabajador,Cliente cliente,Model model,HttpSession session) {        
        String path = "/login";        
        Optional <Trabajador> trabaautenticado=trabaService.findByLoginyPassword(trabajador.getLogin(),trabajador.getPassword());
        Optional <Cliente> cliautenticado =cliService.findByLoginyPassword(cliente.getLogin(), cliente.getPassword());
        if (trabaautenticado.isPresent())   {        	
        	logger.info("Usuario: {}", trabajador);
        	logger.info("Usuario de BD:{}", trabaautenticado.get());
        	session.setAttribute("idpersona", trabaautenticado.get().getIdpersona());
        	if (trabaautenticado.get().getAcceso().equals("ADMIN")) {   //para rol ADMIN
        		path="redirect:/home";
        	}
        	else {     //para  ROL recepcionista
        		path = "redirect:/home"; 
        	}        	              
        }
        else if(cliautenticado.isPresent()) {
        	logger.info("Cliente: {}", cliente);
        	session.setAttribute("idpersona", cliautenticado.get().getIdpersona());
        	path= "redirect:/Clients/misdatos";
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
