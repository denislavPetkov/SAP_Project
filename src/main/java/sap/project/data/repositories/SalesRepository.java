package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.Sales;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SalesRepository extends CrudRepository<Sales, Long> {

    @Query(value = "SELECT representative_id FROM sales ", nativeQuery = true)
    @Transactional
    List<Long> getRepresentativeIds();

    @Query(value = "SELECT client_id FROM sales ", nativeQuery = true)
    @Transactional
    List<Long> getClientIds();

    @Query(value = "SELECT stock_id FROM sales ", nativeQuery = true)
    @Transactional
    List<Long> getStockIds();
}
