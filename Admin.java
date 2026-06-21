package Assignment;

import java.util.*;
import java.io.*;

public class Admin extends User
{
	BookingSystem booklist = new BookingSystem();
	List<Booking> bookings = booklist.getBookings();
	List<String> Techs = new ArrayList<>();
	List<String> Users = (new LoginSystem()).getUsers();
	
	
	
	public Admin(String userID, String name, String password, String role) 
	{
        super(userID, name, password, role);
    }
	
	

	
	public void listTech()
	{
		Scanner scan = new Scanner(System.in);
		try
		{
			File tec = new File("Technicians.txt");
			Scanner read = new Scanner(tec);
			
			
			while (read.hasNext())
			{
				String App = read.nextLine();
				Techs.add(App);
				System.out.println(Techs.size());
				System.out.println(App);
			}
			
			read.close();
		}
		catch (FileNotFoundException filemiss) // to handle exception when file is not found
		{
			System.out.println("File not found.");
		}
		
		System.out.println("-".repeat(50));
		System.out.println();
		System.out.println("Technicians");
		System.out.println();
		System.out.println("-".repeat(50));
		System.out.println();
		
		for (String tech:Techs)
		{
			int in = Techs.indexOf(tech) + 1;
			String[] parts = tech.split(",");
			System.out.printf("%d. %-30s %-30s\n",in,parts[0],parts[1]);
		}
		
		System.out.println();
		System.out.println("Press enter to return...");
		scan.nextLine();
	}
	
	
	
	
	public void assignJob(String book)
	{
		Scanner scan = new Scanner(System.in);
				
		for (Booking b:bookings)
		{
			if (b.getBookingID().equals(book))
			{
				System.out.println("Assign a technician for the booking: ");
				String tech = scan.nextLine();
				b.setTech(tech);
				b.setStatus("Processing");
			}
			else
			{
				System.out.println("Booking ID not found.");
				scan.nextLine();
			}
		}
	}
	
	
	
	public void assignPage(String book)
	{
		boolean loop = true;
		while (loop)
		{
			Scanner scan = new Scanner(System.in);

			
			System.out.println("==== TECHNICIAN ASSIGNMENT ====");
			System.out.println("1. List Technicians.");
			System.out.println("2. Assign Job to Booking.");
			System.out.println("3. Exit.");
			System.out.println();
			System.out.println("-".repeat(50));
			System.out.println();
			System.out.println("Option: ");
			char opt = scan.nextLine().toUpperCase().charAt(0);
			
			if (opt == '1')
			{
				listTech();
			}
			else if (opt == '2')
			{
				assignJob(book);
			}
			else if (opt == '3')
			{
				loop = false;
			}
			else
			{
				System.out.println("Invalid input.");
				scan.nextLine();
			}
		}
	}
	
	
	public void listUsers()
	{
		System.out.println("-".repeat(50));
		System.out.println();
		System.out.println("Users");
		System.out.println();
		System.out.println("-".repeat(50));
		System.out.println();
		
		for (String u: Users)
		{
			String[] parts = u.split(",");
			int in = Users.indexOf(u);
			String id = parts[0];
			String name = parts[2];
			String role = parts[3];
			
			System.out.printf("%d. %-20s%-30s%-20s\n",in+1,id,name,role);
		}
	}
	
