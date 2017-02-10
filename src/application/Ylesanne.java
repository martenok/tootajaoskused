package application;
import java.util.HashMap;
import java.util.Map;

public class Ylesanne {
	
	static Map<String, Ylesanne> ylesanded = new HashMap <String, Ylesanne>();
	
	String id;
	String kesID;
	String kelleleID;
	String kirjeldus;
	
	private Ylesanne(String kesID, String kelleleID, String kirjeldus){
		this.id = "Y" + (ylesanded.size() + 1);
		this.kesID = kesID;
		this.kelleleID = kelleleID;
		this.kirjeldus = kirjeldus;
	}
	
	static Ylesanne uusYlesanne(String kesID, String kelleleID, String kirjeldus){
		if (Tootaja.tootajad.get(kesID).onAdmin){
			return new Ylesanne(kesID, kelleleID, kirjeldus);
		}
		else return null;
	}

}
