package rico.embedtomcat;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
//import java.security.GeneralSecurityException;
import java.util.Enumeration;
//import java.util.List;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;

import ApiGoogle.SheetSAVM;
import ApiGoogle.SpreadsheetSnippets; 

//import ApiGoogle.SpreadsheetSnippets;
//import com.google.api.client.json.JsonFactory;

//import ApiGoogle.SpreadsheetSnippets;

@WebServlet(
        name = "MyServlet",
        urlPatterns = {"/hello"}
    )
    
public class HelloServlet extends HttpServlet {
	/*
	private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.DRIVE);// SPREADSHEETS_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "credentials.json";
	*/
	final String spreadsheetId = "1_wYqKTN57uwoshND3snSbuzzhOHqFQnYkMoDCAaotLw";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp)
            throws ServletException, IOException {  
    	
    	
 		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");    	    
	    String idPost=dateFormat.format(new java.util.Date());
    	
    	 // Set response content type
        resp.setContentType("text/html");
    	
        ServletOutputStream out = resp.getOutputStream();
        //PrintWriter out = response.getWriter();
        String title = "Reading All Form Parameters";
        String docType =
           "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
           "<html>\n" +
           "<head><title>" + title + "</title></head>\n" +
           "<body bgcolor = \"#f0f0f0\">\n" +
           "<h1 align = \"center\">" + title + "</h1>\n" +
           "<table width = \"100%\" border = \"1\" align = \"center\">\n" +
           "<tr bgcolor = \"#949494\">\n" +
              "<th>Param Name</th>"+
              "<th>Param Value(s)</th>\n"+
           "</tr>\n"
        );

        Enumeration paramNames = request.getParameterNames();

        while(paramNames.hasMoreElements()) {
           String paramName = (String)paramNames.nextElement();
           out.print("<tr><td>" + paramName + "</td>\n<td>");
           String[] paramValues = request.getParameterValues(paramName);

           // Read single valued data
           if (paramValues.length == 1) {
              String paramValue = paramValues[0];
              if (paramValue.length() == 0)
                 out.println("<i>No Value</i>");
                 else
                 out.println(paramValue);
           } else {
              // Read multiple valued data
              out.println("<ul>");

              for(int i = 0; i < paramValues.length; i++) {
                 out.println("<li>" + paramValues[i]);
              }
              out.println("</ul>");
           }
        }
        out.println("</tr>\n</table>\n");                             
        
        

        
        
        
        
        //================== Lecture des param en Llist ======================================================================                        	
        List<Object> llRow = new ArrayList<Object>();
        
        
        int nbParam = 0;
        paramNames = request.getParameterNames();
    	//Envoi des param dans la googleSheet  => 1 colonne = paramName + 1 colonne = PAramValue
        while(paramNames.hasMoreElements()) {
        	nbParam ++;
            String paramName = (String)paramNames.nextElement();
            llRow.add(paramName);
            System.out.print("paramName=" + paramName+"/" );
            String[] paramValues = request.getParameterValues(paramName);

            // Read single valued data
            if (paramValues.length == 1) {
               String paramValue = paramValues[0];
               if (paramValue.length() == 0) {
            	   System.out.println("No Value");
                  llRow.add("No Value");
               } else {
            	   System.out.println(paramValue);
                  llRow.add(paramValue);
               }
            } else {
               // Read multiple valued data
            	System.out.println("Read multiple valued data");

               for(int i = 0; i < paramValues.length; i++) {
                  System.out.println("<li>" + paramValues[i]);
               }
               //On prend que la première valuer
               llRow.add(paramValues[0]);
            }
         }

        
        
        //=========== transformation liste paramX=valueX,ParamY=ValueY,... en value1,value2,... trié dans l'odre de la sheet
        SheetSAVM _SheetSAVM = new SheetSAVM();  
		_SheetSAVM.initOdreChamps("NOM,PRENOM,ADR_NUM,ADR_RUE,ADR_CODE_POSTAL,ADR_VILLE,TEL,EMAIL,OEUVRE_TYPE,OEUVRE_TITRE,OEUVRE_DETAIL,OEUVRE_DIM,OEUVRE_PRIX,SIRET_MDA,DISPO_GARDE,SITE_INTERNET,PHOTO");			
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("API Goole Acces Lecture / Ecriture : exception : " +e);
			e.printStackTrace();
		}
        
		System.out.println("API Goole Mise a jour OK, deplacement du fichier serialisé Backup");
		File file = new File("data/new/"+nomFichierSerialise);
		file.renameTo(new File("data/ok/"+nomFichierSerialise));
		
		System.out.println("Servlet Fin");
		
		
        out.println("</body></html>");
     }
    
    
    
    
     
     // Method to handle POST method request.
     public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {        
        doGet(request, response);
     }
     
     
    
     
     
     
     
     
}