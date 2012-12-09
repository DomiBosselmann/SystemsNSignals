package functions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import rungeKutta.*;

public class stolenFromPhilipp {

	/**
	 * Builds a Function f(x,y) with equations and possible incoming Signal u.
	 * 
	 * @param equations
	 *            Each equation is one String. The equations must be in Java
	 *            Syntax and must return an double Possible Variables in the
	 *            equations: x y[0]....y[numberOfEquations], u[0]....u[n] must
	 *            be set over the method setU(double u[])
	 * @return A derivnFunction
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 *             DerivnFunction
	 */
	public static DerivnFunction getDerivnFunction(String[] equations)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		File file = CreateJavaFile("Function");
		String name = file.getName();
		name = name.substring(0, name.length() - 5); // Delete .java
		String code = CreateCodeForFunction(name, equations, 'X');
		WriteCodeOfJavaFile(file, code);
		CompileJavaFile(file);
		DerivnFunction funktion = (DerivnFunction) LoadClass(name);
		return funktion;
	}

	public static AlgebraFunction getYAlgebraFunction(String[] equations)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		File file = CreateJavaFile("YFunction");
		String name = file.getName();
		name = name.substring(0, name.length() - 5); // Delete .java
		String code = CreateCodeForFunction(name, equations, 'Y');
		WriteCodeOfJavaFile(file, code);
		CompileJavaFile(file);
		AlgebraFunction funktion = (AlgebraFunction) LoadClass(name);
		return funktion;
	}

	public static AlgebraFunction getUAlgebraFunction(String[] equations)
			throws IOException, ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		File file = CreateJavaFile("UFunction");
		String name = file.getName();
		name = name.substring(0, name.length() - 5); // Delete .java
		String code = CreateCodeForFunction(name, equations, 'U');
		WriteCodeOfJavaFile(file, code);
		CompileJavaFile(file);
		AlgebraFunction funktion = (AlgebraFunction) LoadClass(name);
		return funktion;
	}

	private static String CreateCodeForFunction(String name,
			String[] equations, char kind) throws IOException {
		String template = GetTemplateString(kind);

		String prefix;
		switch (kind) {
		case 'U':
			prefix = "U";
			break;
		case 'Y':
			prefix = "Y";
			break;
		default:
			prefix = "";
		}

		template = template.replace("package functions;", ""); // Delete the
																// package (so
																// it becomes
																// default)
		template = template.replace(prefix + "Function/*F-ID*/", name); // Set
																		// function
																		// Name

		// Define length of return vektor
		String length = "vLength = " + equations.length + ";";
		template = template.replace("/*VLENGTH*/", length);

		// Define functions
		String functions = "";
		for (int i = 0; i < equations.length; i++) {
			String newLine = "erg[" + i + "] = ";
			newLine += equations[i] + ";";
			functions += newLine + "\n";
		}
		template = template.replace("/*EQUATIONS*/", functions);
		System.out.println(functions);
		return template;
	}

	private static File CreateJavaFile(String name) throws IOException {
		File file = new File("bin\\" + name + ".java");
		File controlFile = new File("bin\\" + name + ".class");
		int index = 0;
		// Increase index until we have a file we havent load!
		while (file.exists() || controlFile.exists()) {
			index++;
			file = new File("bin\\" + name + index + ".java");
			controlFile = new File("bin\\" + name + index + ".class");
		}
		if (index != 0) {
			name = name + index;
		}
		file.createNewFile();

		return file;
	}

	private static String GetTemplateString(char kind) throws IOException {
		switch (kind) {
		case 'Y':
			return GetTemplateString("src/functions/YFunction.java");
		case 'X':
			return GetTemplateString("src/functions/Function.java");
		case 'U':
			return GetTemplateString("src/functions/UFunction.java");
		default:
			return null;
		}
	}

	private static String GetTemplateString(String source) throws IOException {
		String templateSrc = source;
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(templateSrc));
		String template = "";
		while (reader.ready()) {
			template += reader.readLine() + "\n";
		}
		return template;
	}

	private static void WriteCodeOfJavaFile(File file, String code)
			throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.print(code);
		writer.close();
	}

	private static void CompileJavaFile(File file) throws IOException {

		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

		StandardJavaFileManager fileManager = compiler.getStandardFileManager(
				null, null, null);

		File binDir = file.getParentFile();

		fileManager.setLocation(StandardLocation.CLASS_OUTPUT,
				Arrays.asList(binDir));
		// Compile the file
		compiler.getTask(null, fileManager, null, null, null,
				fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file)))
				.call();
		fileManager.close();

	}

	private static Object LoadClass(String name) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		Object instance = Class.forName(name).newInstance();
		return instance;
	}

}