// Autor: Dominique Bosselmann, 3530073, Unterstützung durch Philipp Stadelmann

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;


public class MathEvaluator {
	private static String name = "SampleClass";
	private static String basename = name;
	private static File file;
	
	
	public static DerivnFunction convertFirstOrderEquationToClass (String[] equations) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		
		createClassFirstOrder(equations);
		compileClass();
		
		return getFirstOrderInstance();
	}
	
	public static SimpleFunction convertNullOrderEquationToClass (String[] equations) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		createClassNullOrder(equations);
		compileClass();
		
		return getNullOrderInstance();
	}
	
	public static OutputFunction convertOutputFunctionToClass (String[] equations) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		createOutputFunction(equations);
		compileClass();
		
		return getOutputInstance();
	}
	
	private static void createClassFirstOrder (String[] equations) throws IOException {
		// Template basteln
		
		String classTemplate = "public class %s implements DerivnFunction {\n%s\n%s\n}";
		String uVariableTemplate = "\tprivate double[] u;";
		String derivnMethodTemplate = "\tpublic double[] derivn(double t, double[] x) {\n\t\tdouble[] x_dot_vec = new double[x.length];\n\n\t\t%s\n\n\t\treturn x_dot_vec;\n\t}";
		String setUMethodTemplate = "\tpublic void setU(double[] u) {\n\t\tthis.u = u;\n\t}";
		
		// Strings in Code umwandeln
		
		String equationsAsCode = "";
		
		for (int i = 0; i < equations.length; i++) {
			if (equations[i] == null) {
				break;
			}
			equationsAsCode += "x_dot_vec[" + i + "] = " + equations[i] + ";\n\t\t";
		}
		
		// zusammenklatschen
		
		file = new File("bin/" + name + ".java");
		int i = 1;
		
		while (file.exists()) {
			name = basename + i;
			file = new File("bin/" + name + ".java");
			i++;
		}
		
		String method = String.format(derivnMethodTemplate, equationsAsCode) + String.format(setUMethodTemplate, "");
		String classCode = String.format(classTemplate, name, uVariableTemplate, method);
		
		// wegschreiben
		
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.print(classCode);
		writer.close();
	}
	
	private static void createClassNullOrder (String[] equations) throws IOException {
		// Template basteln
		
		String classTemplate = "public class %s implements SimpleFunction {\n%s\n%s\n}";
		String constantTemplate = "\tprivate double[] u;";
		String derivnMethodTemplate = "\tpublic double[] calc(double t) {\n\t\tdouble[] x = new double[" + equations.length + "];\n\n\t\t%s\n\n\t\treturn x;\n\t}";
		String setUMethodTemplate = "\tpublic void setU(double[] u) {\n\t\tthis.u = u;\n\t}";
		
		// Strings in Code umwandeln
		
		String equationsAsCode = "";
		
		for (int i = 0; i < equations.length; i++) {
			if (equations[i] == null) {
				break;
			}
			
			equationsAsCode += "x[" + i + "] = " + equations[i] + ";\n\t\t";
		}
		
		file = new File("bin/" + name + ".java");
		int i = 1;
		
		while (file.exists()) {
			name = basename + i;
			file = new File("bin/" + name + ".java");
			i++;
		}
		
		// zusammenklatschen
		
		String method = String.format(derivnMethodTemplate, equationsAsCode) + String.format(setUMethodTemplate, "");
		String classCode = String.format(classTemplate, name, constantTemplate, method);
		
		// wegschreiben
		
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.print(classCode);
		writer.close();
	}
	
	private static void createOutputFunction (String[] equations) throws IOException {
		// Template basteln
		
		String classTemplate = "public class %s implements OutputFunction {\n%s\n%s\n}";
		String constantTemplate = "\tprivate double[] u;";
		String derivnMethodTemplate = "\tpublic double[] calc(double t, double[] x) {\n\t\tdouble[] y = new double[" + equations.length + "];\n\n\t\t%s\n\n\t\treturn y;\n\t}";
		String setUMethodTemplate = "\tpublic void setU(double[] u) {\n\t\tthis.u = u;\n\t}";
		
		// Strings in Code umwandeln
		
		String equationsAsCode = "";
		
		for (int i = 0; i < equations.length; i++) {
			if (equations[i] == null) {
				break;
			}
			
			equationsAsCode += "y[" + i + "] = " + equations[i] + ";\n\t\t";
		}
		
		file = new File("bin/" + name + ".java");
		int i = 1;
		
		while (file.exists()) {
			name = basename + i;
			file = new File("bin/" + name + ".java");
			i++;
		}
		
		// zusammenklatschen
		
		String method = String.format(derivnMethodTemplate, equationsAsCode) + String.format(setUMethodTemplate, "");
		String classCode = String.format(classTemplate, name, constantTemplate, method);
		
		// wegschreiben
		
		PrintWriter writer = new PrintWriter(new FileWriter(file));
		writer.print(classCode);
		writer.close();
	}
	
	private static void compileClass () throws IOException {
		// Vorbereiten
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		File binDir = file.getParentFile();
		fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(binDir));
		
		// Kompilieren
		
		compiler.getTask(null, fileManager, null, null, null,
				fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file)))
				.call();
		fileManager.close();
	}
	
	private static DerivnFunction getFirstOrderInstance () throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Instanz für Rungekutta zurückwerfen
		Object instance = Class.forName(name).newInstance();
		System.out.println(instance instanceof DerivnFunction ? "yay" : "och nö");
		return (DerivnFunction) instance;
	}
	
	private static SimpleFunction getNullOrderInstance () throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Instanz zur Berechnung zurückwerfen
		Object instance = Class.forName(name).newInstance();
		System.out.println(instance instanceof SimpleFunction ? "yay" : "och nö");
		return (SimpleFunction) instance;
	}
	
	private static OutputFunction getOutputInstance () throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// Instanz für Ausgang zurückwerfen
		Object instance = Class.forName(name).newInstance();
		System.out.println(instance instanceof SimpleFunction ? "yay" : "och nö");
		return (OutputFunction) instance;
	}
	
	public static void main (String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		String[] equations = new String[2];
		
		equations[0] = "Math.pow(t,3) * Math.tan(t - 2)";
		equations[1] = "t/2 + Math.sin(t)";
		
		MathEvaluator.convertFirstOrderEquationToClass(equations);
	}
}