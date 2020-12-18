package sap.project.service;

import sap.project.data.enteties.Sales;

import java.util.List;

public interface SalesService {
    List<Sales> getAllSales();
    void saveSale(Sales sale);
    Sales getSaleByID(long id);
    void deleteSaleById(long id);
    List<Long> getRepresentativeIds();
    List<Long> getClientIds();
    List<Long> getStockIds();
    List<Sales> getSalesByRepId(long repId);
    void updateSalesRepIdById(long id, long repId);
    void updateSalesSoldForPrice(double price, long id);

}
