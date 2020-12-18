package sap.project.service;

import sap.project.data.enteties.Representative;

import java.util.List;

public interface UserService {


    void insertUser(Representative representative);
    void deleteUser(long id);
    long getIdByUsername(String username);
    void updateUser(Representative representative);
    List<String> getEmailFromAdminRole();


}
