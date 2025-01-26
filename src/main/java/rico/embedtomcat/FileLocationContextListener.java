package rico.embedtomcat;



import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FileLocationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	
    	ServletContext ctx = servletContextEvent.getServletContext();    	
    	
    	String OSRegular = System.getProperty("os.name").toLowerCase();
    	if (OSRegular.contains("win")) {
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
