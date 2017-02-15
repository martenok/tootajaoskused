package application;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//package application;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class MainController implements Initializable {

	private ObservableList<TootajaTabel> dataTootajad = FXCollections.observableArrayList();
	private ObservableList<TootajaTabel> kasutajad = FXCollections.observableArrayList();
	private ObservableList<OskusUI> dataOskused = FXCollections.observableArrayList();
	private ObservableList<KoolitusUI> dataKoolitused = FXCollections.observableArrayList();
	
//	private HashMap<String, Tootaja> kasutajad;
	
	private boolean algus = true;
	private boolean tootajaMuutmine = true;
	private TootajaTabel muudetavTootaja;
	
//	TootajaTabel nahtavTootaja;
	
	FilteredList<TootajaTabel> filteredData;
	FilteredList<KoolitusUI> filteredKoolitus;
	
	@FXML
    private Menu mnuSeadistamine;
	
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
    
	@FXML
	private Label lblAdmin;
	
    @FXML
    private ListView<OskusUI> lstOskused;
    
    @FXML
	private Button nuppFiltrNimi;
    
    @FXML
    private TextArea txtMuudatused;
    
    @FXML
    private TextArea txtKoolitused;
    
    @FXML
    private Label lblFilter;
    
    @FXML
    private Menu mnuLogi;
    
    @FXML
    private Button btnSulgeLogi;
    
    @FXML
    private TableView<KoolitusUI> tblKoolitused;

    @FXML
    private TableColumn<KoolitusUI, String> veerKoolitus;

    @FXML
    private TableColumn<KoolitusUI, String> veergFail;
    
    @FXML
    private Label lblOskusteFilter;
    
//    @FXML
//    private Hyperlink hlinkProov;
    
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
	 	.map(  p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup, p.muutmiseKuup))
		.forEach(p -> dataTootajad.add(p));
	
	filteredData = new FilteredList<>(dataTootajad, p -> true);
	// 3. Wrap the FilteredList in a SortedList. 
    SortedList<TootajaTabel> sortedData = new SortedList<>(filteredData);

    // 4. Bind the SortedList comparator to the TableView comparator.
    sortedData.comparatorProperty().bind(showTable.comparatorProperty());

    // 5. Add sorted (and filtered) data to the table.
    this.showTable.setItems(sortedData);	 
	 
	Tootaja.aktiivsedMap().entrySet().stream()
			 	.map(Map.Entry::getValue)
			 	.map(  p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup, p.muutmiseKuup))
			 	.collect(Collectors.toList()).forEach(p -> kasutajad.add(p));
	 
	 cmbKasutaja.getItems().addAll(kasutajad);
	 
