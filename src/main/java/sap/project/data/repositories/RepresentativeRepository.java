package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.Representative;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface RepresentativeRepository extends CrudRepository<Representative, Long> {

    @Query(value = "SELECT id FROM representatives WHERE email=:email AND representative_name=:name AND phone=:phone", nativeQuery = true)
    @Transactional
    long getRepId(@Param("email") String email, @Param("name") String name,@Param("phone") String phone);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE  representatives SET user_id = :userId WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateUserId(@Param("id") long id, @Param("userId") long userId);


    @Query(value = "SELECT id FROM representatives WHERE user_id = :userId", nativeQuery = true)
    @Transactional
    long getRepIdByUserId(@Param("userId") long id);

    @Query(value = "SELECT id FROM representatives WHERE email = :email AND NOT id = :id", nativeQuery = true)
    @Transactional
    List<Long> getRepIdByEmail(@Param("email") String email, @Param("id") long id);

    @Query(value = "SELECT id FROM representatives WHERE phone = :phone AND NOT id = :id", nativeQuery = true)
    @Transactional
    List<Long> getRepIdByPhone(@Param("phone") String phone, @Param("id") long id);
}
