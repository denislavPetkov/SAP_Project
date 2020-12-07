package sap.project.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sap.project.data.enteties.Client;
import sap.project.data.enteties.Representative;
import sap.project.data.enteties.Sales;
import sap.project.data.enteties.Stock;
import sap.project.service.ClientService;
import sap.project.service.RepresentativeService;
import sap.project.service.SalesService;
import sap.project.service.StockService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sales")
public class SalesController extends BaseController{

    @Autowired
    private SalesService salesService;

    @Autowired
    private RepresentativeService representativeService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private StockService stockService;

    @Autowired
    private JavaMailSender javaMailSender;

    private boolean update = false;
    private int oldQty = 0;

    @GetMapping
    public ModelAndView sales(Model model) {
            model.addAttribute("listSales", salesService.getAllSales());
        return super.view("sales");
    }

    @GetMapping("/salesAdmin")
    public ModelAndView salesAdmin(Model model) {
        model.addAttribute("listSales", salesService.getAllSales());
        return super.view("salesAdmin");
    }

    @GetMapping("/addToSalesForm")
    public ModelAndView addToSalesForm(Model model)  {
        Sales sale = new Sales();

        List<Representative> representativeList = representativeService.getAllRepresentatives();
        List<Stock> stocksList = stockService.getStock();
        List<Client> clientList = clientService.getAllClients();


        model.addAttribute("sale", sale);
        model.addAttribute("representative", representativeList);
        model.addAttribute("stock", stocksList);
        model.addAttribute("client", clientList);

        return super.view("new_sale");
    }


    @GetMapping("/addToSalesForm/error")
    public ModelAndView addToSalesFormError(Model model)  {
        Sales sale = new Sales();

        List<Representative> representativeList = representativeService.getAllRepresentatives();
        List<Stock> stocksList = stockService.getStock();
        List<Client> clientList = clientService.getAllClients();


        model.addAttribute("sale", sale);
        model.addAttribute("representative", representativeList);
        model.addAttribute("stock", stocksList);
        model.addAttribute("client", clientList);

        return super.view("new_sale_error");
    }


    @PostMapping("/saveSale")
    public ModelAndView saveSale(Sales sale) {

        if(update){
            update = false;
            if (!stockService.updateQuantity(sale.getStock().getId(), (sale.getQuantity() - oldQty))) {
                return super.redirect("/sales/showUpdateForm/error");

            } else {
                salesService.saveSale(sale);
                //
                List<Long> IDs = stockService.getIdLimitedQuantity();
                for(Long id: IDs){
                    int quantity = stockService.getQuantityById(id);
                    if( quantity <= 6 && quantity > 0)
                        sendEmail(stockService.getDetailsById(id));
                }
                return super.redirect("/sales");
            }
        }
        else {
            if (!stockService.updateQuantity(sale.getStock().getId(), sale.getQuantity())) {
                return super.redirect("/sales/addToSalesForm/error");

            } else {
                salesService.saveSale(sale);
                //
                List<Long> IDs = stockService.getIdLimitedQuantity();
                for(Long id: IDs){
                    int quantity = stockService.getQuantityById(id);
                    if( quantity <= 6 && quantity > 0)
                    sendEmail(stockService.getDetailsById(id));
                }
                return super.redirect("/sales");
            }
        }
    }

    private void sendEmail(String details) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(clientService.getClientsEmails().toArray(new String[0]));

        String[] detailsHolder = details.split(",");

        msg.setSubject("Limited Quantity");
        msg.setText("Hello! Product {"+ detailsHolder[0] + "} in size {"+ detailsHolder[2] + "} and color {"+ detailsHolder[1] + "} has limited quantity! You better hurry if you want to add it to your collection!");

        javaMailSender.send(msg);
    }


    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {

        Sales sale = salesService.getSaleByID(id);

        List<Representative> representativeList = representativeService.getAllRepresentatives();
        List<Stock> stocksList = stockService.getStock();
        List<Client> clientList = clientService.getAllClients();

        update = true;
        oldQty = sale.getQuantity();

        int maxQ = stockService.getQuantityById(sale.getStock().getId()) + oldQty;

        List<Integer> qty = new ArrayList<Integer>();
        for(int i = 1;i<=maxQ;i++){
            qty.add(i);
        }


        model.addAttribute("sale", sale);
        model.addAttribute("qty", qty);
        model.addAttribute("representative", representativeList);
        model.addAttribute("stock", stocksList);
        model.addAttribute("client", clientList);


        return super.view("update_sales");
    }

    @GetMapping("/deleteSale")
    public ModelAndView deleteSale(long id) {

        this.salesService.deleteSaleById(id);

        return super.redirect("/sales");
    }






}
