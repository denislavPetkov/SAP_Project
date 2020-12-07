package sap.project.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.project.data.enteties.Stock;
import sap.project.data.repositories.StockRepository;
import sap.project.service.StockService;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> getStock() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public void saveProduct(Stock stock) {
        this.stockRepository.save(stock);
    }

    @Override
    public Stock getProductByID(long id) {
        Optional<Stock> optionalProduct = stockRepository.findById(id);
        Stock stock = null;
        if(optionalProduct.isPresent()) {
            stock = optionalProduct.get();
        }
        else {
            throw new RuntimeException(" Product not found! ");
        }
        return stock;
    }

    @Override
    public void deleteProductById(long id) {
        this.stockRepository.deleteById(id);
    }


    @Override
    public boolean updateQuantity(long id, int quantity) {
        int q = this.stockRepository.getQuantityById(id);
        if(q-quantity>=0) {
            this.stockRepository.updateQuantity(id, q-quantity);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public int getQuantityById(long id) {
        return this.stockRepository.getQuantityById(id);
    }

    @Override
    public List<Long> getIdLimitedQuantity() {
        return this.stockRepository.getIdLimitedQuantity();
    }

    @Override
    public String getDetailsById(long id) {
        return this.stockRepository.getDetailsById(id);
    }

}
