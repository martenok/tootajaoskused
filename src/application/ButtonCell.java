package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

//teeme ühe sisemise klassi ButtonCell (mis sobib Tnimene, boolean veeruks

class ButtonCell extends TableCell<KoolitusUI, Boolean> {
 Button cellButton = new Button("Lisa eksam");
 
 
 ButtonCell(){
     // nb! siin saad buttonile igasuguseid mõõtusid jms panna
 	
     cellButton.setOnAction(new EventHandler<ActionEvent>(){
     	  	
         @Override
         public void handle(ActionEvent t) {
             // do something when button clicked
        	 Tootaja tootaja = Tootaja.tootajad.get(Main.nahtavTootaja.ID.getValue());
        	 Koolitus.lisaTunnistus(tootaja.id, "Kalapüük");
         	
             //...
         }
     });
 }
 
 //Display button if the row is not empty
 @Override
 protected void updateItem(Boolean t, boolean empty) {
     super.updateItem(t, empty);

     if(!empty){
         setGraphic(cellButton);
     }
 }
 
//EventHandler<ActionEvent> btnNewHandler = 
//      new EventHandler<ActionEvent>(){
//  @Override
//  public void handle(ActionEvent t) {
//      
//      System.out.println("nuppu vajutati");
//  }
//};
}
