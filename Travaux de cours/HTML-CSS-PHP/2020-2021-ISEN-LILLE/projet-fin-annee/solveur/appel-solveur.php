<?php
$tab = $_GET['tableau'];

$fp = fopen("recu.txt", "w+"); // ouverture du fichier en écriture
fputs($fp, "$tab"); // on va a la ligne
fclose($fp);
exec('solitaire_v2.exe');
$fp = fopen("ecriture.txt", "r+");
$chaine = fgets($fp, 82);
fclose($fp);

header('Location:../game.php?board=' . $_GET['board'] . '&help=' . $chaine . '&hour=' . $_GET['hour'] . '&minute=' . $_GET['minute'] . '&second=' . $_GET['second'] . '#game');
?>