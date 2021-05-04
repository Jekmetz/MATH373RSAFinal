/* Authors: Jay Kmetz and Drey Newland
 * Date: 04/11/2021
 * File: Main.java
 */

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	//Init program-global vars
	private static HashMap<String, Person> people = new HashMap<String, Person>();
	private final static String prompt = "> "; 		// Prompt for user input
	
	public static void main(String[] args) {
		// Init Vars
		String cmd = null;		// used to store cmd typed by user
		boolean exit = false;	// used for exit control
		Scanner scan = new Scanner(System.in);	// Scanner for user input
		PrintStream out = System.out;
		
		printAuthorInfo(out);
		printHelp(out);
		
		
		/*************/
		while(!exit)	// while we don't want to exit...
		{
			out.printf(prompt);
			cmd = scan.nextLine();
			
			switch(cmd.toLowerCase())
			{
			case "e":
			case "exit":
				exit = true;
				break;
				
			case "h":
			case "help":
				printHelp(out);
				break;
				
			case "g":
			case "genkey":
				genKey(scan, out);
				break;
				
			case "s":
			case "send:":
				send(scan, out);
				break;
				
			case "v":
			case "viewkeys":
				viewKeys(scan, out);
				break;
				
			default:
				out.println("Command not recognized");
				break;
			}
		}
		System.out.println("Thank you for using our RSA program and have a splendiferous day!");
		
		scan.close();
	}
	
	private static void printAuthorInfo(PrintStream out)
	{
		out.println();
		out.printf(
			"----------AUTHORS----------\n" +
			"Jay Kmetz and Drey Newland\n\n"
		);
	}
	
	private static void printHelp(PrintStream out) 
	{
		out.printf(
			"----------COMMANDS----------\n" +
			"(h)elp       - print this message\n" +
			"(g)enkey     - Generate public key for person\n" +
			"(v)iewkeys   - View public keys\n" +
			"(s)end       - Send message\n" +
			"(e)xit       - Exit program\n\n"
		);
	}

	// Commands - all functions should take in a scanner
	/**
	 * Command handler for genKey 
	 * @author Drey Newland
	 * @param scan - Input stream scanner
	 * @param out - Output stream to write to
	 */
	private static void genKey(Scanner scan, PrintStream out)
	{
		/* TODO: Implement genKey
		 * Prompt user for name
		 * Generate a new private key for them and put it into the privKeys map
		 * Display "Generated new person: "
		 * Display "<name> : (private, public) -> (<person.getPriv()>,<person.getPub()>)
		 * name -> Person
		 */
		String enteredName = null;
		Person newName;
		
		out.printf("Please enter a name.\n");
		enteredName = scan.nextLine();
		
		PrivKey newPrivKey = new PrivKey();
		people.put(enteredName, new Person(newPrivKey, enteredName));
		newName = people.get(enteredName);
		
		out.printf("Generated new person:\n");
		out.printf("%s : (Private, Public) - > (%s, %s)\n", enteredName, newPrivKey, newName.getPubKey());
		
		
		//people.put("Alice", new Person("Alice"));
		//people.put("Bob", new Person("Bob"));
	}
	
	/**
	 * Command handler for send
	 * @author Jay Kmetz
	 * @param scan - Input stream scanner
	 * @param out - Output stream to write to
	 */
	private static void send(Scanner scan, PrintStream out)
	{
		/* TODO: Implement send
		 * This is the meat and potatoes
		 * Prompt user for message sender -----------------------------------------
		 * Prompt user for message reciever ---------------------------------------
		 * Show public key for reciever -------------------------------------------
		 * Prompt user for message ------------------------------------------------
		 * Display "Encrypting '<message>' with <reciever public key>..." ---------
		 * Display Encryption information (enc_message) ---------------------------
		 * Display "Sending from <Sender>..." -------------------------------------
		 * Display "<Reciever> is recieving <enc_message>" ------------------------
		 * Display "<Reciever> is decrypting with their own key information" ------
		 * Display "Decrypted message: <decrypted message>" -----------------------
		 */
		// People size validation
		if(people.size() < 2)
		{
			out.printf("Please create at least 2 people using genkey!\n\n");
			return;
		}
		
		// Init vars
		ArrayList<String> peopleList = null;
		Person sender, receiver;	// sender and reciever indecies
		String msg = null;			// msg to be messed with
		byte[] ciphertext = null;
		String decrypted = null;
		
		// Prompt user for message sender
		peopleList = listPeople(out);
		out.printf("Please input the number corresponding to the person sending the message:\n");
		sender = people.get(peopleList.get(getInteger(scan, out, 0, peopleList.size() - 1)));
		out.printf("%s is the sender\n\n", sender.getName());
		
		// Prompt user for message receiver
		listPeople(out, peopleList);
		out.printf("Please input the number corresponding to the recieving person\n");
		receiver = people.get(peopleList.get(getInteger(scan, out, 0, peopleList.size() - 1)));
		out.printf("%s is the receiver\n\n", receiver.getName());
		
		// Show Public Key for receiver
		out.printf(
			"Public key for %s\n" +
			"%s\n\n",
			receiver.getName(),
			receiver.getPubKey()
		);
		
		// Prompt user for message
		out.printf("Please input your message to be encrypted: \n");
		msg = scan.nextLine();
		
		// Encrypting
		out.printf("Encrypting '%s' with %s's public key: %s\n", msg, sender.getName(), sender.getPubKey());
		ciphertext = sender.encrypt(receiver.getPubKey(), msg);
		
		out.printf("Encrypted Message (in hex):\n%s\n\n", Utils.bytesToHexString(ciphertext));
		
		out.printf("Sending from %s\n", sender.getName());
		out.printf("%s is recieving message\n\n", receiver.getName());
		
		//Decrypting
		out.printf("Decrypting message with %s's public key and their secret d\n", receiver.getName());
		decrypted = receiver.decrypt(ciphertext);
		out.printf("Decrypted message:\n%s\n\n", decrypted);
	}
	
	/**
	 * Command handler for viewKeys
	 * @author Jay Kmetz
	 * @param scan - Input stream scanner
	 * @param out - Output stream to write to
	 */
	private static void viewKeys(Scanner scan, PrintStream out)
	{
		if(people.size() == 0) // If there are no people in our list...
		{
			out.printf("There are no people! Use 'genkey' to generate one!\n\n");
			return;
		}
		
		listPeople(out, false);
	}
	
	// Helpers
	
	/**
	 * Lists all of the generated people
	 * @author Jay Kmetz
	 * @param out - PrintStream to print to
	 * @param lst - already calculated list (if applicable)
	 * @param index - Should index? (indexes if true, displays keys if false)
	 * @return List of people in alphabetical order from people map
	 */
	private static ArrayList<String> listPeople(PrintStream out, ArrayList<String> lst, boolean index)
	{
		// Init vars
		ArrayList<String> peopleList = lst != null ? lst : new ArrayList<String>(people.size());
		int maxILen = (int)Math.log10(people.size() - 1) + 1; 	// Max index length
		int maxPLen = "Person".length();						// Max Person Length
		
		if(lst == null) // If they did not provide a list...
		{
			// Sort each person by name and add them to the peopleList
			for(String person : people.keySet())
			{
				if(person.length() > maxPLen) // If we have a person with the longest name...
					maxPLen = person.length();
				
				peopleList.add(person);
			}
			
			peopleList.sort((a,b)->a.compareTo(b));	// sort alphabetically
		}
		
		// Add headers if not indexing
		if(!index)
		{
			String header = String.format("%-" + maxPLen + "s -> %s", "Person", "(pub-key)");
			out.printf("%s\n%s\n", header, "-".repeat(header.length()));
		}
		
		// List each person with index beside them
		Person p = null;
		for(int i = 0; i < peopleList.size(); i++)
			if(index)	// If we want to index it...
				out.printf("[%0" + maxILen + "d] %s\n", i, peopleList.get(i));
			else // If we do not want to index it...
			{
				p = people.get(peopleList.get(i));
				out.printf("%-" + maxPLen + "s -> (N, e) = (%s, %s)\n", peopleList.get(i), p.getPubKey().getN(), p.getPubKey().getE());
			}
		out.println();

		return peopleList;
	}
	
	private static ArrayList<String> listPeople(PrintStream out) { return listPeople(out, null, true); }
	private static void listPeople(PrintStream out, ArrayList<String> lst) { listPeople(out, lst, true); }
	private static ArrayList<String> listPeople(PrintStream out, boolean index) { return listPeople(out, null, index); }
	
	/**
	 * Gets an integer from the user between lower and upper
	 * @author Jay Kmetz
	 * @param scan - Scanner to scan from
	 * @param out - PrintStream to print to
	 * @param lower - lower bound for the integer (inclusive)
	 * @param upper - upper bound for the integer (inclusive)
	 * @return integer from user
	 */
	private static int getInteger(Scanner scan, PrintStream out, int lower, int upper)
	{
		boolean isInt = false;
		String supposedInt = null;
		int ret = 0;
		
		while(!isInt)	// while it is not an int...
		{
			out.printf(prompt);				// Prompt the user for an int
			supposedInt = scan.nextLine();	// Scan their input
			
			try {
				ret = Integer.parseInt(supposedInt);	// try parsing it as an int
				if(ret < lower || ret > upper) throw new IndexOutOfBoundsException();
				isInt = true;							// If it worked, exit the loop
			} catch (NumberFormatException e)			// If it wasn't an int...
			{
				out.printf("Please input a valid number!\n\n");	//Ask for one.
			} catch (IndexOutOfBoundsException e)
			{
				out.printf("Please input a valid number between %d and %d\n\n",lower,upper);
			}
		}
		
		return ret;
	}
}
