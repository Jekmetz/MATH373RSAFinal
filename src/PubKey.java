import java.math.BigInteger;

public class PubKey {
	private BigInteger n,e;
	public PubKey(BigInteger n, BigInteger e)
	{
		this.n = n;
		this.e = e;
	}
	
	// Getters
	public BigInteger getN() { return this.n; }
	public BigInteger getE() { return this.e; }
	
	// Overrides from Object
	@Override
	public String toString()
	{
		return String.format("(N, e) = (%s, %s)",this.n.toString(), this.e.toString());
	}
}
