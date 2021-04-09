/* Authors: Jay Kmetz and Drey Newland
 * Date: 04/11/2021
 * File: Main.java
 */

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
	//Init program-global vars
	private static HashMap<String, Person> people = new HashMap<String, Person>();
	private static PubKey pubKey = new PubKey();

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
			out.printf("> ");
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
		"(h)elp     - print this message\n" +
		"(g)enkey   - Generate private key\n" +
		"(v)iewkeys - View private keys\n" +
		"(s)end     - Send message\n" +
		"(e)xit     - Exit program\n\n"
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
	 * @author 
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
		/* TODO: Implement
		 * List out all private keys
		 */
		notYetImplemented();
	}
}
