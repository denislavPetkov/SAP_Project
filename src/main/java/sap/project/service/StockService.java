package sap.project.service;

import sap.project.data.enteties.Stock;

import java.util.List;

public interface StockService {

    List<Stock> getStock();
    void saveProduct(Stock stock);
    Stock getProductByID(long id);
    void deleteProductById(long id);

    boolean updateQuantity(long id, int quantity);
    int getQuantityById(long id);
    List<Long> getIdLimitedQuantity();
    String getDetailsById(long id);
}
