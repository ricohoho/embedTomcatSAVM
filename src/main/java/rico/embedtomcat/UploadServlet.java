package rico.embedtomcat;


import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.*;

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
        name = "UploadServlet",
        urlPatterns = {"/helloUpload"}
    )
public class UploadServlet extends HttpServlet {

	static final String spreadsheetId = "1R_dZQNYTqObHshByD447M3Qk2lG5Rr4lOhORre4qsic";
	
	protected static boolean isMultipart;
	protected String filePath;
	protected int maxFileSize = 50 * 1024 * 1024;
	protected int maxFileSizeAVM = 5 * 1024 * 1024;
	protected int minFileSize = 1 * 1024 * 1024;
	protected int maxMemSize = 1024 * 1024;
	long sizeInBytes = 0; 
	protected File file ;
	   
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
	   
     // Method to handle POST method request.
     public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {        
        
    	 System.out.println("UploadServlet doPost debut");
    	 
    	 // Check that we have a file upload request
         isMultipart = ServletFileUpload.isMultipartContent(request);
         //response.setContentType("text/html");
         response.setContentType("text/html;charset=UTF-8");
         java.io.PrintWriter out = response.getWriter( );
      
         if( !isMultipart ) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>INSCRIPTION SALON SAVM 2024 - Merci</title>");  
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
    	    
    	    /*
    	    if (fileItems==null) {
    	    	System.out.println("UploadServlet doPost fileItems==null");
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
    			out.println("la taille du fichier image doit etre inf�rieure � 5 Mo !! <BR><B>votre  inscription n'a pas �t� pris en compte<B>"); 
    			out.println("<BR><BR><A href='#'  onClick='history.go(-1)' >Retour au formulaire</A>");
    			out.println("<label>");
    			out.println("<p>");
    			out.println("</body>");
    			out.println("</html>");
    			//response.sendRedirect(request.getContextPath() + "/" + "index.jsp");
    			return;
    	    }
    	    */

   	
            // Process the uploaded file items
            System.out.println("UploadServlet doPost avant Iteratotor");
            Iterator i = fileItems.iterator();

            out.println("<html>");
            out.println("<head>");
            out.println("<title>INSCRIPTION SALON SAVM 2024 - Merci</title>");  
            out.println("<link rel='stylesheet' href='styles1.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='img-container'> <!-- Block parent element -->");
            out.println("<img src=banniereSAVM.jpg width=600px align=hcenter>");
            
    	
            
            //================== Lecture des param en Llist ======================================================================                        	
            List<Object> llRow = new ArrayList<Object>();
            
            
            String DIMENSIONX="0";
            String DIMENSIONY="0";
            String DIMENSIONZ="0";
			String DIMENSIONX_2="0";
            String DIMENSIONY_2="0";
            String DIMENSIONZ_2="0";
            String paramNom="";
            String paramPrenom="";
            System.out.println("UploadServlet doPost avant parcour des ficheirs");
            while ( i.hasNext () ) {
               
               FileItem fi = (FileItem)i.next();
               if ( !fi.isFormField () & fi.getName()!="" ) {
            	   System.out.println("UploadServlet doPost !fi.isFormField");
                  // Get the uploaded file parameters
                  String fieldName = fi.getFieldName();
				  System.out.println("UploadServlet fieldName=["+fieldName+"]");
                  String fileName = fi.getName();
                  System.out.println("UploadServlet fileName Original =["+fileName+"]");
                  
                  //EF 20240225 rempalcement des caract�re accentu�s
                  //fileName = NomaliseStringAccent.enleverAccents(fileName);                                   
                  //System.out.println("UploadServlet fileName Sans accens  ="+fileName);
                  /*
                  String[] encodingsToTry = {"UTF-8", "ISO-8859-1", "ISO-8859-2","Windows-1252"};
                  for (String encoding : encodingsToTry) {
                      try {
                          fileName = new String(fileName.getBytes(encoding), "UTF-8");
                          System.out.println(encoding+" UrdDECODER  ="+fileName);
                      } catch (UnsupportedEncodingException e) {
                    	  System.out.println(encoding+" exceltion   ="+e);
                      }
                  }                  
                  */
                  
                  Character car = fileName.charAt(1); 
                  System.out.println("car au 1er :  "+car);
                  int code = (int) car;
              	  System.out.println("code car au 1 er carcatere "+code);
              	  
              	  car = fileName.charAt(3);
              	  System.out.println("car au 1er :  "+car);
              	  code = (int) car;
            	  System.out.println("code car au 1 er carcatere "+code);
                  
            	  /*
                  byte[] bytes = fileName.getBytes(StandardCharsets.ISO_8859_1);
                  //String encoding =NomaliseStringAccent.detectEncoding(bytes);
                  //System.out.println("Encodage d�tect� : " + encoding);
                  fileName = new String(bytes, "UTF-8");
                  //System.out.println("defaultCharset:"+Charset.defaultCharset());
                  //fileName = new String(bytes, StandardCharsets.ISO_8859_1);//StandardCharsets.ISO_8859_1
                  
                  car = fileName.charAt(1);
                  System.out.println("car au 1er :  "+car);
                  code = (int) car;
              	  System.out.println("code car au 1 er carcatere "+code);
                  
                  System.out.println("UploadServlet fileName Apres UrdDECODER  ="+fileName);
                  fileName = fileName.replace("?", "e");
                  System.out.println("UploadServlet apres suppression des ?  ="+fileName);
                  */
                  
                  
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
				  if (fieldName.equals("file_2")) {
					llRow.add("PHOTO_2");
				  } else { 
					llRow.add("PHOTO");
				  }

                  
                  
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
            	   System.out.println("UploadServlet doPost else name : "+name );
            	   llRow.add(name);            	   
                   String value = fi.getString();
                   System.out.println("UploadServlet doPost else value : "+value );
                   
                   //EF 20240225
                   //En ajouter UTF-8 dans la balise <FORM, le nom du fichier est correcte apr contre les champs saisie doivent etre convertie 
                   value = new String(value.getBytes(StandardCharsets.ISO_8859_1), "UTF-8");
                   
                   if (name.equals("NOM")) {                	   
                	   value=value.toUpperCase();
                	   paramNom=value;
                   }
                   if (name.equals("PRENOM")) {
                	   if ((value==null) || "".equals(value))   {
                		   value="";
                	   } else { 
                		   value=UtilServlet.upper1Lettre(value);
                	   }
                	   paramPrenom=value;
                   }
                   /*
                   if (name.equals("ADR_VILLE")) {
                	   value = new String(value.getBytes(StandardCharsets.ISO_8859_1), "UTF-8");
                	   value=value.toUpperCase();
                   }
                   */
                   if (name.equals("EMAIL")) value=value.toLowerCase();
                   if (name.equals("SITE_INTERNET")) value=value.toLowerCase();
                   
                   if (name.equals("DIMENSIONX")) DIMENSIONX=value;
                   if (name.equals("DIMENSIONY")) DIMENSIONY=value;
                   if (name.equals("DIMENSIONZ")) DIMENSIONZ=value;

				   if (name.equals("DIMENSIONX_2")) DIMENSIONX_2=value;
                   if (name.equals("DIMENSIONY_2")) DIMENSIONY_2=value;
                   if (name.equals("DIMENSIONZ_2")) DIMENSIONZ_2=value;

                                      
                   llRow.add(value);

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
            
            
            //Si taille > 5mo : Inscription refus� 
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
            
            
            out.println("<p>");
            out.println("<label class='label' >"+paramPrenom +" "+ paramNom+"</label><br>");
            out.println("<label class='label' >Votre inscription a �t� prise en compte</label><br>");
            out.println("<label class='label' >N� d'inscription : "+idPost+"</label><br>");            
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
           if (DIMENSIONZ.equals("0"))
        	   llRow.add(DIMENSIONX+" x "+DIMENSIONY);
           else 
        	   llRow.add(DIMENSIONX+" x "+DIMENSIONY+" x "+DIMENSIONZ);
        	   
			if 	(! DIMENSIONX_2.equals("")) {
		   		llRow.add("OEUVRE_DIM_2");
					if (DIMENSIONZ.equals("0"))
						llRow.add(DIMENSIONX_2+" x "+DIMENSIONY_2);
					else 
						llRow.add(DIMENSIONX_2+" x "+DIMENSIONY_2+" x "+DIMENSIONZ_2);
			}
            
          //=========== transformation liste paramX=valueX,ParamY=ValueY,... en value1,value2,... tri� dans l'odre de la sheet
            System.out.println("UploadServlet doPost append ligne dans Google Sheet");
            SheetSAVM _SheetSAVM = new SheetSAVM();
            String OrdreColonne;
            //Version 2024
            OrdreColonne = "CIVIL,NOM,PRENOM,ADR_NUM,ADR_RUE,ADR_CODE_POSTAL,ADR_VILLE,TEL,EMAIL,OEUVRE_TYPE,OEUVRE_TITRE,OEUVRE_DETAIL,OEUVRE_DIM,OEUVRE_PRIX,SIRET_MDA,DISPO_GARDE,SITE_INTERNET,PHOTO,DATE,STATUT,CHEQUE,REMARQUES,OEUVRE_DETAIL_EXPO,ADAGP";
            //version 2025
            OrdreColonne = "CIVIL,NOM,PRENOM,ADR_NUM,ADR_RUE,ADR_CODE_POSTAL,ADR_VILLE,TEL,EMAIL,OEUVRE_TYPE,OEUVRE_TITRE,OEUVRE_DETAIL,OEUVRE_DIM,OEUVRE_PRIX,PHOTO,OEUVRE_TYPE_2,OEUVRE_TITRE_2,OEUVRE_DETAIL_2,OEUVRE_DIM_2,OEUVRE_PRIX_2,PHOTO_2,SIRET_MDA,DISPO_GARDE,SITE_INTERNET,DATE,STATUT,CHEQUE,REMARQUES,OEUVRE_DETAIL_EXPO,ADAGP";
            _SheetSAVM.initOdreChamps(OrdreColonne);			
    		_SheetSAVM.listeDepart =  new ArrayList<Object>(llRow);
    		_SheetSAVM.creteListeShhet();
    		
    		
    		
            //=========== Serialisation des valeurs de parametre (en cas de plantage d'inscritpion Goole)=========================================
    		//Sauvegarde du formulaire valis�

    		String nomFichierSerialise="listData_"+ idPost;
    		try {
    			System.out.println("UploadServlet doPost fichier Backup");
    			nomFichierSerialise = UtilServlet.serialise(nomFichierSerialise,_SheetSAVM.ListeArrive);
    			UtilServlet.deSerialise(nomFichierSerialise);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("Serialise exception : " +e);
    			e.printStackTrace();
    		}
    		
    		 
    		//===================== LEcture puis ecriture de la SpreedSheet ======================================================================
    		try {
    			System.out.println("UploadServlet doPost lectrue ligne dans Google Sheet");
    			//===================== LEcture de la SpreedSheet			
    			UtilServlet.lectureGoogleSheet(spreadsheetId);
    			//=========== Ecriture : Append dans la SpreedSheet 
    			System.out.println("UploadServlet doPost append ligne dans Google Sheet");
    			UtilServlet.appendGoogleSheet(spreadsheetId,_SheetSAVM.ListeArrive);
    			
        		System.out.println("API Goole Mise a jour OK, deplacement du fichier serialis� Backup");
        		File file = new File("data/new/"+nomFichierSerialise);
        		file.renameTo(new File("data/ok/"+nomFichierSerialise));
        		
        		
        		
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("API Goole Acces Lecture / Ecriture : exception : " +e);
    			e.printStackTrace();
    		}
            
 
    		
    		System.out.println("Servlet Fin");
            
            
        } catch(Exception ex) {
        	System.out.println("Excption generale:"+ex);
            // 	TODO Auto-generated catch block
			System.out.println("fileupload exception : " +ex);
			out.println("<html>");
			out.println("<head>");
			out.println("<title>INSCRIPTION SALON SAVM 2024 - Merci</title>");  
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
