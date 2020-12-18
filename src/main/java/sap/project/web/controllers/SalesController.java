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
import sap.project.data.enteties.Sales;
import sap.project.data.enteties.Stock;
import sap.project.service.*;
import sap.project.service.services.impl.MyUserDetails;

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
    private UserService userService;

    @Autowired
    private EmailSender emailSender;

    private int oldQty = 0;
    private long representativeId = 0;
    private boolean error = false;

    @GetMapping
    public ModelAndView sales(@AuthenticationPrincipal MyUserDetails user,  Model model) {

            representativeId = this.representativeService.getRepIdByUserId(this.userService.getIdByUsername(user.getUsername()));
            model.addAttribute("listSales", salesService.getSalesByRepId(representativeId));

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

        List<Stock> stocksList = stockService.getStock();
        List<Client> clientList = clientService.getClientsByRepId(representativeId);


        model.addAttribute("sale", sale);
        model.addAttribute("stock", stocksList);
        model.addAttribute("client", clientList);
        model.addAttribute("error", error);

        error = false;

        return super.view("new_sale");
    }


    @PostMapping("/saveSale")
    public ModelAndView saveSale(Sales sale) {

        if(!stockService.updateQuantity(sale.getStock().getId(), (sale.getQuantity() - oldQty))){
            oldQty = 0;
            error = true;
            return super.redirect("/sales/addToSalesForm");
        }
        else {
            salesService.saveSale(sale);
                salesService.updateSalesRepIdById(sale.getId(), representativeId);
                salesService.updateSalesSoldForPrice(Double.parseDouble(stockService.getDetailsById(sale.getStock().getId()).split(",")[2]),sale.getId());
                emailSender.sendEmail();
                return super.redirect("/sales");
        }
    }



    @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {

        Sales sale = salesService.getSaleByID(id);

        List<Stock> stocksList = stockService.getStock();
        List<Client> clientList = clientService.getClientsByRepId(representativeId);

        oldQty = sale.getQuantity();

        int maxQ = stockService.getQuantityById(sale.getStock().getId()) + oldQty;

        List<Integer> qty = new ArrayList<Integer>();
        for(int i = 1;i<=maxQ;i++){
            qty.add(i);
        }

        model.addAttribute("sale", sale);
        model.addAttribute("qty", qty);
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
