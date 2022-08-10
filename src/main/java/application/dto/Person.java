package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public abstract class Person {

    private String name;
    private String email;
    private String password;
    private List<Trip> pastTrips;
    private String tripId;
    private String id;
}
