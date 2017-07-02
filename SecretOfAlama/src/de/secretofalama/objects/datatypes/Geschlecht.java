package de.secretofalama.objects.datatypes;

public enum Geschlecht {
	
	MAENNLICH("m�nnlich"),
	WEIBLICH("weiblich");
	
	private String anzeigetext;
	
	Geschlecht(String anzeigetext){
		this.anzeigetext = anzeigetext;
	}

	public String getAnzeigetext() {
		return anzeigetext;
	}

}
