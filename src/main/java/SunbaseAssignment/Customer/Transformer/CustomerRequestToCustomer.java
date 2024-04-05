package SunbaseAssignment.Customer.Transformer;

import SunbaseAssignment.Customer.Entities.Customer;
import SunbaseAssignment.Customer.Dtos.Request.AddCustomerRequest;

public class CustomerRequestToCustomer {
    public static Customer customerRequestToCustomer(AddCustomerRequest addCustomerRequest){
        Customer customer = Customer.builder()
                .firstName(addCustomerRequest.getFirstName())
                .lastName(addCustomerRequest.getLastName())
                .street(addCustomerRequest.getStreet())
                .address(addCustomerRequest.getAddress())
                .city(addCustomerRequest.getCity())
                .state(addCustomerRequest.getState())
                .email(addCustomerRequest.getEmail())
                .phone(addCustomerRequest.getPhone())
                .build();
        return customer;
    }

}
