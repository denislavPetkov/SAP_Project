package sap.project.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{


    public User getUserByUsername(String username);



}
