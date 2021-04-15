import java.math.BigInteger;
import java.util.HashMap;

public class Person {
	// Init vars
	private PubKey pubKey = null;
	private String name = "";
	private HashMap<String, BigInteger> pubKeyMap;
	private BigInteger priv;
	
	public Person(PubKey pubKey, String name) { 
		this.pubKey = pubKey;
		this.pubKeyMap = new HashMap<String, BigInteger>();
		this.name = name;
		this.generatePriv(); 
	}
	
	public Person(PubKey pubKey, String name, BigInteger priv)
	{
		this.pubKey = pubKey;
		this.pubKeyMap = new HashMap<String, BigInteger>();
		this.name = name;
		this.priv = priv;
	}
	
	// Getters
	public BigInteger getPriv()					{ return priv; }
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
	public BigInteger encrypt(String name, String plaintext)
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
	public String decrypt(String name, BigInteger ciphertext)
	{
		// TOOD: Implement decrypt
		return null;
	}
	
	/**
	 * Adds an encryption/decryption key to this person to the pubKeyMap
	 * @author Jay Kmetz
	 * @param name - Name of the person to add to pubKeyMap
	 * @param personalPubKey - Public key associated with name given
	 */
	public void addTrustedPerson(String name, BigInteger personalPubKey)
	{
		pubKeyMap.put(
				name, 
				Utils.fastPow(personalPubKey, this.priv, this.pubKey.getN())
		);
	}
	
	// Private Class Methods
	/**
	 * Generates priv key
	 * @author Jay Kmetz
	 */
	private void generatePriv() { this.priv = Utils.generatePrime(32); }
	
	// Overrides from Object
	@Override
	public String toString()
	{
		return String.format("(%d)", this.priv);
	}
}
