package sap.project.service.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sap.project.data.enteties.Representative;
import sap.project.data.repositories.RepresentativeRepository;
import sap.project.service.RepresentativeService;

import java.util.List;
import java.util.Optional;

@Service
public class RepresentativeServiceImpl implements RepresentativeService {

    @Autowired
    private RepresentativeRepository representativeRepository;

    private Representative representative;

    @Override
    public List<Representative> getAllRepresentatives() {
        return (List<Representative>) representativeRepository.findAll();
    }

    @Override
    public void saveRepresentative(Representative representative) {
        this.representativeRepository.save(representative);
    }

    @Override
    public Representative getRepresentativeByID(long id) {
        Optional<Representative> optionalRepresentative = representativeRepository.findById(id);
        Representative representative = null;
        if(optionalRepresentative.isPresent()) {
            representative = optionalRepresentative.get();
        }
        else {
            throw new RuntimeException(" Representative not found! ");
        }
        return representative;
    }

    @Override
    public void deleteRepresentativeById(long id) {
        this.representativeRepository.deleteById(id);
    }

}
