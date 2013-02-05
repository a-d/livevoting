package de.fuberlin.livevoting.server.dao;

import java.util.List;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.Course;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.User;

public interface QuestionDAO extends SimpleObjectDAO<Question> {

	public Integer getNumberVotes(Question question);
	public List<Question> getQuestionsAnsweredByUser(User u);
	public List<Question> getActiveQuestionsFromCourse(Course c, int minutes);

}
