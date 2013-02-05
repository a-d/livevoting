package de.fuberlin.livevoting.server.action.kind;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.domain.kind.EntityDomain;


@Result(type = "json")
public abstract class JsonTableAction<T extends EntityDomain> extends ActionSupportAdmin {

	private static final long serialVersionUID = 5078264277068533593L;
	private static final Log log = LogFactory.getLog(JsonTableAction.class);
	private static final String PROPERTY_ID = "id";
	private static final String DEFAULT_SORT_INDEX = "id";
	private static final SimpleDateFormat defaultTimeFormatter = new SimpleDateFormat("dd.mm.yyyy");
	
	protected static enum ParameterType { String, Integer, Double, Long, LongArray, Date, Boolean }
	
	
	// Your result List
	private List<T> gridModel;

	// get how many rows we want to have into the grid - rowNum attribute in the
	// grid
	private Integer rows = 0;

	// Get the requested page. By default grid sets this to 1.
	private Integer page = 0;

	// sorting order - asc or desc
	private String sord = "asc";

	// get index row - i.e. user click to sort.
	private String sidx = DEFAULT_SORT_INDEX;

	// Search Field
	private String searchField;

	// The Search String
	private String searchString;

	// he Search Operation
	// ['eq','ne','lt','le','gt','ge','bw','bn','in','ni','ew','en','cn','nc']
	private String searchOper;

	// Your Total Pages
	private Integer total = 0;

	// All Records
	private Integer records = 0;


	
	// attribute settings
	private HashMap<ParameterType, HashSet<String>> attrList = new HashMap<ParameterType, HashSet<String>>();
	{
		for(ParameterType pt : ParameterType.values())
			attrList.put(pt, new HashSet<String>());
	}
	
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */

	abstract protected Class<T> getEntityClass();
	abstract protected SimpleObjectDAO<T> getEntityDao();
	abstract protected void initialize();
	
	protected void postEditCriteria(DetachedCriteria criteria) {
		// do nothing
	}

	protected String parameterToField(String searchField) {
		// do nothing
		return searchField;
	}
	
	
	public void setForceId(String id) {
		setSearchField("id");
		setSearchOper("eq");
		setSearchString(id);
	}

	protected void setParameterAttributes(ParameterType type, Object... arr) {
		HashSet<String> typeSet = attrList.get(type);
		for(Object field : arr) {
			if(field!=null) {
				typeSet.add(field.toString());
			}
		}
	}

		

	public String execute() {
		String superResult = ERROR;
		try {
			superResult = super.execute();
		} catch (Exception e) {
			log.error("Could not execute parent action.", e);
		}
		if(superResult!=SUCCESS) {
			log.error("Parent action execution failed: "+superResult);
		}
		
		
		initialize();
		
		log.debug("Page " + getPage() + " Rows " + getRows() + " Sorting Order " + getSord() + " Index Row :" + getSidx());
		log.debug("Search :" + searchField + " " + searchOper + " " + searchString);

		// Calcalate until rows ware selected
		int to = (rows * page);

		// Calculate the first row to read
		int from = to - rows;

		// Criteria to Build SQL
		DetachedCriteria criteria = DetachedCriteria.forClass(getEntityClass());
		


		// Handle Search
		if (searchField != null) {
			searchField = parameterToField(searchField);
			handleSearchWithRestriction(criteria, searchField, searchString, searchOper);
		}

		postEditCriteria(criteria);

		
		// Count Companies
		records = getEntityDao().countByCriteria(criteria);

		
		// Reset count Projection
		criteria.setProjection(null);

		
		// Handle Order By
		String ordBy = parameterToField(getSidx());
		criteria.addOrder(getSord().equals("asc") ? Order.asc(ordBy) : Order.desc(ordBy));

		criteria.setProjection(
			Projections.projectionList()
				.add(Projections.distinct(Projections.property(PROPERTY_ID)), "id")
				.add(Projections.property(ordBy), "ord")
		);
		
		// Get entity Ids by Criteria
		List<?> rowsRawList = getEntityDao().listByCriteria(criteria, from, rows, Criteria.PROJECTION);
		List<Long> idList = new LinkedList<Long>();
		for(Object rowRaw : rowsRawList) {
			if(rowRaw instanceof Object[]) {
				Object[] row = (Object[]) rowRaw;
				if(row.length>0 && row[0] instanceof Long) {
					idList.add((Long) row[0]);
				}
			}
		}

		// Get Entites by Criteria plus evaluated Ids
		criteria.setProjection(null);
		criteria.add(Restrictions.in(PROPERTY_ID, idList));
		gridModel = getEntityDao().listByCriteria(criteria, null, null);

		// Set to = max rows
		if (to > records)
			to = records;

		// Calculate total Pages
		total = (int) Math.ceil((double) records / (double) rows);

		
		log.info("Getting List from type "+getEntityClass());

		return SUCCESS;
	}

