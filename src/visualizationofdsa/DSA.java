package visualizationofdsa;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAPrivateKeySpec;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;


public class DSA {
	
	private static String algorithm = "DSA";
	private static int size = 1024;
	
	private static PrivateKey privateKey;
	private static PublicKey publicKey;
	
	private static DSAPrivateKey dsaPrivateKey;
	private static DSAPublicKey dsaPublicKey;
	
	public static void generateParams() throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException {
	    
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
	    keyGen.initialize(size);
	    
	    KeyPair keypair = keyGen.generateKeyPair();
	    dsaPrivateKey = (DSAPrivateKey) keypair.getPrivate();
	    dsaPublicKey = (DSAPublicKey) keypair.getPublic();

	    DSAParams dsaParams = dsaPrivateKey.getParams();
	    BigInteger p = dsaParams.getP();
	    BigInteger q = dsaParams.getQ();
	    BigInteger g = dsaParams.getG();
	    BigInteger x = dsaPrivateKey.getX();
	    BigInteger y = dsaPublicKey.getY();
	    
	    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("javni.txt"));
        out.writeObject(dsaPublicKey);
        out.close();
        System.out.println(dsaPublicKey);
        out = new ObjectOutputStream(new FileOutputStream("tajni.txt"));
        out.writeObject(dsaPrivateKey);
        out.close();
        
	    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
	    
	    KeySpec privateKeySpec = new DSAPrivateKeySpec(x, p, q, g);
	    privateKey = keyFactory.generatePrivate(privateKeySpec);
	    
	    KeySpec publicKeySpec = new DSAPublicKeySpec(y, p, q, g);
	    publicKey = keyFactory.generatePublic(publicKeySpec);
	    System.out.println(publicKey);
	    
	}

	
	public static void sign() throws Exception {
        
        ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("tajni.txt"));
        DSAPrivateKey privkey = (DSAPrivateKey) keyIn.readObject();
        keyIn.close();
        
		Signature signalg = Signature.getInstance("DSA");
        signalg.initSign(privkey);

        File infile = new File("test.txt");
        InputStream in = new FileInputStream(infile);
        int length = (int) infile.length();
        byte[] message = new byte[length];
        in.read(message, 0, length);
        in.close();

        signalg.update(message);
        byte[] signature = signalg.sign();

        DataOutputStream out = new DataOutputStream(new FileOutputStream("izlaz.txt"));
        int signlength = signature.length;
        //System.out.print(signature);
        out.writeInt(signlength);
        out.write(signature, 0, signlength);
        out.write(message, 0, length);
        out.close();
        
	}
	
	
	public static void verify() throws Exception {

        ObjectInputStream keyIn = new ObjectInputStream(new FileInputStream("javni.txt"));
        DSAPublicKey publkey = (DSAPublicKey) keyIn.readObject();
        keyIn.close();
		
        Signature verifyalg = Signature.getInstance("DSA");
        verifyalg.initVerify(publkey);

        File infile = new File("izlaz.txt");
        DataInputStream in = new DataInputStream(new FileInputStream(infile));
        int signlength = in.readInt();
        byte[] signature = new byte[signlength];
        in.read(signature, 0, signlength);

        int length = (int) infile.length() - signlength - 4;
        byte[] message = new byte[length];
        in.read(message, 0, length);
        in.close();

        verifyalg.update(message);
        if (!verifyalg.verify(signature)) System.out.print("not ");
        System.out.println("verified");
	}
	
	
	public static void main(String[] args) throws Exception {
        
		generateParams();
		sign();
		verify();

	}

}
