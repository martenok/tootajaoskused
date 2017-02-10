package application;
import java.io.IOException;
//package application;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MainController implements Initializable {

	private ObservableList<TootajaTabel> dataTootajad = FXCollections.observableArrayList();
	private ObservableList<TootajaTabel> kasutajad = FXCollections.observableArrayList();
//	private HashMap<String, Tootaja> kasutajad;
	
	private boolean algus = true;
	private boolean tootajaMuutmine = true;
	
	
//	TootajaTabel nahtavTootaja;
	
	FilteredList<TootajaTabel> filteredData;
	
	@FXML
	private TextField txtFilter;
	
	@FXML
	private TableView<TootajaTabel> showTable;
	
	@FXML
	private TableColumn<TootajaTabel, String> veergNimi;
	
	@FXML
	private TableColumn<TootajaTabel, String> veergID;
	
	@FXML
	private TableColumn<TootajaTabel, String> veergLisatud;
	
	@FXML
	private TableColumn<TootajaTabel, String> veergAktiivne;

	@FXML
	private TableColumn<TootajaTabel, String> veergAmet;
	
	@FXML
	private TextField txtNimi;

	@FXML
	private TextInputControl txtID;

	@FXML
	private TextInputControl txtAmet;
	
	@FXML
	private CheckBox chkAdmin;
	
	@FXML
	private ComboBox<String> cmbStaatus;
	
	@FXML
	private TableView<OskusUI> oskusTabel;
	
	@FXML
	private TableColumn<OskusUI, String> veergOskus;
	
	@FXML
	private TableColumn<OskusUI, String> veergTase;
	
	@FXML
	private TableColumn<OskusUI, String> veergKirjeldus;
	
	@FXML
	private TextField txtFilterID;
	
	@FXML
	private TextField txtFilterAmet;
	
	@FXML
	private ComboBox<String> cmbFilterStaatus;
	
	@FXML
	private Button cmdLisaOskus;

	@FXML
	private TextField txtLisamiseAeg;
	
	@FXML
	private TextField txtViimatiMuudetud;
	
	@FXML
	private AnchorPane acpYlemine;
	
	@FXML
	private Button cmdKatkesta;
	
	@FXML
	private Button cmdSalvesta;
	
    @FXML
    private ComboBox<TootajaTabel> cmbKasutaja;
    
//	public MainController () {
//		
//		Tootaja.esimesed(Tootaja.tootajad.size()).stream().map( p -> new TootajaTabel(p.nimi , p.id, p.lisamiseKuup, p.mitteAktiivneKuup))
//		.collect(Collectors.toList()).forEach(p -> dataTootajad.add(p))  ;
//	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		
	if (algus) {
	 Tootaja.tootajad.entrySet().stream()
		.filter(a -> a.getValue().mitteAktiivneKuup == null)
		.sorted( (x,y) -> y.getValue().muutmiseKuup.compareTo(x.getValue().muutmiseKuup))
		.limit(10)
		.map(Map.Entry::getValue)
	 	.map(  p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup))
		.collect(Collectors.toList()).forEach(p -> dataTootajad.add(p));
	 
