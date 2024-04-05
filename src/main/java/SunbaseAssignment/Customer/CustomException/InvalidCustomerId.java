package SunbaseAssignment.Customer.CustomException;

public class InvalidCustomerId extends Exception{
    public InvalidCustomerId(String message){
        super(message);
    }
}
