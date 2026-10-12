package com.infy.validator;

import java.util.List;

import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceRequest;

public class Validator {

	public void validate(ServiceRequest service) throws MobileServiceException {	
		//your code goes here
		if (!isValidBrand(service.getBrand())) {
		    throw new MobileServiceException(
		        "Sorry! we do not provide service for this brand"
		    );
		}

		if (!isValidIssues(service.getIssues())) {
		    throw new MobileServiceException(
		        "Please provide the device only if there are issues."
		    );
		}

		if (!isValidIMEINumber(service.getiMEINumber())) {
		    throw new MobileServiceException(
		        "Sorry! we’re not able to detect the IMEI number for this device"
		    );
		}

		if (!isValidContactNumber(service.getContactNumber())) {
		    throw new MobileServiceException(
		        "Please provide a valid contact number"
		    );
		}

		if (!isValidCustomerName(service.getCustomerName())) {
		    throw new MobileServiceException(
		        "Please provide a valid customer name"
		    );
		}
	}	

	
	// validates the brand
	// brand should always start with a upper case alphabet 
	// and can be followed by one or more alphabets (lower case or upper case) 
	public Boolean isValidBrand(String brand){
		return brand.matches("[A-Z][a-zA-Z]+");
	}
	
	
	// validates the list of issues
	// checks if the list is null or empty
	public Boolean isValidIssues(List<String> issues) {
		return issues!=null &&!issues.isEmpty();
	}

	// validates the IMEINumber
	// it should be a 16 digit number 
	public Boolean isValidIMEINumber(Long iMEINumber) {
		if(iMEINumber==null) {
			return false;
		}
		String temp=iMEINumber.toString();
		return temp.length()==16;
	}
	
	// validates the contact number
	// should contain 10 numeric characters and should not contain 10 repetitive characters
	public Boolean isValidContactNumber(Long contactNumber) {
		if(contactNumber==null){
			return false;
		}
		String temp=contactNumber.toString();
		return temp.length()==10&& !temp.matches("(.)\\1+");
	}
	
	
	// validates the customer name
	// should contain at least one word and each word separated by a single space should contain at least one letter.
	// the first letter of every word should be an upper case character 
	public Boolean isValidCustomerName(String customerName) {
		return customerName!=null&&customerName.matches("[A-Z][a-z]+([A-Z][a-z]+)*");
	}
}
