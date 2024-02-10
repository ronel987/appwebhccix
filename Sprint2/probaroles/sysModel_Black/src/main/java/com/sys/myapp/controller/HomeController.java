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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.sys.myapp.modelo.Cliente;
import com.sys.myapp.modelo.Trabajador;
import com.sys.myapp.servicio.ClienteService;
import com.sys.myapp.servicio.PersonaService;
import com.sys.myapp.servicio.TrabajadorService;

@Controller
public class HomeController {
	
	private final Logger logger= LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
    private TrabajadorService trabaService;
	@Autowired
    private ClienteService cliService;
	@Autowired
    private PersonaService perService;
			
	@GetMapping({"/login"})
	public String goLogin(Model model) {
		model.addAttribute("titulo", "Ingresa tus credenciales");
		model.addAttribute("trabajador", new Trabajador());
		model.addAttribute("cliente", new Cliente());
		return "login";
	}
	
	@PostMapping("/login")    //llega un traba o un cliente
    public String postIngresar(Object object,Model model,HttpSession session) {        
        String path = "/login";        
        Trabajador trabajador =(Trabajador)object;
        Cliente cliente=(Cliente)object;
        Optional <Trabajador> trabaautenticado=trabaService.findByLoginyPassword(trabajador.getLogin(),trabajador.getPassword());
        Optional <Cliente> cliautenticado =cliService.findByLoginyPassword(cliente.getLogin(), cliente.getPassword());
        if (trabaautenticado.isPresent())   {        	
        	logger.info("Trabajador: {}", trabajador);
        	logger.info("Trabajador de BD:{}", trabaautenticado.get());
        	session.setAttribute("idpersona", trabaautenticado.get().getIdpersona());
        	if (trabaautenticado.get().getAcceso().equals("Administrador")) {   //para rol ADMIN
        		path="redirect:/home";
        	}
        	else {     //para  ROL recepcionista
        		
        		path = "redirect:/home"; 
        	}        	              
        }
        else if(cliautenticado.isPresent()) {
        	logger.info("Cliente: {}", cliente);
        	session.setAttribute("idpersona", cliautenticado.get().getIdpersona());        	
        	path= "redirect:/home";
        }
        else {
        	logger.info("Usuario No Existe!");
        }
        
        return path;
    }
	
	@GetMapping({"/home"})     //session viene cargada con idpersona de traba o cliente
	public String goLoHome(Model model,HttpSession session) {		
		logger.info("Session del Usuario:{}",session.getAttribute("idpersona"));
		Optional <Trabajador> trabajador= trabaService.findById((Integer) session.getAttribute("idpersona")) ;
		Optional <Cliente> cliente= cliService.findById2((Integer) session.getAttribute("idpersona")) ;
		
		if (trabajador.isPresent()) {
			
			model.addAttribute("trabajador", trabajador.get());
		}
		else if (cliente.isPresent()) {
			
			model.addAttribute("cliente", cliente.get());
		}
		
		model.addAttribute("titulo", "Bienvenido");		
		return "home";
	}
	
	//para q el cliente se registre solo, previo al logueo
	@GetMapping({"/register"})
	public String goRegister(Model model, RedirectAttributes attribute) {
		Cliente cliente=new Cliente();
		model.addAttribute("titulo", "Registrate wey");
		model.addAttribute("cliente", cliente);
		return "register";
	}
	
	@PostMapping({"/register"})
	public String guardarCliente(Model model, RedirectAttributes attribute, Cliente cliente) {
		model.addAttribute("titulo", "Registrate wey");
		perService.insert(cliente.getPersona());
		Integer idpersona= perService.obtenerultimo();
		cliente.setIdpersona(idpersona);     //ope en memoria
		cliService.insert(cliente);
		attribute.addFlashAttribute("success", "Cliente Guardado con Éxito!");
		return "redirect:/register";
	}
		
	
	@GetMapping({"/password"})
	public String goPassword(Model model) {
		model.addAttribute("titulo", "Ingresa tu Password");
		return "password";
	}
}
