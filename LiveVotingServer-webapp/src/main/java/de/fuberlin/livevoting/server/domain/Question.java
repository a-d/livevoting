package de.fuberlin.livevoting.server.domain;

import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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

import org.apache.log4j.Logger;


import de.fuberlin.livevoting.server.dao.kind.SessionToMany;
import de.fuberlin.livevoting.server.domain.kind.EntityContainsPicture;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain;
import de.fuberlin.livevoting.server.intern.util.DateFormatUtil;

@Entity
@Table(name = "question")
public class Question extends EntityContainsPicture implements Comparable<Question>, EntityDomain  {

	public enum Field implements DatabaseField {
		id, answers, dateFrom, dateTo, course, text, picture, multipleChoice, customAnswer, maxAnswerPictureWidth
	}

	@Transient
	private static Logger log = Logger.getLogger(Question.class);
			
	@Id
	@GeneratedValue
	private Long id;

	@SessionToMany
	@OneToMany(cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy="question")
	private Set<Answer> answers = new HashSet<Answer>();


	private Date dateFrom;
	private Date dateTo;

	@ManyToOne
	private Course course;

	private String text;

	@Column(columnDefinition = "TEXT")
	private String picture;

	private Boolean multipleChoice;
	private Boolean customAnswer;

	@Transient
	private Integer maxAnswerPictureWidth;

	/*
	 * custom functions
	 */

	
	/*
	 * getter and setter
	 */
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Answer> getAnswers() {
		return answers;
	}

	public void setCustomAnswer(Boolean customAnswer) {
		this.customAnswer = customAnswer;
	}

	public Boolean getCustomAnswer() {
		return customAnswer != null && customAnswer == true;
	}

	public void setMultipleChoice(Boolean multiChoice) {
		this.multipleChoice = multiChoice;
	}

	public Boolean getMultipleChoice() {
		return multipleChoice != null && multipleChoice == true;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Course getCourse() {
		return course;
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
	
	/*
	 * additional methods
	 */
	
	@Transient
	public List<Answer> getNonCustomAnswers() {
		List<Answer> out = new LinkedList<Answer>();
		for (Answer a : this.getAnswers()) {
			if (a.getUser() == null) {
				out.add(a);
			}
		}
		Collections.sort(out, new Comparator<Answer>() {
			public int compare(Answer o1, Answer o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		
		return out;
	}

	@Transient
	public boolean isOld() {
		return (this.getDateTo() != null && this.getDateTo().before(new Date()));
	}

	
	@Transient
	public int compareTo(Question q) {
		return -(this.getDateFrom()!=null && q!=null && q.getDateFrom()!=null
				? this.getDateFrom().compareTo(q.getDateFrom())
				: (q!=null && q.getDateFrom()!= null ? -1 : 0));
	}

	@Transient
	public Boolean getMultipleChoiceUnchecked() {
		return multipleChoice;
	}

	@Transient
	public Integer getMaxAnswerPictureWidth() {
		if (maxAnswerPictureWidth != null) {
			return maxAnswerPictureWidth;
		}
		if (answers != null) {
			Integer maxWidth = 0;
			for (Answer a : answers) {
				if (a.getUser() == null && a.getPictureWidth() > maxWidth) {
					maxWidth = a.getPictureWidth();
				}
			}
			if (maxWidth > 0) {
				maxAnswerPictureWidth = maxWidth;
				return maxWidth;
			}
		}
		return null;
	}
}
