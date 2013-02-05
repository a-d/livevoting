package de.fuberlin.livevoting.server.intern.util;

import java.lang.reflect.Field;
import java.util.Collection;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

public class ReflectionUtil {

	private static final Logger log = Logger.getLogger(ReflectionUtil.class);

	public static void copySimpleProperties(Object source, Object target) {
		for(Field srcField : source.getClass().getDeclaredFields()) {
			srcField.setAccessible(true);
			if(!Collection.class.isAssignableFrom(srcField.getType())) {
				try {
					BeanUtils.copyProperty(target, srcField.getName(), srcField.get(source));
				} catch (Exception e) {
					log.warn("Could not update property "+srcField+" from "+source+" to "+target, e);
				}
			}
		}
	}
}
