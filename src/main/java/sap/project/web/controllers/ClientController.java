package sap.project.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sap.project.data.enteties.Client;
import sap.project.service.ClientService;
import sap.project.service.SalesService;

@Controller
@RequestMapping("/clients")
public class ClientController extends BaseController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private SalesService salesService;

    @GetMapping
    public ModelAndView clients(Model model) {
        model.addAttribute("listClients", clientService.getAllClients());
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
            return super.redirect("/clients/error");
        }
        else {
            this.clientService.deleteClientById(id);
            return super.redirect("/clients");
        }
    }

    @GetMapping("/error")
    public ModelAndView deleteClientError(Model model) {
        model.addAttribute("listClients", clientService.getAllClients());
        return super.view("clients_error");
    }

}
