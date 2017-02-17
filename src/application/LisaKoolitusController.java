package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Koolituste ja eksamite lisamise akna kontroller
 *
 */

public class LisaKoolitusController //implements Initializable 
{
	public MainController mc = null;
	public TootajaTabel muudetavTootaja;
	public String koolitusID;
	
	File fail;
	
    @FXML
    private TextArea txtKirjeldus;

    @FXML
    private Button nuppValiFail;

    @FXML
    private TextField txtFail;

    @FXML
    private Button nuppKatkesta;

    @FXML
    private Button nuppSalvesta;
    
    @FXML
    private Label lblTootaja;
    
//	@Override
//	public void initialize(URL arg0, ResourceBundle arg1) {
//		
//	}
	
    
	public void pildiLaadimiseNupp(ActionEvent actionEvent) {
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterKoik = new FileChooser.ExtensionFilter("Kõik failid", "*.*");
		fileChooser.getExtensionFilters().addAll(extFilterKoik);

		fail = fileChooser.showOpenDialog(null);
		
		if (fail != null) txtFail.setText(fail.getName());
		else txtFail.setText("");

	}
	
	public void sulgeKatkesta(ActionEvent event){
		if (event.getSource() == nuppKatkesta){
			Stage lava = (Stage)nuppKatkesta.getScene().getWindow();
			lava.close();
		}
	}
	
	
	public void sulgeSalvesta(ActionEvent event){	
		
		Tootaja tootaja = Tootaja.tootajad.get(Main.nahtavTootaja.ID.getValue());

		if (fail == null){
			if (koolitusID.equals("")){
				Koolitus.lisaTunnistus(tootaja.id, txtKirjeldus.getText());
			}
			else {
				Koolitus.koolitused.get(koolitusID).muudaKirjeldus(Main.praeguneKasutaja, txtKirjeldus.getText());
				Koolitus.koolitused.get(koolitusID).muudaFail(Main.praeguneKasutaja, "");
			}
		}
		
		else {
			
			if (Tootaja.annaKaust(Main.nahtavTootaja) != null) {
				System.out.println(fail);
				
			    Path source = Paths.get(fail.getAbsolutePath());
			    Path destination = Paths.get(Tootaja.annaKaust(Main.nahtavTootaja) + "\\" + fail.getName());
			 
			    try {
					Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	
				if (koolitusID.equals("")){
					Koolitus.lisaTunnistus(tootaja.id, null, txtKirjeldus.getText(), fail.getName());
				}
				else {
					Koolitus.koolitused.get(koolitusID).muudaKirjeldus(Main.praeguneKasutaja, txtKirjeldus.getText());
					Koolitus.koolitused.get(koolitusID).muudaFail(Main.praeguneKasutaja, fail.getName());
				}
				
			}
		}
		

			
	
		if (this.mc != null) {
			mc.naitaTootajaDetaile(muudetavTootaja);
			mc.naitaTootajaLogi(muudetavTootaja);
		}
		
		if (event.getSource() == nuppSalvesta){
			Stage lava = (Stage)nuppSalvesta.getScene().getWindow();
			lava.close();
		}		
	}
	
	public void onShowing(WindowEvent e){
		lblTootaja.setText(String.format("%s (%s)", muudetavTootaja.getNimi(), muudetavTootaja.getID()));
		if (!koolitusID.equals("")) {
			txtKirjeldus.setText(Koolitus.koolitused.get(koolitusID).annaKirjeldus());
			if (Koolitus.koolitused.get(koolitusID).annaFail() != null) txtFail.setText(Koolitus.koolitused.get(koolitusID).annaFail());
		}

	}
}
