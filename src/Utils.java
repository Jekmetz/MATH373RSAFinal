import java.math.BigInteger;
import java.util.Random;

/**
 * Utility class for all static helper functions
 * @author jekme
 */
public class Utils {
	/**
	 * Generates a large prime
	 * @author Jay Kmetz
	 * 
	 * @return large prime
	 */
	public static BigInteger generatePrime(int bitLength)
	{
		// Return BigIntger with bitLength bitLength, certainty 1-2^12 and a random generator with the system time as seed
		return new BigInteger(bitLength, 12, new Random(System.currentTimeMillis()));
	}
	
	/**
	 * Computes a^n (mod z) quickly
	 * @author 
	 * 
	 * @param a - base
	 * @param n - exponent
	 * @param z - moduls
	 * @return a^n (mod z)
	 */
	public static BigInteger fastPow(BigInteger a, BigInteger n, BigInteger z)
	{
		//TODO: implement
		return a.modPow(n, z);	// Please for the love of God don't keep this
	}
	
	/**
	 * Converts an array of bytes to string
	 * @author Jay Kmetz
	 * @param bytes - array of bytes to turn into a string
	 * @return string from bytes
	 */
	public static String bytesToString(byte[] bytes)
	{
		// returns null if bytes passed is null;
		if(bytes == null) return null;
		
		StringBuilder sb = new StringBuilder();
		for(byte b : bytes)
			sb.append((b >= 32 && b <= 126) ? ((char)b) : '-');	//If it is within the range of printable characters...

		return sb.toString();
	}
}
