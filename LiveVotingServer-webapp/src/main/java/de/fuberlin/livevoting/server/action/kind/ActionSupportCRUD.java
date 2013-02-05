package de.fuberlin.livevoting.server.action.kind;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.Transaction;

import com.googlecode.s2hibernate.struts2.plugin.annotations.TransactionTarget;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain;
import de.fuberlin.livevoting.server.intern.util.ReflectionUtil;


@Results( { @Result(name = "error", location = "messages.jsp") })
public abstract class ActionSupportCRUD<T extends EntityDomain> extends ActionSupportParameter implements CRUDAction {

	private static final long serialVersionUID = -3454448309088641394L;
	private static final Log log = LogFactory.getLog(ActionSupportCRUD.class);


	@TransactionTarget
	protected Transaction hTransaction;
	
	private String oper = "edit";

	private List<T> entityList = new LinkedList<T>();
	private Long[] ids = new Long[0];

	protected abstract SimpleObjectDAO<T> getModelDao();
	public abstract T getModel();
	public abstract void setModel(T obj);

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */

	protected List<T> listPrepare() throws Exception {
		return getModelDao().listAll();
	}

	protected void createPrepare(T obj) throws Exception {
		// overload if needed
	}
	protected void createFinish(T obj) throws Exception {
		// overload if needed
	}
	protected void updatePrepare(T obj, T oldObj) throws Exception {
		// overload if needed
	}
	protected void deletePrepare(T obj) throws Exception {
		// overload if needed
	}
	protected void updateFinish(T dbObj) throws Exception {
		// overload if needed
	}
	protected void savePrepare(T obj) throws Exception {
		// overload if needed
	}
	protected void saveFinish(T obj) throws Exception {
		// overload if needed
	}

	private T getOriginalModel(T obj) throws Exception {
		T oldObj = getModelDao().getById(obj.getId());
		return oldObj;
	}

	
	public String save() {
		T obj = getModel();
		if(obj!=null) {
			String out;
			try {
				savePrepare(obj);
				out = obj.getId()!=null ? update() : create();
				saveFinish(obj);
				return out;
			}
			catch(Exception e) {
				getLog().warn("\nKonnte "+obj+" nicht erfolgreich speichern.", e);
			}
		}
		return ERROR;
	}
	
	public String update() {
		T obj = getModel();
		try {
			T oldObj = getOriginalModel(obj);
			updatePrepare(obj, oldObj);
			ReflectionUtil.copySimpleProperties(obj, oldObj);
			getModelDao().update(oldObj);
			updateFinish(oldObj);
			getLog().info("Updated "+oldObj);
		} catch (Exception e) {
			getLog().warn("Kann "+obj.getClass()+" nicht speichern", e);
			return ERROR;
		}
		return SUCCESS;
		
	}

	public String create() {
		T obj = getModel();
		try {
			createPrepare(obj);
			getModelDao().save(obj);
			getLog().info("\nAdded "+obj);
			createFinish(obj);
		} catch (Exception e) {
			getLog().warn("\nKann "+obj.getClass()+" nicht speichern", e);
			return ERROR;
		}
		return SUCCESS;
	}

	
	public String delete() {
		T obj = getModel();
		try {
			Long id = obj.getId();
			if(id!=null) {
				ids = new Long[] { id };
			}
			for(Long i : ids) {
				obj = getModelDao().getById(i);
				if(obj!=null) {
					deletePrepare(obj);
					getModelDao().delete(obj);
				}
			}
		} catch (Exception e) {
			getLog().warn("\nKann "+obj.getClass()+" nicht speichern", e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	public String list() {
		try {			
			entityList = listPrepare();
			entityListSortById(entityList);
			return SUCCESS;
		}
		catch(Exception e) {
			getLog().warn("Unable to get list", e);
			return ERROR;
		}
	}
	
	// helper
	protected void entityListSortById(List<T> entityList) {
		Collections.sort(entityList, new Comparator<T>() {
			public int compare(T o1, T o2) {
				return o1.getId().compareTo(o2.getId());
			}
		});
	}
	
	
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */	
	
	public String execute() throws Exception {
		try {
			String out = ERROR;
			if (oper.equalsIgnoreCase("add")) {
				log.debug("Add");
				out = save();
			} else if (oper.equalsIgnoreCase("edit")) {
				log.debug("Edit");
				out = save();
			} else if (oper.equalsIgnoreCase("del")) {
				log.debug("Delete");
				out = delete();
			} else if (oper.equalsIgnoreCase("list")) {
				log.debug("List");
				out = list();
			}

			if(out.equals(ERROR)) {
				throw new RuntimeException("A problem occured with action: "+oper);
			}
			
			// Commit changes
			getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			getTransaction().rollback();
			addActionError("ERROR : " + e.getLocalizedMessage());
			return ERROR;
		}
		return NONE;
	}

	
	private Transaction getTransaction() {
		if(hTransaction==null) {
			log.error("(CRUD) Transaction is not set for object "+getModel());
		}
		return hTransaction;
	}

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */

	/**
	 * @return the oper
	 */
	public String getOper() {
		return oper;
	}
	/**
	 * @param oper the oper to set
	 */
	public void setOper(String oper) {
		this.oper = oper;
	}

	
	public List<T> getList() {
		return entityList;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}
}
