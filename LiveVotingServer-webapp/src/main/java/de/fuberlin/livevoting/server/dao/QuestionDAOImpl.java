package de.fuberlin.livevoting.server.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Course;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.User;
import de.fuberlin.livevoting.server.domain.Vote;
import de.fuberlin.livevoting.server.intern.util.HibernateUtil;

public class QuestionDAOImpl extends SimpleObjectDAOImpl<Question> implements QuestionDAO {
	protected static final Logger log = Logger.getLogger(QuestionDAOImpl.class);

	@Override
	public Class<Question> getEntityClass() { return Question.class; }


	public Integer getNumberVotes(Question question) {
		Integer out = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			out = (Integer) (
					session.createCriteria(Vote.class, "v")
					.createAlias("v.answer", "a")
					.add(Restrictions.eq("a.question", question))
					.setProjection(Projections.rowCount())
					.list()
					.get(0)
				);
		} catch (Exception e) {
			log.warn("Could not get Number of Votes from Question "+question, e);
		}

		session.close();
		return out;
	}
	

	public List<Question> getQuestionsAnsweredByUser(User u) {
		return listByCriteria(
			DetachedCriteria.forClass(Question.class, "q")
				.createAlias("q.answers", "a")
				.createAlias("a.votes", "v")
				.add(Restrictions.eq("v.user", u))
				.addOrder(Order.asc("q.dateFrom"))
		);
	}
	

	public List<Question> getActiveQuestionsFromCourse(Course c, int minutes) {
		Date currDate = new Date();
		Date lateDate = DateUtils.addMinutes(currDate, minutes);
		
		return listByCriteria(
			DetachedCriteria.forClass(Question.class)
				.add(Restrictions.eq("course", c))
				.add(Restrictions.le("dateFrom", currDate))
				.add(Restrictions.gt("dateTo", lateDate))
				.addOrder(Order.asc("dateFrom"))
		);
	}

}
