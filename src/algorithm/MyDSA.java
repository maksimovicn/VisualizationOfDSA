package algorithm;

import java.util.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyDSA {
	
	final static BigInteger one = new BigInteger("1");
	final static BigInteger zero = new BigInteger("0");
	
	public static BigInteger p;
	public static BigInteger q;
	public static BigInteger g;
	public static BigInteger x;
	public static BigInteger y;
	public static BigInteger k;
	public static BigInteger kInv;
	public static BigInteger r;
	public static BigInteger s;
	public static BigInteger hashVal;
	public static BigInteger w;
	public static BigInteger v;
	
	
	public static BigInteger getNextPrime(String ans) {
		BigInteger test = new BigInteger(ans);
		while (!test.isProbablePrime(99))
			test = test.add(one);
		return test;		
	}
	
	public static BigInteger findQ(BigInteger n) {
		
		BigInteger start = new BigInteger("2");
		
		while (!n.isProbablePrime(99)) {
			
			while (!((n.mod(start)).equals(zero))) {
				start = start.add(one);
			}
			n = n.divide(start);
			
		}
		return n;
	}
	
	public static BigInteger getG(BigInteger p, BigInteger q, Random r) {
		// Pick the random value.
		BigInteger h = new BigInteger(p.bitLength(), r);
		h = h.mod(p);
		
		// Exponentiate it to this power: (p-1)/q.
		return h.modPow((p.subtract(one)).divide(q), p);
	}
	
	public void generateParams(String test) {
		p = getNextPrime(test);
		q = findQ(p.subtract(one));
		g = getG(p, q, new Random());
		x = new BigInteger(q.bitLength(), new Random());
		x = x.mod(q);
		y = g.modPow(x,p);
		k = new BigInteger(q.bitLength(), new Random());
		k = k.mod(q);
		kInv = k.modInverse(q);
	}
	
	public void sign(String message) {
		r = (g.modPow(k,p)).mod(q);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] hash = md.digest(message.getBytes());
		hashVal = new BigInteger(hash);
		
		kInv = k.modInverse(q);
		
		s = kInv.multiply(hashVal.add(x.multiply(r)));
		s = s.mod(q);

	}
	
	public boolean verify(String message, BigInteger valueS, BigInteger valueR) {
		
		w = valueS.modInverse(q);
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] hash = md.digest(message.getBytes());
		hashVal = new BigInteger(hash);
		BigInteger u1 = (hashVal.multiply(w)).mod(q);
		BigInteger u2 = (valueR.multiply(w)).mod(q);
		v = (g.modPow(u1,p)).multiply(y.modPow(u2,p));
		v = (v.mod(p)).mod(q);
		
		return v.equals(valueR);
	}
	
	private void setParameters() {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("Enter an approximate value for p.");
		String ans = stdin.next();

		MyDSA myDsa = new MyDSA();
		myDsa.generateParams(ans);
		
		System.out.println("\nHere are the public key components:\n");
		System.out.println("p is " + myDsa.p);
		System.out.println("q is " + myDsa.q);
		System.out.println("g is " + myDsa.g);

		System.out.println("\nSecret Information:\n");
		System.out.println("x is " + myDsa.x);
		System.out.println("k is " + myDsa.k);
		
		System.out.println("\nPublic Key:\n");
		System.out.println("y is " + myDsa.y);
		
		myDsa.sign("Enter your message here");
		
		System.out.println("Hash Val = "+hashVal);
		
		System.out.println("\nThe signature:\n");
		System.out.println("r is "+r);
		System.out.println("s is "+s);
		
		boolean verified = myDsa.verify("Enter your message here", s, new BigInteger("84972384932"));
		
		System.out.println("\nVerifying checkpoints:\n");
		System.out.println("w is "+w);
		System.out.println("v is "+v);
		
		// Here's the actual check for the valid signature.
		if (verified)
			System.out.println("Signature is verified!");
		else
			System.out.println("Incorrect signature.");
		
	}

}
