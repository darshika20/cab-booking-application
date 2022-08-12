package application.mapper;

import application.dto.Location;

public class LocationMapper {

    public static Location entityToDto(application.entity.Location location) {
        return Location.builder().id(location.getId()).xCoordinate(location.getX())
                .yCoordinate(location.getY()).build();
    }

    public static application.entity.Location dtoToEntity(Location location) {
        return application.entity.Location.builder().y(location.getYCoordinate())
                .x(location.getXCoordinate()).id(location.getId()).build();
    }
}
