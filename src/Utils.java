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
}
