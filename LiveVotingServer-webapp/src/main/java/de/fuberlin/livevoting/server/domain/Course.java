package de.fuberlin.livevoting.server.domain;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;

import de.fuberlin.livevoting.server.dao.kind.SessionToMany;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain;
import de.fuberlin.livevoting.server.intern.util.DateFormatUtil;

@Entity
@Table(name = "course")
public class Course implements EntityDomain {
	public enum Field implements DatabaseField {
		id, teacher, questions, url, name, token, dateFrom, dateTo, users
	}
	
	@Transient
	private static Logger log = Logger.getLogger(Course.class);
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Teacher teacher;

	@SessionToMany
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy="course")
	private Set<Question> questions = new HashSet<Question>();

	@Column(unique = true)
	private String name;

	private String token;
	private String url;

	private Date dateFrom;
	private Date dateTo;


	@SessionToMany
	@ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	@JoinTable(name = "user_in_course", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "course_id") })
	private Set<User> users = new HashSet<User>();


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

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Question> getQuestions() {
		return questions;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	public void setDateFrom(String date) {
		try {
			this.setDateFrom(DateFormatUtil.getDateFormat().parse(date));
		} catch (ParseException e) {
			log.warn("Could not save Date-From: \""+date+"\"", e);
		}
	}
	
	public void setDateTo(String date) {
		try {
			this.setDateTo(DateFormatUtil.getDateFormat().parse(date));
		} catch (ParseException e) {
			log.warn("Could not save Date-To: \""+date+"\"", e);
		}
	}

	public String getUrl() {
		return url;
	}

	public void setQuestions(Set<Question> questions) {
		this.questions = questions;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
