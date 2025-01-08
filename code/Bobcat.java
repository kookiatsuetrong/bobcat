/**
 * Bobcat is an embedded version of Tomcat.
 * 
 * Example of usage:
 * 
 * java Bobcat 
 *     --home web 
 *     --port 7300
 *     --deployment-descriptor web.xml
 *     --verbose
 *
 */
import java.io.File;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;
import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;

public class Bobcat {
	public static void main(String[] data) {
		int port = 12345;
		String home = "web";
		String working = "working";
		boolean verbose = false;
		String descriptor = null; 
		
		for (int i = 0; i < data.length; i++) {
			if ("--port".equals(data[i])) {
				i++;
				port = Integer.parseInt(data[i]);
			}
			if ("--home".equals(data[i])) {
				i++;
				home = data[i];
			}
			if ("--verbose".equals(data[i])) {
				verbose = true;
			}
			if ("--deployment-descriptor".equals(data[i])) {
				i++;
				descriptor = data[i];
			}
		}
		if (descriptor == null) { 
			descriptor = home + "/WEB-INF/web.xml"; 
		}

		// LogManager.getLogManager().reset();
		/*
		Logger logger = Logger.getGlobal();
		System.out.println(logger);
		logger.setLevel(Level.OFF);
		*/
		

		try {
			var tomcat = new Tomcat();
			tomcat.setPort(port);
			tomcat.setBaseDir(working);
			tomcat.setSilent(!verbose);
			
			File file = new File(home);
			var context = tomcat.addWebapp("", file.getAbsolutePath());
			context.setAltDDName(descriptor);
			
			var connector = tomcat.getConnector(); // Mandatory
			tomcat.start();
			tomcat.getServer().await();
			
		} catch (Exception e) {
			System.out.println("ERROR " + e);
		}
		
		/*
		try {
			tomcat.stop();
			tomcat.destroy();
			// remove the temporary working directory
		} catch (Exception e) { }
		*/
	}
}

// --port 12345
// --home web
// --war sample.war

