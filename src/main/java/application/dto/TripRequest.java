package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {

    private int sourceXCoordinate;
    private int sourceYCoordinate;
    private int destXCoordinate;
    private int destYCoordinate;
    private String rideremail;
}
