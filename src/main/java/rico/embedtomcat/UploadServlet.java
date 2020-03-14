package rico.embedtomcat;


import java.io.*;
import java.net.URI;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.print.DocFlavor.URL;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

import ApiGoogle.SheetSAVM; 

@WebServlet(
        name = "UploadServlet",
        urlPatterns = {"/helloUpload"}
    )
public class UploadServlet extends HttpServlet {
//
	//final String spreadsheetId = "1_wYqKTN57uwoshND3snSbuzzhOHqFQnYkMoDCAaotLw";
	final String spreadsheetId = "1R_dZQNYTqObHshByD447M3Qk2lG5Rr4lOhORre4qsic";
	
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 5 * 1024 * 1024;
	private int minFileSize = 1 * 1024 * 1024;
	private int maxMemSize = 1024 * 1024;
	long sizeInBytes = 0;
	private File file ;
	   
	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse resp)
	            throws ServletException, IOException {  
		 
		 throw new ServletException("GET method used with " +
		            getClass( ).getName( )+": POST method required.");
	 
	 }
	 
	 
	 public void init( ){
	      // Get the file location where it would be stored.
	      filePath = "c:\\tempo\\";
	      filePath =getServletContext().getInitParameter("file-upload"); 
	      System.out.println("UploadServlet.init : filePath="+filePath);
	  }
	   
     // Method to handle POST method request.
     public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {        
        
    	 // Check that we have a file upload request
         isMultipart = ServletFileUpload.isMultipartContent(request);
         response.setContentType("text/html");
         java.io.PrintWriter out = response.getWriter( );
      
         if( !isMultipart ) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>INSCRIPTION SALON SAVM 2020 - Merci</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<p>No file uploaded</p>"); 
            out.println("</body>");
            out.println("</html>");
            return;
         }
     
         DiskFileItemFactory factory = new DiskFileItemFactory();
      
         // maximum size that will be stored in memory
         factory.setSizeThreshold(maxMemSize);
      
         // Location to save data that is larger than maxMemSize.
         factory.setRepository(new File(UtilServlet.getTempoDir()));

         // Create a new file upload handler
         ServletFileUpload upload = new ServletFileUpload(factory);
      
         // maximum file size to be uploaded.
         upload.setSizeMax( maxFileSize );

         try {
        	 
        	//======== ID de POST =========
     		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");    	    
    	    String idPost=dateFormat.format(new java.util.Date());
        	 
        	 
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
   	
            // Process the uploaded file items
            Iterator i = fileItems.iterator();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>INSCRIPTION SALON SAVM 2020 - Merci</title>");  
            out.println("<link rel='stylesheet' href='styles1.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='img-container'> <!-- Block parent element -->");
            out.println("<img src=banniereSAVM.jpg width=600px align=hcenter>");
            
    	
            
            //================== Lecture des param en Llist ======================================================================                        	
            List<Object> llRow = new ArrayList<Object>();
            
            
            String DIMENSIONX="0";
            String DIMENSIONY="0";
            String paramNom="";
            String paramPrenom="";
            
            while ( i.hasNext () ) {
               FileItem fi = (FileItem)i.next();
               if ( !fi.isFormField () ) {
                  // Get the uploaded file parameters
                  String fieldName = fi.getFieldName();
                  String fileName = fi.getName();
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
                  llRow.add("PHOTO");
                  
                  URI uri = new URI(
                		    "http", 
                		    "davic.mkdh.fr", 
                		    "/savm-data/file/"+fileNameID,
                		    null);
                  java.net.URL _URL = uri.toURL();                                    
                  String url= _URL.toString();//"http://davic.mkdh.fr/savm-data/file/"+URLEncoder.encode(fileNameID, "UTF-8");
                  llRow.add(url);
               } else {
            	   String name = fi.getFieldName();
            	   llRow.add(name);            	   
                   String value = fi.getString(); 
                   
                   if (name.equals("NOM")) {                	   
                	   value=value.toUpperCase();
                	   paramNom=value;
                   }
                   if (name.equals("PRENOM")) {
                	   value=UtilServlet.upper1Lettre(value);
                	   paramPrenom=value;
                   }
                   if (name.equals("ADR_VILLE")) value=value.toUpperCase();
                   if (name.equals("EMAIL")) value=value.toLowerCase();
                   if (name.equals("SITE_INTERNET")) value=value.toLowerCase();
                   
                   if (name.equals("DIMENSIONX")) DIMENSIONX=value;
                   if (name.equals("DIMENSIONY")) DIMENSIONY=value;
                   
                                      
                   llRow.add(value);
                   //out.println("PAram : "+name +"="+ value+ "<br>");                                                         
               }
            }
            
            
            //Si taille < 1mo : Inscription refusé 
            if ( sizeInBytes<minFileSize) {            	
                 out.println("<br><br><p>");
                 out.println("<label class=''>"); 
                 out.println("Votre image n'a pas une définition suffisante (la taille du fichier image doit etre supèrieure à 1 Mo), <BR><B>votre  inscription n'a pas été pris en compte<B>"); 
                 out.println("<BR><BR><A href='#'  onClick='history.go(-1)' >Retour au formulaire</A>");
                 out.println("<label>");
                 out.println("<p>");
                 out.println("</body>");
                 out.println("</html>");
                 return;
            }
            
            
            out.println("<p>");
            out.println("<label class='label' >"+paramPrenom +" "+ paramNom+"</label><br>");
            out.println("<label class='label' >Votre inscription a été prise en compte</label><br>");
            out.println("<label class='label' >N° d'inscription : "+idPost+"</label><br>");            
            out.println("</p>");
            
            
            out.println("<p>");
            out.println("<label class='label' for='contact_nom'>Les artistes du Val De Marne </label><br><br>");
            out.println("<img src='http://davic.mkdh.fr/savm.png'>");
            out.println("</p>");
            
 
             
            out.println("</div>");//Pour centrer
            out.println("</body>");
            out.println("</html>");
            
            /* Champs par defaut*/
           
     	   llRow.add("STATUT");
           llRow.add("INIT");
            
           
     	   llRow.add("CHEQUE");
           llRow.add("NON");
           
           
     	   llRow.add("DATE");
           llRow.add(idPost);
           
           llRow.add("OEUVRE_DIM");
           llRow.add(DIMENSIONX+" x "+DIMENSIONY);
            
           
            
          //=========== transformation liste paramX=valueX,ParamY=ValueY,... en value1,value2,... trié dans l'odre de la sheet
            SheetSAVM _SheetSAVM = new SheetSAVM();  
    		_SheetSAVM.initOdreChamps("CIVIL,NOM,PRENOM,ADR_NUM,ADR_RUE,ADR_CODE_POSTAL,ADR_VILLE,TEL,EMAIL,OEUVRE_TYPE,OEUVRE_TITRE,OEUVRE_DETAIL,OEUVRE_DIM,OEUVRE_PRIX,SIRET_MDA,DISPO_GARDE,SITE_INTERNET,PHOTO,DATE,STATUT,CHEQUE,REMARQUES");			
    		_SheetSAVM.listeDepart =  new ArrayList<Object>(llRow);
    		_SheetSAVM.creteListeShhet();
    		
    		
    		
            //=========== Serialisation des valeurs de parametre (en cas de plantage d'inscritpion Goole)=========================================
    		//Sauvegarde du formulaire valisé

    		String nomFichierSerialise="listData_"+ idPost;
    		try {
    			nomFichierSerialise = UtilServlet.serialise(nomFichierSerialise,_SheetSAVM.ListeArrive);
    			UtilServlet.deSerialise(nomFichierSerialise);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("Serialise exception : " +e);
    			e.printStackTrace();
    		}
    		
    		 
    		//===================== LEcture puis ecriture de la SpreedSheet ======================================================================
    		try {
    			//===================== LEcture de la SpreedSheet			
    			UtilServlet.lectureGoogleSheet(spreadsheetId);
    			//=========== Ecriture : Append dans la SpreedSheet 
    			UtilServlet.appendGoogleSheet(spreadsheetId,_SheetSAVM.ListeArrive);
    			
        		System.out.println("API Goole Mise a jour OK, deplacement du fichier serialisé Backup");
        		File file = new File("data/new/"+nomFichierSerialise);
        		file.renameTo(new File("data/ok/"+nomFichierSerialise));
        		
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("API Goole Acces Lecture / Ecriture : exception : " +e);
    			e.printStackTrace();
    		}
            

    		
    		System.out.println("Servlet Fin");
            
            
            } catch(Exception ex) {
               System.out.println(ex);
            }
    	 
     }

}
