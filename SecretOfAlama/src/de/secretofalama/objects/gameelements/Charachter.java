package de.secretofalama.objects.gameelements;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import de.secretofalama.objects.datatypes.Date;
import de.secretofalama.objects.datatypes.Geschlecht;

@XmlRootElement
public class Charachter {

	private String vorname;
	private String nachname;
	private Geschlecht geschlecht;
	private Date geburtsdatum;
	
	//Getter und Setter
	
	public String getVorname() {
		return vorname;
	}

	@XmlElement
	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	@XmlElement
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	@XmlElement
	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	public Geschlecht getGeschlecht() {
		return geschlecht;
	}

	@XmlElement
	public void setGeschlecht(Geschlecht geschlecht) {
		this.geschlecht = geschlecht;
	}
	
}
