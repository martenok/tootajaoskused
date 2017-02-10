package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.beans.property.SimpleStringProperty;

public class TootajaTabel {
	
	SimpleStringProperty nimi;
	SimpleStringProperty ID;
	SimpleStringProperty amet;
	SimpleStringProperty lisamiseKuup;
	SimpleStringProperty mitteAktiivneKuup;
	
	
	DateTimeFormatter kuupaevaFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
	
	TootajaTabel(String nimi, String ID, String amet, LocalDateTime lisamiseKuup, LocalDateTime mitteAktiivneKuup) {
		this.nimi = new SimpleStringProperty(nimi);
		this.ID = new SimpleStringProperty(ID);
		this.amet = new SimpleStringProperty(amet);
		this.lisamiseKuup = new SimpleStringProperty(kuupaevaFormatter.format(lisamiseKuup));
		this.mitteAktiivneKuup = new SimpleStringProperty( (mitteAktiivneKuup != null) ? kuupaevaFormatter.format(mitteAktiivneKuup): "");
		
	}
	
    public String getNimi() {
        return nimi.get();
    }
//	 
    public void setNimi(String nimi) {
        this.nimi.set(nimi);
    }
//	 
    public String getID() {
        return ID.get();
    }
//	 
    public void setID(String ID) {
        this.ID.set(ID);
    }
    
	public String getAmet() {
		return amet.get();
	}
	public void setAmet(String amet) {
		this.amet.set(amet);
	}
	public String getLisamiseKuup() {
		return lisamiseKuup.get();
	}
	public void setLisamiseKuup(String lisamiseKuup) {
		this.lisamiseKuup.set(lisamiseKuup);;
	}
	public String getMitteAktiivneKuup() {
		return mitteAktiivneKuup.get();
	}
	public void setMitteAktiivneKuup(String mitteAktiivneKuup) {
		this.mitteAktiivneKuup.set(mitteAktiivneKuup);;
	}

	public String toString(){
		return String.format("%s (%s)", this.getNimi(), this.getID());
	}
}
