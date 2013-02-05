package de.fuberlin.livevoting.server.dao.kind;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({FIELD})
@Retention(RUNTIME)

/**
 * This Annotation is used to support simple ManyToOne relationships between entities.
 * if a field is marked as SessionToMany AND OneToMany (hibernate) then the declared
 * list or set gets overloaded by a java reflection algorithm.
 * See de.kap.fabrik.dao.kind.SimpleObjectDAOImpl and de.kap.fabrik.dao.kind.SessionWrapCollection 
 */
public @interface SessionToMany {

}
