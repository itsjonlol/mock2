package vttp.ssf.assessment.eventmanagement.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class User {

    @NotNull(message = "Name cannot be null")
    @NotEmpty(message = "Name cannot be empty")
    @Pattern(regexp = "^[a-zA-Z\s]+$",message = "Name cannot contain numbers")
    @Size(min = 5, max = 25, message = "Name must be inbetween 5 and 25 characters")
    private String fullName;
    
    
    @Past(message = "Past dates only")
    @NotNull(message = "You must set your date of birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date DOB;

    @NotNull
    @NotEmpty(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 50, message = "Email must be maximum of 50 characters")
    private String email;

    @Pattern(regexp = "(8|9)[0-9]{7}",message = "Please enter a valid number, starting with 8/9, and 8 digits max")
    private String phoneNumber;

    @Min(value = 1,message = "Minimum value of 1")
    @Max(value = 3, message = "Maximum value of 3")
    private Integer ticketsNumber;

    public User() {

    }

    

    public User(
            @NotNull(message = "Name cannot be null") @NotEmpty(message = "Name cannot be empty") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Name cannot contain numbers") @Size(min = 5, max = 25, message = "Name must be inbetween 5 and 25 characters") String fullName,
            @Past(message = "Past dates only") @NotNull(message = "You must set your date of birth") Date dOB,
            @NotNull @NotEmpty(message = "Email is required") @Email(message = "Please provide a valid email address") @Size(max = 50, message = "Email must be maximum of 50 characters") String email,
            @Pattern(regexp = "(8|9)[0-9]{7}", message = "Please enter a valid number, starting with 8/9, and 8 digits max") String phoneNumber,
            @Min(value = 1, message = "Minimum value of 1") @Max(value = 3, message = "Maximum value of 3") Integer ticketsNumber) {
        this.fullName = fullName;
        DOB = dOB;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ticketsNumber = ticketsNumber;
    }



    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDOB() {
        return DOB;
    }

    public void setDOB(Date dOB) {
        DOB = dOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getTicketsNumber() {
        return ticketsNumber;
    }

    public void setTicketsNumber(Integer ticketsNumber) {
        this.ticketsNumber = ticketsNumber;
    }

    



    
}
