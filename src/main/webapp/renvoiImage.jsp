<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>INSCRIPTION SALON SAVM 2022</title>
    <link rel="stylesheet" href="styles1.css">
</head>
 <!-- Verson du 05.03 à 17:44 -->
<body>
	<div class="img-container"> <!-- Block parent element -->
		<img src=banniereSAVM.jpg width=600px align=hcenter>
		<H2>INSCRIPTION SALON SAVM 2022</H2>
		<label for="md">Exposition du 5 au 27 Novembre 2022</label><BR><br>
	</div>

    <form action = "helloUploadRemplace" method = "post" enctype = "multipart/form-data">

        <fieldset>
            <legend>Renvoi de l'image de l'oeuvre</legend>
            
	            <p>
	                <label class="label" for="ID">ID<span class="obligatoire">*</span></label>
	                <input id="oldIdPost" type="text" name="oldIdPost" required size="10" placeholder="ID : YYYYMMDD_HHMiSS"  maxlength="15" pattern="[0-9]{8}_[0-9]{6}">
	            </p>
            
                <P>
                	<label class="Dimensions" for="Dimensions">Image de l'oeuvre<span class="obligatoire">*</span></label>                	
                    <input type = "file" name = "file" size = "50" required/>
                    <br><label class="Dimensions" for="Dimensions">(La qualité de l'image doit être suffisante pour le catalogue : taille supérieure à <b>1 Mo et inférieure à 5 Mo</b>)</label>
                </P>
         
        </fieldset>

      

        <p class="center">
            <input type="submit" value="Envoyer">
        </p>

    </form>

</body>


</html>
