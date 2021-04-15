import java.math.BigInteger;

public class PubKey {
	// Init vars
	private BigInteger n; 		// Modulus for all transactions
	private BigInteger e; 		// Base for all transactions
	
	public PubKey() { this.generateKey(); }
	
	public PubKey(BigInteger n, BigInteger e)
	{
		this.n = n;
		this.e = e;
	}
	
	// Getters
	public BigInteger getN() { return n; }
	public BigInteger getE() { return e; }
	
	// Class Methods
	/**
	 * Generates random public key
	 * @author 
	 */
	public void generateKey()
	{
		/* TODO: Implement generateKey
		 * Choose two distinct prime numbers p and q (similar in magnitude... differ in length by a few digits
		 * this.n = p*q
		 * ord = Carmichael(n) = lcm(p-1, q-1) = ((p-1)*(q-1))/gcd(p-1,q-1)
		 * choose this.e = x s.t. (1 < this.e < ord) and gcd(this.e, ord) = 1
		 * 		e should have a short bit-length and a small Hamming weight
		 * 		You might just be able to set this to 2^16 + 1 and that will be sufficient
		 * 		Or you could randomly generate it and check. Up to you.
		 */
		
		// Init Vars
		BigInteger p = Utils.generatePrime(256);	// Randomly generate large prime
		BigInteger q = BigInteger.valueOf(256);	// Randomly generate large prime
		
		this.n = p.multiply(q);			// Mutate
		this.e = BigInteger.valueOf(3);	// Mutate
	}
	
	@Override
	public String toString()
	{
		return String.format("(%d,%d)", this.n, this.e);
	}
}
