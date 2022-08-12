package application.service;

import application.dto.Rider;
import application.dto.Trip;
import application.dto.TripRequest;
import application.mapper.RiderMapper;
import application.mapper.TripMapper;
import application.repository.RiderRepository;
import application.repository.TripRepository;
import application.utility.Validation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RiderService {

    @Autowired
    RiderRepository riderRepository;

    @Autowired
    DriverService driverService;

    @Autowired
    TripRepository tripRepository;

    private static final Logger logger = LoggerFactory.getLogger(RiderService.class);

    @Transactional
    public Rider register(Rider rider) {
        if (Validation.notEmptyString(rider.getEmail()) && Validation.notEmptyString(rider.getName())
                && Validation.notEmptyString(rider.getPassword())) {
            if (riderRepository.findById(rider.getEmail()).isPresent()) {
                throw new RuntimeException("This email id is already registered!");
            }
            application.entity.Rider riderEntity = RiderMapper.dtoToEntity(rider);
            riderRepository.save(riderEntity);
            logger.info("Successfully saved rider {} in repository", rider);
            return rider;
        } else {
            throw new RuntimeException("One or more mandatory fields are empty/null");
        }
    }

    public List<Rider> getAllRiders() {
        List<Rider> riders = new ArrayList<>();
        for (application.entity.Rider rider : riderRepository.findAll()) {
            riders.add(RiderMapper.entityToDto(rider));
        }
        return riders;
    }

    public Rider getRiderByEmail(String email) {
        Optional<application.entity.Rider> rider = riderRepository.findById(email);
        if (rider.isEmpty()) {
            throw new RuntimeException("This email Id is not registered in application!");
        } else {
            return RiderMapper.entityToDto(rider.get());
        }
    }

    public Trip bookCab(TripRequest tripRequest) {

        //validate trip details
        boolean validation = validateTrip(tripRequest);
        boolean riderExists = riderRepository.existsById(tripRequest.getRideremail());
        if (!validation || !riderExists) {
            throw new RuntimeException("Trip details validation failed");
        } else {
            return driverService.bookCab(tripRequest);
        }
    }

    private boolean validateTrip(TripRequest tripRequest) {

        return Validation.notEmptyString(tripRequest.getRideremail()) && !(tripRequest.getSourceXCoordinate() ==
                tripRequest.getDestXCoordinate() && tripRequest.getSourceYCoordinate() ==
                tripRequest.getDestYCoordinate());
    }

    public List<Trip> getAllRides(String email) {

        List<Trip> trips = new ArrayList<>();
        for (application.entity.Trip trip : tripRepository.findByRiderId(email)) {
            trips.add(TripMapper.entityToDto(trip));
        }
        return trips;
    }

    public Trip endTrip(String tripId) {

        if (!tripRepository.existsById(tripId)) {
            throw new RuntimeException("This trip does not exist in the application!");
        } else {
            return  driverService.endTrip(tripId);
        }
    }
}
