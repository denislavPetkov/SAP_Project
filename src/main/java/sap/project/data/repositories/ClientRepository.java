package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.Client;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query(value = "SELECT email FROM clients", nativeQuery = true)
    @Transactional
    List<String> getEmails();
}
