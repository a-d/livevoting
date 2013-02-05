package de.fuberlin.livevoting.server.action.admin;

import java.util.Map;
import java.util.TreeMap;

import com.opensymphony.xwork2.ModelDriven;

import de.fuberlin.livevoting.server.action.kind.ActionSupportAdmin;
import de.fuberlin.livevoting.server.dao.AnswerDAO;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.QuestionDAO;
import de.fuberlin.livevoting.server.dao.QuestionDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Question;


public class InfoQuestionAction extends ActionSupportAdmin implements ModelDriven<Question> {

	private QuestionDAO questionDao = new QuestionDAOImpl();
	private AnswerDAO answerDao = new AnswerDAOImpl();
	private Question question = new Question();
	private Map<Answer, Integer> stats;
	private Integer numberOfAllVotes = 0;

	private static final long serialVersionUID = 6721064966173343669L;

	
	public String execute() throws Exception {
		super.execute();
		
		question = questionDao.getById(question.getId());
		
		stats = new TreeMap<Answer, Integer>();
		for(Answer answer : question.getAnswers()) {
			Integer votes = answerDao.getNumberOfVotes(answer);
			numberOfAllVotes += votes;
			stats.put(answer, votes);
		}
		
		return SUCCESS;
	}

	public Map<Answer, Integer> getStats() {
		return stats;
	}
	
	public Integer getNumberOfAllVotes() {
		return numberOfAllVotes;
	}
	
	public Question getModel() {
		return question;
	}
}
