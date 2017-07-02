package de.secretofalama.objects.datatypes;

public enum Geschlecht {
	
	MAENNLICH("männlich"),
	WEIBLICH("weiblich");
	
	private String anzeigetext;
	
	Geschlecht(String anzeigetext){
		this.anzeigetext = anzeigetext;
	}

	public String getAnzeigetext() {
		return anzeigetext;
	}

}
