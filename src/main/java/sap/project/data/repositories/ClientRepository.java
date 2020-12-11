package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.Client;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long> {

    @Query(value = "SELECT email FROM clients", nativeQuery = true)
    @Transactional
    List<String> getEmails();

    @Query(value = "SELECT * FROM clients WHERE representative_id = :repId", nativeQuery = true)
    @Transactional
    List<Client> getClientsByRepId(@Param("repId")long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE clients SET representative_id = :repId WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateClientRepIdById(@Param("id") long id, @Param("repId") long repId);
}
