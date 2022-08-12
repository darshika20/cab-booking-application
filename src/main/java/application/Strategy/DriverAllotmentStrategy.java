package application.Strategy;

import application.dto.Driver;
import application.dto.TripRequest;

public interface DriverAllotmentStrategy {

    Driver allotDriver(TripRequest tripRequest);
}
