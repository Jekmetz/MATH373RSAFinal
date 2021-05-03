import java.math.BigInteger;
import java.util.Random;

/**
 * Utility class for all static helper functions
 * @author jekme
 */
public class Utils {
	//Init vars
	private static final char[] HEX_ARR = "0123456789ABCDEF".toCharArray();
	
	public static final int BLOCK_SIZE = 512;
	public static final int PAD_LOWER_BOUND = 2;
	public static final int PAD_UPPER_BOUND = 100;
	
	/**
	 * Generates a large prime
	 * @author Jay Kmetz
	 * 
	 * @return large prime
	 */
	public static BigInteger generatePrime(int byteLength)
	{
		// Return BigIntger with bitLength bitLength, certainty 1-2^12 and a random generator with the system time as seed
		return new BigInteger(8*byteLength, 12, new Random(System.currentTimeMillis()));
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
	
	public static String bytesToHexString(byte[] bytes)
	{
		//Input validation
		if(bytes == null) return null;
		
		//Init vars
		char[] hexChars = new char[bytes.length * 2];	// Two chars per byte
		int m;
		
		for(int i = 0; i < bytes.length; i++)
		{
			m = bytes[i] & 0xFF;					// create byte mask
			hexChars[i*2]   = HEX_ARR[ m >>> 4 ];	// get unsigned shift of first 4 bits
			hexChars[i*2+1] = HEX_ARR[ m & 0x0F ];	// get unsigned shift of the last 4 bits
		}
		
		return "0x" + new String(hexChars);
	}
	
	/**
	 * Generates random number between low and high (inclusive)
	 * @author Jay Kmetz
	 * @param rand - random number generator to generate from
	 * @param low - lower bound
	 * @param high - upper bound
	 * @return random integer in interval [low, high]
	 */
	public static int getBoundedRand(Random rand, int low, int high)
	{
		return rand.nextInt(high - low + 1) + low;
	}
	
	/**
	 * Pads blockSize with Kmetz-Newland padding scheme
	 * NOTE: You cannot pass data with leading zeros. This won't be a problem
	 * because this is only being used to send text.
	 * @author Jay Kmetz
	 * @param blockSize
	 * @param bytes
	 * @param lPad
	 * @param rPad
	 * @return
	 */
	public static byte[] padBytes(int blockSize, byte[] bytes, byte lPad, byte rPad)
	{
		// Init Vars
		int nLeftZeros = blockSize - bytes.length - rPad;
		byte[] out = new byte[blockSize];
		
		// Add the lpad to byte 0 and the rpad to byte 1
		out[0] = lPad;
		out[1] = rPad;
		
		// Fill the rest of the left with zeros including 0 padding for bytes
		for(int i = 2; i < nLeftZeros; i++) out[i] = 0x00;
		
		// Fill the data
		System.arraycopy(bytes, 0, out, nLeftZeros, bytes.length);
		
		// Fill the rest of the block with zeros
		for(int i=blockSize - rPad; i < blockSize; i++) out[i] = 0x00;
		
		return out;
	}
	
	/**
	 * Returns a byte[] which is padded left with 0s to the specified size
	 * @param size - Size you want the returned array to be
	 * @param bytes - data you want copied into the end of the bytes array
	 * @return array which is padded left with 0s to size
	 */
	public static byte[] fillZerosLeft(int size, byte[]  bytes)
	{
		// Guard clause - If the array is already the intended size... throw it back
		if(bytes.length == size) return bytes;
		
		// If we need to pad it...
		byte[] padded = new byte[size];

		// Copy the bytes into the end of the new initialized padded array
		System.arraycopy(bytes, 0, padded, size-bytes.length, bytes.length);
		
		return padded;
	}
	
	public static byte[] removeLeadingZeros(byte[] bytes)
	{
		// Guard clause - If the array has no leading zeros... throw it back
		if(bytes[0] != 0x00) return bytes;
		
		// If we need to remove...
		
		// Init vars
		int fByteLoc = 0;	// First non-zero byte location
		byte[] out = null;
		
		for(int i = 0; i < bytes.length; i++)	// For every index i of bytes...
			if(bytes[i] != 0x00)	// If the current byte is not 0...
			{
				fByteLoc = i;		// record the index and break.
				break;
			}
		
		out = new byte[bytes.length - fByteLoc];
		
		// copy the bytes starting at the first nonzero byte to out
		System.arraycopy(bytes, fByteLoc, out, 0, bytes.length - fByteLoc);
		
		return out;
	}
	
	//remove trailing zeros
	public static byte[] removeTrailingZeros(byte[] bytes, byte numberToDelete)
	{
		
		int messageSize = BLOCK_SIZE - numberToDelete;
		byte[] out = null;
		out = new byte[messageSize];
		
		System.arraycopy(bytes, 0, out, 0, messageSize);
	
		return out;
	}
	
}