//	 System.out.println(Main.praeguneKasutaja.id);
	 
	 for (TootajaTabel i : kasutajad){

		  if (i.getID().equals(Main.praeguneKasutaja.id)) {
			  
			  cmbKasutaja.setValue(i);
			  
			  break;
		  }
	 }
	 

	}
	
	teeOskusteList();
	
	lstOskused.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(person -> {
        	OskusUI o = lstOskused.getSelectionModel().getSelectedItem();
//        	System.out.println(o.id.getValue());
//        	System.out.println(Tootaja.tootajad.get(person.getID()).oskused.get(o.id.getValue()));
       	 return tootajaFilter(person, txtFilter.getText(), txtFilterID.getText(), txtFilterAmet.getText(), cmbFilterStaatus.getValue(), o);     	
       });
	});	
	
	
	
	veerKoolitus.setCellValueFactory(cellData -> cellData.getValue().kirjeldus);
	veergFail.setCellValueFactory(cellData -> cellData.getValue().fail);
	
	tblKoolitused.setOnMousePressed(new EventHandler<MouseEvent>() {
	    @Override 
	    public void handle(MouseEvent event) {
	        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
	        	String id = tblKoolitused.getSelectionModel().getSelectedItem().getId();
	        	
	        	if (Koolitus.koolitused.get(id).fail != null){
		        	Path failiTee = Paths.get(Tootaja.annaKaust(Main.nahtavTootaja) + "\\" + Koolitus.koolitused.get(id).fail);
		        	File fail = failiTee.toFile();
		        	
		        	editFile(fail);
	        	}
//	        	int i = tblKoolitused.getSelectionModel().getFocusedIndex();
//	            System.out.println("Cell text: " + fail);                   
	        }
	    }
	});
	

	annaKoolitused();
	
    cmbKasutaja.valueProperty().addListener((observable, oldValue, newValue) -> {
    	Boolean onAdmin = Tootaja.tootajad.get(newValue.getID()).onAdmin;
    	lblAdmin.setVisible(onAdmin);
    	naitaTootajaDetaile(newValue);
    	Main.praeguneKasutaja = Tootaja.tootajad.get(newValue.getID());
    	
    	if (onAdmin) {
    		txtMuudatused.setText("");
    		adminNupud(true);
    	}
    	else {
    		
    		naitaTootajaLogi(newValue);
    		adminNupud(false);
    	}
    	
    });
	
	cmbStaatus.getItems().addAll("Aktiivne", "Mitte aktiivne");
	
	cmbFilterStaatus.getItems().addAll("Aktiivsed", "Mitte aktiivsed", "Kõik");
	
	cmbFilterStaatus.setValue("Aktiivsed");
	
	showTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> naitaTootajaDetaile(newValue));
	
	txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
        filteredData.setPredicate(person -> {
        	System.out.println(newValue);
        	OskusUI o = lstOskused.getSelectionModel().getSelectedItem();
        	return tootajaFilter(person, newValue, txtFilterID.getText(), txtFilterAmet.getText(), cmbFilterStaatus.getValue(), o);
        	
        });
    });

	txtFilterAmet.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(person -> {
			OskusUI o = lstOskused.getSelectionModel().getSelectedItem();
			return tootajaFilter(person, txtFilter.getText(), txtFilterID.getText(), newValue, cmbFilterStaatus.getValue(), o);
			
        });
    });
	
	txtFilterID.textProperty().addListener((observable, oldValue, newValue) -> {
		filteredData.setPredicate(person -> {
			OskusUI o = lstOskused.getSelectionModel().getSelectedItem();
			return tootajaFilter(person, txtFilter.getText(), newValue, txtFilterAmet.getText(), cmbFilterStaatus.getValue(), o);
			
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
			OskusUI o = lstOskused.getSelectionModel().getSelectedItem();
			return tootajaFilter(person, txtFilter.getText(), txtFilterID.getText(), txtFilterAmet.getText(), newValue, o);	
        });
	});
	}

	
	boolean tootajaFilter(TootajaTabel toot, String uusNimi, String uusID, String uusAmet, String uusAkt, OskusUI oskus){   
		if ((uusNimi == null || uusNimi.isEmpty() ) 
			&& (uusID == null || uusID.isEmpty())
			&& (uusAmet == null || uusAmet.isEmpty())
			&& uusAkt == "Kõik"
			&& (oskus == null )) {
            return true;
        }
        if (toot.getID().contains(uusID) 
        	&& toot.getNimi().contains(uusNimi)
        	&& toot.getAmet().contains(uusAmet)	
        	&& (toot.getMitteAktiivneKuup() == "" && uusAkt == "Aktiivsed" 
        	   || toot.getMitteAktiivneKuup() != "" && uusAkt == "Mitte aktiivsed" 
        	   || uusAkt == "Kõik")
        	&& (oskus != null ? Tootaja.tootajad.get(toot.getID()).oskused.get(oskus.id.getValue()) != null : true)
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
		 	.map(  p -> new TootajaTabel(p.nimi , p.id, p.amet, p.lisamiseKuup, p.mitteAktiivneKuup, p.muutmiseKuup))
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
				
				Tootaja t = Tootaja.uusTootaja(txtID.getText(),  txtNimi.getText(), Main.praeguneKasutaja);
				
				if (chkAdmin.isSelected()) t.onAdmin = true;
				if (!txtAmet.getText().isEmpty()) t.amet = txtAmet.getText();
				
				TootajaTabel p = new TootajaTabel(t.nimi , t.id, t.amet, t.lisamiseKuup, t.mitteAktiivneKuup, t.muutmiseKuup);
				dataTootajad.add(p);
				
				lisaTootajaNupud(false);
				
				tootajaMuutmine = true;	
			}
		}
		
		koikTootajad();
		showTable.refresh();
	}
	
	
	public void lisaOskusSysteemi() throws IOException{
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/OskusSysteemi.fxml"));
		Parent uusAken = loader.load();

		OskusSysteemiController loc = (OskusSysteemiController) loader.getController();
		loc.mc = this;
		
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(cmdLisaOskus.getScene().getWindow());
        
        Scene dialogScene = new Scene(uusAken, 715, 520);
       
        dialog.setScene(dialogScene);
        dialog.setTitle("Lisa oskus");
               
        dialog.show();
	}
	

	public void lisaOskus(ActionEvent e) throws IOException{
//		TootajaTabel tootaja = nahtavTootaja;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/LisaOskusAken.fxml"));
		
		Parent uusAken = loader.load();

		LisaOskusController loc = (LisaOskusController) loader.getController();
		loc.mc = this;
		loc.muudetavTootaja = muudetavTootaja;
		
        Stage dialog = new Stage();
        
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(cmdLisaOskus.getScene().getWindow());
                
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
	
	private void adminNupud(Boolean nahtavus){
		chkAdmin.setVisible(nahtavus);
		showTable.setVisible(nahtavus);
		mnuSeadistamine.setDisable(!nahtavus);
		lblOskusteFilter.setVisible(nahtavus);
		mnuLogi.setDisable(!nahtavus);
		cmbStaatus.setDisable(!nahtavus);
		nuppFiltrNimi.setVisible(nahtavus);
		cmbFilterStaatus.setVisible(nahtavus);
		txtFilter.setVisible(nahtavus);
		txtFilterID.setVisible(nahtavus);
		txtFilterAmet.setVisible(nahtavus);
		txtMuudatused.setVisible(!nahtavus);
		lblFilter.setVisible(nahtavus);
	}
	
	public void katkestaLisaTootaja(){
		
		lisaTootajaNupud(false);
		
		tootajaMuutmine = true;
		
	}
	
	
	void naitaTootajaDetaile (TootajaTabel tootaja){
				
		if (tootaja != null) {
		        // Täida väljad töötaja andmetega
			muudetavTootaja = tootaja;
			Main.nahtavTootaja = tootaja;
			
			Tootaja t = Tootaja.tootajad.get(tootaja.getID().toString());
			
	        txtNimi.setText(tootaja.getNimi());
	        txtID.setText(tootaja.getID());
	        txtAmet.setText(tootaja.getAmet());
  
       
	        chkAdmin.setSelected(Tootaja.tootajad.get(tootaja.getID().toString()).onAdmin);
	       	        
	        if (tootaja.mitteAktiivneKuup.getValue().equals("")){
	        	cmbStaatus.setValue("Aktiivne");
	        }
	        else {
	        	cmbStaatus.setValue("Mitte aktiivne");
	        }
	        		        
	        ObservableList<OskusUI> dataO = FXCollections.observableArrayList();
	        
	        t.oskused.entrySet().stream()
	        		.map(p -> new OskusUI(p.getKey(),
					        				Oskus.oskused.get(p.getKey()).nimetus, 
					        				p.getValue(), 
					        				Oskus.oskused.get(p.getKey()).tasemed.get(p.getValue())))
	        		.forEach(p -> dataO.add(p));
    
	        cmdLisaOskus.setDisable(false);
	         
	        this.oskusTabel.setItems(dataO);
	       
	        
	        if (t.lisamiseKuup != null) {
	        	txtLisamiseAeg.setText((tootaja.getLisamiseKuup().toString()));
	        }
	        if (t.muutmiseKuup != null) {
	        	txtViimatiMuudetud.setText(tootaja.kuupaevaFormatter.format(t.muutmiseKuup));
	        }
	        else txtViimatiMuudetud.setText("");

	        annaKoolitused();
	        
		} 
		else {
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
	
	public void naitaTootajaLogi(TootajaTabel tootaja){
		txtMuudatused.setText(Muudatus.viimasedMuudatused(tootaja.getID().toString()).stream()
				.map(p -> p.toString())
				.collect(Collectors.joining("\n")));
	}
	
	public void naitaAdminLogi(ActionEvent e){
		adminNupud(false);
		btnSulgeLogi.setVisible(true);
		if (showTable.getSelectionModel().getSelectedItem() == null) {
			naitaTootajaLogi(cmbKasutaja.getSelectionModel().getSelectedItem());
		}
		else naitaTootajaLogi(showTable.getSelectionModel().getSelectedItem());
	}
	
	
	public void sulgeAdminLogi(ActionEvent e){
		adminNupud(true);
		btnSulgeLogi.setVisible(false);
	}
	
	//Kogu logi näitamine
	public void naitaKoguLogi(ActionEvent e){
		adminNupud(false);
		btnSulgeLogi.setVisible(true);
		txtMuudatused.setText(Muudatus.muutused.stream()
				.sorted((x, y) -> y.millal.compareTo(x.millal))
				.map(p -> p.toString())
				.collect(Collectors.joining("\n")));
	}

	public void annaKoolitused(){
//		System.out.println("annaKoolitused");
		dataKoolitused.clear();
		
		Koolitus.koolitused.entrySet().stream()
			.map(Map.Entry::getValue)
			.filter(p -> p.tootajaID.equals(txtID.getText()))
			.map(p -> new KoolitusUI(p.id, p.tootajaID, p.kirjeldus, p.fail))
			.forEach(p -> dataKoolitused.add(p));
		
//		if (!txtID.getText().equals("")) dataKoolitused.add(new KoolitusUI(null, null, "lisa koolitus", "lisa fail"));
		
		this.tblKoolitused.setItems(dataKoolitused);	
		
		filteredKoolitus = new FilteredList<>(dataKoolitused, p -> true);
		// 3. Wrap the FilteredList in a SortedList. 
	    SortedList<KoolitusUI> sortedKoolitus = new SortedList<>(filteredKoolitus);

	    // 4. Bind the SortedList comparator to the TableView comparator.
	    sortedKoolitus.comparatorProperty().bind(tblKoolitused.comparatorProperty());

	    // 5. Add sorted (and filtered) data to the table.
	    this.tblKoolitused.setItems(sortedKoolitus);
	}
	
	public void lisaKoolitus(ActionEvent e) throws IOException{
//		TootajaTabel tootaja = nahtavTootaja;
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/LisaKoolitus.fxml"));
		
		Parent uusAken = loader.load();

		LisaKoolitusController loc = (LisaKoolitusController) loader.getController();
		
		loc.mc = this;
		loc.muudetavTootaja = muudetavTootaja;
		
        Stage dialog = new Stage();
        
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(cmdLisaOskus.getScene().getWindow());
                
        Scene dialogScene = new Scene(uusAken, 467, 222);
       
        dialog.setScene(dialogScene);
        dialog.setTitle("Lisa koolitus");
        dialog.setOnShowing(p -> loc.onShowing(p));
        dialog.show();    
	 }
	
	public void avaKoolituseFail(ActionEvent e){

		
	}
	
	public boolean editFile(File file) {
		  if (!Desktop.isDesktopSupported()) {
		    return false;
		  }

		  Desktop desktop = Desktop.getDesktop();
		  if (!desktop.isSupported(Desktop.Action.EDIT)) {
		    return false;
		  }

		  try {
		    desktop.open(file);
		  } catch (IOException e) {
		    // Log an error
		    return false;
		  }

		  return true;
		}
	
	
	public void dblClick(MouseEvent event) {
        if (event.getClickCount() > 1) {
//            System.out.println("double clicked!");
        	
//            TableCell c = (TableCell) event.getSource();
        	String id = tblKoolitused.getSelectionModel().getSelectedItem().getId();
        	
            
            System.out.println("Cell text: " + id);
        }
    }
	
	public void teeOskusteList(){
		 Oskus.oskused.entrySet().stream()
		 	.sorted((x, y) -> x.getValue().nimetus.compareTo(y.getValue().nimetus))
		 	.map(Map.Entry::getValue)
		 	.map(  p -> new OskusUI(p.id , p.nimetus, p.kirjeldus))
		 	.collect(Collectors.toList()).forEach(p -> dataOskused.add(p));
		lstOskused.setItems(dataOskused);
	}
}
