package application;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Koolitus implements Serializable{

	static Map<String, Koolitus> koolitused = new HashMap<String, Koolitus>();
	
	String id;
	String tootajaID;
	String oskusID;
	String kirjeldus;

	String fail;
	
	private Koolitus(String tootajaID, String oskusID, String kirjeldus, String fail) {
		this.tootajaID = tootajaID;
		this.oskusID = oskusID;
		this.kirjeldus = kirjeldus;
		this.fail = fail;
		this.id = "T" + (koolitused.size() + 1);
		koolitused.put(this.id, this);
	}
	
	
	static Koolitus lisaTunnistus(String tootajaID, String oskusID, String kirjeldus, String fail){
		return new Koolitus(tootajaID, oskusID, kirjeldus, fail);	
	}
	
	static Koolitus lisaTunnistus(String tootajaID, String kirjeldus){
		return new Koolitus(tootajaID, null, kirjeldus, null);	
	}
	
}
