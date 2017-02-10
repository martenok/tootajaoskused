package application;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Tootaja {
		
		static HashMap<String, Tootaja> tootajad = new HashMap<>();
//		static int esimesed = 2;
		
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
		
		protected Tootaja(){	}

		
		private Tootaja(String id, String nimi , String amet){
			this.nimi=nimi;
			this.id=id;
			this.amet=amet;
			lisamiseKuup = LocalDateTime.now();
			muutmiseKuup = LocalDateTime.now();
//			roll = Roll.tava;
//			aktiivne = true;
			tootajad.put(id, this);	
			new Muudatus("** SÜSTEEM **", this.id, String.format("Töötaja id:%s (%s) lisatud", this.id, this.nimi));
		}
		
		
		static Tootaja uusTootaja(String id, String nimi, String amet){
			Tootaja x = tootajad.get(id);
			if (x== null){
				return new Tootaja(id, nimi, amet);
			}
			else {
				//TODO Veateade tootaja olemas
				return x;		
			}
		}
		
		static Tootaja uusTootaja( String id, String nimi){
			return uusTootaja(id, nimi, "määramata");
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
	
		
		
		public Tootaja lisaOskus (Oskus oskus, Tase tase, Boolean muudatus){
		    if (oskus != null) {
                this.oskused.put(oskus.id, tase);
               if (muudatus) this.muutmiseKuup = LocalDateTime.now();
                    new Muudatus(this.id, this.id, String.format("Töötajale id:%s (%s) lisatud oskus %s", this.id, this.nimi, oskus.nimetus));
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
				new Muudatus(kes.id, this.id, String.format("Töötaja id:%s (%s) admin=%s", this.id, this.nimi, this.onAdmin));
			}
			return this;
		}
		
		
		Tootaja muudaAktiivne(Tootaja kes, Boolean aktiivne){
			if (kes.onAdmin ){
				if (aktiivne && this.mitteAktiivneKuup == null){
					this.mitteAktiivneKuup = LocalDateTime.now();
				
					this.muutmiseKuup = LocalDateTime.now();
					new Muudatus(kes.id, this.id, String.format("Töötaja id:%s (%s) muudetud mitteaktiivseks", this.id, this.nimi, this.onAdmin));
				}
				else if (!aktiivne && this.mitteAktiivneKuup != null)
					this.mitteAktiivneKuup = null;
					this.muutmiseKuup = LocalDateTime.now();
					new Muudatus(kes.id, this.id, String.format("Töötaja id:%s (%s) muudetud aktiivseks", this.id, this.nimi, this.onAdmin));				 	
			}
			return this;
		}
		
		
		
		public String toString(){
			return String.format("%s (%s), amet: %s, %s.", nimi, id, amet, lisamiseKuup);
		}
	
	
	
}
