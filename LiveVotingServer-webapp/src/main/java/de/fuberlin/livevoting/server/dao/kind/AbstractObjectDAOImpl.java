package de.fuberlin.livevoting.server.dao.kind;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;

import org.apache.log4j.Logger;
import com.googlecode.s2hibernate.struts2.plugin.annotations.SessionTarget;
import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

@SuppressWarnings("unchecked")
public abstract class AbstractObjectDAOImpl<C extends EntityDomain, I extends Serializable> implements SimpleDao<C, I> {


	private static final Logger log = Logger.getLogger(AbstractObjectDAOImpl.class);
	
	public abstract Class<C> getEntityClass();
	public abstract Class<I> getIdClass();

	
	
	@SessionTarget
	protected Session hSession;

	@TransactionTarget
	protected Transaction hTransaction;
	
	
	public List<C> listAll() {
		try {
			return hSession.createCriteria(getEntityClass())
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}


	public List<C> listByField(String key, Object value) {
		try {
			return hSession.createCriteria(getEntityClass())
					.add(Restrictions.eq(key, value))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public List<C> listByField(EntityDomain.DatabaseField field, Object value) {
		return listByField(field.toString().toLowerCase(), value);
	}

	
	public List<C> listByCriteria(DetachedCriteria dc) {
	    return listByCriteria(dc, null, null);
	}

	public List<C> listByCriteria(DetachedCriteria dc, Integer from, Integer size) {
	    return listByCriteria(dc, from, size, Criteria.DISTINCT_ROOT_ENTITY);
	}


	public List<C> listByCriteria(DetachedCriteria dc, Integer from, Integer size, ResultTransformer transformer) {
	    if (log.isDebugEnabled())
	    	log.debug("Return "+getEntityClass().getSimpleName()+" from " + from + " to " + size);

		try {
			Criteria criteria = dc.getExecutableCriteria(hSession);
			criteria.setResultTransformer(transformer);
			criteria.setFirstResult(from!=null ? from : -1);
			criteria.setMaxResults(size!=null ? size : Integer.MAX_VALUE);
			return criteria.list();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	


	public int countByCriteria(DetachedCriteria dc) {
		if (log.isDebugEnabled())
			log.debug("count "+getEntityClass().getSimpleName());

		try {
			Criteria criteria = dc
									.getExecutableCriteria(hSession)
									.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
									.setProjection(Projections.rowCount());
			return ((Long) criteria.list().get(0)).intValue();
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}
	
	public I nextIdentifier() {
		// TODO unused
		if (log.isDebugEnabled())
			log.debug("find next customer number");

		try {
			DetachedCriteria dc = DetachedCriteria.forClass(getEntityClass());
			Criteria criteria = dc
									.getExecutableCriteria(hSession)
									.setProjection(Projections.max("id"));
			Number n = (Number) criteria.list().get(0);
			return getIdClass().cast(n.longValue() + 1);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		} catch(Exception e) {
			log.error("Could not get next identifier", e);
			return null;
		}
	}
	
	public C getById(I id) {
		try {
			return (C) hSession.get(getEntityClass(), id);
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}


	public C getByKey(String key, Object value) {
		try {
			List<?> list = hSession.createCriteria(getEntityClass())
					.add(Restrictions.eq(key, value))
					.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
					.list();
			return list.size()>0 ? (C) list.get(0) : null;
		} catch (HibernateException e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public C getByKey(EntityDomain.DatabaseField field, Object value) {
		return getByKey(field.toString(), value);
	}
	
	public void save(C object) {
		try {
			hSession.save(object);
		} catch (HibernateException e) {
			hTransaction.rollback();
			log.error(e.getMessage());
			log.error("Be sure your Database is in read-write mode!");
			throw e;
		}
	}

	public void update(C object) {
		try {
			hSession.update(object);
		} catch (HibernateException e) {
			hTransaction.rollback();
			log.error(e.getMessage());
			log.error("Be sure your Database is in read-write mode!");
			throw e;
		}
	}

	
	public C merge(C object) {
		try {
			Object objNew = hSession.merge(object);
			hSession.save(objNew);
			return (C) objNew;
		} catch (HibernateException e) {
			hTransaction.rollback();
			log.error(e.getMessage());
			log.error("Be sure your Database is in read-write mode!");
			throw e;
		}
	}
	
	public void delete(C actual) {
		try {
			hSession.delete(actual);
		} catch (HibernateException e) {
			hTransaction.rollback();
			log.error(e.getMessage());
			log.error("Be sure your Database is in read-write mode!");
			throw e;
		}
	}
}
