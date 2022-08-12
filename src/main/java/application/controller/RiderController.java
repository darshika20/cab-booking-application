package application.controller;

import application.dto.Rider;
import application.dto.Trip;
import application.dto.TripRequest;
import application.service.RiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RiderController {

    @Autowired
    private RiderService riderService;

    @PostMapping("/registerRider")
    public ResponseEntity<Rider> registerRider(@RequestBody Rider rider){
        return new ResponseEntity<>(riderService.register(rider), HttpStatus.OK);
    }

    @GetMapping("/getAllRiders")
    public ResponseEntity<List<Rider>> getAllRiders() {
        return new ResponseEntity<>(riderService.getAllRiders(), HttpStatus.OK);
    }

    @GetMapping("/getRiderByEmail")
    public ResponseEntity<Rider> getRider(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(riderService.getRiderByEmail(email), HttpStatus.OK);
    }

    @PostMapping("/bookCab")
    public ResponseEntity<Trip> bookCab(@RequestBody TripRequest tripRequest) {

        return new ResponseEntity<>(riderService.bookCab(tripRequest), HttpStatus.OK);
    }

    @GetMapping("/getRideHistory")
    public ResponseEntity<List<Trip>> getRideHistory(@RequestParam(name = "rider") String email) {

        return new ResponseEntity<>(riderService.getAllRides(email), HttpStatus.OK);
    }

    @PostMapping("/endTrip")
    public ResponseEntity<Trip> endTrip(@RequestParam(name = "tripId") String tripId) {

        return new ResponseEntity<>(riderService.endTrip(tripId), HttpStatus.OK);
    }

}
