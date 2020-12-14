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
import sap.project.service.TwitterService;
import twitter4j.TwitterException;

@Controller
@RequestMapping("/stock")
public class StockController extends BaseController{

    @Autowired
    private StockService stockService;

    @Autowired
    private SalesService salesService;

    @Autowired
    private TwitterService twitterService;

    private boolean error = false;
    private boolean updateProduct = false;

    @GetMapping
    public ModelAndView sales(Model model) {
        model.addAttribute("listStock", stockService.getStock());
        model.addAttribute("error", error);
        error = false;
        return super.view("stock");
    }

    @GetMapping("/addToStockForm")
  public ModelAndView addToStockForm(Model model) {
        updateProduct = false;
        Stock stock = new Stock();

        model.addAttribute("product", stock);

        return super.view("new_product");
  }

  @PostMapping("/saveProduct")
  public ModelAndView saveProduct(Stock stock) throws TwitterException {

        if(!updateProduct)
            twitterService.createTweet("A new product is in stock! You might want to check it out!" + "\nNew " + stock.getProduct() + " in " + stock.getColor() + " only for " + stock.getPrice() + " BGN!");

        stockService.saveProduct(stock);
        return super.redirect("/stock");
  }

  @GetMapping("/showUpdateForm")
    public ModelAndView showUpdateForm(long id, Model model) {
        updateProduct = true;
        Stock stock = stockService.getProductByID(id);

        model.addAttribute("product", stock);
        return super.view("update_product");
  }

  @GetMapping("/deleteProduct")
  public ModelAndView deleteProduct(long id) {

      if(this.salesService.getStockIds().contains(id)){
          error = true;
      }
      else {
          this.stockService.deleteProductById(id);
      }
      return super.redirect("/stock");
  }

}
