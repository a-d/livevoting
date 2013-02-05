package de.fuberlin.livevoting.server.action;

import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.Configuration;
import de.fuberlin.livevoting.server.dao.AnswerDAO;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.CourseDAO;
import de.fuberlin.livevoting.server.dao.CourseDAOImpl;
import de.fuberlin.livevoting.server.dao.QuestionDAO;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Course;
import de.fuberlin.livevoting.server.domain.Question;


public class ClientRequestAction extends ActionSupport implements ModelDriven<Question> {

	private static final long serialVersionUID = -6659925652584240539L;
	private static Logger log = Logger.getLogger(ClientRequestAction.class);

	private Question question = new Question();

	private AnswerDAO answerDao = new AnswerDAOImpl();
	private QuestionDAO questionDao = new QuestionDAOImpl();
	private CourseDAO courseDao = new CourseDAOImpl();
	private Long courseId;
	private Long questionId;
	
	public Question getModel() {
		return question;
	}

	@Action(value="/adm/clientrequest/postQuestion", results={
		@Result(name="success", location="/clientrequest/addQuestionSuccess.jsp"),
		@Result(name="error", location="/clientrequest/addQuestionError.jsp")
	})
	public String saveQuestion()
	{

		checkQuestionInput();
		
		if(getCourseFromDatabase()==null || question.getPicture()==null || question.getPicture().equals("")) {
			if(getCourseFromDatabase()==null) {
				log.warn("Parameter course is not set!");
			}
			else {
				log.warn("picture graphic is not set!");
			}
			return ERROR;
		}
		
		log.info("Receiving new Question:");
		question.setCourse(getCourseFromDatabase());
		
		try {
			
			HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
	
			LinkedList<Answer> questionAnswers = new LinkedList<Answer>();
	
			String picCompareKey = "answerSVG";
			String titCompareKey = "answerTitle";
			
			Enumeration<?> pn = request.getParameterNames();
			while(pn.hasMoreElements()) {
				Object obj = pn.nextElement();
				if(obj!=null && obj instanceof String) {
					String key = (String) obj;
					if(key.startsWith(picCompareKey)) {
						
						String aNr = key.substring(picCompareKey.length());
						String aTi = request.getParameter(titCompareKey+aNr);
						String svg = request.getParameter(key);
						
						if(svg!=null && !svg.equals("")) {
							Answer answer = new Answer();
							answer.setPicture(svg);
							answer.setTitle(aTi);
							answer.setQuestion(question);
							questionAnswers.add(answer);
						}
					}
				}
			}
			log.info(" Received "+questionAnswers.size()+" answers in SVG format.");
			
			if(questionAnswers.size()==0) {
				question.setCustomAnswer(true);
			}

			Date currDate = new Date();
			if(question.getDateFrom()==null) {
				question.setDateFrom(currDate);
			}
			if(question.getDateTo()==null) {
				question.setDateTo(DateUtils.addMinutes(currDate, Configuration.getInstance().getDefaultQuestionTimeout()));
			}
			if(question.getMultipleChoiceUnchecked()==null && request.getParameter("multipleChoice")!=null) {
				question.setMultipleChoice(Boolean.valueOf(request.getParameter("multipleChoice")));
			}
			
			questionDao.save(question);

			for(Answer a : questionAnswers) {
				answerDao.save(a);
			}
			
			return SUCCESS;
		}
		catch(Exception e) {
			log.warn("Could not save question", e);
		}
		return ERROR;
	}
	

	public Long getCId() {
		return courseId;
	}
	public void setCId(Long courseId) {
		this.courseId = courseId;
	}
	
	public Long getQId() {
		return questionId;
	}
	public void setQId(Long questionId) {
		this.questionId = questionId;
	}

	
	
	

	
	@Action(value="/adm/clientrequest/getQuestion", results={
		@Result(name="success", location="/clientrequest/retrieveQuestionSuccess.jsp"),
		@Result(name="error", location="/clientrequest/retrieveQuestionError.jsp")
	})
	public String retrieveQuestion()
	{
		log.info("Getting Question and ending it to now.");
		question = getQuestionFromDatabase();
		if(question!=null) {
			question.setDateTo(new Date());
			questionDao.save(question);			
			return SUCCESS;
		}
		return ERROR;
	}
	
	

	private Question getQuestionFromDatabase() {
		return getQId()!=null ? questionDao.getById(getQId()) : null;
	}
	private Course getCourseFromDatabase() {
		return getCId()!=null ? courseDao.getById(getCId()) : null;
	}


	private void checkQuestionInput() {
		Date currDate = new Date();
		
		if(question.getDateFrom()==null) {
			question.setDateFrom(currDate);
		}
		if(question.getDateTo()==null) {
			question.setDateTo(DateUtils.addMinutes(currDate, 60));
		}
	}
	
	public Question getQuestion() {
		if(question==null) {
			question = getQuestionFromDatabase();
		}
		return question;
	}
	
	public void setQuestion(Question question) {
		this.question = question;
	}


	public void setQuestionSVG(String questionSVG) {
		if(questionSVG!=null && !questionSVG.equals("")) {
			log.debug("Question picture has been set.");
			this.question.setPicture(questionSVG);
		}
	}
}
