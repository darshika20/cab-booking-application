package application.mapper;

import application.dto.Rider;

public class RiderMapper {

    public static Rider entityToDto(application.entity.Rider rider) {
        return Rider.builder().email(rider.getEmail()).name(rider.getName())
                .password(rider.getPassword()).build();
    }

    public static application.entity.Rider dtoToEntity(Rider rider) {
        return application.entity.Rider.builder().password(rider.getPassword()).name(rider.getName())
                .email(rider.getEmail()).build();
    }
}
