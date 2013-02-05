package de.fuberlin.livevoting.server.action.kind;

import org.apache.commons.logging.Log;


public abstract class ActionSupportParameter extends ActionSupportAdmin {
	private static final long serialVersionUID = -6716581092643609624L;

	// validation / authentication
	protected abstract Log getLog();
}
