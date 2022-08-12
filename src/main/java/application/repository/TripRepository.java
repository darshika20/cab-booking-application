package application.repository;

import application.entity.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TripRepository extends CrudRepository<Trip, String> {
    List<Trip> findByRiderId(String riderId);
}
