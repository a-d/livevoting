package de.fuberlin.livevoting.server.dao.kind;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

public abstract class SimpleObjectDAOImpl<T extends EntityDomain> extends AbstractObjectDAOImpl<T, Long> implements SimpleObjectDAO<T> {

	@Override
	public Class<Long> getIdClass() {
		return Long.class;
	}
}
