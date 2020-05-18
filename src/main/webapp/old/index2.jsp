<!DOCTYPE html>
<html lang="fr">

<head>
    <meta charset="UTF-8">
    <title>formulaire</title>
    <link rel="stylesheet" href="styles1.css">
</head>

<body>




    <form action="hello" method="">
    
        <fieldset>
            <legend>Qui êtes-vous ?</legend>

            <p>Les champs marqués de l'astérisque rouge sont obligatoires.</p>
            <p class="margel">
                <input type="radio" value="madame" id="md" name="civil">
                <label for="md">Madame</label>
                <input type="radio" value="monsieur" id="ms" name="civil">
                <label for="ms">Monsieur</label>

                
            </p>

            <p>
                <label class="label" for="contact_nom">Nom<span class="obligatoire">*</span></label>
                <input type="text" id="contact_nom" name="NOM" required>

            </p>

            <p>
                <label class="label" for="contact_prenom">Prénom<span class="obligatoire">*</span></label>
                <input id="contact_prenom" type="text" name="PRENOM" required>
            </p>           
            <p>
                <label class="label" for="email">Email<span class="obligatoire">*</span></label>
                <input id="email" type="email" name="EMAIL" required>
            </p>

            
            <p>
                <label for="selectniveau" class="label">Type d'oeuvre</label>
                <select id="selectniveau" name="OEUVRE_TYPE">
                    <option value="0">-- Sélectionnez votre type d'oeuvre --</option>
                    <option value="bts">Sculpture</option>
                    <option value="dut">Peinture</option>
                    <option value="licence">Dessin</option>
                    <option value="master">Gravure</option>
                </select>
            </p>
        </fieldset>

        <br>
        <br>
        <fieldset>
            <legend>Où habitez-vous ?</legend>
            <p>
                <label class="label" for="num">Numéro de rue</label>
                <input id="num" type="text" name="ADR_NUM" size="4">
            </p>
            <p>
                <label class="label" for="rue">Nom de rue</label>
                <input id="rue" type="text" size="40" name="ADR_RUE" maxlength="150">
            </p>
            <p>
                <label class="label" for="cp">Code postal</label>
                <input id="cp" type="text" maxlength="5" name="ADR_CODE_POSTAL" size="10" placeholder="5 chiffres" pattern="[0-9]{5}" title="nombre à 5 chiffres">                
            </p>
        </fieldset>

        <br>
        <br>
        <fieldset>
            <legend>Quelques informations sur l'oeuvre...</legend>
            <p>
                <label class="label" for="Titre">Titre de l'oeuvre</label>
                <input id="num" type="text" size="40">
            </p>
            <p>
                <label class="label" for="rue">Detail technique de l'oeuvre</label>
                <input id="rue" type="number" size="40" maxlength="150">
            </p>
                <p>
                    <label class="Dimensions" for="Dimensions">Dimensions</label>
                    <input id="Dimensions" type="number" maxlength="5" size="5" placeholder="Cm" pattern="[0-9]{5}" title="Dimensions">
                
                    <label class="Dimensions" for="Dimensions">x</label>
                    <input id="Dimensions" type="number" maxlength="5" size="5" placeholder="Cm" pattern="[0-9]{5}" title="Dimensions">
                </p>
            <p>
                <label class="label" for="prix">Prix de vente</label>
                <input id="prix" type="number" maxlength="5" size="5" placeholder="$" pattern="[0-9]{5}" title="Dimensions">
            </p>
            <p>
                <label class="label" for="siret">Numéro de siret</label>
                <input id="site" type="text" maxlength="5" size="10" placeholder="siret" pattern="[0-9]{5}" title="Dimensions">
            </p>
            <p class="margel">Disponibilité pour les gardes
                <input type="radio" value="oui" id="md" name="civil">
                <label for="md">oui</label>
                <input type="radio" value="non" id="ms" name="civil">
                <label for="ms">non</label>    
            </p>
            <p>
                <label class="label" for="site">Votre site internet</label>
                <input id="site" type="text" maxlength="5" size="40" placeholder="www.VotreSite.fr" pattern="[0-9]{5}" title="Dimensions">
            </p>
        </fieldset>

        <br>
        <br>
        <fieldset>
            <legend>Des remarques ?</legend>

            <textarea id="comment" rows="5" cols="60" placeholder="La parole est à vous"></textarea>
        </fieldset>

        <p class="center">
            <input type="submit" value="Envoyer">
        </p>

    </form>

</body>

</html>