//	 Tootaja.tootajad.entrySet().stream()
//	 	.filter(a -> a.getValue().mitteAktiivneKuup == null)
//	 	.map(p -> new TootajaTabel(p.getValue().nimi , p..id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup))
//	 	.collect(Collectors.toList()).forEach(p -> kasutajad.add(p));
	 
	 Tootaja.aktiivsedMap().entrySet().stream()
			 	.map(Map.Entry::getValue)
			 	.map(  p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup))
			 	.collect(Collectors.toList()).forEach(p -> kasutajad.add(p));
	 
	 cmbKasutaja.getItems().addAll(kasutajad);
	 System.out.println(Main.praeguneKasutaja.id);
	 
	 for (TootajaTabel i : kasutajad){
	  System.out.println(i.getID());
		  if (i.getID().equals(Main.praeguneKasutaja.id)) {
			  String s = i.toString();
			  cmbKasutaja.setValue(i);
			  
			  break;
		  }
	 }
	 
	}

	
	filteredData = new FilteredList<>(dataTootajad, p -> true);
	// 3. Wrap the FilteredList in a SortedList. 
    SortedList<TootajaTabel> sortedData = new SortedList<>(filteredData);

    // 4. Bind the SortedList comparator to the TableView comparator.
    sortedData.comparatorProperty().bind(showTable.comparatorProperty());

    // 5. Add sorted (and filtered) data to the table.
    this.showTable.setItems(sortedData);
	

	
	cmbStaatus.getItems().addAll("Aktiivne", "Mitte aktiivne");
	
	cmbFilterStaatus.getItems().addAll("Aktiivsed", "Mitte aktiivsed", "Kõik");
	
	cmbFilterStaatus.setValue("Aktiivsed");
	
	showTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> naitaTootajaDetaile(newValue));
	
	txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(person -> {
        	 return tootajaFilter(person, newValue, txtFilterID.getText(), txtFilterAmet.getText(), cmbFilterStaatus.getValue());
        	
        });
    });

	txtFilterAmet.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(person -> {
			return tootajaFilter(person, txtFilter.getText(), txtFilterID.getText(), newValue, cmbFilterStaatus.getValue());
			
        });
    });
	
	txtFilterID.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(person -> {
			return tootajaFilter(person, txtFilter.getText(), newValue, txtFilterAmet.getText(), cmbFilterStaatus.getValue());
			
//            // If filter text is empty, display all persons.
//            if (newValue == null || newValue.isEmpty()) {
//                return true;
//            }
//
//            if (person.getID().contains(newValue)) {
//                return true; // Filter matches first name.
//
//            }
//            return false; // Does not match.
        });
    });
    
	
	
	cmbFilterStaatus.valueProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(person -> {
			return tootajaFilter(person, txtFilter.getText(), txtFilterID.getText(), txtFilterAmet.getText(), newValue);
			
        });
		
	});
	
	
	}

	boolean tootajaFilter(TootajaTabel toot, String uusNimi, String uusID, String uusAmet, String uusAkt){
       
		if ((uusNimi == null || uusNimi.isEmpty() ) 
			&& (uusID == null || uusID.isEmpty())
			&& (uusAmet == null || uusAmet.isEmpty())
			&& uusAkt == "Kõik") {
            return true;
        }

        if (toot.getID().contains(uusID) 
        	&& toot.getNimi().contains(uusNimi)
        	&& toot.getAmet().contains(uusAmet)	
        	&& (toot.getMitteAktiivneKuup() == "" && uusAkt == "Aktiivsed" 
        	   || toot.getMitteAktiivneKuup() != "" && uusAkt == "Mitte aktiivsed" 
        	   || uusAkt == "Kõik")
        		) {
            return true; 

        }		
				
		return false;
	}
	
	
	
	
	public void MoniMeetod(ActionEvent e) {
//		ObservableList<TootajaTabel> data = FXCollections.observableArrayList();
//		Tootaja.esimesed(3).stream().map( p -> new TootajaTabel(p.nimi , p.id))
//		.collect(Collectors.toList()).forEach(p -> data.add(p))  ;
//		
//		this.showTable.setItems(data);
	}

	public void nimeFilter(ActionEvent e) {
		koikTootajad();
//		String s =  txtFilter.getText();
//		
//		ObservableList<TootajaTabel> data = FXCollections.observableArrayList();
//		
//		Tootaja.leiaNimega(s).stream()
//		.map( p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup))
//		.collect(Collectors.toList()).forEach(p -> data.add(p));
//		
//		this.showTable.setItems(data);
	}
	
	private void koikTootajad(){
		cmbFilterStaatus.setValue("Kõik");
		if (algus) { 
			dataTootajad.clear();
			Tootaja.tootajad.entrySet().stream()
//			.filter(a -> a.getValue().mitteAktiivneKuup == null)
//			.sorted( (x,y) -> y.getValue().muutmiseKuup.compareTo(x.getValue().muutmiseKuup))
//			.limit(10)
			.map(Map.Entry::getValue)
		 	.map(  p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup))
			.collect(Collectors.toList()).forEach(p -> dataTootajad.add(p));
		 
		   filteredData = new FilteredList<>(dataTootajad, p -> true);
		  
			// 3. Wrap the FilteredList in a SortedList. 
		    SortedList<TootajaTabel> sortedData = new SortedList<>(filteredData);

		    // 4. Bind the SortedList comparator to the TableView comparator.
		    sortedData.comparatorProperty().bind(showTable.comparatorProperty());

		    // 5. Add sorted (and filtered) data to the table.
		    this.showTable.setItems(sortedData);
//		    showTable.refresh();
		  
		  
		  algus = false;
		}
	}
	
	
	void naitaOskusi (List<OskusUI> oskused){

	}
	
	
	public void muudaTootaja() {
		
		if (tootajaMuutmine){ //tootaja muutmine
			TootajaTabel tootaja = Main.nahtavTootaja;
			
			Tootaja t = Tootaja.tootajad.get(tootaja.getID().toString());
			
			t.nimi = txtNimi.getText();
			tootaja.setNimi(t.nimi);
			
			t.amet = txtAmet.getText();
			tootaja.setAmet(t.amet);
			
			t.muudaAdmin(Main.praeguneKasutaja, chkAdmin.isSelected());
			
			if (cmbStaatus.getValue() == "Aktiivne"){
				t.muudaAktiivne(Main.praeguneKasutaja, false);		
			}
			else if (cmbStaatus.getValue() == "Mitte aktiivne"){
				t.muudaAktiivne(Main.praeguneKasutaja, true);
			}
			if (t.mitteAktiivneKuup != null) 
				tootaja.setMitteAktiivneKuup(t.mitteAktiivneKuup.toString());
			else tootaja.setMitteAktiivneKuup("");
			
			txtID.editableProperty().setValue(false);
			
			}
		else { //tootaja lisamine
		
			if (txtID.getText().length() == 11
					&& !txtNimi.getText().isEmpty() ){
				
				Tootaja t = Tootaja.uusTootaja(txtID.getText(),  txtNimi.getText());
				
				if (chkAdmin.isSelected()) t.onAdmin = true;
				if (!txtAmet.getText().isEmpty()) t.amet = txtAmet.getText();
				
				TootajaTabel p = new TootajaTabel(t.nimi , t.id, t.amet, t.lisamiseKuup, t.mitteAktiivneKuup);
				dataTootajad.add(p);
				
				lisaTootajaNupud(false);
				
				tootajaMuutmine = true;
				
			}
		}
		
		
		koikTootajad();
		showTable.refresh();
		
	}
	
	public void lisaOskusSysteemi() throws IOException{
		
		Parent uusAken = FXMLLoader.load(getClass().getResource("/application/OskusSysteemi.fxml"));

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(cmdLisaOskus.getScene().getWindow());
        
//      VBox dialogVbox = new VBox(20);       
//      dialogVbox.getChildren().add(new Text("This is a Dialog"));
        
        Scene dialogScene = new Scene(uusAken, 715, 520);
       
        dialog.setScene(dialogScene);
        dialog.setTitle("Lisa oskus");
        
        dialog.show();
		
	}
	

	public void lisaOskus(ActionEvent e) throws IOException{
//		TootajaTabel tootaja = nahtavTootaja;
		
		Parent uusAken = FXMLLoader.load(getClass().getResource("/application/LisaOskusAken.fxml"));

        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(cmdLisaOskus.getScene().getWindow());
        
//      VBox dialogVbox = new VBox(20);       
//      dialogVbox.getChildren().add(new Text("This is a Dialog"));
        
        Scene dialogScene = new Scene(uusAken, 600, 400);
       
        dialog.setScene(dialogScene);
        dialog.setTitle("Lisa oskus");
        
        dialog.show();
	       
	 }

	public void lisaTootaja(ActionEvent e){
		System.out.println("Lisan töötajat");
		nulliTootajaDet();
		
		lisaTootajaNupud(true);
		
		tootajaMuutmine = false;
		
	}
	
	private void lisaTootajaNupud(Boolean nahtavus){
		txtID.editableProperty().setValue(nahtavus);
		acpYlemine.disableProperty().setValue(nahtavus);
		cmdKatkesta.setVisible(nahtavus);
		cmdKatkesta.disableProperty().setValue(!nahtavus);
//		cmdSalvesta.setVisible(nahtavus);
	
	}
	
	public void katkestaLisaTootaja(){
		
		lisaTootajaNupud(false);
		
		tootajaMuutmine = true;
		
	}
	
	
	void naitaTootajaDetaile (TootajaTabel tootaja){
				
		if (tootaja != null) {
		        // Fill the labels with info from the person object.
		        txtNimi.setText(tootaja.getNimi());
		        txtID.setText(tootaja.getID());
		        txtAmet.setText(tootaja.getAmet());
		        
		        Tootaja t = Tootaja.tootajad.get(tootaja.getID());
		        
		       chkAdmin.setSelected(Tootaja.tootajad.get(tootaja.getID().toString()).onAdmin);
		       	        
		        if (tootaja.mitteAktiivneKuup.getValue().equals("")){
		        	cmbStaatus.setValue("Aktiivne");
		        }
		        	else {
		        		cmbStaatus.setValue("Mitte aktiivne");
		        	}
		        
		        
		        ObservableList<OskusUI> dataO = FXCollections.observableArrayList();
		        Tootaja.tootajad.get(tootaja.getID().toString()).oskused.entrySet().stream()
		        		.map(p -> new OskusUI(p.getKey(),
						        				Oskus.oskused.get(p.getKey()).nimetus, 
						        				p.getValue(), 
						        				Oskus.oskused.get(p.getKey()).tasemed.get(p.getValue())))
		        		.collect(Collectors.toList()).forEach(p -> dataO.add(p));

		        

		        
		        cmdLisaOskus.setDisable(false);
		         
		        this.oskusTabel.setItems(dataO);
		        Main.nahtavTootaja = tootaja;
		        
		        if (t.lisamiseKuup != null) {
		        	txtLisamiseAeg.setText((tootaja.getLisamiseKuup().toString()));
		        }
		        if (t.muutmiseKuup != null) {
		        	txtViimatiMuudetud.setText(tootaja.kuupaevaFormatter.format(t.muutmiseKuup));
		        }
		        else txtViimatiMuudetud.setText("");
		        
//		        txtViimatiMuudetud.setText("Kuku");
		       
		        
		        // TODO: We need a way to convert the birthday into a String! 
		        // birthdayLabel.setText(...);
		    } 
		 else {
		        // Person is null, remove all the text.
//		        txtNimi.setText("");
//		        txtID.setText("");
//		        txtAmet.setText("");
//		        cmbStaatus.setValue("");
//		        txtViimatiMuudetud.setText("");
//		        txtLisamiseAeg.setText("");
//		        Main.nahtavTootaja = null;
//		        cmdLisaOskus.setDisable(true);
		        
			 nulliTootajaDet();
		    }
		
	}
	
	void nulliTootajaDet(){
		
		oskusTabel.setItems(null);
        txtNimi.setText("");
        txtID.setText("");
        txtAmet.setText("");
        cmbStaatus.setValue("");
        txtViimatiMuudetud.setText("");
        txtLisamiseAeg.setText("");
        chkAdmin.setSelected(false);
       
        txtNimi.setText("");
       
        Main.nahtavTootaja = null;
        cmdLisaOskus.setDisable(true);
	}
	
	
}
