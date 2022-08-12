package application.Strategy;

import application.dto.Driver;
import application.dto.TripRequest;
import application.entity.Cab;
import application.entity.Location;
import application.mapper.DriverMapper;
import application.repository.CabRepository;
import application.repository.DriverRepository;
import application.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AllotNearestDriverStrategy implements DriverAllotmentStrategy{

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CabRepository cabRepository;

    @Autowired
    LocationRepository locationRepository;

    @Override
    public Driver allotDriver(TripRequest tripRequest) {
        double distance = Double.MAX_VALUE;
        Driver allotedDriver = null;
        int sourcex = tripRequest.getSourceXCoordinate();
        int sourcey = tripRequest.getSourceYCoordinate();
        for (application.entity.Driver driver : driverRepository.findAll()) {
            if (driver.isAvailable()) {
                Optional<Cab> cab = cabRepository.findById(driver.getCabId());
                Optional<Location> location = locationRepository.findById(cab.get().getLocationId());
                int driverx = location.get().getX();
                int drivery = location.get().getY();
                double temporaryDistance = Math.sqrt(Math.pow(driverx-sourcex, 2) + Math.pow(drivery - sourcey, 2));
                if (temporaryDistance < distance) {
                    distance = temporaryDistance;
                    allotedDriver = DriverMapper.entityToDto(driver);
                }
            }
        }
        return allotedDriver;
    }
}
