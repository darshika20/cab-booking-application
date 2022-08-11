package application.mapper;

import application.dto.Driver;

public class DriverMapper {

    public static Driver entityToDto(application.entity.Driver driver) {
        return Driver.builder().available(driver.isAvailable()).cabId(driver.getCabId()).id(driver.getId())
                .email(driver.getEmail()).name(driver.getName()).password(driver.getPassword()).build();
    }

    public static application.entity.Driver dtoToEntity(Driver driver) {
        return application.entity.Driver.builder().available(driver.isAvailable()).id(driver.getId())
                .cabId(driver.getCabId()).name(driver.getName()).email(driver.getEmail())
                .password(driver.getPassword()).build();
    }
}
