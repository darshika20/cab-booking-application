package application.repository;

import application.entity.Cab;
import org.springframework.data.repository.CrudRepository;

public interface CabRepository extends CrudRepository<Cab, String> {
}
