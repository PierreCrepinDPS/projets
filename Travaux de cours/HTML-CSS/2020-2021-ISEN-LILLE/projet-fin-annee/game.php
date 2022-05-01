<?php
require 'partials/bdd_connexion.php';

$id_modele = rand(1, 5);//on définit par défaut du modèle (au cas où le tableau $_GET soit vide)
if (isset($_GET['board'])) {
    //on fait une requette pour vérifier que m_id existe
    $count_id = $bdd->prepare('SELECT COUNT(m_id) FROM modeles WHERE m_id = :m_id');
    $count_id->bindValue(':m_id', $_GET['board']);
    $count_id->execute();
    $nb_modele = $count_id->fetchAll(PDO::FETCH_NUM);

    if ($nb_modele['0']['0'] == 1) {//s'il y a bien cet m_id dans le tableau on modifie id_modele
        $id_modele = $_GET['board'];
    }
}

//récupération des infos concernant le modèle
$req = $bdd->prepare('SELECT nom, plateau, nb_billes, billes_record, DATE_FORMAT(time_record, \'%H\') AS hour_record, DATE_FORMAT(time_record, \'%i\') AS minute_record, DATE_FORMAT(time_record, \'%s\') AS second_record, time_record, pseudo_record FROM modeles WHERE m_id = :m_id');
$req->bindValue(':m_id', $id_modele);
$req->execute();
$data_modele = $req->fetch(PDO::FETCH_ASSOC);

$data_modele['plateau'] = unserialize($data_modele['plateau']);//création du tableau à partir de la chaine qui le stockait

//var_dump($data_modele);

if (isset($_GET['help'])) {
    $chaine = $_GET['help'];
    $new_plateau = array();
    $data_modele['nb_billes'] = 0;
    for ($y = 0; $y < 9; $y++) {
        for ($x = 0; $x < 9; $x++) {
            $etat = substr($chaine, 9 * $y + $x, 1);

            $case_tmp = array();
            $case_tmp['x'] = $x;
            $case_tmp['y'] = $y;

            //on initialise les variables à false
            $case_tmp['t'] = false;
            $case_tmp['b'] = false;

            // on regarde si on doit mettre une bille ou un trou
            if ($etat == 0) {
                $case_tmp['t'] = false;
                $case_tmp['b'] = false;
            }

            if ($etat == 1) {
                $case_tmp['t'] = true;
                $case_tmp['b'] = false;
            }

            if ($etat == 2) {
                $case_tmp['t'] = true;
                $case_tmp['b'] = true;
                $data_modele['nb_billes']++;
            }
            $new_plateau[] = $case_tmp;
        }
    }
    $data_modele['plateau'] = $new_plateau;
}

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require 'partials/head.php' ?>
    <script type="text/javascript" src="JS/play-game.js"></script>
    <title>Play board "<?php echo $data_modele['nom'] ?>" | Solidaire</title>
    <script>
        plateau = Array.from(Array(9), () => new Array(9));

        <?php foreach ($data_modele['plateau'] as $case) { //pour chaque case, on écrit une ligne pour remplir le tableau en js avec soit 0 (hors-champ), soit 1 (trou), soit 2 (trou+bille)
        if ($case['t']) {
            $etat_tab_js = 1;//s'il y a un trou, valeur = 1
            if ($case['b']) {
                $etat_tab_js = 2;//s'il y a en plus une bille, on modifie cette valeur à 2
            }
        } else {
            $etat_tab_js = 0;//s'il n'y a pas de trou, valeur = 0
        }?>
        plateau[<?php echo $case['y'] . '][' . $case['x'] ?>] = <?php echo $etat_tab_js ?>;
        <?php } ?>
        remainingBilles = <?php echo $data_modele['nb_billes'] ?>;
        recordBille = <?php echo(($data_modele['billes_record'] != 0) ? $data_modele['billes_record'] : $data_modele['nb_billes']) ?>;
        hour_record = <?php echo sprintf('%02d', $data_modele['hour_record']) ?>;
        minute_record = <?php echo sprintf('%02d', $data_modele['minute_record']) ?>;
        second_record = <?php echo sprintf('%02d', $data_modele['second_record']) ?>;

        hasHelp = <?php echo(isset($_GET['help']) ? 'true' : 'false')?>;
        idModele = <?php echo $id_modele ?>;

        <?php
        if(isset($_GET['help']) && isset($_GET['hour']) && isset($_GET['minute']) && isset($_GET['second'])){ ?>
        timer_hour = <?php echo sprintf('%02d', $_GET['hour']) ?>;
        timer_minute = <?php echo sprintf('%02d', $_GET['minute']) ?>;
        timer_second = <?php echo sprintf('%02d', $_GET['second']) ?>;
        <?php } else {?>
        timer_second = 0;
        timer_minute = 0;
        timer_hour = 0;
        <?php } ?>
    </script>
</head>
<body>
<header>
    <?php require 'partials/nav.php' ?>
