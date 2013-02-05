package de.fuberlin.livevoting.server.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;


@Entity
@Table(name="vote")
public class Vote implements EntityDomain {

	public enum Field implements DatabaseField {
		id, answer, user, date
	}
	
	
	@Id
	@GeneratedValue
	private Long id;


	@ManyToOne
	private Answer answer;

	@ManyToOne
	private User user;
	
	private Date date;


	
	/*
	 * custom functions
	 */
	
	
	/*
	 * getter and setter
	 */
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
