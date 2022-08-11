package application.mapper;

import application.dto.Trip;

public class TripMapper {

    public static Trip entityToDto(application.entity.Trip trip) {
        return Trip.builder().tripId(trip.getTripId()).driverId(trip.getDriverId()).active(trip.isActive())
                .destinationId(trip.getDestinationId()).riderId(trip.getRiderId()).sourceId(trip.getSourceId())
                .timeTaken(trip.getTimeTaken()).build();
    }

    public static application.entity.Trip dtoToEntity(Trip trip) {
        return application.entity.Trip.builder().tripId(trip.getTripId()).driverId(trip.getDriverId())
                .active(trip.isActive()).destinationId(trip.getDestinationId()).riderId(trip.getRiderId())
                .sourceId(trip.getSourceId()).timeTaken(trip.getTimeTaken()).build();
    }
}
