package sap.project.service;

import sap.project.data.enteties.Representative;

public interface UserService {


    void insertUser(Representative representative);
    void deleteUser(long id);
    long getIdByUsername(String username);
    void updateUser(Representative representative);


}
