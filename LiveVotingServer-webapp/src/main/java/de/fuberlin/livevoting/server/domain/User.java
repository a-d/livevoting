package de.fuberlin.livevoting.server.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

@Entity
@Table(name="user")
public class User implements EntityDomain {

	public enum Field implements DatabaseField {
		id, ip, browserHash, browserAgent, date
	}
	
	@Id
	@GeneratedValue	
	private Long id;
	
	private String ip;
	private Integer browserHash;
	private String browserAgent;
	private Date date;

	
	/*
	 * getter and setter
	 */
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getIp() {
		return ip;
	}
	public void setBrowserAgent(String browserId) {
		this.browserAgent = browserId;
	}
	public String getBrowserAgent() {
		return browserAgent;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setBrowserHash(Integer browserHash) {
		this.browserHash = browserHash;
	}
	public Integer getBrowserHash() {
		return browserHash;
	}	
}
