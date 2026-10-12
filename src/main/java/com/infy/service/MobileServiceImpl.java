package com.infy.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.infy.validator.Validator;
import com.infy.dao.MobileServiceDAO;
import com.infy.dao.MobileServiceDAOImpl;
import com.infy.exception.MobileServiceException;
import com.infy.model.ServiceReport;
import com.infy.model.ServiceRequest;
import com.infy.model.Status;

public class MobileServiceImpl implements MobileService{
	
	private MobileServiceDAO dao =  new MobileServiceDAOImpl();
        private Validator validator=new Validator();

	@Override
	public ServiceRequest registerRequest(ServiceRequest service) throws MobileServiceException {
		 validator.validate(service);
		 Float fee=calculateEstimateCost(service.getIssues());
		 if(fee<=0) {
			 throw new MobileServiceException("Sorry, we do not provide that service.");
		 }
		 service.setServiceFee(fee);
		 service.setStatus(Status.ACCEPTED);
		 service.setTimeOfRequest(LocalDateTime.now());
		return dao.registerRequest(service);
	}

	@Override
	public Float calculateEstimateCost(List<String> issues) throws MobileServiceException {
		Float total=0f;
		for(String issue:issues) {
			if(issue.equalsIgnoreCase("Battery"))total+=10;
			if(issue.equalsIgnoreCase("Camera"))total+=5;
			if(issue.equalsIgnoreCase("Screen"))total+=15;
		}
		return total;
	}

	@Override
	public List<ServiceReport> getServices(Status status) throws MobileServiceException {
		List<ServiceRequest>requests=dao.getServices();
		List<ServiceReport> reports = new ArrayList<>();
		for(ServiceRequest request:requests) {
			if(request.getStatus()==status) {
			    reports.add(new ServiceReport(
			    		request.getServiceId(),
			    		request.getBrand(),
			    		request.getIssues(),
			    		request.getServiceFee()
			    		));
			}
		}
		if (reports.isEmpty()) {
		    throw new MobileServiceException(
		        "Sorry, we did not find any records for your query."
		    );
		}
		return reports;
	}

}
