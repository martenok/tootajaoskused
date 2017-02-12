package application;

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
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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

		
		for (String x: Tootaja.tootajad.keySet()){
			
			System.out.println(Tootaja.tootajad.get(x));
		}
		
		
		for (String x: Oskus.oskused.keySet()){
			System.out.println(Oskus.oskused.get(x));
//			System.out.println(x);
		}
			
		for (Muudatus x: Muudatus.muutused){
			System.out.println(x);
			//System.out.println(x);
		}
		

		for (Tootaja x: Tootaja.esimesed(3)){
			System.out.println(x.nimi + "----" + x.muutmiseKuup);
			
		}
		
		for (Tootaja x: Tootaja.leiaNimega("i")){
			System.out.println(x.nimi + "****" + x.muutmiseKuup);
			
		}
		
		
		launch(args);
		
		LoeDemoAndmed.SalvestaTootajad();
		
		LoeDemoAndmed.salvestaOskused();
		LoeDemoAndmed.salvestaInimOskused();
	}

	
	public static void veaKorv(String viga){
		vead.add(viga);
	}
	
}
