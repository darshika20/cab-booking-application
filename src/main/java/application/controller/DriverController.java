package application.controller;

import application.dto.Cab;
import application.dto.Driver;
import application.dto.Location;
import application.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DriverController {

    @Autowired
    DriverService driverService;

    @PostMapping("/registerDriver")
    public ResponseEntity<Driver> registerDriver(@RequestBody Driver driver) {

        return new ResponseEntity<>(driverService.registerDriver(driver), HttpStatus.OK);
    }

    @GetMapping("/getAllDrivers")
    public ResponseEntity<List<Driver>> getAllDrivers() {

        return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
    }

    @GetMapping("/getDriverByEmail/{email}")
    public ResponseEntity<Driver> getDriverById(@PathVariable String email) {

        return new ResponseEntity<>(driverService.getDriverByEmail(email), HttpStatus.OK);
    }

    @PutMapping("/updateCabLocation")
    public ResponseEntity<Cab> updateCabLocation(@RequestParam(name = "cabId") String cabId, @RequestBody
            Location location) {

        return new ResponseEntity<>(driverService.updateCabLocation(cabId, location), HttpStatus.OK);
    }

    @PutMapping("/updateDriverAvailability")
    public ResponseEntity<Driver> updateDriverAvailability(@RequestParam(name = "email") String email,
                                                           @RequestParam(name = "available") boolean available)  {
        return new ResponseEntity<>(driverService.updateDriverAvailability(email, available), HttpStatus.OK);
    }
}
