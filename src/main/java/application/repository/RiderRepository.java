package application.repository;

import application.entity.Rider;
import org.springframework.data.repository.CrudRepository;

public interface RiderRepository extends CrudRepository<Rider, String> {
}
