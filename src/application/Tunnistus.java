package application;
import java.util.HashMap;
import java.util.Map;

public class Tunnistus {

	static Map<String, Tunnistus> tunnistused = new HashMap<String, Tunnistus>();
	
	String id;
	String tootajaID;
	String oskusID;
	String kirjeldus;
	
	String fail;
	
	private Tunnistus(String tootajaID, String oskusID, String kirjeldus, String fail) {
		this.tootajaID = tootajaID;
		this.oskusID = oskusID;
		this.kirjeldus = kirjeldus;
		this.fail = fail;
		this.id = "T" + (tunnistused.size() + 1);
		
	}
	
	static Tunnistus lisaTunnistus(String tootajaID, String oskusID, String kirjeldus, String fail){
		return new Tunnistus(tootajaID, oskusID, kirjeldus, fail);
		
	}
	
}
