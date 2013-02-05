package de.fuberlin.livevoting.server.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import de.fuberlin.livevoting.server.dao.kind.SessionToMany;
import de.fuberlin.livevoting.server.domain.kind.EntityContainsPicture;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

@Entity
@Table(name = "answer")
public class Answer extends EntityContainsPicture implements Comparable<Answer>, EntityDomain {
	public enum Field implements DatabaseField {
		id, title, text, picture, user, question, votes
	}

	@Id
	@GeneratedValue
	private Long id;

	private String title;
	private String text;

	@Column(columnDefinition = "TEXT")
	private String picture;

	@ManyToOne
	private User user;

	@ManyToOne
	private Question question;

	@SessionToMany
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy="answer")
	private Set<Vote> votes = new HashSet<Vote>();

	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}


	public Set<Vote> getVotes() {
		return votes;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	@Transient
	public String toParticipateString() {
		return (this.getTitle() != null ? this.getTitle() + "<br />" : "")
				+ "<div style=\"padding:10px 30px\">"
				+ (this.getText() != null ? "<i>" + this.getText() + "</i>"
						: "") + "<br />" + this.getPicture() + "</div>"
				+ "<br />";
	}

	
	@Transient
	public int compareTo(Answer o) {
		return this.getTitle() != null ? this.getTitle()
				.compareTo(o.getTitle()) : this.getId() != null ? this.getId()
				.compareTo(o.getId()) : 0;
	}
}
