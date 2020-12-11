package sap.project.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.project.data.enteties.Sales;
import sap.project.data.repositories.SalesRepository;
import sap.project.service.SalesService;

import java.util.List;
import java.util.Optional;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesRepository salesRepository;

    @Override
    public List<Sales> getAllSales() {
         return (List<Sales>) salesRepository.findAll();
    }

    @Override
    public void saveSale(Sales sale) {
        this.salesRepository.save(sale);
    }

    @Override
    public Sales getSaleByID(long id) {
        Optional<Sales> optionalSale = salesRepository.findById(id);
        Sales sale = null;
        if(optionalSale.isPresent()) {
            sale = optionalSale.get();
        }
        else {
            throw new RuntimeException(" Sale not found! ");
        }
        return sale;
    }

    @Override
    public void deleteSaleById(long id) {
        this.salesRepository.deleteById(id);
    }

    @Override
    public List<Long> getRepresentativeIds() {
        return this.salesRepository.getRepresentativeIds();
    }

    @Override
    public List<Long> getClientIds() {
        return this.salesRepository.getClientIds();
    }

    @Override
    public List<Long> getStockIds() {
        return this.salesRepository.getStockIds();
    }

    @Override
    public List<Sales> getSalesByRepId(long repId) {
        return this.salesRepository.getSalesByRepId(repId);
    }

    @Override
    public void updateSalesRepIdById(long id, long repId) {
        this.salesRepository.updateSalesRepIdById(id, repId);
    }


}
