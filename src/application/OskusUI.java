package application;

import javafx.beans.property.SimpleStringProperty;

public class OskusUI {

	SimpleStringProperty id;
	SimpleStringProperty nimetus;
	
	SimpleStringProperty tase;
	SimpleStringProperty kirjeldus;
//	String lisaja;
		
	public OskusUI(String id, String nimetus, Tase tase, String kirjeldus) {

		this.id = new SimpleStringProperty(id);
		this.nimetus = new SimpleStringProperty(nimetus);
		this.tase = new SimpleStringProperty(tase.toString());
		this.kirjeldus = new SimpleStringProperty(kirjeldus);
		
	}

	public String getId() {
		return id.get();
	}

	public void setId(String id) {
		this.id.set(id);
	}

	public String getNimetus() {
		return nimetus.get();
	}

	public void setNimetus(String nimetus) {
		this.nimetus.set(nimetus);
	}

	public String getTase() {
		return tase.get();
	}

	public void setTase(String tase) {
		this.tase.set(tase);
	}

	public String getKirjeldus() {
		return kirjeldus.get();
	}

	public void setKirjeldus(String kirjeldus) {
		this.kirjeldus.set(kirjeldus);
	}

	

	
	

}
