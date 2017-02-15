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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class LisaKoolitusController implements Initializable {
	public MainController mc = null;
	public TootajaTabel muudetavTootaja;
	
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
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
	
    
	public void pildiLaadimiseNupp(ActionEvent actionEvent) {
		
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilterKoik = new FileChooser.ExtensionFilter("Kõik failid", "*.*");
		fileChooser.getExtensionFilters().addAll(extFilterKoik);

		fail = fileChooser.showOpenDialog(null);
		
		if (fail != null) txtFail.setText(fail.getName());

	}
	
	public void sulgeKatkesta(ActionEvent event){
		if (event.getSource() == nuppKatkesta){
			Stage lava = (Stage)nuppKatkesta.getScene().getWindow();
			lava.close();
		}
	}
	
	public void sulgeSalvesta(ActionEvent event){	
		Tootaja tootaja = Tootaja.tootajad.get(Main.nahtavTootaja.ID.getValue());
		if (txtFail.getText().equals("")) Koolitus.lisaTunnistus(tootaja.id, txtKirjeldus.getText());
		
		else {
			
			if (Tootaja.annaKaust(Main.nahtavTootaja) != null) {
				
			    Path source = Paths.get(fail.getAbsolutePath());
			    Path destination = Paths.get(Tootaja.annaKaust(Main.nahtavTootaja) + "\\" + fail.getName());
			 
			    try {
					Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Koolitus.lisaTunnistus(tootaja.id, null, txtKirjeldus.getText(), fail.getName());
			}
		}
		
		if (event.getSource() == nuppSalvesta){
			Stage lava = (Stage)nuppSalvesta.getScene().getWindow();
			lava.close();
		}
		
		if (this.mc != null) {
			mc.naitaTootajaDetaile(muudetavTootaja);
			mc.naitaTootajaLogi(muudetavTootaja);
		}
		
	}
	
	public void onShowing(WindowEvent e){
		lblTootaja.setText(String.format("%s (%s)", muudetavTootaja.getNimi(), muudetavTootaja.getID()));
	}
}
