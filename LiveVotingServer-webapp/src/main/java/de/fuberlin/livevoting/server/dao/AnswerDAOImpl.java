package de.fuberlin.livevoting.server.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Vote;
import de.fuberlin.livevoting.server.intern.util.HibernateUtil;

public class AnswerDAOImpl extends SimpleObjectDAOImpl<Answer> implements AnswerDAO {

	protected static final Logger log = Logger.getLogger(AnswerDAOImpl.class);

	@Override
	public Class<Answer> getEntityClass() { return Answer.class; }

	public Integer getNumberOfVotes(Answer answer) {
		Integer out = null;

		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			out = ((Number)
					session.createCriteria(Vote.class, "v")
					.createAlias("v.answer", "a")
					.add(Restrictions.eq("a.id", answer.getId()))
					.setProjection(Projections.rowCount())
					.list()
					.get(0)
				).intValue();
		} catch (Exception e) {
			log.warn("Could not get Number of Votes from Answer "+answer, e);
		}

		session.close();
		return out;
	}


}
