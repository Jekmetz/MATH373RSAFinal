import java.math.BigInteger;
import java.util.HashMap;

public class Person {
	// Init vars
	private PubKey pubKey = null;
	private String name = "";
	private HashMap<String, BigInteger> pubKeyMap;
	private BigInteger priv, pub;
	
	public Person(PubKey pubKey, String name) { 
		this.pubKey = pubKey;
		this.pubKeyMap = new HashMap<String, BigInteger>();
		this.name = name;
		this.generatePair(); 
	}
	
	public Person(PubKey pubKey, String name, BigInteger priv, BigInteger pub)
	{
		this.pubKey = pubKey;
		this.pubKeyMap = new HashMap<String, BigInteger>();
		this.name = name;
		this.priv = priv;
		this.pub = pub;
	}
	
	// Getters
	public BigInteger getPriv()					{ return priv; }
	public BigInteger getPub()					{ return pub; }
	public String     getName()					{ return name; }
	public BigInteger getEncKey(String name) 	{ return pubKeyMap.get(name); }
	
	// Public Class Methods
	/**
	 * Encrypts some plaintext for the name specified
	 * @author 
	 * @param name - Name of the person you are sending it to
	 * @param plaintext - Plaintext to encrypt
	 * @returns Encrypted String as a BigInteger
	 */
	public byte[] encrypt(String name, String plaintext)
	{
		// TODO: Implement encrypt
		return null;
	}
	
	/**
	 * Decrypts a BigInteger back into the original string using the name specified
	 * @author 
	 * @param name - Name of the person recieving from
	 * @param ciphertext - Ciphertext to decrypt
	 * @return Decrypted ciphertext
	 */
	public String decrypt(String name, byte[] ciphertext)
	{
		// TOOD: Implement decrypt
		return null;
	}
	
	/**
	 * Adds an encryption/decryption key to this person to the pubKeyMap
	 * @author Jay Kmetz
	 * @param person - Person to add to trusted list
	 */
	public void addTrustedPerson(Person p)
	{
		// generate encryption key between them with <other's pub key>^<priv key> (mod <universal pub key n>)
		pubKeyMap.put(
				p.getName(), 
				Utils.fastPow(p.getPub(), this.priv, this.pubKey.getN())
		);
	}
	
	// Private Class Methods
	/**
	 * Generates priv and pub keys
	 * @author Jay Kmetz
	 */
	private void generatePair() { 
		this.priv = Utils.generatePrime(32); 
		this.pub = Utils.fastPow(this.pubKey.getE(), this.priv, this.pubKey.getN());
	}
	
	// Overrides from Object
	@Override
	public String toString()
	{
		return String.format("(%d)", this.priv);
	}
}
