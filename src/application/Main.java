package application;
import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
	static List<String> vead = new ArrayList<>();
	static TootajaTabel nahtavTootaja;
	static Tootaja praeguneKasutaja;
	
	@Override
	public void start(Stage primaryStage) {
		try {

				
			Parent root = FXMLLoader.load(getClass().getResource("/application/Nimekiri.fxml"));
			
			Scene scene = new Scene(root,935,871);
//				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		

		
		new LoeDemoAndmed();	
		
		praeguneKasutaja = Tootaja.tootajad.get("38201020255");

//		Tootaja.tootajad.get("35210112353").lisaOskus(Oskus.oskused.get("O1"), Tase.Keskmine);
//		Tootaja.tootajad.get("35210112353").lisaOskus(Oskus.oskused.get("O3"), Tase.VägaHea);
//		Tootaja.tootajad.get("38201020255").onAdmin = true;
//		
//		Tootaja.tootajad.get("47210112347").mitteAktiivneKuup = LocalDateTime.now();
		
		for (String x: Tootaja.tootajad.keySet()){
			
			System.out.println(Tootaja.tootajad.get(x));
		}
		
		
		for (String x: Oskus.oskused.keySet()){
			
			System.out.println(Oskus.oskused.get(x));
//			System.out.println(x);
		}
		
		Oskus o = Oskus.oskused.get("O2");
		
		Tootaja t = Tootaja.tootajad.get("38201020255");
		System.out.println(String.format("Töötaja : %s (%s) oskused:",t.nimi, t.id));
		
		for (String x: t.oskused.keySet()){
			System.out.println(String.format("%s, tase: %s (%s)", Oskus.oskused.get(x).nimetus, t.oskused.get(x), Oskus.oskused.get(x).tasemed.get(t.oskused.get(x)) ));
			//System.out.println(x);
		}
		
		try {
	    Thread.sleep(1);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
//		Muudatus m = new Muudatus(t.id, "t", "Testimine");	
//		Muudatus n = new Muudatus(t.id, "o", "Testimine 2");	
//		new Muudatus(t.id, "oo", "Testimine 3");
		
//		System.out.println(m);
//		System.out.println(n);
		
//		Tootaja t2 = Tootaja.tootajad.get("48003040345");
//		t2.onAdmin = true;
//		
//		t.muudaAdmin(t2, true);
		
//		try {
//		    Thread.sleep(1);
//			} catch(InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
//		
//		t2.muudaAdmin(t, true);
//		
//		try {
//		    Thread.sleep(1);
//			} catch(InterruptedException ex) {
//				Thread.currentThread().interrupt();
//			}
		
		//t.muudaAdmin(t2, false);
		
//		o.MuudaKirjeldus(t2, "Vanast uus")
//		  .MuudaNimi(t2, "lEIutamine")
//		  .MuudaNimi(t2, "leiutamine");
		
		for (Muudatus x: Muudatus.muutused){
			System.out.println(x);
			//System.out.println(x);
		}
		
//		for (String x: Oskus.oskused.keySet()){
//			
//			System.out.println(Oskus.oskused.get(x));
////			System.out.println(x);
//		}
		
		

		for (Tootaja x: Tootaja.esimesed(3)){
			System.out.println(x.nimi + "----" + x.muutmiseKuup);
			
		}
		
		for (Tootaja x: Tootaja.leiaNimega("i")){
			System.out.println(x.nimi + "****" + x.muutmiseKuup);
			
		}
		
//		for (Muudatus x: Muudatus.viimasedMuudatused(o.id, 3)){
//			System.out.println(x);
//			
//		}
		
		launch(args);
		
		LoeDemoAndmed.SalvestaTootajad();
		
		LoeDemoAndmed.salvestaOskused();
		LoeDemoAndmed.salvestaInimOskused();
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
			
			catch (Exception ex) {}
		
		return read;
	}
	
	
	
	public static void veaKorv(String viga){
		vead.add(viga);
	}
	

	
	
}
