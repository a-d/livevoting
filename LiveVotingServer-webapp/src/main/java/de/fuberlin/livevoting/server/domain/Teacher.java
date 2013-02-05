package de.fuberlin.livevoting.server.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import de.fuberlin.livevoting.server.dao.kind.SessionToMany;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

@Entity
@Table(name="teacher")
public class Teacher implements EntityDomain {

	public enum Field implements DatabaseField {
		id, ip, browserHash, browserAgent, dateRegistered, name, hashedPassword, courses
	}

	private static final String SALT = "This is my unique Salt!";


	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true, nullable=false)
	private String name;

	private String hashedPassword;
	

	@Column(updatable=false)
	private Date dateRegistered = new Date();
	

	private String ip;
	private Integer browserHash;
	private String browserAgent;
	
	@Transient
	private String password;
	

	@SessionToMany
	@OneToMany(fetch=FetchType.LAZY, mappedBy="teacher")
	//@OneToMany(cascade = CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="teacher")
	private Set<Course> courses = new HashSet<Course>();


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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public Set<Course> getCourses() {
		return courses;
	}
	
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	@Transient
	public void generateHashedPassword() {
		String date = (getDateRegistered()!=null ? getDateRegistered() : new Date()).toString();
		setHashedPassword( hash(date+getPassword()+SALT) );
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
	public boolean isCorrectPassword(String password) {
		String date = (getDateRegistered()!=null ? getDateRegistered() : new Date()).toString();
		return getHashedPassword().equals( hash(date+password+SALT) );
	}
	
	private String hash(String a) {
		return org.apache.commons.codec.digest.DigestUtils.sha256Hex(a);
	}
	public void populateByRequest(HttpServletRequest r) {
		setBrowserAgent(r.getHeader("user-agent"));
		setBrowserHash((r.getHeader("host") +
				"\n" + r.getHeader("user-agent") +
				"\n" + r.getHeader("accept") +
				"\n" + r.getHeader("accept-language") +
				"\n" + r.getHeader("accept-encoding") +
				"\n" + r.getHeader("accept-charset")).hashCode());
		setIp(r.getRemoteAddr());
	}
	
	
	
	public Date getDateRegistered() {
		return dateRegistered;
	}


	public String getPassword() {
		return password;
	}


	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}


	public void setDateRegistered(Date dateRegistered) {
		this.dateRegistered = dateRegistered;
	}


	public String getIp() {
		return ip;
	}


	public Integer getBrowserHash() {
		return browserHash;
	}


	public String getBrowserAgent() {
		return browserAgent;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}


	public void setBrowserHash(Integer browserHash) {
		this.browserHash = browserHash;
	}


	public void setBrowserAgent(String browserAgent) {
		this.browserAgent = browserAgent;
	}
}
