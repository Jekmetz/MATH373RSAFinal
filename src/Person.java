import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;

public class Person {
	// Init vars
	private PrivKey privKey = null;
	private PubKey pubKey = null;
	private String name = "";
	
	public Person(String name) { 
		this.privKey = new PrivKey();
		this.pubKey = this.privKey.getPubKey();
		this.name = name;
	}
	
	public Person(PrivKey privKey, String name)
	{
		this.privKey = privKey;
		this.pubKey = privKey.getPubKey();
		this.name = name;
	}
	
	// Getters
	public String     getName()		{ return name; }
	public PubKey	  getPubKey() 	{ return this.pubKey; }
	
	// Public Class Methods
	/**
	 * Encrypts some plaintext for the name specified
	 * @author Jay Kmetz
	 * @param key - Public key of the person you are encrypting for
	 * @param plaintext - Plaintext to encrypt
	 * @returns Encrypted String as a BigInteger
	 */
	public byte[] encrypt(PubKey key, String plaintext)
	{
		// Init vars
		BigInteger n = key.getN();
		BigInteger e = key.getE();
		int blockSize = Utils.BLOCK_SIZE;
		int outputLen = blockSize * (plaintext.length()/(blockSize) + 1);
		byte lPad, rPad;
		Random rand = new Random();
		ByteArrayInputStream plainTextBytes = new ByteArrayInputStream(plaintext.getBytes());
		ByteArrayOutputStream ciphertext = new ByteArrayOutputStream(outputLen);
		byte[] buffer = null, chonk = null;	// used for each chunk of data	

		try {
			lPad = (byte)(Utils.getBoundedRand(rand, Utils.PAD_LOWER_BOUND, Utils.PAD_UPPER_BOUND) & 0xFF);
			rPad = (byte)(Utils.getBoundedRand(rand, Utils.PAD_LOWER_BOUND, Utils.PAD_UPPER_BOUND) & 0xFF);
			
			while((buffer = plainTextBytes.readNBytes(blockSize - lPad - rPad)).length > 0)
			{
				
				chonk = Utils.padBytes(blockSize, buffer, lPad, rPad);
				
				// Perform m^e (mod n) calculation
				chonk = (new BigInteger(1, chonk)).modPow(e, n).toByteArray();

				if(chonk[0] == 0x00)	//If the first byte is leading 0s... (this happens because of big integer sometimes)
					chonk = Utils.removeLeadingZeros(chonk);
					
				// Pad it with zeros if necessary
				chonk = Utils.fillZerosLeft(blockSize, chonk);
				ciphertext.write(chonk, 0, blockSize);
				
				// Generate left padding and right padding randomly
				lPad = (byte)(Utils.getBoundedRand(rand, Utils.PAD_LOWER_BOUND, Utils.PAD_UPPER_BOUND) & 0xFF);
				rPad = (byte)(Utils.getBoundedRand(rand, Utils.PAD_LOWER_BOUND, Utils.PAD_UPPER_BOUND) & 0xFF);
			}
		} catch (IOException ex)
		{
			System.out.printf("Error when encrypting '%s'. This should never happen! Check Person.encrypt()\n");
			return null;
		}
		
		return ciphertext.toByteArray();
	}
	
	/**
	 * Decrypts a BigInteger back into the original string using the name specified
	 * @author 
	 * @param ciphertext - Ciphertext to decrypt
	 * @return Decrypted ciphertext
	 */
	public String decrypt(byte[] ciphertext)
	{	
		// TODO: Implement decrypt
		BigInteger n = this.pubKey.getN();
		BigInteger d = this.privKey.getD();
		int blockSize = Utils.BLOCK_SIZE;
		int outputLen = blockSize * (ciphertext.length/(blockSize) + 1);
		ByteArrayInputStream ciphertextStream = new ByteArrayInputStream(ciphertext);
		int numberToBeRemoved = 0;
		byte[] buffer = null;
		byte[] chonk = null;
		ByteArrayOutputStream plaintext = new ByteArrayOutputStream(outputLen);
		
		try {
		
			//for(int i = 0; i < numChonks; i++)
				
			while((buffer = ciphertextStream.readNBytes(Utils.BLOCK_SIZE)).length > 0)
			{
				//m=c^d(mod N)
				chonk = (new BigInteger(1, buffer)).modPow(d, n).toByteArray();
				numberToBeRemoved = (0xFF & chonk[1]); 
				chonk[0] = 0x00;
				chonk[1] = 0x00;
				chonk = Utils.removeLeadingZeros(chonk);
				chonk = Utils.removeTrailingZeros(chonk, numberToBeRemoved);
				
				plaintext.write(chonk, 0, chonk.length);
			}
		} catch (IOException ex) {
			
			System.out.printf("Error when decrypting '%s'. This should never happen! Check Person.encrypt()\n");
			return null;
		}
		
		
		//6,13,0,0,0,0,message,0,0,0,0,0,0,0,0,0,0,0,0,0
		
		return Utils.bytesToString(plaintext.toByteArray());
	}
	
	// Overrides from Object
	@Override
	public String toString()
	{
		return String.format("%s: %s", this.name, this.pubKey);
	}
}
