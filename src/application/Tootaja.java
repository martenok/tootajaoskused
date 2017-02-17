package application;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Tootaja {
		
		static HashMap<String, Tootaja> tootajad = new HashMap<>();
		
		String nimi;
		String id;
		String amet;
//		Roll roll;
		Boolean onAdmin = false;
//		Boolean aktiivne;
		LocalDateTime muutmiseKuup;
		LocalDateTime lisamiseKuup;
		LocalDateTime mitteAktiivneKuup;
		
		public HashMap<String, Tase> oskused = new HashMap<>();
//		public TreeSet<String> koolitused = new TreeSet<>();
		
		protected Tootaja(){	}

		
		private Tootaja(String id, String nimi , String amet){
			this.nimi=nimi;
			this.id=id;
			this.amet=amet;
			lisamiseKuup = LocalDateTime.now();
			muutmiseKuup = LocalDateTime.now();
			
			tootajad.put(id, this);	
		}
		
		
		static Tootaja uusTootaja(String id, String nimi, String amet, Tootaja kes){
			Tootaja x = tootajad.get(id);
			if (x== null && kes.onAdmin){
				new Muudatus(kes.id, id, String.format("Lisati töötaja %s (id:%s)", nimi, id));
				return new Tootaja(id, nimi, amet);
			}
			else {
				new Muudatus(kes.id, x.id, String.format("Püüti lisada töötajat %s (id:%s)", x.nimi, x.id));
				return x;		
			}
		}
		
		static Tootaja sysAdmin(String id, String nimi, String amet){
			return new Tootaja(id, nimi, amet);
		}
		
		static Tootaja uusTootaja( String id, String nimi, Tootaja kes){
			return uusTootaja(id, nimi, "määramata", kes);
		}
		
		static List<Tootaja> esimesed(int esimesed){
			if (esimesed > tootajad.size()) esimesed = tootajad.size();
			
			return tootajad.entrySet().stream()
//					.filter(a -> a.getValue().mitteAktiivneKuup == null)
					.sorted( (x,y) -> y.getValue().muutmiseKuup.compareTo(x.getValue().muutmiseKuup)) // Kui kasvavas järjekorras siis x y kui kahanevas siis y x
					.map(Map.Entry::getValue)
					.collect(Collectors.toList()).subList(0, esimesed);
		}
		
		static List<Tootaja> oskusegaList(List<Tootaja> t, String oID){
			return  t.stream()
					.filter(p -> p.oskused.containsKey(oID))
					.collect(Collectors.toList());
		}
		
		
		static List<Tootaja> leiaNimega(String nimi){
			List<Tootaja> ttja = tootajad.entrySet().stream()
					.filter(p -> p.getValue().nimi.contains(nimi))
					.filter(a -> a.getValue().mitteAktiivneKuup == null)
					.map(Map.Entry::getValue)
					.collect(Collectors.toList());
//					.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
			return ttja;
		}

		
		
		static HashMap<String, Tootaja> aktiivsedMap(){
			return (HashMap<String, Tootaja>) tootajad.entrySet().stream()
					.filter(a -> a.getValue().mitteAktiivneKuup == null)
					.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		}
		
		
		static Map<String, Tootaja> mitteAktiivsedMap(Map<String, Tootaja> t){
			return t.entrySet().stream()
					.filter(a -> a.getValue().mitteAktiivneKuup != null)
					.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		}
	
		
//		public Tootaja lisaKoolitus(Koolitus koolitus,  Boolean muudatus, Tootaja kes){
//		    if (koolitus != null && (kes.onAdmin || kes.id.equals(this.id))) {
//	               this.koolitused.add(koolitus.id);
//	               if (muudatus) this.muutmiseKuup = LocalDateTime.now();
//	                    new Muudatus(kes.id, this.id, String.format("Töötajale %s (id:%s) lisatud koolitus/eksam %s", this.nimi, this.id, koolitus.kirjeldus));
//	                   //TODO Muudatus oskus lisatud
//	             } 
//			    else {
//	                    Main.veaKorv("Oskust ei leitud!");
//	            }			
//			return this;
//		}
		
		
		public Tootaja lisaOskus (Oskus oskus, Tase tase, Boolean muudatus, Tootaja kes){
		    if (oskus != null && (kes.onAdmin || kes.id.equals(this.id))) {
               this.oskused.put(oskus.id, tase);
               if (muudatus) this.muutmiseKuup = LocalDateTime.now();
                    new Muudatus(kes.id, this.id, String.format("Töötajale %s (id:%s) lisatud oskus %s", this.nimi, this.id, oskus.nimetus));
                   //TODO Muudatus oskus lisatud
             } 
		    else {
                    Main.veaKorv("Oskust ei leitud!");
            }
	        
		    return this;
	    }

		Tootaja muudaAdmin(Tootaja kes, Boolean admin){
			if (kes.onAdmin && !kes.id.equals(this.id)){
				this.onAdmin = admin;
				this.muutmiseKuup = LocalDateTime.now();
				new Muudatus(kes.id, this.id, String.format("Töötaja %s (id:%s) admin=%s", this.nimi, this.id, this.onAdmin));
			}
			return this;
		}
		
		
		Tootaja muudaAktiivne(Tootaja kes, Boolean aktiivne){
			if (kes.onAdmin ){
				if (aktiivne && this.mitteAktiivneKuup == null){
					this.mitteAktiivneKuup = LocalDateTime.now();
				
					this.muutmiseKuup = LocalDateTime.now();
					new Muudatus(kes.id, this.id, String.format("Töötaja %s (id:%s) muudetud mitteaktiivseks", this.nimi, this.id, this.onAdmin));
				}
				else if (!aktiivne && this.mitteAktiivneKuup != null)
					this.mitteAktiivneKuup = null;
					this.muutmiseKuup = LocalDateTime.now();
					new Muudatus(kes.id, this.id, String.format("Töötaja %s (id:%s) muudetud aktiivseks", this.nimi, this.id, this.onAdmin));				 	
			}
			return this;
		}
		
		Tootaja muudaNimi(Tootaja kes, String nimi){
			if (nimi != null && (kes.onAdmin || kes.id.equals(this.id))) {
				new Muudatus(kes.id, this.id, String.format("Töötaja %s (id:%s) nimi muudetud %s", this.nimi, this.id, nimi));				
				this.nimi = nimi;
			}
			return this;
		}
		
		Tootaja muudaAmet(Tootaja kes, String amet){
			if (amet != null && (kes.onAdmin || kes.id.equals(this.id))) {
				new Muudatus(kes.id, this.id, String.format("Töötaja %s (id:%s) amet muudetud %s", this.nimi, this.id, amet));
				this.amet = amet;		
			}
			return this;
		}
		
		static File annaKaust(TootajaTabel tootaja) {
			String kaust = Integer.toString(tootaja.getID().hashCode());
			File dir = new File(kaust);
			if (dir.exists()) return dir;
			
		    try{
		        dir.mkdir();
		        return dir;
		    } 
		    catch(SecurityException se){
		        return null;
		    	//handle it
		    }
		    
		}	
		
		public String toString(){
			return String.format("%s (%s), amet: %s, %s.", nimi, id, amet, lisamiseKuup);
		}
	
	
	
}
