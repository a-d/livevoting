package de.fuberlin.livevoting.server.dao.kind;

import de.fuberlin.livevoting.server.domain.kind.EntityDomain;

public interface SimpleObjectDAO<T extends EntityDomain> extends SimpleDao<T, Long> {

}
