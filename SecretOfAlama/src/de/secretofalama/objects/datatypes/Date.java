package de.secretofalama.objects.datatypes;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Date {

	private int year;
	private int month;
	private int day;

	public int getYear() {
		return year;
	}

	@XmlElement
	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	@XmlElement
	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	@XmlElement
	public void setDay(int day) {
		this.day = day;
	}

}
