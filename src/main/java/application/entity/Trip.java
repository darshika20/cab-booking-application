package application.entity;

import application.dto.tripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Trip {

    @Id
    private String tripId;
    private String sourceId;
    private String destinationId;
    private long timeTaken;
    private tripStatus tripStatus;
    private String riderId;
    private String driverId;
}