	public void addUser()
	{
		int loop = 0;
    	Scanner scan = new Scanner(System.in);
    		do
    		{
    			
    			if (loop == 1)
    			{
    				System.out.println("Please enter again.");
    			}
    			
    			loop = 0;
    			
	        	Scanner scanner = new Scanner(System.in);
	        	
	        	System.out.print("-----Add User-----\n");
	        	System.out.print("Enter ID: ");
	        	String Id = scanner.nextLine();

	        	System.out.println("Enter Password: ");
	        	String password = scanner.nextLine();

	        	System.out.println("Enter Name: ");
	        	String name = scanner.nextLine();
	        	  	
	        	System.out.println("Enter Role: ");
	        	String role;
	        	role = scanner.nextLine().toLowerCase();
	        	
	        	
	        	if (role.equals("admin") || role.equals("customer"))
	        	{
	        		loop = 0;
	        	}
	        	else
	        	{
	        		loop = 1;
	        	}
	        	
	        	
	        	for (String u: Users)
	        	{
	        		String[] parts = u.split(",");
	        		if (parts[0].equals(Id))
	        		{
	        			System.out.println("ID already exists. Try again.");
	        			loop = 1;
	        			continue;
	        		}
	        	}
	        
	        	if (loop != 1)
	        	{
	        		String[] newpart = {Id,password,name,role};
	        		String join = String.join(",",newpart);
	        		try
	        		{
	        			FileWriter us = new FileWriter("users.txt",true);
	        			PrintWriter use = new PrintWriter(us);
	        			use.println(join);
	        			use.close();
	        			System.out.println("Registration successful! User data saved.\n");
	        			System.out.println();
	        		}
	        		catch(IOException ex)
	        		{
	        			System.out.println("An error occurred while saving data.");
	        		}
	        	}	
	        	
	        	while (true)
	        	{
		         	System.out.println("Do you want to add more user? [Y/N] ");
		        	char opt = scan.nextLine().toUpperCase().charAt(0);
		        	
		        	if (opt == 'Y')
		        	{
		        		loop = 1;
		        		break;
		        	}
		        	else if(opt == 'N')
		        	{
		        		loop = 0;
		        		break;
		        	}
		        	else {}
	        	}
	        	
	        } while (loop == 1);
	}
	
	
	public void removeUser()
	{
		
		Scanner scan = new Scanner(System.in);
		String ID;
		int selec = 1;
		int in = 0;
		
		boolean loop = true;
		while (loop)
		{
			int found = 0;
			
			System.out.println("Enter User ID: ");
			ID = scan.nextLine();
			
			for (String u: Users)
			{
				String[] parts = u.split(",");
				
				String id = parts[0];
				
				if (id.equals(ID))
				{
					found = 1;
					in = Users.indexOf(u);
				}
				
			}
			
			if (found == 1)
			{
				
				Users.remove(in);
				try
				{
					PrintWriter us = new PrintWriter("users.txt");
					for (String user: Users)
					{
						us.println(user);
					}
					us.close();
				}
				catch (FileNotFoundException fi)
				{
					System.out.println("File not found.");
				}
				
				Users = (new LoginSystem()).getUsers();
				System.out.println("User removed successfully.");
			}
			
			if (selec == 1)
			{
				while (true)
	        	{
		         	System.out.println("Is there any other user you want to remove? [Y/N] ");
		        	char opt = scan.nextLine().toUpperCase().charAt(0);
		        	
		        	if (opt == 'Y')
		        	{
		        		break;
		        	}
		        	else if(opt == 'N')
		        	{
		        		loop = false;
		        		break;
		        	}
		        	else {}
	        	}
			}
			
		}
	}
	
	
	
	
	public void updateUser()
	{
		
		Scanner scan = new Scanner(System.in);
		String ID;
		int selec = 1;
		
		boolean loop = true;
		while (loop)
		{
			System.out.println("Enter User ID: ");
			ID = scan.nextLine();
			
			for (String u: Users)
			{
				String[] parts = u.split(",");
				
				String id = parts[0];
				
				if (id.equals(ID))
				{
					int IN = Users.indexOf(u);
					int option;
					
					while (true)
					{
						System.out.println("What do you want to edit?");
						System.out.println("1. ID");
						System.out.println("2. Password");
						System.out.println("3. Name");
						System.out.println("4. Role");
						option = scan.nextInt();
						
						if (!(option == 1 || option == 2 || option == 3 || option == 4) )
						{
							System.out.println("Invalid option. Pleasee retry.");
							scan.nextLine();
						}
						else 
						{
							break;
						}
						
					}
					
					int in = option - 1;
					
					System.out.println("Change it to: ");
					scan.nextLine();
					parts[in] = scan.nextLine();
					
					String join = String.join(",",parts);
					Users.set(IN, join);
					
					try
					{
						PrintWriter us = new PrintWriter("users.txt");
						for (String user: Users)
						{
							us.println(user);
						}
						us.close();
					}
					catch (FileNotFoundException fi)
					{
						System.out.println("File not found.");
					}
					
					Users = (new LoginSystem()).getUsers();
					System.out.println("User's information edited successfully.");
				}
				
			}
			
			if (selec == 1)
			{
				while (true)
	        	{
		         	System.out.println("Is there any other user you want to edit information? [Y/N] ");
		        	char opt = scan.nextLine().toUpperCase().charAt(0);
		        	
		        	if (opt == 'Y')
		        	{
		        		break;
		        	}
		        	else if(opt == 'N')
		        	{
		        		loop = false;
		        		break;
		        	}
		        	else {}
	        	}
			}
			
		}
	}
	
	
	
	
	
	public void manageUsers()
	{ 
		Scanner scan = new Scanner(System.in);
		
		boolean loop = true;
		while (loop)
		{
			System.out.println("==== MANAGE USERS ====");
			System.out.println("1. View All Users");
	        System.out.println("2. Add New User");
	        System.out.println("3. Remove User");
	        System.out.println("4. Update User");
			System.out.println("5. Exit.");
			System.out.println();
			System.out.println("-".repeat(50));
			System.out.println();
			System.out.println("Option: ");
			char opt = scan.nextLine().toUpperCase().charAt(0);
			
			if (opt == '1')
			{
				listUsers();
			}
			else if (opt == '2')
			{
				addUser();
			}
			else if (opt == '3')
			{
				removeUser();
			}
			else if (opt == '4')
			{
				updateUser();
			}
			else if (opt == '5')
			{
				loop = false;
			}
			else
			{
				System.out.println("Invalid input.");
				scan.nextLine();
			}
		}
	}
	
	
	

	
	
	
	public void AdminPage(Admin admin) 
	{
        Scanner scanner = new Scanner(System.in);
        while (true) 
        {

        	
            System.out.println("======= ADMIN =======");
            System.out.println("1. Assign Job to Technician");
            System.out.println("2. Manage Users");
            System.out.println("3. Logout");
            System.out.print("Enter choice: ");
            
            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.print("Enter booking id: ");
                String bookingID = scanner.nextLine();
                assignPage(bookingID);
            } 
            
            else if (choice == 2) 
            {
                manageUsers();
            } 
            
            else if (choice == 3) 
            {
                break;
            } 
            
            else 
            {
                System.out.println("Invalid option.");
            }
        }
    }
}
