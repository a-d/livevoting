package de.fuberlin.livevoting.server.intern.util;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ClassLoaderUtil {
	/**
	 * Scans all classes accessible from the context class loader which belong
	 * to the given package and subpackages. Adapted from
	 * http://snippets.dzone.com/posts/show/4831 and extended to support use of
	 * JAR files
	 * 
	 * @param packageName
	 *            The base package
	 * @param regexFilter
	 *            an optional class name pattern.
	 * @return The classes
	 */
	public static Class<?>[] getClassesInPackage(String packageName, String regexFilter) {
		Pattern regex = null;
		if (regexFilter != null)
			regex = Pattern.compile(regexFilter);

		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			assert classLoader != null;
			String path = packageName.replace('.', '/');
			Enumeration<URL> resources = classLoader.getResources(path);
			List<URL> dirs = new ArrayList<URL>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(resource);
			}
			TreeSet<String> classes = new TreeSet<String>();
			for (URL dirLink : dirs) {
				classes.addAll(findClasses(dirLink, packageName, regex));
			}
			ArrayList<Class<?>> classList = new ArrayList<Class<?>>();
			for (String clazz : classes) {
				classList.add(Class.forName(clazz));
			}
			return classList.toArray(new Class[classes.size()]);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Recursive method used to find all classes in a given path (directory or
	 * zip file url). Directories are searched recursively. (zip files are
	 * Adapted from http://snippets.dzone.com/posts/show/4831 and extended to
	 * support use of JAR files
	 * 
	 * @param path
	 *            The base directory or url from which to search.
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @param regex
	 *            an optional class name pattern. e.g. .*Test
	 * @return The classes
	 */
	private static TreeSet<String> findClasses(URL dirLink, String packageName, Pattern regex) throws Exception {
		TreeSet<String> classes = new TreeSet<String>();
		String path = dirLink.getFile();
		if (path.startsWith("file:") && path.contains("!")) {
			String[] split = path.split("!");
			URL jar = new URL(split[0]);
			ZipInputStream zip = new ZipInputStream(jar.openStream());
			ZipEntry entry;
			while ((entry = zip.getNextEntry()) != null) {
				if (entry.getName().endsWith(".class")) {
					String className = entry.getName().replaceAll("[$].*", "").replaceAll("[.]class", "").replace('/', '.');
					if (className.startsWith(packageName) && (regex == null || regex.matcher(className).matches()))
						classes.add(className);
				}
			}
		}
		
		File dir = new File(dirLink.toURI());
		//File dir = new File(path);
		if (!dir.exists()) {
			return classes;
		}
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				URL subDirLink = file.toURI().toURL();
				classes.addAll(findClasses(subDirLink, packageName + "." + file.getName(), regex));
			} else if (file.getName().endsWith(".class")) {
				String className = packageName + '.' + file.getName().substring(0, file.getName().length() - 6);
				if (regex == null || regex.matcher(className).matches())
					classes.add(className);
			}
		}
		return classes;
	}
}
