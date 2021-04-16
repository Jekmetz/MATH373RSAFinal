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
		 * Choose two distinct prime numbers p and q (The length of p + the length of q is 513 bytes.)
		 * The length of p and the length of q should be above 240.
		 * 	Solution: Get a random number p_len between 240 and 272. Generate a prime that is that many bytes long
		 * 	          Then, make q_len = 513 - p_len. This ensures that both of our above parameters are true
		 * this.n = p*q
		 * ord = Carmichael(n) = lcm(p-1, q-1) = ((p-1)*(q-1))/gcd(p-1,q-1)
		 * choose this.e = x s.t. (1 < this.e < ord) and gcd(this.e, ord) = 1
		 * 		e should have a short bit-length and a small Hamming weight
		 * 		You might just be able to set this to 2^16 + 1 and that will be sufficient
		 * 		Or you could randomly generate it and check. Up to you.
		 */
		
		// Init Vars
		BigInteger p = Utils.generatePrime(258);	// Randomly generate large prime
		BigInteger q = Utils.generatePrime(255);	// Randomly generate large prime
		
		this.n = p.multiply(q);			// Mutate
		this.e = BigInteger.valueOf(3);	// Mutate
	}
	
	@Override
	public String toString()
	{
		return String.format("(%d,%d)", this.n, this.e);
	}
}
