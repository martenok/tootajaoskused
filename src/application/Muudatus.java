package application;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Muudatus {
	
	static List<Muudatus> muutused = new ArrayList<Muudatus>();
	static int loendur = 0;
	
	String kes;
	String mida;
	LocalDateTime millal;
	String kirjeldus;
	int id;

	Muudatus(String kes, String mida, String kirjeldus){
		this.id = loendur++;
		this.kes = kes;
		this.mida = mida;
		this.kirjeldus = kirjeldus;
		this.millal = LocalDateTime.now();
		muutused.add(this);
//		try {
//		    Thread.sleep(1);
//		} catch(InterruptedException ex) {
//		    Thread.currentThread().interrupt();
//		}
	}
	
//	Muudatus(String mida, String kirjeldus){
//		this.id = ++loendur;
//		
//		this.mida = mida;
//		this.kirjeldus = kirjeldus + "** SÜSTEEM **";
//		this.millal = LocalDateTime.now();
//		muutused.add(this);
//		try {
//		    Thread.sleep(1);
//		} catch(InterruptedException ex) {
//		    Thread.currentThread().interrupt();
//		}
//	}

	static List<Muudatus> viimasedMuudatused (String ID, int mitu){
		return muutused.stream()
				.filter(p -> p.mida.equals(ID))
				.sorted((x, y) -> y.millal.compareTo(x.millal))
				.collect(Collectors.toList()).subList(0, mitu);
	}

	static List<Muudatus> viimasedMuudatused (String ID){
		return muutused.stream()
				.filter(p -> p.mida.equals(ID))
				.sorted((x, y) -> y.millal.compareTo(x.millal))
				.collect(Collectors.toList());
	}

	
	
	public String toString(){
		if (kes != null){
			return String.format("%s: %s (%s): %s", millal, mida , kirjeldus, kes);
		}
		else return String.format("%s: %s (%s)", millal, mida , kirjeldus);
	}
	
}