	private void handleSearchWithRestriction(DetachedCriteria criteria, String field, String value, String oper) {
		for(Entry<ParameterType, HashSet<String>> entry : attrList.entrySet()) {
			try {
				if(entry.getValue().contains(field)) {
					ParameterType type = entry.getKey();
					Object searchValue = null;
					switch(type) {
						case String:
							searchValue = value;
							break;
						case Integer:
							searchValue = Integer.parseInt(value);
							break;
						case Double:
							searchValue = Double.parseDouble(value);
							break;
						case Long:
							searchValue = Long.parseLong(value);
							break;
						case Boolean:
							searchValue = Boolean.parseBoolean(value);
							break;
						case Date:
							searchValue = defaultTimeFormatter.parse(value);
							break;
						case LongArray:
							String[] stmp = value.split(",");
							Long[] ltmp = new Long[stmp.length];
							for(int i=0; i<stmp.length; i++) {
								ltmp[i] = Long.parseLong(stmp[i]);
							}
							searchValue = ltmp;
							break;
					}
					addRestrToCrit(criteria, field, searchValue, oper);
				}
			} catch (ParseException e) {
				log.error("Parsing of field \""+field+"\" with value \""+value+"\" and operation \""+oper+"\" failed.", e);
			}
		}
		
	}
	private void addRestrToCrit(DetachedCriteria criteria, String field, Object value, String oper) {
		Criterion crit = null;
		if (oper.equals("eq"))
			crit = Restrictions.eq(field, value);
		else if (oper.equals("ne"))
			crit = Restrictions.ne(field, value);
		else if (oper.equals("lt"))
			crit = Restrictions.lt(field, value);
		else if (oper.equals("gt"))
			crit = Restrictions.gt(field, value);
		else if (oper.equals("bw"))
			crit = Restrictions.like(field, value+"%");
		else if (oper.equals("cn"))
			crit = Restrictions.like(field, "%"+value+"%");
		else if (oper.equals("in") || oper.equals("ni")) {
			crit = createCollectionRestriction(oper, field, value);
		}
	
		if(crit!=null) {
			criteria.add(crit);
		}
	}		
			
	private Criterion createCollectionRestriction(String oper, String field, Object value) {
		if(oper.equals("in")) {
			if(value instanceof Collection<?>) {
				return Restrictions.in(field, (Collection<?>) value);
			}
			if(value instanceof Object[]) {
				return Restrictions.in(field, (Object[]) value);
			}
		}
		else if(oper.equals("ni")) {
			Criterion in = createCollectionRestriction("in", field, value);
			if(in!=null) {
				return Restrictions.or(
						Restrictions.isNull(field),
						Restrictions.not(in)
				);
			}
		}
		return null;
	}

	
	protected static <T> HashMap<T,T> arrayIdToMap(T[] a) {
		HashMap<T, T> out = new HashMap<T,T>();
		for(int i=0; i<a.length; i+=2) {
			out.put(a[i], a[i+1]);
		}
		return out;
	}
	
	
	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	
	public String getJSON() {
		return execute();
	}

	/**
	 * @return how many rows we want to have into the grid
	 */
	public Integer getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            how many rows we want to have into the grid
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * @return current page of the query
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page
	 *            current page of the query
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return total pages for the query
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            total pages for the query
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return total number of records for the query. e.g. select count(*) from
	 *         table
	 */
	public Integer getRecords() {
		return records;
	}

	/**
	 * @param record
	 *            total number of records for the query. e.g. select count(*)
	 *            from table
	 */
	public void setRecords(Integer records) {

		this.records = records;

		if (this.records > 0 && this.rows > 0) {
			this.total = (int) Math.ceil((double) this.records
					/ (double) this.rows);
		} else {
			this.total = 0;
		}
	}

	/**
	 * @return an collection that contains the actual data
	 */
	public List<T> getGridModel() {
		return gridModel;
	}

	/**
	 * @return sorting order
	 */
	public String getSord() {
		return sord;
	}

	/**
	 * @param sord
	 *            sorting order
	 */
	public void setSord(String sord) {
		if(sord!=null && !sord.equals("")) {
			this.sord = sord;
		}
	}

	/**
	 * @return get index row - i.e. user click to sort.
	 */
	public String getSidx() {
		return sidx;
	}

	/**
	 * @param sidx
	 *            get index row - i.e. user click to sort.
	 */
	public void setSidx(String sidx) {
		if(sidx!=null && !sidx.equals("")) {
			this.sidx = sidx;
		}
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public String getSearchString() {
		return searchString;
	}

	public String getSearchOper() {
		return searchOper;
	}
	
	public String getSearchField() {
		return searchField;
	}
	
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}
}
