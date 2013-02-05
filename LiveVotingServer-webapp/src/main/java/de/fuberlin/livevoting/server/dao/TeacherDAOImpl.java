package de.fuberlin.livevoting.server.dao;

import org.apache.log4j.Logger;
import de.fuberlin.livevoting.server.dao.kind.SimpleObjectDAOImpl;
import de.fuberlin.livevoting.server.domain.Teacher;

public class TeacherDAOImpl extends SimpleObjectDAOImpl<Teacher> implements TeacherDAO {

	protected static final Logger log = Logger.getLogger(TeacherDAOImpl.class);

	@Override
	public Class<Teacher> getEntityClass() { return Teacher.class; }


}
