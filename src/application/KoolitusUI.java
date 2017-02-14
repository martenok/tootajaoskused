package application;

import javafx.beans.property.SimpleStringProperty;

public class KoolitusUI {
	SimpleStringProperty id;
	SimpleStringProperty tootajaID;
	SimpleStringProperty oskusID;
	SimpleStringProperty kirjeldus;

	SimpleStringProperty fail;
	
	public KoolitusUI(String id, String tootajaID, String kirjeldus, String fail) {
		this.id = new SimpleStringProperty(id);
		this.tootajaID = new SimpleStringProperty(tootajaID);
		this.kirjeldus = new SimpleStringProperty(kirjeldus);
		this.fail = new SimpleStringProperty(fail);		
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getTootajaID() {
		return tootajaID.get();
	}

	public void setTootajaID(String tootajaID) {
		this.tootajaID.set(tootajaID);
	}

	public String getOskusID() {
		return oskusID.get();
	}

	public void setOskusID(String oskusID) {
		this.oskusID.set(oskusID);
	}

	public String getKirjeldus() {
		return kirjeldus.get();
	}

	public void setKirjeldus(String kirjeldus) {
		this.kirjeldus.set(kirjeldus);
	}

	public String getFail() {
		return fail.get();
	}

	public void setFail(String fail) {
		this.fail.set(fail);
	}
	
	

}
