package application;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Koolitus implements Serializable{

	static Map<String, Koolitus> koolitused = new HashMap<String, Koolitus>();
	
	private String id;
	private String tootajaID;
	private String oskusID;
	private String kirjeldus;
	private String fail;
	
	private Koolitus(String tootajaID, String oskusID, String kirjeldus, String fail) {
		this.tootajaID = tootajaID;
		this.oskusID = oskusID;
		this.kirjeldus = kirjeldus;
		this.fail = fail;
		this.id = "T" + (koolitused.size() + 1);
		koolitused.put(this.id, this);
		new Muudatus(tootajaID, this.id, String.format("Töötajale %s (%s) lisatud uus koolitus %s (%s)", Tootaja.tootajad.get(this.tootajaID), this.tootajaID, this.kirjeldus, this.id));
	}
	
	
	static Koolitus lisaTunnistus(String tootajaID, String oskusID, String kirjeldus, String fail){
		return new Koolitus(tootajaID, oskusID, kirjeldus, fail);	
	}
	
	
	static Koolitus lisaTunnistus(String tootajaID, String kirjeldus){
		return new Koolitus(tootajaID, null, kirjeldus, null);	
	}
	
	
	public Koolitus muudaKirjeldus(Tootaja kes, String uusNimi){
		String muudatus = String.format("Koolituse kirjeldus %s -> %s", this.kirjeldus, uusNimi);
		this.kirjeldus = uusNimi;
		new Muudatus(kes.id, this.id, muudatus);
		return this;
	}
	
	
	public Koolitus muudaFail(Tootaja kes, String uusNimi){
		String muudatus = String.format("Koolituse fail %s -> %s", this.fail, uusNimi);
		this.fail = uusNimi;
		new Muudatus(kes.id, this.id, muudatus);
		return this;
	}
	
	public String annaID() {
		return this.id;
	}
	
	public String annaTootajaID() {
		return this.tootajaID;
	}
	
	public String annaKirjeldus() {
		return this.kirjeldus;
	}
	
	public String annaFail(){
		return this.fail;
	}
	
	
	public String toString(){
		return String.format("%s", this.kirjeldus);
	}




	
}

