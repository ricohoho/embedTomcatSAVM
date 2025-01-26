package rico.embedtomcat;

/*
 * ====================== PAS UTILISE ============================
 */

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem; 
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import ApiGoogle.SheetSAVM;
 

@WebServlet(
        name = "UploadServletRemplace",
        urlPatterns = {"/helloUploadRemplace"}
    ) 

public class UploadServletRemplace extends UploadServlet {
	
    // Method to handle POST method request.
	@Override
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
              System.out.println("UploadServletRemplace fileName="+fileName);
              
              //EF 20240225 rempalcement des caract�re accentu�s
              fileName = NomaliseStringAccent.enleverAccents(fileName);                                   
              System.out.println("UploadServlet fileName Sans accens  ="+fileName);
              byte[] bytes = fileName.getBytes("ISO-8859-1");
              fileName = new String(bytes, "UTF-8");
              System.out.println("UploadServlet fileName Apres UrdDECODER  ="+fileName);
              
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
	        //String sheetName="Sheet1";
			String sheetName="Inscription";
	        
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
	

}
