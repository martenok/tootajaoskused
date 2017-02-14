package application;
import java.util.*;
import java.util.stream.Collectors;

public class Oskus {
	
		private static int oskusteArv = 1;
		
		static HashMap<String, Oskus> oskused = new HashMap<String, Oskus>(); 
		
		String id;
		String nimetus;
		String kirjeldus;
		String lisaja;
		
		HashMap<Tase, String> tasemed = new HashMap<Tase, String>(); 
	
		Oskus(){}
		
		private Oskus(String ID, String nimetus, String kirjeldus, String lisajaID){
			this.nimetus = nimetus;
			this.kirjeldus = kirjeldus;
			this.id = ID;
			this.lisaja = lisajaID;
			oskusteArv ++;
			
			oskused.put(this.id, this);	
			
			new Muudatus(lisajaID, this.id, String.format("Oskus id:%s (%s) lisatud", this.id, this.nimetus));
		}
		
		
		
		public static Oskus uusOskus(String nimetus, String kirjeldus, String lisaja){
			String id = "O" + String.valueOf(oskusteArv);
			
			return new Oskus(id, nimetus, kirjeldus, lisaja);
		}
		
		
		public static Oskus uusOskus(String ID, String nimetus, String kirjeldus, String lisaja){
			Oskus a = oskused.get(ID);
			if (a != null) {
				a.kirjeldus = kirjeldus;
				new Muudatus(lisaja, a.id, String.format("Oskust id:%s (%s) püüti uuesti lisada", a.id, a.nimetus));
				return a;
			}
			else
			{
				return new Oskus(ID, nimetus, kirjeldus, lisaja);
			}
		}
		
		
		public Oskus lisaTasemed(List<String> tasemed, String lisaja){
			int i=0;
			
			for (Tase t : Tase.values()) {			
				if (t.ordinal() > tasemed.size() - 1) break;
				
				i = t.ordinal();
				if (!tasemed.get(i).trim().isEmpty()) this.tasemed.put(t,tasemed.get(i).trim());
				
//				System.out.println(this.nimetus + "---" + t + "---"+ tasemed.get(i).trim());	
			}
			
			this.tasemed.put(Tase.Määramata,"Taset pole määratud");
			new Muudatus(lisaja, this.id, String.format("Oskuse id:%s (%s) tasemeid muudeti", this.id, this.nimetus));
			return this;
		}
		
		
		public Oskus MuudaNimi (Tootaja kes, String uusNimi){
			String muudatus = "Oskuse nimetus " + this.nimetus + " -> " + uusNimi;
			if (kes.onAdmin){
				this.nimetus = uusNimi;
				new Muudatus(kes.id, this.id, muudatus);
			}
			
			return this;
		}
		
		
		public Oskus MuudaKirjeldus (Tootaja kes, String uus){
			String muudatus ="Oskuse kirjeldus " +  this.kirjeldus + " -> " + uus;
			if (kes.onAdmin){
				this.kirjeldus = uus;
				new Muudatus(kes.id, this.id, muudatus);
			}
			
			return this;
		}
		
		
		static List<Oskus> leiaNimega(String nimi){
			List<Oskus> oskus = oskused.entrySet().stream()
					.filter(p -> p.getValue().nimetus.contains(nimi))
					.map(Map.Entry::getValue)
					.collect(Collectors.toList());

			return oskus;
		}
		
//		String getID(){
//			return this.id;
//		}

		

		public String toString(){
			String s;
			s = String.format("Oskus %s. Kirjeldus: %s, kood: %s\n\r", nimetus, kirjeldus, id);
			for (Tase x: tasemed.keySet()){
				s += String.format("%s : %s\n\r",x.name(), tasemed.get(x)  );
			}
			
			return s;
		}
		
}



