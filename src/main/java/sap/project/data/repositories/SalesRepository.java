package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Query(value = "SELECT * FROM sales WHERE representative_id = :repId", nativeQuery = true)
    @Transactional
    List<Sales> getSalesByRepId(@Param("repId") long repId);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE sales SET representative_id = :repId WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateSalesRepIdById(@Param("id") long id, @Param("repId") long repId);
}
