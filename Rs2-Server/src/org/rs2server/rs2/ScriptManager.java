package org.rs2server.rs2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.logging.Logger;

import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyFunction;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * 
 * @author Josh Mai
 * 
 */
public class ScriptManager {

	private static ScriptManager instance = null;

	public static ScriptManager getScriptManager() {
		if (instance == null) {
			instance = new ScriptManager();
		}
		return instance;
	}

	private PythonInterpreter interp;

	private final Logger logger = Logger.getLogger(this.toString());

	public ScriptManager() {
		interp = new PythonInterpreter();
	}

	/**
	 * Call a definition function.
	 * 
	 * @param name
	 *            the name of the script.
	 * @param bindings
	 *            the objects to pass to the script.
	 * @return whether the script was called or not.
	 */
	public boolean call(String name, Object... bindings) {
		try {
			PyObject obj = interp.get(name);
			if (obj != null && obj instanceof PyFunction) {
				PyFunction func = (PyFunction) obj;
				PyObject[] objects = new PyObject[bindings.length];
				for (int i = 0; i < bindings.length; i++) {
					Object bind = bindings[i];
					objects[i] = Py.java2py(bind);
				}
				func.__call__(objects);
			} else {
				return false;
			}
			return true;
		} catch (PyException e) {
			System.out.println(e.type);
			System.out.println(e.value);
			System.out.println(e.traceback);
			logger.severe("Problem while parsing: " + name + ":");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Load or reload the directory of scripts.
	 * 
	 * @throws IOException
	 */
	public void loadScripts() throws IOException {
		logger.info("Loading scripts...");
		interp.cleanup();
		File scriptDir = new File(Constants.SCRIPTS_DIRECTORY);
		int parsed = 0;
		if (scriptDir.isDirectory()) {
			File[] children = scriptDir.listFiles();
			for (File child : children) {
				if (child.isFile() && child.getName().endsWith(".py")) {
					final long start = System.nanoTime();
					interp.execfile(new FileInputStream(child));
					final long elapsed = System.nanoTime() - start;
					logger.info("Successfully loaded script: "
							+ child.getName() + " in " + elapsed / 1000000D
							+ " milliseconds");
					parsed++;
				}
			}
			logger.info("Parsed a total of " + parsed + " scripts.");
		}
	}
}
