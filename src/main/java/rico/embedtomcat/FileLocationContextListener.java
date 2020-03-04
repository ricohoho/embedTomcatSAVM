package rico.embedtomcat;


import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FileLocationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	/*
    	String rootPath = System.getProperty("catalina.home");
    	ServletContext ctx = servletContextEvent.getServletContext();
    	String relativePath = ctx.getInitParameter("tempfile.dir");
    	File file = new File(rootPath + File.separator + relativePath);
    	if(!file.exists()) file.mkdirs();
    	System.out.println("File Directory created to be used for storing files");
    	ctx.setAttribute("FILES_DIR_FILE", file);
    	ctx.setAttribute("FILES_DIR", rootPath + File.separator + relativePath);
    	*/
    	ServletContext ctx = servletContextEvent.getServletContext();
    	//ctx.setInitParameter("file-upload", "c:\\tempo\\");
    	
    	
    	String OS = System.getProperty("os.name").toLowerCase();
    	if (OS.contains("win")) {
    		ctx.setInitParameter("file-upload", "data\\file\\");
        	UtilServlet.createDir("data\\file\\");	
    	} else {
    		ctx.setInitParameter("file-upload", "data/file/");
        	UtilServlet.createDir("data/file/");
    	}
    	
    }

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		//do cleanup if needed
	}
	
}
