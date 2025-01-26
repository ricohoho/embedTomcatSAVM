package rico.embedtomcat;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ApiGoogle.SheetSAVM;
 

@WebServlet(
        name = "UploadServletRemplace2",
        urlPatterns = {"/helloUploadRemplace2"}
    ) 

public class UploadServletRemplace2 extends HttpServlet {
	
	final String spreadsheetId = "1R_dZQNYTqObHshByD447M3Qk2lG5Rr4lOhORre4qsic";
	
	protected boolean isMultipart;
	protected String filePath;
	protected int maxFileSize = 15 * 1024 * 1024;
	protected int maxFileSizeAVM = 5 * 1024 * 1024;
	protected int minFileSize = 1 * 1024 * 1024;
	protected int maxMemSize = 1024 * 1024;
	long sizeInBytes = 0; 
	protected File file ;
	   
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse resp)
	            throws ServletException, IOException {  
		 
		 throw new ServletException("GET method used with " +
		            getClass( ).getName( )+": POST method required.");
	 
	 }
	 
	 
	 public void init( ){
	      // Get the file location where it would be stored.
	      filePath = "c:\\tempo\\";
	      filePath =getServletContext().getInitParameter("file-upload"); 
	      System.out.println("UploadServlet.init :-) : filePath="+filePath);
	  }
	 
	 
	   @SuppressWarnings("unchecked")  //this is needed to suppress the error:
	    //Type safety: The expression of type List needs unchecked conversion to conform to List<FileItem>
	    //at line fileItems = upload.parseRequest(request);
	    //see http://mannionjava.blogspot.com/2006/09/expression-of-type-x-needs-unchecked.html
	    private List<FileItem> parseRequest(final HttpServletRequest request, final HttpServletResponse response) {
	        HttpSession session = request.getSession(true);
	        List<FileItem> fileItems = null;
	        try {
	        	DiskFileItemFactory factory = new DiskFileItemFactory();
	            
	            // maximum size that will be stored in memory                           
	            System.out.println("UploadServlet doPost maxMemSize="+maxMemSize);
	            factory.setSizeThreshold(maxMemSize);
	         
	            // Location to save data that is larger than maxMemSize.
	            factory.setRepository(new File(UtilServlet.getTempoDir()));

	            // Create a new file upload handler
	            ServletFileUpload upload = new ServletFileUpload(factory);
	         
	            // maximum file size to be uploaded.
	            System.out.println("UploadServlet doPost maxFileSize="+maxFileSize);
	            upload.setSizeMax( maxFileSize );
	            
	            // Parse the request to get file items.
	    	    System.out.println("UploadServlet doPost avant parseRequest");
	    	    fileItems = upload.parseRequest(request);
	            
	            return fileItems;
	        } catch (FileUploadBase.SizeLimitExceededException se) {
	        	System.out.println("Excepiotn 1");
	            String errMsg = "File exceeds size limit.";
	            se.printStackTrace();           
	            session.setAttribute("errorMessage", errMsg);
	            return null; 
	        } catch (FileUploadException fe) {
	        	System.out.println("Excepiotn 2");
	            String errMsg = "A file upload exception has occurred while parsing a vendor inquiry.";
	            fe.printStackTrace();           
	            session.setAttribute("errorMessage", errMsg);
	            return fileItems;
	        } catch (Exception ex){
	        	System.out.println("Excepiotn 3");
	        	String errMsg = "General Exception ";
	            ex.printStackTrace();           
	            session.setAttribute("errorMessage", errMsg);
	            return fileItems;
	        	
	        }
	 
	    }
	
	

    public void doPost(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {        
       
   	 System.out.println("UploadServletRemplace doPost debut");
   	 
  // Check that we have a file upload request
     isMultipart = ServletFileUpload.isMultipartContent(request);
     response.setContentType("text/html");
     java.io.PrintWriter out = response.getWriter( );
  
     if( !isMultipart ) {
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Modification : INSCRIPTION SALON SAVM 2020 - Merci</title>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<p>No file uploaded</p>"); 
        out.println("</body>");
        out.println("</html>");
        return;
     }
 
     DiskFileItemFactory factory = new DiskFileItemFactory();      
     // maximum size that will be stored in memory                           
     System.out.println("UploadServlet doPost maxMemSize="+maxMemSize);
     factory.setSizeThreshold(maxMemSize);      
     // Location to save data that is larger than maxMemSize.
     factory.setRepository(new File(UtilServlet.getTempoDir()));
     // Create a new file upload handler
     ServletFileUpload upload = new ServletFileUpload(factory);      
     // maximum file size to be uploaded.
     System.out.println("UploadServlet doPost maxFileSize="+maxFileSize);
     upload.setSizeMax( maxFileSize );
  
     try {
    	 
    	//======== ID de POST =========
 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");    	    
	    String idPost=dateFormat.format(new java.util.Date());
    	 
    	 
	    List fileItems = null;
        // Parse the request to get file items.
	    System.out.println("UploadServlet doPost avant parseRequest");
	    fileItems = upload.parseRequest(request);    	    
	    //fileItems = parseRequest(request, response);
	    
	 
	
        // Process the uploaded file items
        System.out.println("UploadServlet doPost avant Iteratotor");
        Iterator i = fileItems.iterator();

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Modification : INSCRIPTION SALON SAVM 2020 - Merci</title>");  
        out.println("<link rel='stylesheet' href='styles1.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='img-container'> <!-- Block parent element -->");
        out.println("<img src=banniereSAVM.jpg width=600px align=hcenter>");
        
	
        
        //================== Lecture des param en Llist ======================================================================                        	
        //List<Object> llRow = new ArrayList<Object>();
        
        String sUrlFichierImage="";
        String paramOldIdPost="";
        System.out.println("UploadServlet doPost avant parcour des ficheirs");
        while ( i.hasNext () ) {
           FileItem fi = (FileItem)i.next();
           if ( !fi.isFormField () ) {
              // Get the uploaded file parameters
              String fieldName = fi.getFieldName();
              String fileName = fi.getName();
              System.out.println("UploadServlet fileName="+fileName);
              String contentType = fi.getContentType();
              boolean isInMemory = fi.isInMemory();
              sizeInBytes = fi.getSize();
              String fileNameID="";
           
              System.out.println("isInMemory/sizeInBytes="+isInMemory+"/"+sizeInBytes);
              sizeInBytes = sizeInBytes;
              
              //Ecriture du fichier photo si > 1 Mo
              if ( sizeInBytes>=minFileSize) {       
            	  // Write the file
            	  if( fileName.lastIndexOf("\\") >= 0 ) {
            		  fileNameID=  idPost+"_" + fileName.substring( fileName.lastIndexOf("\\"));                     
            	  } else {
            		  fileNameID= idPost+"_" + fileName.substring(fileName.lastIndexOf("\\")+1);
            	  }
            	  file = new File(filePath+ fileNameID) ;
            	  fi.write( file ) ;
              }
              //out.println("Uploaded Filename: " + fileNameID + "<br>");
              //llRow.add("PHOTO");
              
              URI uri = new URI(
            		    "http", 
            		    "davic.mkdh.fr", 
            		    "/savm-data/file/"+fileNameID,
            		    null);
              java.net.URL _URL = uri.toURL();                                    
              String url= _URL.toString();//"http://davic.mkdh.fr/savm-data/file/"+URLEncoder.encode(fileNameID, "UTF-8");
              sUrlFichierImage = url;
           } else {
        	   String name = fi.getFieldName(); 	   
               String value = fi.getString(); 
               
               if (name.equals("oldIdPost")) {                	   
            	   value=value.toUpperCase();
            	   paramOldIdPost=value;
               }                             
                                  
               //out.println("PAram : "+name +"="+ value+ "<br>");                                                         
           }
        }
        
        
        //Si taille < 1mo : Inscription refus� 
        if ( sizeInBytes<minFileSize  ) {            	
             out.println("<br><br><p>");
             out.println("<label class=''>"); 
             out.println("Votre image n'a pas une d�finition suffisante (la taille du fichier image doit etre sup�rieure � 1 Mo), "
            		 +" (Taille actuelle : " +humanReadableByteCountSI(sizeInBytes)+")"     
             		+ "<BR><B>votre  inscription n'a pas �t� pris en compte<B>"); 
             out.println("<BR><BR><A href='#'  onClick='history.go(-1)' >Retour au formulaire</A>");
             out.println("<label>");
             out.println("<p>");
             out.println("</body>");
             out.println("</html>");
             return;
        }
        
        
        //Si taille < 1mo : Inscription refus� 
        if ( sizeInBytes>maxFileSizeAVM  ) {            	
             out.println("<br><br><p>");
             out.println("<label class=''>"); 
             out.println("la taille du fichier image doit etre inf�rieure � 5 Mo !! "
            		 +" (Taille actuelle : " +humanReadableByteCountSI(sizeInBytes)+")"               		 
             		+ "<BR><B>votre  inscription n'a pas �t� pris en compte<B>"); 
             out.println("<BR><BR><A href='#'  onClick='history.go(-1)' >Retour au formulaire</A>");
             out.println("<label>");
             out.println("<p>");
             out.println("</body>");
             out.println("</html>");
             return;
        }
        
        

        
                            
		
		 
		//===================== Lecture puis ecriture de la SpreedSheet ======================================================================
		try {
			//===================== LEcture de la SpreedSheet			
			UtilServlet.lectureGoogleSheet(spreadsheetId);
			//=========== Ecriture : On recherche la ligne avec l'ancien ID d'insscriptio, si trouver on remplace l'url du fichier par le nouveau 
			String numLigneOuRange="LIGNE";
	        String sheetName="Sheet1";
	        String numLignFind = UtilServlet.rechercheValeurDansUneColonne( spreadsheetId,paramOldIdPost,sheetName,"S",numLigneOuRange);
	        System.out.println("numLignFind="+numLignFind);
	        int numLignFindInt = Integer.parseInt(numLignFind);
	        System.out.println("numLignFind="+numLignFind);        
	        if (numLignFindInt >=0) {
	        	//http://davic.mkdh.fr/savm-data/file/20200420_035010_20200409_185533.jpg
	        	UtilServlet.updateCell(spreadsheetId,sheetName+"!R"+numLignFind+":R",sUrlFichierImage);
	        } else {
	        	//identifiant innexistant
	        	  out.println("<br><br><p>");
	              out.println("<label class=''>"); 
	              out.println("Identifiant non trouv� : <B>"+paramOldIdPost+"<B>"); 
	              out.println("<BR><BR><A href='#'  onClick='history.go(-1)' >Retour au formulaire</A>");
	              out.println("<label>");
	              out.println("<p>");
	              out.println("</body>");
	              out.println("</html>");
	              return;
	        }
			
    		/*
	        System.out.println("API Goole Mise a jour OK, deplacement du fichier serialis� Backup");
    		File file = new File("data/new/"+nomFichierSerialise);
    		file.renameTo(new File("data/ok/"+nomFichierSerialise));
    		*/
    		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("API Goole Acces Lecture / Ecriture : exception : " +e);
			e.printStackTrace();
		}        		

		
        out.println("<p>");
        out.println("<label class='label' >Nouvel image : "+sUrlFichierImage+"</label><br>");
        out.println("<label class='label' >Votre moifcation inscription a �t� prise en compte</label><br>");
        out.println("<label class='label' >N� d'inscription : "+paramOldIdPost+"</label><br>");            
        out.println("</p>");
        
        
        out.println("<p>");
        out.println("<label class='label' for='contact_nom'>Les artistes du Val De Marne </label><br><br>");
        out.println("<img src='http://davic.mkdh.fr/savm.png'>");
        out.println("</p>");
        

         
        out.println("</div>");//Pour centrer
        out.println("</body>");
        out.println("</html>");
        
    } catch(Exception ex) {
    	System.out.println("Excption generale:"+ex);
        // 	TODO Auto-generated catch block
		System.out.println("fileupload exception : " +ex);
		out.println("<html>");
		out.println("<head>");
		out.println("<title>INSCRIPTION SALON SAVM 2020 - Merci</title>");  
		out.println("<link rel='stylesheet' href='styles1.css'>");
		out.println("</head>");
		out.println("<body>");
		out.println("<div class='img-container'> <!-- Block parent element -->");
		out.println("<img src=banniereSAVM.jpg width=600px align=hcenter>");
		out.println("<br><br><p>");
		out.println("<label class=''>"); 
		out.println("la taille du fichier image doit etre inf�rieure � 5 Mo ! <BR><B>votre  inscription n'a pas �t� pris en compte<B>"); 
		out.println("<BR><BR><A href='#'  onClick='history.go(-1)' >Retour au formulaire</A>");
		out.println("<label>");
		out.println("<p>");
		out.println("</body>");
		out.println("</html>");
        //return;
    }
   	 
   	System.out.println("UploadServletRemplace doPost Fin ");
    }

    /**
     * Converison pour affichage client taille en Byte en taille en Ko / Mo
     * @param bytes
     * @return
     */
    public static String humanReadableByteCountSI(long bytes) {
   	 
   	    if (-1000 < bytes && bytes < 1000) {
   	        return bytes + " B";
   	    }
   	    
   	    CharacterIterator ci = new StringCharacterIterator("kMGTPE");
   	    while (bytes <= -999950 || bytes >= 999950) {
   	        bytes /= 1000;
   	        ci.next();
   	    }
   	    return String.format("%.1f %cB", bytes / 1000.0, ci.current());
   	    
   	}
    

}
