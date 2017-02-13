package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.stage.Stage;

public class LisaOskusController implements Initializable{

	public MainController mc = null;
	public TootajaTabel muudetavTootaja;
	
	private ObservableList<OskusUI> dataTasemed = FXCollections.observableArrayList();
	FilteredList<OskusUI> filteredData;
	
	@FXML
	private TextField txtNimi;
	
	@FXML
	private TextField txtID;
		
	@FXML
	private ComboBox<String> cmbOskus;
	
	@FXML
	private TableView<OskusUI> tabelTasemed;
	 	
	@FXML
	private TableColumn<OskusUI, String> veergTase;
	
	@FXML
	private TableColumn<OskusUI, String> veergKirjeldus;
	
	@FXML
	private Button nuppLisaOskus;
	
	@FXML
	private Button nuppKatkesta;
	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
      veergTase.setCellValueFactory(cellData -> cellData.getValue().tase);

      
      veergKirjeldus.setCellValueFactory(cellData -> cellData.getValue().kirjeldus);

		Tootaja kedaMuuta = Tootaja.tootajad.get(Main.nahtavTootaja.getID().toString());
		
		txtNimi.setText(kedaMuuta.nimi);
		
		txtID.setText(kedaMuuta.id);
		
		Oskus.oskused.entrySet().stream()
			.map(oskus -> oskus.getValue())
			.forEach(p -> cmbOskus.getItems().addAll(p.nimetus));
		
		cmbOskus.setValue(cmbOskus.getItems().get(0));
		
		annaTasemed();
		
		
		cmbOskus.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(oskus -> {
	//				annaTasemed();
				
	            // If filter text is empty, display all persons.
	            if (newValue == null || newValue.isEmpty()) {
	                return true;
	            }
	
	            if (oskus.getNimetus().contains(newValue)) {
	                return true; // Filter matches first name.
	
	            }
	            return false; // Does not match.
	        });
			
		});
		
		
	}

	public void lisaOskus(ActionEvent event){
		if (event.getSource() == nuppLisaOskus){
			
			Tootaja kedaMuuta = Tootaja.tootajad.get(txtID.getText());
			
			List<Oskus> oskused = Oskus.leiaNimega(cmbOskus.getValue());
			Oskus o = oskused.get(0);
			String tase = tabelTasemed.getSelectionModel().selectedItemProperty().getValue().getTase().toString();
			System.out.println(tase);
			
			Tase t = null;
			
			for (Tase x : Tase.values()){
				if (x.name().equals(tase)){
					t = x ;
				}
			}
			
			kedaMuuta.lisaOskus(o, t, true, Main.praeguneKasutaja);
			System.out.println(Main.praeguneKasutaja);

			if (this.mc != null) {
				mc.naitaTootajaDetaile(muudetavTootaja);
				mc.naitaTootajaLogi(muudetavTootaja);
			}
			
			Stage lava = (Stage)nuppLisaOskus.getScene().getWindow();
			
			lava.close();
		}
	}
	
	
	public void sulgeKatkesta(ActionEvent event){
		if (event.getSource() == nuppKatkesta){
			Stage lava = (Stage)nuppKatkesta.getScene().getWindow();
			lava.close();
		}
	}
	
	public void annaTasemed() {

		List<Oskus> oskused = Oskus.leiaNimega(cmbOskus.getValue());
		
		dataTasemed.clear();

		Oskus o =oskused.get(0);
		
		o.tasemed.entrySet().stream()
			.map(p -> new OskusUI(o.id, o.nimetus, p.getKey(), p.getValue()))
//			.peek(p -> System.out.println(p.kirjeldus))
			.collect(Collectors.toList()).forEach(p -> dataTasemed.add(p));

		this.tabelTasemed.setItems(dataTasemed);	
	
		filteredData = new FilteredList<>(dataTasemed, p -> true);
		// 3. Wrap the FilteredList in a SortedList. 
	    SortedList<OskusUI> sortedData = new SortedList<>(filteredData);

	    // 4. Bind the SortedList comparator to the TableView comparator.
	    sortedData.comparatorProperty().bind(tabelTasemed.comparatorProperty());

	    // 5. Add sorted (and filtered) data to the table.
	    this.tabelTasemed.setItems(sortedData);		
				
	}

}
