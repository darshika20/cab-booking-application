package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Driver {

    private String cabId;
    private boolean available;
    private String name;
    private String email;
    private String password;
    private String id;
}
