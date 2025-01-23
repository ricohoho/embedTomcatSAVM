<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="utf-8">
    <title>INSCRIPTION SALON SAVM 2022</title>
    <link rel="stylesheet" href="styles1.css">
</head>
 <!-- Verson du 05.03 à 17:44 --> 
<body>
	<div class="img-container"> <!-- Block parent element -->
		<img src=banniereSAVM.jpg width=600px align=hcenter>
		<H2>INSCRIPTION SALON SAVM 2022</H2>
		<label for="md">Exposition du  5 au 27 Novembre 2022</label><BR><br>
	</div>

    <form action = "helloUpload" method = "post" enctype = "multipart/form-data">
    
        <fieldset>
            <legend>Qui êtes-vous ?</legend>

            <p>Les champs marqués de l'astérisque rouge sont obligatoires.</p>
            <p >
            	<label class="label" for="civilite">Civilité<span class="obligatoire">*</span></label>
                <input type="radio" value="Madame" id="md" name="CIVIL"  required="required">
                <label for="md">Madame</label>
                <input type="radio" value="Monsieur" id="ms" name="CIVIL"  required="required">
                <label for="ms">Monsieur</label>                
            </p>

            <p>
                <label class="label" for="contact_nom">Nom<span class="obligatoire">*</span></label>
                <input type="text" id="contact_nom" name="NOM / Pseudo" required>
                <label class="label" for="contact_nom">(utilisé dans le catalogue)</label>
            </p>

            <p>
                <label class="label" for="contact_prenom">Prénom</label>
                <input id="contact_prenom" type="text" name="PRENOM" >
            </p>           
            <p>
                <label class="label" for="email">Email<span class="obligatoire">*</span></label>
                <input id="email" type="email" name="EMAIL" required>
            </p>

            <p>
                <label class="label" for="email">Tel<span class="obligatoire">*</span></label>
                <input id="tel" type="text" name="TEL" required size="10" placeholder="10 chiffres"  maxlength="10" pattern="[0-9]{10}">
            </p>
            
           
        </fieldset>

        <br>
        <br>
        <fieldset>
            <legend>Où habitez-vous ?</legend>
            <p>
                <label class="label" for="num">Numéro de rue <span class="obligatoire">*</span></label>
                <input id="num" type="text" name="ADR_NUM" size="10"  title="nuléro : 33 Bis par exemple" required>
            </p>
            <p>
                <label class="label" for="rue">Nom de rue<span class="obligatoire">*</span></label>
                <input id="rue" type="text" size="40" name="ADR_RUE" maxlength="150" required>
            </p>
            <p>
                <label class="label" for="cp">Code postal<span class="obligatoire">*</span></label>
                <input id="cp" type="text" maxlength="5" name="ADR_CODE_POSTAL" size="10" placeholder="5 chiffres" pattern="[0-9]{5}" title="nombre à 5 chiffres" required>                
            </p>
            
            <p>
                <label class="label" for="ville">Ville<span class="obligatoire">*</span></label>
                <input id="cp" type="text"   onkeyup="this.value = this.value.toUpperCase();" maxlength="30" name="ADR_VILLE" size="10" placeholder="ville"  title="ville" required>                
            </p>
        </fieldset>

        <br>
        <br>
        <fieldset>
            <legend>Quelques informations sur l'oeuvre (Une seule oeuvre sera présentée)</legend>
            <p>
                <label class="label" for="Titre">Titre de l'oeuvre<span class="obligatoire">*</span></label>
                <input id="OEUVRE_TITRE" type="text" size="40" name="OEUVRE_TITRE" required>
            </p>
            
             <p>
                <label for="selectniveau" class="label">Type d'oeuvre<span class="obligatoire">*</span></label>
                <select id="selectniveau" name="OEUVRE_TYPE" required>
                    <option value="0">-- Sélectionnez votre type d'oeuvre --</option>
                    <option value="Sculpture">Sculpture</option>
                    <option value="Peinture">Peinture</option>
                    <option value="Dessin">Dessin</option>
                    <option value="Gravure">Gravure</option>
                    <option value="Autres">Autres</option>
                </select>
            </p>
            
            <p>
                <label class="label" for="rue">Décrire son oeuvre </label>
                <textarea id="OEUVRE_DETAIL" rows="5" cols="60" placeholder="Description de l'oeuvre (5 lignes max)"  name="OEUVRE_DETAIL" maxlength = "300"></textarea>
            </p>
            <p>
                <label class="label" for="rue">Remarques particulières à l'exposition de l'œuvre  </label>
                <textarea id="OEUVRE_DETAIL_EXPO" rows="5" cols="60" placeholder="Description de l'oeuvre (5 lignes max)"  name="OEUVRE_DETAIL" maxlength = "300"></textarea>
            </p>
            
                <p>
                    <label class="Dimensions" for="Dimensions">Dimensions Hauteur : <span class="obligatoire">*</span></label>
                    <input  type="text" maxlength="4" size="4" placeholder="Cm" pattern="[0-9]*" title="Dimensions" name=DIMENSIONY required>
                
                    <label class="Dimensions" for="Dimensions">x Largeur : <span class="obligatoire">*</span></label>
                    <input id="Dimensions" type="text" maxlength="4" size="4" placeholder="Cm" pattern="[0-9]*" title="Dimensions"  name=DIMENSIONX required>                                        
                    
                    <label class="Dimensions" for="Dimensions">x Profondeur : </label>
                    <input id="Dimensions" type="text" maxlength="4" size="4" placeholder="Cm" pattern="[0-9]*" title="Dimensions"  name=DIMENSIONZ >
                    
                </p>
                <P>
                	<label class="Dimensions" for="Dimensions">Image de l'oeuvre<span class="obligatoire">*</span></label>                	
                    <input type = "file" name = "file" size = "50" required/>
                    <br><label class="Dimensions" for="Dimensions">(La qualité de l'image doit être suffisante pour le catalogue : taille supérieure à <b>1 Mo et inférieure à 5 Mo</b>)</label>
                </P>
            <p>
                <label class="label" for="prix">Prix de vente<span class="obligatoire">*</span></label>
                <input id="prix" type="number" maxlength="5" size="5" placeholder="Euros" pattern="[0-9]{5}" title="Dimensions" name="OEUVRE_PRIX" required> 
            </p>
            <p>
                <label class="label" for="siret">Numéro de siret</label>
                <input id="site" type="text" maxlength="14" size="14" placeholder="siret (14 Chifres)" pattern="[0-9]{14}" title="Dimensions" name="SIRET_MDA" >
            </p>
            <p class="margel">Disponibilités pour les gardes
                <input type="radio" value="oui" id="md1" name="DISPO_GARDE" >
                <label for="md">oui</label>
                <input type="radio" value="non" id="ms1" name="DISPO_GARDE" checked>
                <label for="ms">non</label>     
            </p>
            <p>
                <label class="label" for="site">Votre site internet</label>
                <input id="site" type="text" maxlength="50" size="40" placeholder="www.VotreSite.fr"  title="Dimensions" NAME="SITE_INTERNET">
            </p>
            <label class="label" for="site">Un mini CV (5 lignes max)</label>
            <textarea id="comment" name=REMARQUES rows="5" cols="60" placeholder="Un mini CV (5 lignes max)"  maxlength = "300" ></textarea>
        </fieldset>

        <br>
        <br>
        <fieldset>
            <legend>Remarques / Règlements</legend>
            
            
            <p>
             L'inscription sera <A style="text-decoration:none;color:black"" href=renvoiImage.jsp>définitive</A> après avoir reçu le paiement (un seul chèque) (se reporter au règlement pour participation selon le format) à envoyer par courrier
"SAVM Ateliers d'Art 5 ter, avenue du Bac 94210 La Varenne Saint Hilaire" avec enveloppe de retour.
            </p>
            <p>                
                <input id="REGLE_ADHESION" type="checkbox"  onclick='javascript:regles()'  title="Règles d'adhésion" NAME="REGLE_ADHESION" required>J'ai lu, compris et accepté les règles d'adhésion<span class="obligatoire">*</span>
                <A href="http://davic.mkdh.fr/savm-data/reglementsSAVM2022.pdf" target="Reglements SAVM"> Téléchargement <img height="30" width="30" src="pdf2.png"></A>
            </p>
            
            
        </fieldset>

        <p class="center">
            <input type="submit" value="Envoyer">
        </p>

    </form>

</body>

<script>
function regles() {
	var strWindowFeatures = "menubar=no,location=no,resizable=yes,scrollbars=yes,status=yes";
	//windowObjectReference = window.open("http://davic.mkdh.fr/savm-data/reglementsSAVM2022.pdf", "Reglement SAVM 2022", strWindowFeatures);		
}
</script> 
</html>
