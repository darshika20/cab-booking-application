package application.controller;

import application.dto.Rider;
import application.service.RiderService;
import application.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(Constants.RIDER_CONTROLLER)
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

}
