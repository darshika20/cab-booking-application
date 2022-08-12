package application.repository;

import application.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

    Location findByXAndY(int x, int y);
}
