package Assignment;
import java.util.*;
import java.io.*;

//Booking Class is used to store booking data such as booking ID, customer, appliance and more.

public class Booking {
	private String bookingID;
	private String customerName;
	private String applianceType;
	private String applianceIssue;
	private String date;
	private String time;
	private String technician;
	private String status;

	//Constructor to initialize all the booking details
	public Booking(String bookingID, String customerName, String applianceType, String applianceIssue, String date, String time, String technician, String status) {
		this.bookingID = bookingID;
		this.customerName = customerName;
		this.applianceType = applianceType;
		this.applianceIssue = applianceIssue;
		this.date = date;
		this.time = time;
		this.technician = technician;
		this.status = status;

		}
		
		//Getters
		public String getBookingID() {
			return bookingID;
		}
		
		public String getCustomerName() {
			return customerName;
		}
		
		public String getStatus() {
			return status;
		}

		//Setter
		public void setStatus(String status) {
			this.status = status;
		}
		
		
		public String getAppliance() {
			return applianceType;
		}
		
		public void setTech(String tech) {
			this.technician = tech;
		}
		
		//Return booking details into a organized format to store in file
		@Override
		public String toString() {
			return bookingID + "," + customerName + "," + applianceType + "," + applianceIssue + "," + date + "," + time + "," + technician + "," + status;
		}

		//Return a formatted string of booking details for the user to view
		public String display() {
			return "Booking ID : " + bookingID + "\nCustomer : " + customerName + "\nAppliance Type : " + applianceType + "\nAppliance Issue : " + applianceIssue + "\nDate : " + date + "\nTime : " + time + "\nTechnician : " + technician + "\nStatus : " + status + "\n";
		}

	
}

			
			