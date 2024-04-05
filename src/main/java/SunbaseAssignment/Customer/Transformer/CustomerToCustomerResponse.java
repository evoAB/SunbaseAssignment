package SunbaseAssignment.Customer.Transformer;

import SunbaseAssignment.Customer.Entities.Customer;
import SunbaseAssignment.Customer.Dtos.Response.ShowCustomerResponse;

public class CustomerToCustomerResponse {
    public static ShowCustomerResponse customerToCustomerResponse(Customer customer){
        ShowCustomerResponse showCustomerResponse = ShowCustomerResponse.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .street(customer.getStreet())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();

        return showCustomerResponse;
    }
}
