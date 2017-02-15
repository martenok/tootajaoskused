package application;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



public class LoeDemoAndmed {
	
	static final String TOOTAJAD_FAIL = "tootajad.txt";
	static String OSKUSED_FAIL = "oskused.txt";
	
	static String INIM_OSKUSED = "inim_oskused.txt";
	static String KOOLITUSED = "koolitused.txt";
	
//	static String tootFailSalv = "tootajad.txt";
//	static String oskFailSalv = "oskused.txt";
	
//Loe töötajad
	LoeDemoAndmed() {}
	
	
	static void LoeTootajad(){
//		List <String> read = LoeRead(TOOTAJAD_FAIL);
		List <String> read = KryptitudSisseValja.loeRead(TOOTAJAD_FAIL);
		for (String s: read){
			String tykid[] = s.split(";");
			Tootaja t = Tootaja.uusTootaja(tykid[0], tykid[1], Main.praeguneKasutaja);
			
			switch (tykid.length) {
			case 8: ;
			case 7: t.mitteAktiivneKuup = (!tykid[6].equals("null")) ? LocalDateTime.parse(tykid[6])  : null;
			case 6: t.onAdmin = Boolean.valueOf(tykid[5]);
			case 5: t.muutmiseKuup = LocalDateTime.parse(tykid[4]);
			case 4: t.lisamiseKuup = LocalDateTime.parse(tykid[3]);
			case 3: t.amet = tykid[2];
//			default: break;
			}	
		}
	}
	

	static void SalvestaTootajad(){
		List <String> read = new ArrayList<String>();
		
		for (String s: Tootaja.tootajad.keySet()){
			read.add(String.format("%s;%s;%s;%s;%s;%s;%s",s ,
					Tootaja.tootajad.get(s).nimi, 
					Tootaja.tootajad.get(s).amet, 
					Tootaja.tootajad.get(s).lisamiseKuup, 
					Tootaja.tootajad.get(s).muutmiseKuup, 
					Tootaja.tootajad.get(s).onAdmin,
//					Tootaja.tootajad.get(s).aktiivne,
					Tootaja.tootajad.get(s).mitteAktiivneKuup
//					Tootaja.tootajad.get(s).koolitused
					));
		}
	
		KryptitudSisseValja.salvestaRead(TOOTAJAD_FAIL, read);
//		SalvestaRead(TOOTAJAD_FAIL, read);
	}	
	
	
	static void loeOskused(){
//		List <String> read = LoeRead(OSKUSED_FAIL);
		List <String> read = KryptitudSisseValja.loeRead(OSKUSED_FAIL);
		
		for (String s: read){
			
			String tykid[] = s.split(";");
			Oskus o = Oskus.uusOskus(tykid[0], tykid[1], tykid[2], tykid[3]);
			
			List<String> tasemed = new ArrayList<String>();
			tasemed.addAll(Arrays.asList(tykid).subList(4, tykid.length));
			
			o.lisaTasemed(tasemed, Main.praeguneKasutaja.id);		
		}		
	}
	

	static void salvestaOskused(){
		List <String> read = new ArrayList<String>();
		
		String s;
		String k;
		
		for (Oskus x : Oskus.oskused.values()){
			k = x.tasemed.entrySet().stream()
				.sorted((z, y) -> Integer.compare(z.getKey().ordinal(), y.getKey().ordinal()))
				.map(p -> p.getValue())
				.collect(Collectors.joining(";"));
			
			s = String.format("%s;%s;%s;%s;%s", x.id, x.nimetus, x.kirjeldus, x.lisaja, k);
			
//			System.out.println(s);	
			read.add(s);
		}
		
		KryptitudSisseValja.salvestaRead(OSKUSED_FAIL, read);
//		SalvestaRead(OSKUSED_FAIL, read);
	}
	
	
	
	static void loeInimOskused(){
//		List <String> read = LoeRead(INIM_OSKUSED);
		List <String> read = KryptitudSisseValja.loeRead(INIM_OSKUSED);
		for (String s: read){
			String tykid[] = s.split(";");
//			System.out.println("---" + s);		
			Tootaja.tootajad.get(tykid[0]).lisaOskus(Oskus.oskused.get(tykid[1]), Tase.valueOf(tykid[2]), false, Main.praeguneKasutaja);			
		}	
	}
	
	
	static void salvestaInimOskused(){
		List <String> read = new ArrayList<String>();
		
		String s;
		
		for (Tootaja x : Tootaja.tootajad.values()){	
			if (x.oskused.size() > 0) {
				for (String y : x.oskused.keySet() ){
					
					s = String.format("%s;%s;%s", x.id, y, x.oskused.get(y));
//					System.out.println(s);
					read.add(s);
				}
			}
		}
		KryptitudSisseValja.salvestaRead(INIM_OSKUSED, read);
//		SalvestaRead(INIM_OSKUSED, read);
	}
	
	static void salvestaKoolitused(){
		KryptitudSisseValja.encryptObjectStream(KOOLITUSED);
	}
	
	static void loeKoolitused(){
		KryptitudSisseValja.decryptObjectStream(KOOLITUSED);
	}
	
	public static List<String> LoeRead(String failinimi) {
		List<String> read= new ArrayList<String>();
		try {
			FileReader fr = new FileReader(failinimi);
			BufferedReader br = new BufferedReader(fr);
		
			String rida;
						
			while ((rida = br.readLine()) != null) {
				read.add(rida);
			}						

			br.close();
			
			}
			catch (Exception ex) {System.out.println(ex.getMessage());}
		
		return read;
	}
	
	
	public static void SalvestaRead(String failinimi, List<String> read) {
		
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(failinimi))){
			
			for (String x: read){
				bw.write(x);
				bw.newLine();
			}	
		}		
		catch (Exception ex) {}		
	}
	

	
}
