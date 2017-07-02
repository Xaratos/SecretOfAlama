package de.secretofalama.objects.datatypes;

import java.util.Arrays;

public enum Geschlecht {

	MAENNLICH("maennlich"), WEIBLICH("weiblich");

	private String anzeigetext;

	Geschlecht(String anzeigetext) {
		this.anzeigetext = anzeigetext;
	}

	public String getAnzeigetext() {
		return anzeigetext;
	}

	public static Geschlecht getKeyFromAnzeigetext(String anzeigetext) {
		for (Geschlecht geschlecht : Geschlecht.values()) {
			if (geschlecht.getAnzeigetext().equals(anzeigetext)) {
				return geschlecht;
			}
		}
		return null;
	}
	
	public static String[] toStringArrayAnzeigetexte() {
		return Arrays.stream(Geschlecht.values()).map(Geschlecht::getAnzeigetext).toArray(String[]::new);
	}
}
