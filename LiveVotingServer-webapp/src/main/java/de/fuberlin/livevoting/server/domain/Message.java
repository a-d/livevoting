package de.fuberlin.livevoting.server.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

@Entity
@Table(name="message")
public class Message implements EntityDomain {

	public enum Field implements DatabaseField {
		id, text
	}
	
	@Id
	@GeneratedValue
	private Long id;
	private String text;
	
	
	/*
	 * getter and setter
	 */
	
	
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}
}
