<?php
require 'partials/bdd_connexion.php';
require 'partials/functions.php';

if (!empty($_POST['pseudo']) && !empty($_POST['id']) && isset($_POST['hour-record']) && isset($_POST['minute-record']) && isset($_POST['second-record']) && !empty($_POST['nb-billes'])) {
    if (is_numeric($_POST['id']) && is_numeric($_POST['nb-billes']) && is_numeric($_POST['hour-record']) && is_numeric($_POST['minute-record']) && is_numeric($_POST['second-record'])) {
        $_POST['pseudo'] = securisation($_POST['pseudo']);
        $timer = $_POST['hour-record'] . ':' . $_POST['minute-record'] . ':' . $_POST['second-record'];

        $req = $bdd->prepare("UPDATE modeles SET pseudo_record = :pseudo_record, date_record = DEFAULT, billes_record = :billes_record, time_record = :time_record WHERE m_id = :m_id;");
        $req->bindParam(':pseudo_record', $_POST['pseudo']);
        $req->bindParam(':billes_record', $_POST['nb-billes'], PDO::PARAM_INT);
        $req->bindParam(':time_record', $timer);
        $req->bindParam(':m_id', $_POST['id'], PDO::PARAM_INT);
        $req->execute();
    }
}
header('Location: index.php');
?>