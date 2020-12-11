package sap.project.service;

import sap.project.data.enteties.Representative;

import java.util.List;

public interface RepresentativeService {
    List<Representative> getAllRepresentatives();
    void saveRepresentative(Representative representative);
    Representative getRepresentativeByID(long id);
    void deleteRepresentativeById(long id);

    void updateUserId(long id, long userId);

    long getRepIdByUserId(long id);

    boolean isEmailInUse(String email, long id);

    boolean isPhoneInUse(String phone, long id);
}