</header>
<div class="backgame">
    <div class="pagejeu">
        <div id="plateau" class="plateau">
            <?php foreach ($data_modele['plateau'] as $case) { //pour chaque case, on regarde s'il faut mettre un trou et (si oui) une bille en plus
                if ($case['t']) { ?>
                    <div class="trou" id="t<?php echo $case["x"] . $case["y"] ?>" posx="<?php echo $case["x"] ?>"
                         posy="<?php echo $case["y"] ?>">
                        <img src="img/trou.png" height="75px" alt="trou">
                    </div>

                    <?php if ($case["b"]) { ?>
                        <button onclick="MouvementBille(this)" class="bille" id="b<?php echo $case["x"] . $case['y'] ?>"
                                posx="<?php echo $case["x"] ?>" posy="<?php echo $case["y"] ?>" type="bille">
                            <img src="img/bille.png" alt="bille">
                        </button>
                    <?php }
                }
            } ?>
        </div>
        <div class="menugame" id=game>
            <h1><?php echo $data_modele['nom'] ?></h1>
            <ul>
                <li>
                    <div class="buttonspace">
                    <!-- onclick="callHelp()" -->
                        <button type="button" id="helpBtn" class="btn btn-secondary" data-bs-toggle="popover" data-bs-trigger="hover" data-bs-container="body" data-bs-placement="bottom" title="development in progress" data-bs-content="By clicking on this button, the automatic solver will make for you the best possible move to arrive at the least possible marbles">
                            Help
                            <i class="far fa-life-ring mx-3"></i>
                        </button>
                    </div>
                </li>
                <li>
                    <div class="buttonspace">
                        <a href="?board=<?php echo $id_modele ?>">
                            <button type="button" class="btn btn-secondary">
                                Restart
                                <i class="fa fa-undo-alt mx-3"></i>
                            </button>
                        </a>
                    </div>
                <li>
                <li>
                    <div class="buttonspace2">
                        <div class="input-group">
                            <input type="number" id="number-randomBtn" class="form-control"
                                   value="<?php echo rand(1, 6) ?>" min="1">
                            <button type="button" id="randomBtn" class="btn btn-secondary" onclick="mixGame()">
                                Random moves
                                <i class="fa fa-dice mx-2"></i>
                            </button>
                        </div>
                        <p>
                            <i>Asking for help or making a random move<br>will disable the ability to record a score</i>
                        </p>
                    </div>
                </li>
                <li>
                    <h2 id="timerdiv">
                        <span id="displayHour">00</span>:<span id="displayMinute">00</span>:<span
                                id="displaySecond">00</span>
                    </h2>
                </li>
                <div class="infogame">
                    <li>
                        Remaining marbles : <span id="remaining_billes"><?php echo $data_modele['nb_billes'] ?></span>
                    </li>
                    <?php if ($data_modele['time_record'] == null) { //s'il n'y a pas de record enregistré?>
                        Your are the first to try this board !
                    <?php } else { ?>
                        <li>Record of marbles : <?php echo $data_modele['billes_record'] ?></li>
                        <li>Record time : <?php
                            if ($data_modele['hour_record'] == '00') {//pour l'affichage sur la page, si les heures sont nulles, on les enlève
                                $data_modele['hour_record'] = '';
                            } else {
                                $data_modele['hour_record'] = $data_modele['hour_record'] . ':';//sinon on mets les ':' pour l'affichage
                            }
                            echo $data_modele['hour_record'] . $data_modele['minute_record'] . ':' . $data_modele['second_record']; ?>
                        </li>
                    <?php } ?>
                </div>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="wingame">
    <div class="modal fade" id="popopwin" data-bs-backdrop="static" tabindex="-1" aria-labelledby="popopwin"
         aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="exampleModalLabel">
                        <i class="fas fa-trophy text-primary"></i>
                        New record !
                        <i class="fas fa-trophy text-primary"></i>
                    </h4>
                    <h5>You broke the last record</h5>
                </div>
                <div class="modal-body text-center m-0">
                    <div class="fs-5">
                        You got to <span id="nb-billes-score"></span> marbles in <span id="hour-score"></span>
                        hours <span id="minute-score"></span> minutes and <span id="second-score"></span>
                        seconds
                    </div>
                    <div class="mt-3 fst-italic">
                        <?php if ($data_modele['time_record'] == null) { //s'il n'y a pas de record enregistré ?>
                            You were the first to play this board !
                        <?php } else { ?>
                            Previous high score was <?php echo $data_modele['billes_record'] ?> marbles in <?php echo $data_modele['hour_record'] . $data_modele['minute_record'] . ':' . $data_modele['second_record']; ?> by <?php echo $data_modele['pseudo_record'] ?>
                        <?php } ?>
                    </div>
                </div>
                <div class="modal-footer">
                    <form action="insert-record.php" method="post">
                        <input type="hidden" name="id" value="<?php echo $id_modele ?>">
                        <input type="hidden" id="nb-billes-form" name="nb-billes" value="">
                        <input type="hidden" id="hour-record-form" name="hour-record" value="">
                        <input type="hidden" id="minute-record-form" name="minute-record" value="">
                        <input type="hidden" id="second-record-form" name="second-record" value="">
                        <div class="input-group mb-3">
                            <span class="input-group-text">
                                <i class="fas fa-user-graduate fs-5"></i>
                            </span>
                            <input type="text" id="pseudo-form-save-score" name="pseudo"
                                   class="form-control rounded-end"
                                   placeholder="Write a username and save your high score !" required maxlength="20"
                                   onkeyup="cleanInput(this)">
                        </div>
                        <button type="submit" class="btn btn-primary">
                            Save <i class="fas fa-save fs-5"></i>
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<div class="endgame">
    <div class="modal fade" id="popupend" tabindex="-1" aria-labelledby="popupend" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="exampleModalLabel">Game finished</h4>
                </div>
                <div class="modal-body text-center m-0">
                    <div class="fs-5">
                        You got to <span id="nb-billes-score-not-win"></span> marbles in <span
                                id="hour-score-not-win"></span>
                        hours <span id="minute-score-not-win"></span> minutes and <span
                                id="second-score-not-win"></span>
                        seconds
                    </div>
                    <div id="info-modal-lost" class="mt-3 fst-italic">
                        <!--remplie par JS-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                        Close
                    </button>
                    <a href="?board=<?php echo $id_modele ?>">
                        <button type="button" class="btn btn-primary">
                            Retry
                            <i class="fa fa-undo-alt mx-2"></i>
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<footer>
    <?php require 'partials/footer.php' ?>
</footer>
<body>