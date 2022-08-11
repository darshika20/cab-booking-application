package application.repository;

import application.entity.Driver;
import org.springframework.data.repository.CrudRepository;

public interface DriverRepository extends CrudRepository<Driver, String> {
}
