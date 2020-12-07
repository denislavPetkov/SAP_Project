package sap.project.data.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sap.project.data.enteties.Representative;

@Repository
public interface RepresentativeRepository extends CrudRepository<Representative, Long> {
}
