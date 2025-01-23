package rico.embedtomcat;
import java.text.Normalizer;

import java.util.regex.Pattern;
//import com.ibm.icu.text.CharsetDetector;
//import com.ibm.icu.text.CharsetMatch;
import java.io.UnsupportedEncodingException;
 
public class NomaliseStringAccent {

	public static void main(String[] args) {
		 String texteAvecAccents = "Ceci est un texte avec des caractères accentués : éàèü";
	        String texteSansAccents = enleverAccents(texteAvecAccents);
	        System.out.println("Texte avec accents : " + texteAvecAccents);
	        System.out.println("Texte sans accents : " + texteSansAccents);
	        
	        //=============
	     String nomFichier="Pépères.png";
	     System.out.println("nomFichier:" + nomFichier);
	     System.out.println("detecte Encodig  : " + detectEncoding(nomFichier.getBytes()));
	     try {
			nomFichier = new String(nomFichier.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         System.out.println("UploadServlet fileName Apres UrdDECODER  ="+nomFichier);

	}
	
	 public static String enleverAccents(String texte) {
	        String texteSansAccents = Normalizer.normalize(texte, Normalizer.Form.NFD);
	        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	        return pattern.matcher(texteSansAccents).replaceAll("");
	 }
	 
	 
	 public static String detectEncoding(byte[] bytes) {
		 /*
	        CharsetDetector detector = new CharsetDetector();
	        detector.setText(bytes);
	        CharsetMatch match = detector.detect();
	        if (match != null) {
	            return match.getName();
	        } else {
	            return "Encodage non détecté";
	        }
	        */
		 return "UTF-8";
	    }

}
