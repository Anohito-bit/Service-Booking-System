package Assignment;
import java.util.*;
import java.io.*;

/*BookingSystem class manages the appliance repair bookings.
- Users are able to create, load, view and cancel bookings.
- Admin can update current booking status (Pending, Confirmed, Completed, Cancelled)
- All booking data are stored in bookings.txt file
*/

public class BookingSystem {

	private static final String FILE_NAME = "bookings.txt"; //file to store bookings
	private static List<Booking> bookings = new ArrayList<>();
	private static Scanner scanner = new Scanner(System.in);

	//Function to load existing bookings from file
	public static void loadBookings() {
		bookings.clear();
		try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
			String line; 
			while ((line = br.readLine()) !=null) {
				String [] data = line.split(",");
				if (data.length == 8) {
					bookings.add (new Booking(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]));
				}
			}
		} catch (IOException ex) {
			System.out.println("No existing bookings found!");
		}
	}
	
	//Function to save booking to file
	public static void saveBookings() {
		try (PrintWriter pw = new PrintWriter (new FileWriter(FILE_NAME))) {
			for (Booking b : bookings) {
				pw.println(b.toString());
			}
		} catch (IOException ex) {
			System.out.println("Error saving bookings!");
		}
	}
	
	public static List<Booking> getBookings()
	{
		loadBookings();
		return bookings;
	}
	
	//Function to create a new booking
	public static void createBooking(String customerName) {
		System.out.println("Select Appliance Type : ");
		System.out.println("1. Refrigerator");
		System.out.println("2. Washing Machine");
		System.out.println("3. Television");
		System.out.println("4. Air Conditioner");
		System.out.println("5. Water Heater");
		System.out.println("6. Microwave oven");
		System.out.print("Enter Choice (1-6): ");

		int choice;
		try {
			choice = Integer.parseInt(scanner.nextLine());
		} catch (NumberFormatException e) {
			choice = -1;
		}

		String applianceType = "";
		
		switch (choice) {
			case 1: applianceType = "Refrigerator"; break;
			case 2: applianceType = "Washing Machine"; break;
			case 3: applianceType = "Television"; break;	
			case 4: applianceType = "Air Conditioner"; break;
			case 5: applianceType = "Water Heater"; break;
			case 6: applianceType = "Microwave Oven"; break;
			default:
				System.out.println("Invalid choice, defaulting to 'Others'");
				applianceType = "Others";
			}


		System.out.print("Enter Issue of Appliance : ");
		String applianceIssue = scanner.nextLine();

		System.out.print("Enter Date (YYYY-MM-DD) : ");
		String date = scanner.nextLine();
	
		System.out.print("Enter Time (HH:MM) : ");
		String time = scanner.nextLine();

		String technician = null;

		//Create booking ID based on the highest existing ID
		int maxID = 0;
		for (Booking b : bookings) {
			try { 
				int num = Integer.parseInt(b.getBookingID().substring(1));
				if (num > maxID) {
					maxID = num;
					}
				} catch (NumberFormatException e) {
					System.out.println("Invalid booking ID skipped : " + b.getBookingID());
				}
			}
		
		String bookingID = "B" + (maxID + 1);

		String status = "Pending";
		
		Booking newBooking = new Booking(bookingID, customerName, applianceType, applianceIssue, date, time, technician, status);
		bookings.add(newBooking);
		saveBookings();

		System.out.println("Booking created successfully!");
		System.out.println(newBooking.display());

		}
	
	//Function to view previous bookings for a specific user
	public static void viewBookings(String customerName) {
		boolean found = false;
		for (Booking b : bookings) {
			if (b.getCustomerName().equals(customerName)) {
				System.out.println(b.display());
				found = true;
				}
			}
		if (!found) {
			System.out.println("No bookings found for " + customerName);
		}
	}

	//Function for user to cancel booking
	public static void cancelBooking(String customerName) {
		System.out.print("Enter Booking ID to Cancel : ");
		String id = scanner.nextLine();
		boolean removed = bookings.removeIf(b -> b.getBookingID().equals(id) && b.getCustomerName().equals(customerName));
		
		if (removed) {
			saveBookings();
			System.out.println("Booking " + id + " cancelled successfully!");
		}else {
			System.out.println("Booking not found!");
		}
	}

	//Function for Admin to update booking status(called in Admin class)
	public static void updateBookingStatus() {
		System.out.print("Enter Booking ID to update :");
		String id = scanner.nextLine();
		
		Booking found = null;
		for (Booking b : bookings) {
			if (b.getBookingID().equals(id)) {
				found = b;
				break;
			}
		}
	
		if (found == null) {
			System.out.println("Booking not found!");
			return;
		}
	
		System.out.println("Current Status: " + found.getStatus());
    		System.out.println("Choose new status:");
    		System.out.println("1. Pending");
    		System.out.println("2. Confirmed");
    		System.out.println("3. Completed");
    		System.out.println("4. Cancelled");
    		System.out.print("Enter choice: ");
    		int choice = Integer.parseInt(scanner.nextLine());

		String newStatus = found.getStatus();
    		switch (choice) {
        		case 1: newStatus = "Pending"; break;
        		case 2: newStatus = "Confirmed"; break;
        		case 3: newStatus = "Completed"; break;
        		case 4: newStatus = "Cancelled"; break;
        		default: System.out.println("Invalid choice!"); return;
		}
		
		found.setStatus(newStatus);
		saveBookings();
    		System.out.println("Booking " + id + " status updated to " + newStatus);
	}

	//Customer Menu
	public static void customerMenu(String customerName) {
		while(true) {
			loadBookings();
			System.out.println("\n===== CUSTOMER MENU =====");
            		System.out.println("1. Create a Booking");
            		System.out.println("2. View My Bookings");
            		System.out.println("3. Cancel Booking");
            		System.out.println("4. Payment");
            		System.out.println("5. Logout");
            		System.out.print("Enter choice: ");
            		int choice = Integer.parseInt(scanner.nextLine());

            		if (choice == 1) {
                		createBooking(customerName);
            		} else if (choice == 2) {
                		viewBookings(customerName);
            		} else if (choice == 3) {
                		cancelBooking(customerName);
            		} else if (choice == 4) {
            			Payment pay = new Payment();
                		pay.showBill(customerName);
            		} else if (choice == 5) {
                		break;
            		} else {
                		System.out.println("Invalid option, try again.");
            		}
		}
	}
}
