package de.fuberlin.livevoting.server.intern.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	public static final Class<?>[] classes = ClassLoaderUtil.getClassesInPackage("de.fuberlin.livevoting.server.domain", "^de\\.fuberlin\\.livevoting\\.server\\.domain\\.[\\w]+$");
	/*
	new Class<?>[] {
		de.fuberlin.livevoting.server.domain.Answer.class,
		de.fuberlin.livevoting.server.domain.Course.class,
		de.fuberlin.livevoting.server.domain.Message.class,
		de.fuberlin.livevoting.server.domain.Question.class,
		de.fuberlin.livevoting.server.domain.Teacher.class,
		de.fuberlin.livevoting.server.domain.User.class,
		de.fuberlin.livevoting.server.domain.Vote.class,
	};
	*/
	
	static {
		try {
			
			Configuration configuration = new Configuration();
			configuration.configure();
			
			for(Class<?> c : classes) {
				System.out.println(". "+c.getName());
				configuration.addAnnotatedClass(c);
			}
			sessionFactory = configuration.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}