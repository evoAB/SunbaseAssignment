package SunbaseAssignment.Customer.Services;

import SunbaseAssignment.Customer.CustomException.InvalidCustomerId;
import SunbaseAssignment.Customer.CustomException.InvalidCustomerInfo;
import SunbaseAssignment.Customer.Entities.Customer;
import SunbaseAssignment.Customer.Repository.CustomerRepository;
import SunbaseAssignment.Customer.Dtos.Request.AddCustomerRequest;
import SunbaseAssignment.Customer.Dtos.Response.ShowCustomerResponse;
import SunbaseAssignment.Customer.Transformer.CustomerRequestToCustomer;
import SunbaseAssignment.Customer.Transformer.CustomerToCustomerResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;

    public ShowCustomerResponse addCustomer(AddCustomerRequest addCustomerRequest) throws Exception{
        Customer customer = customerRepository.findByEmail(addCustomerRequest.getEmail());
        //Check by email if customer already exist
        if(customer!=null)
            throw new InvalidCustomerInfo("Customer Already Exist");

        //Check if any of information is missing
        if(addCustomerRequest.getFirstName()==null ||
                addCustomerRequest.getLastName()==null ||
                addCustomerRequest.getStreet()==null ||
                addCustomerRequest.getAddress()==null ||
                addCustomerRequest.getCity()==null ||
                addCustomerRequest.getState()==null ||
                addCustomerRequest.getEmail()==null ||
                addCustomerRequest.getPhone()==null )
            throw new InvalidCustomerInfo("Invalid Customer Information");

        customer = CustomerRequestToCustomer.customerRequestToCustomer(addCustomerRequest);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerToCustomerResponse.customerToCustomerResponse((savedCustomer));
    }

    public ShowCustomerResponse updateCustomer(String email, AddCustomerRequest addCustomerRequest) throws Exception{
        Customer customer = customerRepository.findByEmail(email);
        if(customer == null){
            //if customer is null then we cant update it so throw error
            throw  new InvalidCustomerInfo("Customer not found");
        }

        //whatever info user want to update will get updated here
        if(addCustomerRequest.getFirstName()!=null)
            customer.setFirstName(addCustomerRequest.getFirstName());

        if(addCustomerRequest.getLastName()!=null)
            customer.setLastName(addCustomerRequest.getLastName());

        if (addCustomerRequest.getStreet()!=null)
            customer.setStreet(addCustomerRequest.getStreet());

        if(addCustomerRequest.getAddress()!=null)
            customer.setAddress(addCustomerRequest.getAddress());

        if(addCustomerRequest.getCity()!=null)
            customer.setCity(addCustomerRequest.getCity());

        if (addCustomerRequest.getState()!=null)
            customer.setState(addCustomerRequest.getState());

        if(addCustomerRequest.getEmail()!=null)
            customer.setEmail(addCustomerRequest.getEmail());

        if(addCustomerRequest.getPhone()!=null)
            customer.setPhone(addCustomerRequest.getPhone());

        Customer savedCustomer = customerRepository.save(customer);

        return CustomerToCustomerResponse.customerToCustomerResponse(savedCustomer);
    }

    //pagination
    public Page<ShowCustomerResponse> getAllCustomers(int pageNo, int rowsCount, String sortBy, String searchBy) {


        // this function will return a list of customers with the required number of rows
        Pageable currentPageWithRequiredRows;

        if (sortBy != null) {

            //  if sort by value is provided
            currentPageWithRequiredRows = PageRequest.of(pageNo-1, rowsCount, Sort.by(sortBy));
        }else {
            currentPageWithRequiredRows = PageRequest.of(pageNo-1, rowsCount);
        }

        Page<Customer> customersPage = customerRepository.findAll(currentPageWithRequiredRows);
        return customersPage.map(this::convertToDto);
    }
    public ShowCustomerResponse convertToDto(Customer customer){
        return CustomerToCustomerResponse.customerToCustomerResponse(customer);
    }

//    public List<ShowCustomerResponse> listOfCustomers(String sortBy, String  search){
//        List<Customer> customerList = customerRepository.findAll();
//        List<ShowCustomerResponse> showCustomerResponseList = new ArrayList<>();
//        for(Customer customer : customerList){
//
//            ShowCustomerResponse showCustomerResponse = CustomerToCustomerResponse.customerToCustomerResponse(customer);
//
//            showCustomerResponseList.add(showCustomerResponse);
//        }
//
////        showCustomerResponseList.sort((a,b)->{
////            a.search
////        });
//        return showCustomerResponseList;
//    }

    public ShowCustomerResponse getCustomer(Integer id) throws Exception{
        Customer customer = customerRepository.findById(id).get();
        if(customer == null){
            throw  new InvalidCustomerInfo("Customer not found");        }

        ShowCustomerResponse showCustomerResponse = CustomerToCustomerResponse.customerToCustomerResponse(customer);

        return showCustomerResponse;
    }

    public List<ShowCustomerResponse> searchBySpecificType(String searchBy, String searchQuery) {
        List<Customer> searchRes = new ArrayList<>();

        //check which search type is given and then find it from repo
        if (searchBy.equals("firstName")) {
            searchRes = customerRepository.findByFirstNameLike(searchQuery);
        } else if (searchBy.equals("city")) {
            searchRes = customerRepository.findByCityLike(searchQuery);
        } else if (searchBy.equals("phone")) {
            searchRes = customerRepository.findByPhoneLike(searchQuery);
        } else if (searchBy.equals("email")) {
            searchRes = customerRepository.findByEmailLike(searchQuery);
        }
        List<ShowCustomerResponse> searchList = new ArrayList<>();

        for (Customer cust: searchRes) {
            searchList.add(CustomerToCustomerResponse.customerToCustomerResponse(cust));
        }
        return searchList;
    }

    @Transactional
    public ShowCustomerResponse deleteCustomer(String email) throws Exception{
        //getting Customer by email which is unique
        Customer customer = customerRepository.findByEmail(email);

        if(customer == null)
            //if customer not found it will throw error
            throw new InvalidCustomerId("Invalid Customer");

        customerRepository.deleteByEmail(email);
        //return deleted customer
        return CustomerToCustomerResponse.customerToCustomerResponse(customer);
    }
}
