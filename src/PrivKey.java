import java.math.BigInteger;
import java.util.Random;

public class PrivKey {
	// Init vars
	private PubKey pubKey;
	private BigInteger d;		// secret e^(-1) ( mod (p-1)*(q-1) )
	
	public PrivKey() { this.generateKey(); }
	
	// Getters
	public PubKey	  getPubKey() 	{ return pubKey; }
	public BigInteger getD() 		{ return d; }
	
	// Class Methods
	/**
	 * Generates random public key
	 * @author Drey Newland
	 */
	private void generateKey()
	{
		/* Goal: make p*q exactly 512 bytes
		 * Choose two distinct prime numbers p and q (The length of p + the length of q is 512 bytes.)
		 * The length of p and the length of q should be above 240.
		 * 	Solution: Get a random number p_len between 240 and 272. Generate a prime that is that many bytes long
		 * 	          Then, make q_len = 512 - p_len. This ensures that both of our above parameters are true
		 * 			  If the byte length of p*q is 513, throw the previous out and generate new ones
		 * this.n = p*q
		 * ord = Carmichael(n) = lcm(p-1, q-1) = ((p-1)*(q-1))/gcd(p-1,q-1)
		 * choose this.e = x s.t. (1 < this.e < ord) and gcd(this.e, ord) = 1
		 * 		e should have a short bit-length and a small Hamming weight
		 * 		You might just be able to set this to 2^16 + 1 and that will be sufficient
		 * 		Or you could randomly generate it and check. Up to you.
		 * this.d = e^(-1) (mod ord)
		 */
		
		
		// Init Vars
		BigInteger p=null, q=null, n=null;
		Random r = new Random();
		int firstByteLength = 0;
		int tol = (int)(Utils.BLOCK_SIZE * .1);
		while(n == null || n.bitLength()/8 != Utils.BLOCK_SIZE)	//While we still need to generate...
		{
			firstByteLength = Utils.getBoundedRand(r, Utils.BLOCK_SIZE/2 - tol, Utils.BLOCK_SIZE/2 + tol);
			p = Utils.generatePrime(firstByteLength);	// Randomly generate large prime
			q = Utils.generatePrime(Utils.BLOCK_SIZE - firstByteLength);	// Randomly generate large prime
			n = p.multiply(q);
		}
		
		BigInteger pMinus1 = p.subtract(BigInteger.valueOf(1));
		BigInteger qMinus1 = q.subtract(BigInteger.valueOf(1));
		
		BigInteger ord = pMinus1.multiply(qMinus1).divide(pMinus1.gcd(qMinus1));
		
		BigInteger e;
		do {
			e = BigInteger.probablePrime(Utils.getBoundedRand(new Random(), 2, ord.bitLength()-1), new Random());
		} while (!(e.gcd(ord).equals(BigInteger.ONE)));
	

		this.pubKey = new PubKey(n,e);
		this.d = e.modInverse(ord);
	}
	
	@Override
	public String toString()
	{
		return String.format("(N, e, d) = (%d, %d, %d)", this.pubKey.getN(), this.pubKey.getE(), this.d);
	}
}
