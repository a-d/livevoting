package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import de.fuberlin.livevoting.server.action.kind.JsonTableAction;
import de.fuberlin.livevoting.server.dao.AnswerDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.Question;
import de.fuberlin.livevoting.server.domain.Answer.Field;
import de.fuberlin.livevoting.server.domain.User;


@Result(type = "json", params = {
        "includeProperties",
		"^(rows|page|total|records|sord|sidx),"+
        "^gridModel\\[\\d+\\]\\.(id|title|text|picture)," +
        "^gridModel\\[\\d+\\]\\.user\\.(id|ip)," +
        "^gridModel\\[\\d+\\]\\.question\\.(id|text)," +
        ""
    })
public class JsonTableAnswerAction extends JsonTableAction<Answer> {
	private static final long serialVersionUID = 5278944653342163482L;

	private SimpleObjectDAOImpl<Answer> dao = new AnswerDAOImpl();
	
	@Override
	protected Class<Answer> getEntityClass() { return Answer.class; }
	
	@Override
	protected SimpleObjectDAO<Answer> getEntityDao() { return dao; }
	
	@Override
	protected void initialize() {
		setParameterAttributes(ParameterType.String,
				Field.title,
				Field.text,
				Field.picture,
				Field.user+"."+User.Field.ip,
				Field.question+"."+Question.Field.text
		);
		setParameterAttributes(ParameterType.Long,
				Field.id,
				Field.user+"."+User.Field.id,
				Field.question+"."+Question.Field.id
		);
	}
	
	@Override
	protected String parameterToField(String searchField) {
		if(searchField.equals("answerId")) { return Field.id.toString(); }
		else if(searchField.equals("userId")) { return Field.user+"."+User.Field.id.toString(); }
		else if(searchField.equals("userIp")) { return Field.user+"."+User.Field.ip.toString(); }
		else if(searchField.equals("questionId")) { return Field.question+"."+Question.Field.id.toString(); }
		else if(searchField.equals("questionText")) { return Field.question+"."+Question.Field.text.toString(); }
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
	public List<Answer> getGridModel() { return super.getGridModel(); }
	public String getSord() { return super.getSord(); }
	public String getSidx() { return super.getSidx(); }

}
