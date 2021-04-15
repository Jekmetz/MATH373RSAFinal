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
	private static PubKey pubKey = new PubKey();	// Static public key for all (en|de)cryption
	private final static String prompt = "> "; 		// Prompt for user input
	
	public static void main(String[] args) {
		// Init Vars
		String cmd = null;		// used to store cmd typed by user
		boolean exit = false;	// used for exit control
		Scanner scan = new Scanner(System.in);	// Scanner for user input
		PrintStream out = System.out;
		
		printAuthorInfo(out);
		printHelp(out);
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
				
			case "p":
			case "viewpubkey":
				viewPubKey(scan, out);
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
		"(g)enkey     - Generate private key\n" +
		"(v)iewkeys   - View private keys\n" +
		"view(p)ubkey - View the public key\n" +
		"(s)end       - Send message\n" +
		"(e)xit       - Exit program\n\n"
		);
	}
	
	private static void notYetImplemented() { System.out.printf("This feature is not yet implemented\n\n");};
	
	
	// Commands - all functions should take in a scanner
	/**
	 * Command handler for genKey 
	 * @author 
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
		notYetImplemented();
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
		 * Display public Key
		 * Prompt user for message sender
		 * Prompt user for message reciever
		 * Add sender as trusted person for reciever
		 * Add reciever as trusted person for sender
		 * Prompt user for message
		 * Display "Encrypting '<message>' with <sender encryption key>..."
		 * Display Encryption information (sender encryption key, enc_message)
		 * Display "Sending from <Sender>..."
		 * Display "<Reciever> is recieving <enc_message>"
		 * Display "<Reciever> is encrypting with <reciever enc_key>"
		 * Display "Decrypted message: <decrypted message>"
		 */
		notYetImplemented();
	}
	
	/**
	 * Command handler for viewKeys
	 * @author 
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
	
	private static void viewPubKey(Scanner scan, PrintStream out) { out.printf("%s\n\n", pubKey); }
	
	// Helpers
	
	/**
	 * List all of the people in the people
	 * @author Jay Kmetz
	 * @return list of all the people corresponding to the indecies laid out
	 */
	private static ArrayList<String> listPeople(PrintStream out, boolean index)
	{
		// Init vars
		ArrayList<String> peopleList = new ArrayList<String>(people.size());
		int maxILen = (int)Math.log10(people.size() - 1) + 1; 	// Max index length
		int maxPLen = "Person".length();						// Max Person Length
		
		// Sort each person by name and add them to the peopleList
		for(String person : people.keySet())
		{
			if(person.length() > maxPLen) // If we have a person with the longest name...
				maxPLen = person.length();
			
			peopleList.add(person);
		}
		
		peopleList.sort((a,b)->a.compareTo(b));	// sort alphabetically

		// Add headers if not indexing
		if(!index)
		{
			String header = String.format("%-" + maxPLen + "s -> %s", "Person", "Private Key");
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
				out.printf("%-" + maxPLen + "s -> (%s, %s)\n", peopleList.get(i), p.getPriv());
			}
		out.println();

		return peopleList;
	}
	
	private static ArrayList<String> listPeople(PrintStream out) { return listPeople(out, true); }

	private static int getInteger(Scanner scan, PrintStream out)
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
				isInt = true;							// If it worked, exit the loop
			} catch (NumberFormatException e)			// If it wasn't an int...
			{
				out.printf("Please input a valid number!\n\n");	//Ask for one.
			}
		}
		
		return ret;
	}
}
