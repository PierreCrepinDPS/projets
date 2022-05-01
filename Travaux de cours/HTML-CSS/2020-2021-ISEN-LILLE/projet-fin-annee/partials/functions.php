<?php
function securisation($champ)
{
    // Supprime les espaces de début et de fin
    $champ = trim($champ);
    // Supprime les backslashes
    $champ = stripslashes($champ);
    // Prévention faille XSS
    $champ = htmlspecialchars($champ, ENT_QUOTES);
    return $champ;
}

?>