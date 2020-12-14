package sap.project.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sap.project.data.enteties.Representative;
import sap.project.data.repositories.RepresentativeRepository;
import sap.project.data.repositories.UserRepository;
import sap.project.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RepresentativeRepository representativeRepository;

    private static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public void insertUser(Representative representative) {
        String encodedPassword = encoder.encode(representative.getPassword());
        this.userRepository.insertUser(encodedPassword, representative.getName().split(" ")[0].toLowerCase()+ "" + representative.getId());
    }

    @Override
    public void deleteUser(long id) {
        this.userRepository.deleteUser(id);
    }


    @Override
    public long getIdByUsername(String username) {
        return this.userRepository.getIdByUsername(username);
    }

    @Override
    public void updateUser(Representative representative) {
        if(!representative.getPassword().isEmpty()){
            String encodedPassword = encoder.encode(representative.getPassword());
            this.userRepository.updateUserPassword(representative.getUser().getId(),encodedPassword);
        }
    }

}
