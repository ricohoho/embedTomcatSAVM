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
import java.util.List;

import ApiGoogle.SpreadsheetSnippets;

public class UtilServlet {
	
	 /** 
     *  Lecture de la sheet
     * @throws Exception
     */ 
    public static  void lectureGoogleSheet(String spreadsheetId) throws Exception {
        //============> API GOOGLE ===============        
   	 System.out.println("sErvlet : Debut Lecture");
        SpreadsheetSnippets spreadsheetSnippets=  new SpreadsheetSnippets();
        try {
			spreadsheetSnippets.init();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}              
       
        //READ Sample
        //String range = "Sheet1!A1:E";
        String range = "Inscription!A1:E";
        List<List<Object>> values = spreadsheetSnippets.getValues(spreadsheetId, range);

        //============ Display value  =================
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            System.out.println("Name, Major");
            int ligne=1;
            for (List<Object> row : values) {
                // Print columns A and E, which correspond to indices 0 and 4.
            	if (!row.isEmpty() &&  row!= null && row.get(0) != null) {
            		String Cel0 = (String) row.get(0);
            		String Cel4 = "";
            		if (row.size()>=3)  Cel4= (String) row.get(0);
            		System.out.printf("Ligne ("+ligne+")===> %s, %s\n",Cel0, Cel4);
            	} else {
            		System.out.printf("Ligne ("+ligne+")===> vide");
            	}
                ligne++;
            }
        }   
        System.out.println("sErvlet : Fin  Lecture");
    }
    
    /**
     * Ecriture : Append in Google Sheet
     */
    public static void appendGoogleSheet(String spreadsheetId,ArrayList<Object> llRow ) throws Exception {
   	 //============> API GOOGLE ===============     
   	 System.out.println("sErvlet : Debut Ecriture");
        SpreadsheetSnippets spreadsheetSnippets=  new SpreadsheetSnippets();
        try {
			spreadsheetSnippets.init();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	     	
   	 List<List<Object>> llTab = new ArrayList<List<Object>>(); 
        llTab.add(llRow);        
        //String range = "Sheet1!A:T";
        //String range = "Sheet1";
        String range = "Inscription";
        
    	
        String  valueInputOption = "USER_ENTERED";
        spreadsheetSnippets.appendValues(spreadsheetId, range, valueInputOption, llTab);
        System.out.println("sErvlet : Fin  Ecriture");
    }
    
    /**
     * Mise � jour d'une cellule Google
     * @param spreadsheetId
     * @param range
     * @param NouvelleValeur
     * @throws IOException
     * @throws GeneralSecurityException
     */
    
    static void updateCell(String spreadsheetId,String range , String NouvelleValeur) throws IOException, GeneralSecurityException {
    	//============> API GOOGLE ===============   
    	//UPdate Sample de la cellule E10 ! par la valeur : Americains     	    	
      	 System.out.println("sErvlet : Debut Ecriture");
         SpreadsheetSnippets spreadsheetSnippets=  new SpreadsheetSnippets();
         try {
 			spreadsheetSnippets.init();
 		} catch (GeneralSecurityException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
       	    
    	
       //String range = "Sheet1!E10:E";
       List<List<Object>> values = spreadsheetSnippets.getValues(spreadsheetId, range);
       values = Arrays.asList(
   	        Arrays.asList(
   	        		new Object[]{NouvelleValeur}
   	        )
    	);
       
       String  valueInputOption = "USER_ENTERED";
       spreadsheetSnippets.updateValues(spreadsheetId, range, valueInputOption, values);
   }
    
    
    public static String getTempoDir() {
    	
    	String OS = System.getProperty("os.name").toLowerCase();
    	String tempoDir="";
    	if (OS.contains("win")) {
    		tempoDir="c:\\temp\\";    		
    	} else {
    		tempoDir="/tmp/";        	
    	}    	
    	return tempoDir;
    	
    }
    
    /**
     * Cette methode permet de rechercher une valeur dans une colonne
     * @param spreadsheetSnippets
     * @param spreadsheetId
     * @param valueRech
     * @param sheetName
     * @param colRech
     * @param numLigneOuRange
     * @return  ====> retour le numerao de ligne ou le rage dans lequele  on cherche
     * @throws IOException
     * @throws GeneralSecurityException
     */
    static String rechercheValeurDansUneColonne(String spreadsheetId, String valueRech, String sheetName,String  colRech, String numLigneOuRange) throws IOException, GeneralSecurityException {
    	String  rangeToUpdate="";
        
    	
        SpreadsheetSnippets spreadsheetSnippets=  new SpreadsheetSnippets();
        try {
			spreadsheetSnippets.init();
		} catch (GeneralSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
    	
    	
    	//String range = "Sheet1!A:E";    
    	String range = sheetName+"!"+colRech+":"+colRech;
        List<List<Object>> values = spreadsheetSnippets.getValues(spreadsheetId, range);
        
        //int numColRech=spreadsheetSnippets.columnLetterToNumber(colRech)-1;
        int numColRech=0;
        System.out.println("rechercheValeurDansUneColonne : numColRech:"+numColRech);

		int i = 0;
		int ligneQuiMAtch=-1;
		boolean trouve=false;
		Object value = null;
		if (values != null) {
			//System.out.println("values size:"+values.size());
		    for (List row : values) {
		    	System.out.println("values i:"+i);
		        i += 1;
		        // values == null || values.isEmpty()) 
		        value = null;
		        System.out.println("values row size:"+row.size());
		        if (row != null && row.size()>numColRech) 
		        	value = row.get(numColRech);
		        if (value!= null) {
		        	if (value.equals(valueRech)) {
		        		ligneQuiMAtch = i;
		        		System.out.println( "IT'S A MATCH! ligneQuiMAtch= " + ligneQuiMAtch);
		        		
		        		rangeToUpdate = colRech + ligneQuiMAtch  + ":" +colRech; //row to be updated
		        		trouve = true;
		        	}
		        }
		    }
		}
		System.out.println( "rechercheValeurDansUneColonne trouv� ! Range / ligne :  "+rangeToUpdate	+"/"+ligneQuiMAtch);
		String sRetour="";
		if (numLigneOuRange.contentEquals("LIGNE")) {
			sRetour=new Integer(ligneQuiMAtch).toString();
		} else {
			sRetour=rangeToUpdate;
		}
    	return sRetour;    	
    }
    
    
    /**
     * 
     * @param paramList
     * @return
     * @throws Exception
     */
    public static String serialise(String nomFichier,ArrayList<Object> paramList ) throws Exception 
    {              
   	 
   	 
   	 
   	
    System.out.println("serialise() nomFichier: "+nomFichier);
   	 
    	 System.out.println("UtilServlet.serialise:"+"data\\new\\"+nomFichier); 
        try
        {                    	        	        	
        	
        	String OS = System.getProperty("os.name").toLowerCase();
        	String fichierPath="";
        	if (OS.contains("win")) {
        		createDir("data");
            	createDir("data\\new\\");
            	createDir("data\\ok\\");
            	fichierPath = "data\\new\\"+nomFichier;
        	} else {
        		createDir("data");
            	createDir("data/new/");
            	createDir("data/ok/");
            	fichierPath = "data/new/"+nomFichier;
        	}
        	        	        	        	
        	FileOutputStream fos = new FileOutputStream(fichierPath);            
            ObjectOutputStream oos = new ObjectOutputStream(fos);           
            oos.writeObject(paramList);
            oos.close();
            fos.close();
        } 
        catch (IOException ioe) 
        {
            ioe.printStackTrace();
            System.out.println("serialise UtilServlet.serialise:Exception, "+ioe);
        }
        return nomFichier;
    }
    
    
    public static void createDir(String dos) {
    	File theDir = new File(dos);

    	// if the directory does not exist, create it
    	if (!theDir.exists()) {
    	    System.out.println("creating directory: " + theDir.getName());
    	    boolean result = false;

    	    try{
    	        theDir.mkdir();
    	        result = true;
    	    } 
    	    catch(SecurityException se){
    	    	 System.out.println("createDir"+se);  
    	    }        
    	    if(result) {    
    	        System.out.println("DIR created");  
    	    }
    	}
    }
    
    
    /**
     * 
     * @param nomFichier
     * @return
     * @throws Exception
     */
    public static ArrayList<Object> deSerialise(String nomFichier)  throws Exception 
    {
     	System.out.println("deSerialise");
   	 ArrayList<Object> namesList = new ArrayList<Object>();
         
        try
        {
        	
        	String OS = System.getProperty("os.name").toLowerCase();
        	String fichierPath="";
        	if (OS.contains("win")) {
        		fichierPath = "data\\new\\"+nomFichier;
        	} else {
        		fichierPath = "data/new/"+nomFichier;
        	}
        	
            FileInputStream fis = new FileInputStream(fichierPath);
            ObjectInputStream ois = new ObjectInputStream(fis);
 
            namesList = (ArrayList) ois.readObject();
 
            ois.close();
            fis.close();
        } 
        catch (IOException ioe) 
        {
            ioe.printStackTrace();
            System.out.println("UtilServlet.serialise:Exception, "+ioe);
            return namesList;
        } 
        catch (ClassNotFoundException c) 
        {
            System.out.println("Class not found"+c);
            c.printStackTrace();            
            return namesList;
        }
         
        //Verify list data
        for (Object name : namesList) {
            System.out.println(name);
        }
        return namesList;
    }
    
    public static String upper1Lettre(String val) {
    	System.out.println("upper1Lettre");
    	val=val.toLowerCase();
    	val=val.substring(0, 1).toUpperCase()+val.substring(1);
    	return val;
    }

}
