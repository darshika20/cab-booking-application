package application.mapper;

import application.dto.Cab;

public class CabMapper {

    public static Cab entityToDto(application.entity.Cab cab) {
        return Cab.builder().driverId(cab.getDriverId()).id(cab.getId()).locationId(cab.getLocationId()).build();
    }

    public static application.entity.Cab dtoToEntity(Cab cab) {
        return application.entity.Cab.builder().driverId(cab.getDriverId()).id(cab.getId())
                .locationId(cab.getLocationId()).build();
    }
}
