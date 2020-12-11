package sap.project.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sap.project.data.enteties.Representative;
import sap.project.service.RepresentativeService;
import sap.project.service.SalesService;
import sap.project.service.UserService;

@Controller
@RequestMapping("/representatives")
public class RepresentativeController extends BaseController{

    @Autowired
    private RepresentativeService representativeService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private UserService userService;

    private boolean isEmailInUse = false;
    private boolean isPhoneInUse = false;
    private boolean error = false;


    @GetMapping
    public ModelAndView representatives(Model model) {
        model.addAttribute("listRepresentatives", representativeService.getAllRepresentatives());
        model.addAttribute("error", error);
        error = false;
        return super.view("representatives");
    }

    @GetMapping("/addToRepresentativeForm")
    public ModelAndView addToRepresentativeForm(Model model) {
        Representative representative = new Representative();

        model.addAttribute("representative", representative);
        model.addAttribute("email", isEmailInUse);
        model.addAttribute("phone", isPhoneInUse);
        isEmailInUse = false;
        isPhoneInUse = false;

        return super.view("new_representative");
    }

    @PostMapping("/saveRepresentative")
    public ModelAndView saveRepresentative(Representative representative) {

        isEmailInUse = representativeService.isEmailInUse(representative.getEmail(), 0);
        isPhoneInUse = representativeService.isPhoneInUse(representative.getPhone(), 0);

        if(isEmailInUse || isPhoneInUse) {
            return super.redirect("/representatives/addToRepresentativeForm");
        }

        representativeService.saveRepresentative(representative);
        userService.insertUser(representative);
        representativeService.updateUserId(representative.getId(),userService.getIdByUsername(representative.getName().split(" ")[0].toLowerCase()+""+representative.getId()));


        return super.redirect("/representatives");
    }


    @PostMapping("/updateRepresentative")
    public ModelAndView updateRepresentative(Representative representative, RedirectAttributes redirectAttrs) {

        isEmailInUse = representativeService.isEmailInUse(representative.getEmail(), representative.getId());
        isPhoneInUse = representativeService.isPhoneInUse(representative.getPhone(), representative.getId());

        if(isEmailInUse || isPhoneInUse) {
            redirectAttrs.addAttribute("ID", representative.getId());
            return super.redirect("/representatives/showUpdateForm?id={ID}");
        }

        representativeService.saveRepresentative(representative);
        userService.updateUser(representative);

        return super.redirect("/representatives");
    }


    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {

        Representative representative = representativeService.getRepresentativeByID(id);

        model.addAttribute("representative", representative);
        model.addAttribute("email", isEmailInUse);
        model.addAttribute("phone", isPhoneInUse);
        isEmailInUse = false;
        isPhoneInUse = false;

        return super.view("update_representative");
    }


    @GetMapping("/deleteRepresentative")
    public ModelAndView deleteRepresentative(long id) {

        if(this.salesService.getRepresentativeIds().contains(id)){
            error = true;
        }
        else {
            this.userService.deleteUser(this.representativeService.getRepresentativeByID(id).getUser().getId());
        }
        return super.redirect("/representatives");
    }



}
