package sap.project.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import sap.project.data.enteties.Stock;
import sap.project.service.SalesService;
import sap.project.service.StockService;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseController{

    @Autowired
    private StockService stockService;

    @Autowired
    private SalesService salesService;

    @GetMapping
    public ModelAndView sales(Model model) {
        model.addAttribute("listStock", stockService.getStock());
        return super.view("stock");
    }

    @GetMapping("/addToStockForm")
  public ModelAndView addToStockForm(Model model) {
        Stock stock = new Stock();

        model.addAttribute("product", stock);

        return super.view("new_product");
  }

  @PostMapping("/saveProduct")
  public ModelAndView saveProduct(Stock stock) {
        stockService.saveProduct(stock);
        return super.redirect("/stock");
  }

  @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {

        Stock stock = stockService.getProductByID(id);

        model.addAttribute("product", stock);
        return super.view("update_product");
  }

  @GetMapping("/deleteProduct")
  public ModelAndView deleteProduct(long id) {

      if(this.salesService.getStockIds().contains(id)){
          return super.redirect("/stock/error");
      }
      else {
          this.stockService.deleteProductById(id);
          return super.redirect("/stock");
      }
  }

    @GetMapping("/error")
    public ModelAndView deleteProductError(Model model) {
        model.addAttribute("listStock", stockService.getStock());
        return super.view("stock_error");
    }

}
