package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    private String tripId;
    private String sourceId;
    private String destinationId;
    private long timeTaken;
    private boolean active;
    private String riderId;
    private String driverId;
}
