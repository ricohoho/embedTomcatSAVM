package rico.embedtomcat;

import java.io.File;

import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;

public class Main {

	 
	/* exemple : 
	 * https://devcenter.heroku.com/articles/create-a-java-web-application-using-embedded-tomcat
	 */
    public static void main(String[] args) throws Exception {


    	String webappDirLocation = "src/main/webapp/";
        Tomcat tomcat = new Tomcat();

        //The port that we should run on can be set into an environment variable
        //Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
 
        System.out.println("--> webPort="+webPort);
        tomcat.setPort(Integer.valueOf(webPort));

        StandardContext ctx = (StandardContext) tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());
        
        

        // Ajouter le contexte de l'application web avec un chemin contextuel vide
        //Context context = tomcat.addWebapp("", new File(webappDirLocation).getAbsolutePath());

        //ctx.getServletContext().getServletRegistrations().get("webdavservlet").setInitParameter("file-upload", "c:\\tempo\\");
        //ctx.getServletContext().setInitParameter("file-upload", "c:\\tempo\\");
        //filePath = getServletContext().getInitParameter("file-upload"); 

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work                
        File additionWebInfClasses = new File("target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
                additionWebInfClasses.getAbsolutePath(), "/"));
        ctx.setResources(resources);
        
        System.out.println("-->tomcat.start");
        tomcat.start();
        System.out.println("-->getServer().await");
        tomcat.getServer().await();
        System.out.println("-->Fin initalisation");
    }
}