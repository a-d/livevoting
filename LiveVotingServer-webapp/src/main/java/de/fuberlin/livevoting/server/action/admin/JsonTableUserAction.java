package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import de.fuberlin.livevoting.server.action.kind.JsonTableAction;
import de.fuberlin.livevoting.server.dao.UserDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.User;
import de.fuberlin.livevoting.server.domain.User.Field;


@Result(type = "json", params = {
        "includeProperties",
		"^(rows|page|total|records|sord|sidx),"+
        "^gridModel\\[\\d+\\]\\.(id|ip|browserAgent|browserHash|date)," +
        ""
    })
public class JsonTableUserAction extends JsonTableAction<User> {
	private static final long serialVersionUID = 5278944653342163482L;

	private SimpleObjectDAOImpl<User> dao = new UserDAOImpl();
	
	@Override
	protected Class<User> getEntityClass() { return User.class; }
	
	@Override
	protected SimpleObjectDAO<User> getEntityDao() { return dao; }
	
	@Override
	protected void initialize() {
		setParameterAttributes(ParameterType.String,
				Field.ip,
				Field.browserAgent
		);
		setParameterAttributes(ParameterType.Long,
				Field.id
		);
		setParameterAttributes(ParameterType.Integer,
				Field.browserHash
		);
		setParameterAttributes(ParameterType.Date,
				Field.date
		);
	}
	
	@Override
	protected String parameterToField(String searchField) {
		if(searchField.equals("userId")) { return Field.id.toString(); }
		else return super.parameterToField(searchField);
	}

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	
	public String getJSON() { return super.getJSON(); }
	public Integer getRows() { return super.getRows(); }
	public Integer getPage() { return super.getPage(); }
	public Integer getTotal() { return super.getTotal(); }
	public Integer getRecords() { return super.getRecords(); }
	public List<User> getGridModel() { return super.getGridModel(); }
	public String getSord() { return super.getSord(); }
	public String getSidx() { return super.getSidx(); }

}
