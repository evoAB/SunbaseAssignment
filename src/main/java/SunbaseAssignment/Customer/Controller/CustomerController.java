package SunbaseAssignment.Customer.Controller;

import SunbaseAssignment.Customer.Dtos.Request.AddCustomerRequest;
import SunbaseAssignment.Customer.Dtos.Response.ShowCustomerResponse;
import SunbaseAssignment.Customer.Services.CustomerServices;
import SunbaseAssignment.Customer.Services.ExternalApiCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerServices customerServices;

    ExternalApiCall externalApiCall = new ExternalApiCall();

    @PostMapping("/create")
    public ResponseEntity addCustomer(@RequestBody AddCustomerRequest addCustomerRequest){
        try{
            ShowCustomerResponse result = customerServices.addCustomer(addCustomerRequest);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity updateCustomer(@PathVariable String email,@RequestBody AddCustomerRequest addCustomerRequest){
        try{
            ShowCustomerResponse result = customerServices.updateCustomer(email, addCustomerRequest);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getCustomers")
    public ResponseEntity<Page<ShowCustomerResponse>> getAllCustomers(@RequestParam int pageNo, @RequestParam int rowsCount, @RequestParam(required = false)String sortBy, @RequestParam(required = false) String searchBy){
        Page<ShowCustomerResponse> customerList = customerServices.getAllCustomers(pageNo, rowsCount, sortBy, searchBy);
        return new ResponseEntity<>(customerList, HttpStatus.FOUND);

    }
//    public ResponseEntity<List<ShowCustomerResponse>> listOfCustomers(@RequestParam(value = "sortby", required = false) String sortBy,
//                                                                      @RequestParam(value = "search", required = false) String search) {
//        List<ShowCustomerResponse> result = customerServices.listOfCustomers(sortBy, search);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

    @GetMapping("/getCustomer")
    public ResponseEntity getCustomer(@RequestParam (value = "id") Integer id){
        try {
            ShowCustomerResponse showCustomerResponse = customerServices.getCustomer(id);
            return new ResponseEntity<>(showCustomerResponse, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/searchBy")
    public ResponseEntity searchBySpecificType(@RequestParam String searchBy,@RequestParam String searchQuery){
        List<ShowCustomerResponse>   searchedResult = customerServices.searchBySpecificType(searchBy,searchQuery);
        return new ResponseEntity<>(searchedResult,HttpStatus.FOUND);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@RequestParam String email){
        try{
            ShowCustomerResponse result = customerServices.deleteCustomer(email);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/sync")
    public ResponseEntity<Object[]> getTokenFromApi(){
        Object[]   customerObject =externalApiCall.getTokenFromApi();
        return new ResponseEntity<>(customerObject,HttpStatus.ACCEPTED);
    }
}
