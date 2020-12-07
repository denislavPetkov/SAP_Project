package sap.project.web.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sap.project.data.enteties.Representative;
import sap.project.service.RepresentativeService;
import sap.project.service.SalesService;

@Controller
@RequestMapping("/representatives")
public class RepresentativeController extends BaseController{

    @Autowired
    private RepresentativeService representativeService;

    @Autowired
    private SalesService salesService;


    @GetMapping
    public ModelAndView representatives(Model model) {
        model.addAttribute("listRepresentatives", representativeService.getAllRepresentatives());
        return super.view("representatives");
    }

    @GetMapping("/addToRepresentativeForm")
    public ModelAndView addToRepresentativeForm(Model model) {
        Representative representative = new Representative();

        model.addAttribute("representative", representative);

        return super.view("new_representative");
    }

    @PostMapping("/saveRepresentative")
    public ModelAndView saveRepresentative(Representative representative) {
        representativeService.saveRepresentative(representative);
        return super.redirect("/representatives");
    }
//
    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {

        Representative representative = representativeService.getRepresentativeByID(id);

        model.addAttribute("representative", representative);
        return super.view("update_representative");
    }


    @GetMapping("/deleteRepresentative")
    public ModelAndView deleteRepresentative(long id) {

        if(this.salesService.getRepresentativeIds().contains(id)){
            return super.redirect("/representatives/error");
        }
        else {
            this.representativeService.deleteRepresentativeById(id);
            return super.redirect("/representatives");
        }
    }

    @GetMapping("/error")
    public ModelAndView deleteRepresentativeError(Model model) {
        model.addAttribute("listRepresentatives", representativeService.getAllRepresentatives());
        return super.view("representatives_error");
    }


}
