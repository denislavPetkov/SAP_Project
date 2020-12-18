package sap.project.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sap.project.data.enteties.Client;
import sap.project.service.ClientService;
import sap.project.service.RepresentativeService;
import sap.project.service.SalesService;
import sap.project.service.UserService;
import sap.project.service.services.impl.MyUserDetails;

@Controller
@RequestMapping("/clients")
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private RepresentativeService representativeService;

    @Autowired
    private UserService userService;

    private long representativeId = 0;
    private boolean error = false;

    @GetMapping
    public ModelAndView clients(@AuthenticationPrincipal MyUserDetails user, Model model) {
        representativeId = this.representativeService.getRepIdByUserId(this.userService.getIdByUsername(user.getUsername()));
        model.addAttribute("listClients", clientService.getClientsByRepId(representativeId));
        model.addAttribute("error", error);
        error = false;

        return super.view("clients");
    }

    @GetMapping("/addToClientsForm")
    public ModelAndView addToClientsForm(Model model) {
        Client client = new Client();

        model.addAttribute("client", client);


        return super.view("new_client");
    }

    @PostMapping("/saveClient")
    public ModelAndView saveClient(Client client) {
        clientService.saveClient(client);
        clientService.updateClientRepIdById(representativeId, client.getId());
        return super.redirect("/clients");
    }

    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {

        Client client  = clientService.getClientByID(id);

        model.addAttribute("client", client);
        return super.view("update_client");
    }

    @GetMapping("/deleteClient")
    public ModelAndView deleteClient(long id) {

        if(this.salesService.getClientIds().contains(id)){
            error = true;
        }
        else {
            this.clientService.deleteClientById(id);
        }
        return super.redirect("/clients");
    }

}
