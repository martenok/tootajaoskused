package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class OskusSysteemiController implements Initializable {
	public MainController mc = null;
	static private ObservableList<OskusTase> dataOskused = FXCollections.observableArrayList();
	
	static String muuda = "";
	
	@FXML
	private TreeTableView<OskusTase> treeTable;
	
    @FXML
    private TreeTableColumn<OskusTase, String> veergNimi;

    @FXML
    private TreeTableColumn<OskusTase, String> veergKirjeldus;
    
    @FXML
    private Button nuppKatkesta;
    
    @FXML
    private GridPane grdPaneel;
    
    @FXML
    private TextField txtNimi;

    @FXML
    private TextField txtKirjeldus;

    @FXML
    private TextField txtN6rk;

    @FXML
    private TextField txtKeskmine;

    @FXML
    private TextField txtHea;

    @FXML
    private TextField txtVHea;

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
//		veergNimi.setCellValueFactory(cellData -> cellData.getValue().nimi);
		
		veergNimi.setCellValueFactory(
	            (TreeTableColumn.CellDataFeatures<OskusTase, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getNimi())
	            );
		
		veergKirjeldus.setCellValueFactory((TreeTableColumn.CellDataFeatures<OskusTase, String> param) -> 
	            new ReadOnlyStringWrapper(param.getValue().getValue().getKirjeldus())
	            );
			
		TreeItem<OskusTase> juurikas = annaPuu();
		
		treeTable.setRoot(juurikas);
		juurikas.setExpanded(true);
		
	}
	
	public static TreeItem<OskusTase> annaPuu(){

		TreeItem<OskusTase> rootNode = new TreeItem<>(new OskusTase("Oskus", "Kirjeldus", "ID", "TÜÜP"));
		
		for (Oskus x : Oskus.oskused.values()){
			
			TreeItem<OskusTase> oksOskus = new TreeItem<>(new OskusTase(x.nimetus, x.kirjeldus, x.id, "oskus"));

			x.tasemed.entrySet().stream()
					.sorted((z, y) -> Integer.compare(z.getKey().ordinal(), y.getKey().ordinal()))
					.forEach(tase -> oksOskus.getChildren().add(new TreeItem<>(new OskusTase(tase.getKey().toString(), tase.getValue(), x.id, "tase"))));
			
			rootNode.getChildren().add(oksOskus);
		}
	return	rootNode;
	}

	
	public void sulgeKatkesta(ActionEvent event){
		if (event.getSource() == nuppKatkesta){
			Stage lava = (Stage)nuppKatkesta.getScene().getWindow();
			if (this.mc != null) {
				mc.teeOskusteList();
			}
			
			lava.close();
		}
	}
	
	public void lisaOskusSysteemi(ActionEvent event){
		List<String> tasemed = new ArrayList<String>();
		
		if (!txtNimi.getText().trim().isEmpty()
				&& !txtKirjeldus.getText().trim().isEmpty()){
			
			Oskus o;
			if (muuda != "") {
				o = Oskus.oskused.get(muuda);
				o.MuudaNimi(Main.praeguneKasutaja, txtNimi.getText().trim());
				o.MuudaKirjeldus(Main.praeguneKasutaja, txtKirjeldus.getText().trim());
				
				muuda = "";
			}
			else o = Oskus.uusOskus(txtNimi.getText().trim(), txtKirjeldus.getText().trim(), Main.praeguneKasutaja.id);
			
			tasemed.add("");
			tasemed.add(txtN6rk.getText());
			tasemed.add(txtKeskmine.getText());
			tasemed.add(txtHea.getText());
			tasemed.add(txtVHea.getText());
			
			System.out.println(o.id);
			
			o.lisaTasemed(tasemed, Main.praeguneKasutaja.id);
			
			System.out.println("Controllerist trükk " + o.id + " tasemed " + o.tasemed);
			
			TreeItem<OskusTase> juurikas = annaPuu();
			treeTable.setRoot(juurikas);
			juurikas.setExpanded(true);

		}
		
    	txtNimi.setText("");
    	txtKirjeldus.setText("");
    	
		txtN6rk.setText("");
		txtKeskmine.setText("");
		txtHea.setText("");
		txtVHea.setText("");

	}
	
	
	//Lohista ja pane
	
    public void lohistaja(MouseEvent event) {
      
        
        // drag was detected, start drag-and-drop gesture
        TreeItem<OskusTase> selected = (TreeItem<OskusTase>) treeTable.getSelectionModel().getSelectedItem();
        // to access your RowContainer use 'selected.getValue()'
           
        if (selected != null && selected.getValue().getTyyp().equals("oskus") ) {
            Dragboard db = treeTable.startDragAndDrop(TransferMode.ANY);

            // create a miniature of the row you're dragging
//            db.setDragView(row.snapshot(null, null));

            // Keep whats being dragged on the clipboard
            ClipboardContent content = new ClipboardContent();
            content.putString(selected.getValue().getId());
            db.setContent(content);

            event.consume();
        }
        
    }
    
    public void lohistaYle(DragEvent event) {
        // data is dragged over the target
//        Dragboard db = event.getDragboard();
        if (event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.COPY);
        }
        event.consume();
    }
    
    public void pilla(DragEvent event) {

        Dragboard db = event.getDragboard();
        boolean success = false;
        
        if (event.getDragboard().hasString()) {
        	
        	Oskus x = Oskus.oskused.get(db.getString());
        	muuda = x.id;
        	txtNimi.setText(x.nimetus);
        	txtKirjeldus.setText(x.kirjeldus);
        	
        	if (x.tasemed.get(Tase.Nõrk) != null) {
        		txtN6rk.setText(x.tasemed.get(Tase.Nõrk));
        	}
        	else txtN6rk.setText("");
        	
			if (x.tasemed.get(Tase.Keskmine) != null) {txtKeskmine.setText(x.tasemed.get(Tase.Keskmine));}
			else txtKeskmine.setText("") ;
			
			if (x.tasemed.get(Tase.Hea) != null) {txtHea.setText(x.tasemed.get(Tase.Hea));}
			else txtHea.setText("");

			if (x.tasemed.get(Tase.VägaHea) != null) {txtVHea.setText(x.tasemed.get(Tase.VägaHea));}
			else txtVHea.setText("");
			
            success = true;

        }
        
        event.setDropCompleted(success);
        event.consume();
    }	
	

    
}
