package de.fuberlin.livevoting.server.action;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.dao.AnswerDAO;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.QuestionDAO;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.dao.UserDAO;
import de.fuberlin.livevoting.server.dao.UserDAOImpl;
import de.fuberlin.livevoting.server.dao.VoteDAO;
import de.fuberlin.livevoting.server.dao.VoteDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.User;
import de.fuberlin.livevoting.server.domain.Vote;
import de.fuberlin.livevoting.server.intern.util.UserUtil;


public class SubmitAction extends ActionSupport implements ModelDriven<Question> {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SubmitAction.class);

	private Map<String, Object> sessionMap = ActionContext.getContext().getSession();
	
	private UserDAO userDao = new UserDAOImpl();
	private AnswerDAO answerDao = new AnswerDAOImpl();
	private VoteDAO voteDao = new VoteDAOImpl();
	private QuestionDAO questionDao = new QuestionDAOImpl();

	private User user;
	private Question activeQuestion = new Question();
	private String customAnswer;
	private String givenAnswer;
	
	@Override
	@Action(value="question_{id:[0-9]*}", results={
			@Result(name="error", location="submit-error.jsp"),
			@Result(name="success", location="submit.jsp")
	})
	public String execute() {
		if(getModel().getId()!=null) {
			try {
				refreshUser();
				executeVote();
				return SUCCESS;
			}
			catch(Exception e) {
				log.warn("Could not save vote for question.", e);
			}
		}
		return ERROR;
	}
	
	
	private void refreshUser() {
		if(user==null) {
			String userKey = "user.id";
			try {
				Object userId = sessionMap.get(userKey);
				if(userId!=null) {
					user = userDao.getById((Long)userId);
				}
				if(user==null) {
					// get user (browser, ip)
					user = UserUtil.generateUser(userDao, questionDao);
					userDao.save(user);
					sessionMap.put(userKey, user.getId());
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	private void executeVote() {
		if(sessionMap.get("question.answered." + getModel().getId())!=null) {
			throw new RuntimeException("Question with Id "+getModel().getId()+" has already been answered by active user.");
		}
		
		// clean inputs
		if(customAnswer!=null) {
			customAnswer = customAnswer.trim();
			if(customAnswer.equals("")) {
				customAnswer = null;
			}
		}
		if(givenAnswer!=null) {
			givenAnswer = givenAnswer.trim();
			if(givenAnswer.equals("")) {
				givenAnswer = null;
			}
		}
		
		Set<Long> userAnswers = new HashSet<Long>();
		if(givenAnswer!=null) {
			for(String idStr : givenAnswer.split(", ")) {
				Long id = Long.parseLong(idStr);
				if(id!=null) {
					userAnswers.add(id);
				}
			}
		}


		// custom answers
		if(
			customAnswer!=null
			&& activeQuestion.getCustomAnswer()
			&& (getModel().getMultipleChoice() || userAnswers.size()==0)
		) {
			// create new answer
			Answer a = new Answer();
			a.setTitle(customAnswer.trim());
			a.setQuestion(getModel());
			a.setUser(user);

			// check if answer already exists
			Long aId = searchForAnswer(a);
			if(aId==null) {
				answerDao.save(a);
				aId = a.getId();
			}
			
			userAnswers.add(aId);
		}
		
		// given answers
		if(
			userAnswers.size()>0
			&& (userAnswers.size()==1 || getModel().getMultipleChoice())
		) {
			for(Long id : userAnswers) {
					
				// get answer and check for right question
				Answer answer = answerDao.getById(id);
				if(answer.getQuestion().getId().equals(getModel().getId())) {
					
					// create new vote entry
					Vote v = new Vote();
					v.setAnswer(answer);
					v.setDate(new Date());
					v.setUser(user);
					voteDao.save(v);
				}
			}

			sessionMap.put("question.answered." + getModel().getId(), new Date());
		}
	}



	private Long searchForAnswer(Answer aNew) {
		Long out = null;
		if(getModel()!=null && aNew.getTitle()!=null) {
			for(Answer a : getModel().getAnswers()) {
				if(a.getTitle()!= null && a.getTitle().equals(aNew.getTitle())) {
					return a.getId();
				}
			}
		}
		return out;
	}
	
	
	
	
	public Question getModel() {
		if(activeQuestion!=null && activeQuestion.getId()!=null && (activeQuestion.getAnswers()==null || activeQuestion.getAnswers().size()<1)) {
			Question question = questionDao.getById(activeQuestion.getId());
			if(question!=null) {
				activeQuestion = question;
			}
		}
		return activeQuestion;
	}
	
	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public void setCustomAnswer(String customAnswer) {
		this.customAnswer = customAnswer;
	}
}
