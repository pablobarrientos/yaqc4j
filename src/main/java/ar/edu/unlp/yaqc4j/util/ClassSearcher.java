/*
Yaqc4j is an specification-based testing framework, based on QuickCheck for Haskell by John Hughes.
It makes easier testing task by creating test data automatically through user-defined and built-in
generators.

Copyright (C) 2013 Universidad Nacional de La Plata

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unlp.yaqc4j.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Find all classes in the classpath.
 * 
 * @author Pablo
 * 
 */
public class ClassSearcher {

	/**
	 * The allClasses.
	 */
	private static List<Class<?>> allClasses;

	/**
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Class<?>> getAllClasses() throws ClassNotFoundException,
			IOException {
		if (allClasses == null) {
			allClasses = new ArrayList<Class<?>>();
			List<File> classLocations = getClassLocationsForCurrentClasspath();
			for (File file : classLocations) {
				allClasses.addAll(getClassesFromPath(file));
			}
		}
		return allClasses;
	}

	/**
	 * @param interfaceOrSuperclass
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static List<Class<?>> getAssignableClasses(
			final Class<?> interfaceOrSuperclass)
			throws ClassNotFoundException, IOException {
		List<Class<?>> assignableClasses = new ArrayList<Class<?>>();
		for (Class<?> clazz : getAllClasses()) {
			if (interfaceOrSuperclass.isAssignableFrom(clazz)
					&& !interfaceOrSuperclass.equals(clazz)) {
				assignableClasses.add(clazz);
			}
		}
		return assignableClasses;
	}

	/**
	 * @param path
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static Collection<? extends Class<?>> getClassesFromPath(
			final File path) throws ClassNotFoundException, IOException {
		if (path.isDirectory()) {
			return getClassesFromDirectory(path);
		} else {
			return getClassesFromJarFile(path);
		}
	}

	/**
	 * @param fileName
	 * @return
	 */
	private static String fromFileToClassName(final String fileName) {
		return fileName.substring(0, fileName.length() - 6).replaceAll(
				"/|\\\\", "\\.");
	}

	/**
	 * @param path
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static List<Class<?>> getClassesFromJarFile(final File path)
			throws ClassNotFoundException, IOException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (path.canRead()) {
			JarFile jar = new JarFile(path);
			Enumeration<JarEntry> en = jar.entries();
			while (en.hasMoreElements()) {
				JarEntry entry = en.nextElement();
				if (entry.getName().endsWith("class")) {
					String className = fromFileToClassName(entry.getName());
					loadClass(classes, className);
				}
			}
			jar.close();
		}
		return classes;
	}

	/**
	 * @param path
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private static List<Class<?>> getClassesFromDirectory(final File path)
			throws ClassNotFoundException, IOException {
		List<Class<?>> classes = new ArrayList<Class<?>>();

		// get jar files from top-level directory
		List<File> jarFiles = listFiles(path, new FilenameFilter() {
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".jar");
			}
		}, false);
		for (File file : jarFiles) {
			classes.addAll(getClassesFromJarFile(file));
		}

		// get all class-files
		List<File> classFiles = listFiles(path, new FilenameFilter() {
			public boolean accept(final File dir, final String name) {
				return name.endsWith(".class");
			}
		}, true);

		// List<URL> urlList = new ArrayList<URL>();
		// List<String> classNameList = new ArrayList<String>();
		int substringBeginIndex = path.getAbsolutePath().length() + 1;
		for (File classfile : classFiles) {
			String className = classfile.getAbsolutePath().substring(
					substringBeginIndex);
			className = fromFileToClassName(className);
			loadClass(classes, className);
		}

		return classes;
	}

	/**
	 * @param directory
	 * @param filter
	 * @param recurse
	 * @return
	 */
	private static List<File> listFiles(final File directory,
			final FilenameFilter filter, final boolean recurse) {
		List<File> files = new ArrayList<File>();
		File[] entries = directory.listFiles();

		// Go over entries
		for (File entry : entries) {
			// If there is no filter or the filter accepts the
			// file / directory, add it to the list
			if (filter == null || filter.accept(directory, entry.getName())) {
				files.add(entry);
			}

			// If the file is a directory and the recurse flag
			// is set, recurse into the directory
			if (recurse && entry.isDirectory()) {
				files.addAll(listFiles(entry, filter, recurse));
			}
		}

		// Return collection of files
		return files;
	}

	/**
	 * @return
	 */
	public static List<File> getClassLocationsForCurrentClasspath() {
		List<File> urls = new ArrayList<File>();
		String javaClassPath = System.getProperty("java.class.path");
		if (javaClassPath != null) {
			for (String path : javaClassPath.split(File.pathSeparator)) {
				urls.add(new File(path));
			}
		}
		return urls;
	}

	/**
	 * @param url
	 * @return
	 * @throws MalformedURLException
	 */
	public static URL normalize(URL url) throws MalformedURLException {
		String spec = url.getFile();

		// get url base - remove everything after ".jar!/??" , if exists
		final int i = spec.indexOf("!/");
		if (i != -1) {
			spec = spec.substring(0, spec.indexOf("!/"));
		}

		// uppercase windows drive
		url = new URL(url, spec);
		final String file = url.getFile();
		final int i1 = file.indexOf(':');
		if (i1 != -1) {
			String drive = file.substring(i1 - 1, 2).toUpperCase();
			url = new URL(url, file.substring(0, i1 - 1) + drive
					+ file.substring(i1));
		}
		return url;
	}

	/**
	 * @param allClasses
	 * @param className
	 * @throws ClassNotFoundException
	 */
	private static void loadClass(final List<Class<?>> classes,
			final String className) throws ClassNotFoundException {
		Class<?> claz = null;
		try {
			claz = Class.forName(className, false,
					ClassLoader.getSystemClassLoader());
		} catch (Throwable e) {
		}
		if (claz != null) {
			classes.add(claz);
		}
	}

	// /**
	// * @param args
	// * @throws ClassNotFoundException
	// * @throws IOException
	// */
	// public static void main(String[] args) throws ClassNotFoundException,
	// IOException {
	// // find all classes in classpath
	// List<Class<?>> allClasses = ClassSearcher.getAllClasses();
	// System.out.printf(
	// "There are %s allClasses available in the classpath\n",
	// allClasses.size());
	//
	// // find all allClasses that implement/subclass an interface/superclass
	// List<Class<?>> serializableClasses = ClassSearcher
	// .getAssignableClasses(Serializable.class);
	// for (Class<?> clazz : serializableClasses) {
	// System.out.printf("%s is Serializable\n", clazz);
	// }
	// System.out
	// .printf(
	// "There are %s Serializable allClasses available in the classpath\n",
	// serializableClasses.size());
	// }
}
