package application.mapper;

import application.dto.Cab;

public class CabMapper {

    public static Cab entityToDto(application.entity.Cab cab) {
        return Cab.builder().driverEmail(cab.getDriverEmail()).id(cab.getId()).locationId(cab.getLocationId()).build();
    }

    public static application.entity.Cab dtoToEntity(Cab cab) {
        return application.entity.Cab.builder().driverEmail(cab.getDriverEmail()).id(cab.getId())
                .locationId(cab.getLocationId()).build();
    }
}
