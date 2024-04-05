package SunbaseAssignment.Customer.Dtos.Request;

import lombok.Data;

@Data
public class AddCustomerRequest {
    private String firstName;
    private String lastName;
    private String street;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;
}
