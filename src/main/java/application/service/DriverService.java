package application.service;

import application.Strategy.AllotNearestDriverStrategy;
import application.Strategy.DriverAllotmentStrategy;
import application.dto.*;
import application.mapper.CabMapper;
import application.mapper.DriverMapper;
import application.mapper.LocationMapper;
import application.mapper.TripMapper;
import application.repository.CabRepository;
import application.repository.DriverRepository;
import application.repository.LocationRepository;
import application.repository.TripRepository;
import application.utility.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    CabRepository cabRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    AllotNearestDriverStrategy allotNearestDriverStrategy;

    public Driver registerDriver(Driver driver) {

        if (!validate(driver)) {
            throw new RuntimeException("Validation failed for driver details");
        }
        if (driverRepository.existsById(driver.getEmail())) {
            throw new RuntimeException("This email is already registered as a driver in application");
        } else if (cabRepository.existsById(driver.getCabId())){
            throw new RuntimeException("This cab is already registered with a driver in application");
        } else {
            driverRepository.save(DriverMapper.dtoToEntity(driver));
            application.entity.Location location = locationRepository.findByXAndY(0, 0);
            if (location == null) {
                location = application.entity.Location.builder().id(UUID.randomUUID().toString()).build();
            }
            locationRepository.save(location);
            Cab cab = Cab.builder().id(driver.getCabId()).driverEmail(driver.getEmail()).locationId(location.getId())
                    .build();
            cabRepository.save(CabMapper.dtoToEntity(cab));
            return driver;
        }
    }

    private boolean validate(Driver driver) {

        return Validation.notEmptyString(driver.getName()) && Validation.notEmptyString(driver.getEmail()) &&
                Validation.notEmptyString(driver.getPassword()) && Validation.notEmptyString(driver.getCabId());
    }

    public List<Driver> getAllDrivers() {

        List<Driver> drivers = new ArrayList<>();
        for (application.entity.Driver driver : driverRepository.findAll()) {
            drivers.add(DriverMapper.entityToDto(driver));
        }
        return drivers;
    }

    public Driver getDriverByEmail(String email) {

        Optional<application.entity.Driver> result = driverRepository.findById(email);
        return result.map(DriverMapper::entityToDto).orElse(null);
    }

    public Cab updateCabLocation(String cabId, Location newLocation) {

        if (validateLocation(newLocation) && cabRepository.existsById(cabId)) {
            application.entity.Location location = locationRepository.findByXAndY(
                    newLocation.getXCoordinate(), newLocation.getYCoordinate());
            if (location == null) {
                location = LocationMapper.dtoToEntity(newLocation);
                location.setId(UUID.randomUUID().toString());
            }
            Optional<application.entity.Cab> cab = cabRepository.findById(cabId);
            cab.get().setLocationId(location.getId());
            locationRepository.save(location);
            cabRepository.save(cab.get());
            return CabMapper.entityToDto(cab.get());
        } else {
            throw new RuntimeException("The request to update cab location failed validation!");
        }
    }

    private boolean validateLocation(Location newLocation) {

        return Validation.notNull(newLocation);
    }

    public Driver updateDriverAvailability(String email, boolean available) {

        if (driverRepository.existsById(email)) {
            Optional<application.entity.Driver> driver = driverRepository.findById(email);
            driver.get().setAvailable(available);
            driverRepository.save(driver.get());
            return DriverMapper.entityToDto(driver.get());
        } else {
            throw new RuntimeException("This email id is not registered with any driver in application");
        }
    }

    public Trip bookCab(TripRequest tripRequest) {

        //allot a driver via strategy to a trip
        Driver driver = allotNearestDriverStrategy.allotDriver(tripRequest);

        if (driver == null) {
            throw new RuntimeException("Sorry! no cab available! Please check after sometime!");
        }

        //create location objects
        application.entity.Location sourceLocation = locationRepository.findByXAndY(tripRequest.getSourceXCoordinate(),
                tripRequest.getSourceYCoordinate());
        if (sourceLocation == null) {
            sourceLocation  = getLocation(tripRequest.getSourceXCoordinate(), tripRequest.getSourceYCoordinate());
        }

        application.entity.Location destLocation = locationRepository.findByXAndY(tripRequest.getDestXCoordinate(),
                tripRequest.getDestYCoordinate());
        if (destLocation == null) {
            destLocation  = getLocation(tripRequest.getDestXCoordinate(), tripRequest.getDestYCoordinate());
        }

        //create trip object
        Trip trip = Trip.builder().tripId(UUID.randomUUID().toString()).riderId(tripRequest.getRideremail())
                .tripStatus(tripStatus.ACTIVE).destinationId(destLocation.getId()).sourceId(sourceLocation.getId())
                .driverId(driver.getEmail()).build();
        trip.setTripId(UUID.randomUUID().toString());
        trip.setDriverId(driver.getEmail());

        //mark driver as busy
        driver.setAvailable(false);

        //save trip , source , destination location, driver in repository
        locationRepository.save(sourceLocation);
        locationRepository.save(destLocation);
        driverRepository.save(DriverMapper.dtoToEntity(driver));
        tripRepository.save(TripMapper.dtoToEntity(trip));
        return trip;
    }

    private application.entity.Location getLocation(int x, int y) {

        return application.entity.Location.builder().x(x).y(y).id(UUID.randomUUID().toString()).build();
    }

    public Trip endTrip(String tripId) {

        //mark trip completed
        Optional<application.entity.Trip> trip = tripRepository.findById(tripId);
        trip.get().setTripStatus(tripStatus.COMPLETED);
        //mark driver availability true
        Optional<application.entity.Driver> driver = driverRepository.findById(trip.get().getDriverId());
        driver.get().setAvailable(true);
        //mark cab location at trip destination
        Optional<application.entity.Cab> cab = cabRepository.findById(driver.get().getCabId());
        cab.get().setLocationId(trip.get().getDestinationId());
        //save all to repository
        tripRepository.save(trip.get());
        driverRepository.save(driver.get());
        cabRepository.save(cab.get());
        return TripMapper.entityToDto(trip.get());
    }
}
