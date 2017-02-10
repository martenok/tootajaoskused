package application;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UusOskus {
	
	static int loendur = 0;
	static Map<String, UusOskus> uuedOskused = new HashMap <String, UusOskus>();
	
	String id;
	String nimetus;
	private String kirjeldus;
	String lisaja;
	LocalDate tahtaeg;
	Boolean lubatud = false;
	
	private UusOskus(String nimetus, String kirjeldus, String lisajaID, LocalDate tahtaeg){
		this.id = "U" + ++loendur;
		this.nimetus = nimetus;
		this.kirjeldus = kirjeldus;
		this.lisaja = lisajaID;
		this.tahtaeg = tahtaeg;
		
		if (Tootaja.tootajad.get(lisajaID).onAdmin) {
			 this.lubatud = true;
		 }
		 else this.lubatud = false;
		
	}
	
	static UusOskus lisaUusOskus(String nimetus, String kirjeldus, String lisajaID, LocalDate tahtaeg){
		return new UusOskus(nimetus,kirjeldus, lisajaID, tahtaeg);
	}
	
	public UusOskus seaLubatud(Boolean lubatud){
		this.lubatud = lubatud;		
		return this;
	}
	
	
	public List<UusOskus> uuedOskused (String ID){
		return uuedOskused.entrySet().stream()
				.filter(p -> p.getValue().lisaja.equals(ID))
				.map(Map.Entry::getValue)
				.collect(Collectors.toList());
	}
	
	
}
