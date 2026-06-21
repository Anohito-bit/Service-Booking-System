package Assignment;
import java.util.*;
import java.io.*;

class User {
	    String password;
	    String name;
	    String Id;
	    String role;

	    public User(String Id, String name, String password, String role) {
	        this.Id = Id;
	        this.name = name;
	        this.password = password;
	        this.role = role;
	    }

	    public String getPassword() {
	        return password;
	    }

	    public String getName() {
	        return name;
	    }
	    
	    public String getId() {
	    	return Id;
	    }
	    
	    public String getRole() {
	    	return role;
	    }
	}

	public class LoginSystem {
	    private List<String> users = new ArrayList<>();

	    public void loadUsers() {
	    	try
			{
				File ap = new File("users.txt");
				Scanner read = new Scanner(ap);
				
				
				while (read.hasNext())
				{
					String us = read.nextLine();
					users.add(us);
				}
				
				read.close();
			}
			catch (FileNotFoundException filemiss) // to handle exception when file is not found
			{
				System.out.println("File not found.");
			}
	    }
	    
	    
	    public List<String> getUsers()
	    {
	    	loadUsers();
	    	return users;
	    }
	    
	    
	    public void register() {
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
		        	
		        	System.out.print("-----Register-----\n");
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
		        	
		        	
		        	for (String u: users)
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
			        		scan.nextLine();
		        		}
		        		catch(IOException ex)
		        		{
		        			System.out.println("An error occurred while saving data.");
		        		}
		        		
		        		
		        	}			            
		        	
		        } while (loop == 1);
	    }

	    public void login() {
	    		Scanner scan = new Scanner(System.in);
	    		int veri = 0;
	    		String name,role,id,pass;
	    		
	    		do
	    		{
		    		System.out.print("Enter ID: ");
		    	    String Id = scan.nextLine();
	
		    	    System.out.print("Enter Password: ");
		    	    String password = scan.nextLine();
	
		    	    for (String u: users)
		        	{
		        		String[] parts = u.split(",");
		        		if (parts[0].equals(Id) && parts[1].equals(password))
		        		{
		        			id = parts[0];
		        			pass = parts[1];
		        			name = parts[2];
		        			role = parts[3].toLowerCase();
		        			veri = 1;
		        			
		        			if (role.equals("admin"))
		        			{
		        				Admin admin = new Admin(id,name,pass,role);
		        				admin.AdminPage(admin);
		        			}
		        			else
		        			{
		        				BookingSystem.customerMenu(name);
		        			}
		        		}
		        		else
		        		{
		        			continue;
		        		}
		        	}
		    	    
		    	    System.out.print("Do you want to retry?[Y/N]:  ");
		    	    char opt = scan.nextLine().toUpperCase().charAt(0);
		    	    if (opt == 'Y')
		    	    {
		    	    	veri = 0;
		    	    }
		    	    else if (opt == 'N')
		    	    {
		    	    	veri = 1;
		    	    }
		    	    else
		    	    {
		    	    	veri = 1;
		    	    }
		    	    
	    		} while(veri == 0);
	    }
	        		
	        		
	    	    
	    public void homePage() 
	    {
	    	boolean loop = true;
	    	while (loop)
	    	{
	    		Scanner scan = new Scanner(System.in);
		    	loadUsers();
		    	System.out.println("ABD Appliances Service");
		    	System.out.println("----------------------");
		    	System.out.println("1. Register");
	        	System.out.println("2. Log In");
	        	System.out.println("3. Exit");
	        	int choice2 = Integer.parseInt(scan.nextLine());
	        	
	        	if (choice2 == 1) {
	        		register();
	        	} else if (choice2 == 2) {
	        		login();
	        	} else if (choice2 == 3) {
	        		break;
	        	}
	        }
	    }

	    
}
