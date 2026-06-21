package Assignment;

import java.util.*;
import java.io.*;


public class Payment 
{
	private String fee,name;
	private List<String> Appliances = new ArrayList<>();
	
	private static final String FILE_NAME = "bookings.txt";
	private static List<Booking> bookings = new ArrayList<>();
	private static Scanner scanner = new Scanner(System.in);

	// to load bookings list from txt file to a list
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
	
	// to load appliances list from txt file to a list
	public void loadAppliances()
	{
		try
		{
			File ap = new File("Appliances.txt");
			Scanner read = new Scanner(ap);
			
			
			while (read.hasNext())
			{
				String App = read.nextLine();
				Appliances.add(App);
			}
			
			read.close();
		}
		catch (FileNotFoundException filemiss) // to handle exception when file is not found
		{
			System.out.println("File not found.");
		}
	}
	
		
	// show bill/fees for each service booked by user
	public void showBill(String customer)
	{
		loadBookings();
		loadAppliances();
			
		System.out.println("-".repeat(50));
		System.out.println("Bill");
		System.out.println("-".repeat(50));
		System.out.println("Customer Name: " + customer);
		System.out.println("Fee for");
		
		// to search user's booking on which appliance in the bookings list and show his/her fee for his/her service booking for a specific appliance
		for (Booking b : bookings)
		{
			if (b.getCustomerName().equals(customer))
			{
				String ap = b.getAppliance();
				for (String a: Appliances)
				{
					String[] aprice = a.split(","); //to separate 'a string separated by a comma' by comma
					if (ap.equals(aprice[0]))
					{
						fee = aprice[1];
					}
				}
				
				System.out.printf("%s: %s",ap, fee + "\n");
			}
		}
		
		//ask the user does he/she needs a quotation of his/her bookings
		System.out.println("Do you need a quotation [y/n] : ");
		char opt;
		Scanner in = new Scanner(System.in);
		opt = in.next().toUpperCase().charAt(0);
		
		if (opt == 'Y')
		{
			writeQuotation(customer);
		}
		else {}
		
	
	}
	
	
	public void writeQuotation(String customer)
	{
		loadBookings();
		loadAppliances();
		int total = 0;
		int intFee = 0; 
		
		
		try
		{
			PrintWriter quo = new PrintWriter("Quotation.txt");
			
			// As a quotation format
			quo.println("-".repeat(50));
			quo.println("");
			quo.println("Quotation");
			quo.println("");
			quo.println("-".repeat(50));
			quo.println("");
			quo.println("Customer Name: " + customer);
			quo.println("");
			quo.println("Appliances:");
			
			// to search user's bookings in the bookings list, and correspond the appliances with its price, then record the price and the calculated total fees
			for (Booking b : bookings)
			{
				if (b.getCustomerName().equals(customer))
				{
					String ap = b.getAppliance();
					for (String a: Appliances)
					{
						String[] aprice = a.split(",");
						if (ap.equals(aprice[0]))
						{
							name = ap;
							fee = aprice[1].replace("RM", "");
							intFee = Integer.parseInt(fee);
							total += intFee;
							break;
						}
					}
					
					quo.printf("%s: RM %d",name,intFee);
				}
			}
			
			quo.println("");
			quo.printf("Total: RM %d",total);
			quo.close();
			System.out.println("Quotation exported successfully!");
		}
		catch (FileNotFoundException fi)
		{
			System.out.println("File is not found.");
		}
		
		
	}
}




