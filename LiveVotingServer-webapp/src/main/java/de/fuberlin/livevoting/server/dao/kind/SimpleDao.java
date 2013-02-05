package de.fuberlin.livevoting.server.dao.kind;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.transform.ResultTransformer;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain.DatabaseField;

public interface SimpleDao<C extends EntityDomain, I extends Serializable> {
	public static enum Methods { listAll, listByCriteria, countByCriteria, save, update, delete, merge, getById, getByKey };
	
	public List<C> listAll();
	public List<C> listByField(String key, Object value);
	public List<C> listByCriteria(DetachedCriteria dc, Integer from, Integer size);
	public List<C> listByCriteria(DetachedCriteria dc, Integer from, Integer size, ResultTransformer transformer);
	
	public int countByCriteria(DetachedCriteria dc);
	
	public void save(C object);
	public void update(C object);
	public void delete(C object);
	public C merge(C object);

	public C getById(I id);
	public C getByKey(String key, Object value);
	public C getByKey(DatabaseField field, Object value);
}
