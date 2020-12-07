package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.Stock;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE stock s SET s.quantity = :quantity WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateQuantity(@Param("id") long id, @Param("quantity") int quantity);

    @Query(value = "SELECT quantity FROM stock WHERE id = :id", nativeQuery = true)
    @Transactional
    int getQuantityById(@Param("id") long id);

    @Query(value = "SELECT id FROM stock WHERE quantity<=6", nativeQuery = true)
    @Transactional
    List<Long> getIdLimitedQuantity();

    @Query(value = "SELECT product ,color ,size FROM database66.stock where id=:id", nativeQuery = true)
    @Transactional
    String getDetailsById(@Param("id") long id);
}
