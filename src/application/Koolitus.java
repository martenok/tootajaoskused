package application;
import java.util.HashMap;
import java.util.Map;

public class Koolitus {

	static Map<String, Koolitus> tunnistused = new HashMap<String, Koolitus>();
	
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
		this.id = "T" + (tunnistused.size() + 1);
		tunnistused.put(this.id, this);
	}
	
	
	static Koolitus lisaTunnistus(String tootajaID, String oskusID, String kirjeldus, String fail){
		return new Koolitus(tootajaID, oskusID, kirjeldus, fail);	
	}
	
	static Koolitus lisaTunnistus(String tootajaID, String kirjeldus){
		return new Koolitus(tootajaID, null, kirjeldus, null);	
	}
	
}
