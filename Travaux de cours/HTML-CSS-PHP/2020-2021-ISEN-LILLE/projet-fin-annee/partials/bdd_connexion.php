<?php
$hostname = 'mysql-solidaire.alwaysdata.net';
$dbname = 'solidaire_bdd';
$dbuser = 'solidaire';
$dbpass = 'ISENwtfcp@sf0u';
$charset = 'utf8';

try {
    $bdd = new PDO("mysql:host=$hostname;dbname=$dbname;charset=$charset", $dbuser, $dbpass, array(PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION));
} catch (PDOException $e) {
    printf('Échec de la connexion à la base de données : %s', $e->getMessage());
    exit();
}
?>