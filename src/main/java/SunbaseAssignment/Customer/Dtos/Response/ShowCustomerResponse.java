package SunbaseAssignment.Customer.Dtos.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowCustomerResponse {
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;
}
