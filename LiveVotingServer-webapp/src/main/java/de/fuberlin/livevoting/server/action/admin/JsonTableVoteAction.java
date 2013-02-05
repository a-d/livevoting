package de.fuberlin.livevoting.server.action.admin;

import java.util.List;

import org.apache.struts2.convention.annotation.Result;
import de.fuberlin.livevoting.server.action.kind.JsonTableAction;
import de.fuberlin.livevoting.server.dao.VoteDAOImpl;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAO;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Answer;
import de.fuberlin.livevoting.server.domain.User;
import de.fuberlin.livevoting.server.domain.Vote;
import de.fuberlin.livevoting.server.domain.Vote.Field;


@Result(type = "json", params = {
        "includeProperties",
		"^(rows|page|total|records|sord|sidx),"+
        "^gridModel\\[\\d+\\]\\.(id|date)," +
        "^gridModel\\[\\d+\\]\\.answer\\.(id|title|text)," +
        "^gridModel\\[\\d+\\]\\.user\\.(id|ip)," +
        ""
    })
public class JsonTableVoteAction extends JsonTableAction<Vote> {
	private static final long serialVersionUID = 5278944653342163482L;

	private SimpleObjectDAOImpl<Vote> dao = new VoteDAOImpl();
	
	@Override
	protected Class<Vote> getEntityClass() { return Vote.class; }
	
	@Override
	protected SimpleObjectDAO<Vote> getEntityDao() { return dao; }
	
	@Override
	protected void initialize() {
		setParameterAttributes(ParameterType.String,
				Field.answer+"."+Answer.Field.title,
				Field.answer+"."+Answer.Field.text,
				Field.user+"."+User.Field.ip
		);
		setParameterAttributes(ParameterType.Long,
				Field.id,
				Field.answer+"."+Answer.Field.id,
				Field.user+"."+User.Field.id
		);
		setParameterAttributes(ParameterType.Date,
				Field.date
		);
	}
	
	@Override
	protected String parameterToField(String searchField) {
		if(searchField.equals("voteId")) { return Field.id.toString(); }
		else if(searchField.equals("userId")) { return Field.user+"."+User.Field.id; }
		else if(searchField.equals("userIp")) { return Field.user+"."+User.Field.ip; }
		else if(searchField.equals("answerId")) { return Field.answer+"."+Answer.Field.id; }
		else if(searchField.equals("answerTitle")) { return Field.answer+"."+Answer.Field.title; }
		else if(searchField.equals("answerText")) { return Field.answer+"."+Answer.Field.text; }
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
	public List<Vote> getGridModel() { return super.getGridModel(); }
	public String getSord() { return super.getSord(); }
	public String getSidx() { return super.getSidx(); }

}
