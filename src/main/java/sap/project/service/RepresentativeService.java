package sap.project.service;

import sap.project.data.enteties.Representative;

import java.util.List;

public interface RepresentativeService {
    List<Representative> getAllRepresentatives();
    void saveRepresentative(Representative representative);
    Representative getRepresentativeByID(long id);
    void deleteRepresentativeById(long id);

}
