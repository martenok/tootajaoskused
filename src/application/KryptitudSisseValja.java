package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.security.AlgorithmParameters;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class KryptitudSisseValja {
//	static final String FILENAME = "Cryptotest";
	static final byte[] SOOL = "HastiVingeParooliTegemine".substring(0, 16).getBytes();
	static final String PASSWORD = "paroolLOORAPparoool".substring(0, 16);	
	
	public static Cipher initCipher(int cryptMode)  {
		Cipher c = null;
		try {
			
		    byte[] keyBytes = PASSWORD.getBytes();  //example
		    
//		    final byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 
//		         0x07, 0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f }; //example
		    
		    final SecretKey key = new SecretKeySpec(keyBytes, "AES");
		    final IvParameterSpec IV = new IvParameterSpec(SOOL);
		    c = Cipher.getInstance("AES/CFB8/NoPadding");
		    c.init(cryptMode, key, IV);		    
					
	    } catch(Exception ex) { ex.printStackTrace(); }
		return c;
	}

	
	public static void salvestaRead(String filename, List<String> read) {
		
		Cipher c = initCipher(Cipher.ENCRYPT_MODE);
		
		if (c != null) {
	    
			try (

					
			BufferedWriter cbw = new BufferedWriter(
					new OutputStreamWriter(
						new CipherOutputStream(
							new FileOutputStream(filename), c)))) 	
			{
				for (String x: read){
					cbw.write(x);
					cbw.newLine();
				}

			} catch(Exception ex) { ex.printStackTrace();}
		}
		
	}
	
	
	public static List<String> loeRead(String filename) {
		Cipher c = initCipher(Cipher.DECRYPT_MODE);
		List<String> read= new ArrayList<String>();
		
		if (c != null) {

			try (
			BufferedReader cbr = new BufferedReader(
					new InputStreamReader(
						new CipherInputStream(
							new FileInputStream(filename), c)))) 	
			{
				String rida;
				
				while ((rida = cbr.readLine()) != null) {
					read.add(rida);
				}

			} catch(Exception ex) { ex.printStackTrace();}
		}
		return read;
	}

	
	public static void encryptObjectStream(String filename) {
		
		
		Cipher c = initCipher(Cipher.ENCRYPT_MODE);
		
		if (c != null) {

			try (
					ObjectOutputStream w = new ObjectOutputStream(
						new CipherOutputStream(
							new FileOutputStream(filename), c))) 	
			{

				String[] nimed = {"Henn", "Ants", "Peeter"};
				w.writeObject(nimed);
				
			} catch(Exception ex) { ex.printStackTrace();}
		}
		
	}

	
	public static void decryptObjectStream(String filename) {
		Cipher c = initCipher(Cipher.DECRYPT_MODE);
		
		if (c != null) {

			try (ObjectInputStream r = new ObjectInputStream(
						new CipherInputStream(
							new FileInputStream(filename), c))) 	
			{
				
				String[] nimed = (String[]) r.readObject();
				System.out.println(Arrays.deepToString(nimed));

			} catch(Exception ex) { ex.printStackTrace();}
		}
		
	}

}
