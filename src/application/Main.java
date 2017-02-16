package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application{
	static List<String> vead = new ArrayList<>();
	static TootajaTabel nahtavTootaja;
	static Tootaja praeguneKasutaja;
//	BorderPane r;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			FXMLLoader laadija = new FXMLLoader(getClass().getResource("/application/Nimekiri.fxml"));
			
			Parent root = laadija.load();
			MainController mc = (MainController) laadija.getController();

			Scene scene = new Scene(root,935,871);
			
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LoeDemoAndmed.loeKoolitused();	
		praeguneKasutaja = Tootaja.sysAdmin("11101010101", "***SÜSTEEM***", "Süsteemi administraator");
		praeguneKasutaja.onAdmin=true;
		
		LoeDemoAndmed.LoeTootajad();
		
		praeguneKasutaja = Tootaja.tootajad.get("38201020255");
		
		Tootaja.tootajad.remove("11101010101");
		
		LoeDemoAndmed.loeOskused();
		LoeDemoAndmed.loeInimOskused();
		LoeDemoAndmed.loeKoolitused();
		
		
//		for (Koolitus x : Koolitus.koolitused.values()){
//			System.out.println(x.kirjeldus);
//		}

		
//		for (String x: Tootaja.tootajad.keySet()){
//			
//			System.out.println(Tootaja.tootajad.get(x));
//		}
//		
//		
//		for (String x: Oskus.oskused.keySet()){
//			System.out.println(Oskus.oskused.get(x));
////			System.out.println(x);
//		}
//			
//		for (Muudatus x: Muudatus.muutused){
//			System.out.println(x);
//			//System.out.println(x);
//		}
//		
//
//		for (Tootaja x: Tootaja.esimesed(3)){
//			System.out.println(x.nimi + "----" + x.muutmiseKuup);
//			
//		}
//		
//		for (Tootaja x: Tootaja.leiaNimega("i")){
//			System.out.println(x.nimi + "****" + x.muutmiseKuup);
//			
//		}
		
		
		launch(args);
		
		LoeDemoAndmed.SalvestaTootajad();
		
		LoeDemoAndmed.salvestaOskused();
		LoeDemoAndmed.salvestaInimOskused();
		
		LoeDemoAndmed.salvestaKoolitused();
		LoeDemoAndmed.salvestaLogi();
	}

	
	public static void veaKorv(String viga){
		vead.add(viga);
	}
	
}
