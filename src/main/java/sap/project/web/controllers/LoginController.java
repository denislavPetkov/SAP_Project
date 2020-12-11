package sap.project.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends BaseController{

    private boolean user = false;
    private boolean admin = false;

    @GetMapping("/login")
    public ModelAndView login(){
        return super.view("login");
    }

    @GetMapping("/homeUser")
    public ModelAndView homeUser() {
        user = true;
        admin = false;
        return super.view("homeUser");
    }

    @GetMapping("/homeAdmin")
    public ModelAndView homeAdmin() {
        user = false;
        admin = true;
        return super.view("homeAdmin");
    }

    @GetMapping("/home")
    public ModelAndView home() {
        if(admin){
           return super.redirect("homeAdmin");
        }
        else if(user){
           return super.redirect("homeUser");
        }
       return null;
    }

}
