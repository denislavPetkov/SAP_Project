package sap.project.data.repositories;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.User;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{


    public User getUserByUsername(String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "INSERT INTO users (password,role,username)" + "VALUES (:password,'ROLE_USER',:username)", nativeQuery = true)
    @Transactional
    void insertUser(@Param("password") String password, @Param("username") String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    @Transactional
    void deleteUser(@Param("id") long id);

    @Query(value = "SELECT id FROM users WHERE username = :username", nativeQuery = true)
    @Transactional
    long getIdByUsername(@Param("username") String username);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users  SET username = :username WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateUserUsername(@Param("username") String username, @Param("id") long id);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET password = :password WHERE id = :id", nativeQuery = true)
    @Transactional
    void updateUserPassword(@Param("id") long id, @Param("password") String password);


}
