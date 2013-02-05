package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import de.fuberlin.livevoting.server.action.kind.JsonTableAction;
import de.fuberlin.livevoting.server.dao.TeacherDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Teacher;
import de.fuberlin.livevoting.server.domain.Teacher.Field;


@Result(type = "json", params = {
        "includeProperties",
		"^(rows|page|total|records|sord|sidx),"+
        "^gridModel\\[\\d+\\]\\.(id|name|hashedPassword|ip|browserAgent|browserHash|dateRegistered)," +
        ""
    })
public class JsonTableTeacherAction extends JsonTableAction<Teacher> {
	private static final long serialVersionUID = 5278944653342163482L;

	private SimpleObjectDAOImpl<Teacher> dao = new TeacherDAOImpl();
	
	@Override
	protected Class<Teacher> getEntityClass() { return Teacher.class; }
	
	@Override
	protected SimpleObjectDAO<Teacher> getEntityDao() { return dao; }
	
	@Override
	protected void initialize() {
		setParameterAttributes(ParameterType.String,
				Field.name,
				Field.hashedPassword,
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
				Field.dateRegistered
		);
	}
	
	@Override
	protected String parameterToField(String searchField) {
		if(searchField.equals("teacherId")) { return Field.id.toString(); }
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
	public List<Teacher> getGridModel() { return super.getGridModel(); }
	public String getSord() { return super.getSord(); }
	public String getSidx() { return super.getSidx(); }

}
