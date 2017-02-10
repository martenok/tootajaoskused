package application;

import javafx.beans.property.SimpleStringProperty;

public class OskusTase {
	
	SimpleStringProperty nimi;
	SimpleStringProperty kirjeldus;
	SimpleStringProperty id;
	SimpleStringProperty tyyp;
	
	OskusTase(String nimi, String kirjeldus, String id, String tyyp){
		this.nimi = new SimpleStringProperty(nimi);
		this.kirjeldus = new SimpleStringProperty(kirjeldus);
		this.id = new SimpleStringProperty(id);
		this.tyyp = new SimpleStringProperty(tyyp);
		
	}

	public String getNimi() {
		return nimi.get();
	}

	public void setNimi(String nimi) {
		this.nimi.set(nimi);
	}

	public String getKirjeldus() {
		return kirjeldus.get();
	}

	public void setKirjeldus(String kirjeldus) {
		this.kirjeldus.set(kirjeldus);
	}

	public String getId() {
		return id.get();
	}

	public void setId(SimpleStringProperty id) {
		this.id = id;
	}

	public String getTyyp() {
		return tyyp.get();
	}

	public void setTyyp(SimpleStringProperty tyyp) {
		this.tyyp = tyyp;
	}

	
	
	
}
