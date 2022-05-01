<?php
require 'partials/bdd_connexion.php';
require 'partials/functions.php';

$req_plateaux_officiels = $bdd->prepare('SELECT * FROM modeles WHERE m_id < 6');
$req_plateaux_officiels->execute();
$data_plateaux_oficiels = $req_plateaux_officiels->fetchAll(PDO::FETCH_ASSOC);

//par défaut, le tri est fait par date
$sort = 'daterecent';
$string_req = "SELECT m_id, nom, plateau, nb_billes, nb_trous,  DATE_FORMAT(date_creation, '%d/%m/%Y %H:%i') AS date_creation, pseudo_creation, pseudo_record, DATE_FORMAT(date_record, '%d/%m/%Y %H:%i') AS date_record, billes_record, time_record FROM modeles WHERE m_id > 6";

if (!empty($_GET['search'])) {
    $search = securisation($_GET['search']);
    $string_req = $string_req . " AND (pseudo_creation LIKE :search OR pseudo_record LIKE :search OR nom LIKE :search)  ORDER BY m_id DESC";
    $req_plateaux_custom = $bdd->prepare($string_req);
    $req_plateaux_custom->bindValue(':search', '%' . $search . '%', PDO::PARAM_STR);

} elseif (!empty($_GET['sort'])) {
    $sort = securisation($_GET['sort']);

    //on ajoute le bout de requette nescessaire. Par défaut, $sort = 'daterecent'
    if ($sort == 'daterecent') {
        $string_req = $string_req . " ORDER BY m_id DESC";
    } elseif ($sort == 'dateoldest') {
        $string_req = $string_req . " ORDER BY m_id";
    } elseif ($sort == 'creator') {
        $string_req = $string_req . " ORDER BY pseudo_creation";
    } elseif ($sort == 'board') {
        $string_req = $string_req . " ORDER BY nom";
    } elseif ($sort == 'record') {
        $string_req = $string_req . " ORDER BY date_record DESC";
    }
    $req_plateaux_custom = $bdd->prepare($string_req);

} else {//s'il n'y a ni tri ni recherche qui est faîte
    $string_req = $string_req . " ORDER BY m_id DESC";
    $req_plateaux_custom = $bdd->prepare($string_req);
}

$req_plateaux_custom->execute();
$data_plateaux_custom = $req_plateaux_custom->fetchAll(PDO::FETCH_ASSOC);

?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require 'partials/head.php' ?>
    <title>Choose a level | Solidaire</title>
</head>
<body>
<header>
    <?php require 'partials/nav.php' ?>
</header>
<a href="#top" class="totop"><i class="fa fa-arrow-up fa-3x" aria-hidden="true"></i></a>
<main>
    <div class="pageprincipale">

    <div id="standard" class="basic_plateau">
    <div class="alignement_button">
            <h1>Standa<a href="https://www.youtube.com/watch?v=dQw4w9WgXcQ" target="blank">r</a>d Boards</h1>
            <div class="boutton_random">
            <a href="game.php">
                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">
                    Play random level
               </button>
            </a>
        </div>
        </div>

            <div class="basic_plateau_top">
                <div class="Grand_plateau">
                    <a href="game.php?board=4#game"><img class="bloc_plateau" src="img/p4.png" width="300px"></a>
                    <h2>Standard</h2>
                </div>

                <div class="petit_plateau">
                    <div class="petit_plateau_1">
                        <a href="game.php?board=1#game"><img class="bloc_plateau" src="img/p1.png" width="150px"></a>
                        <h2>European</h2>
                    </div>
                    <div class="petit_plateau_1">
                        <a href="game.php?board=2#game"><img class="bloc_plateau" src="img/p2.png" width="150px"></a>
                        <h2>Wiegleb</h2>
                    </div>
                    <div class="petit_plateau_1">
                        <a href="game.php?board=3#game"><img class="bloc_plateau" src="img/p3.png" width="150px"></a>
                        <h2>Asymmetrical</h2>
                    </div>
                    <div class="petit_plateau_1">
                        <a href="game.php?board=5#game"><img class="bloc_plateau" src="img/p5.png" width="150px"></a>
                        <h2>Diamond</h2>
                    </div>
                </div>
            </div>
        </div>
        <div class="custom_plateau">
            <div class="tri-custom">
                <div id="custom" class="custom_boards">
                    <h2>Custom Boards</h2>
                </div>
                <form action="#custom" method="get">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" placeholder="Search for a username, a board name..."
                               aria-label="Recipient's username" aria-describedby="button-addon2" name="search"
                               value="<?php if (!empty($search)) {
                                   echo $search;
                               } ?>">
                        <button class="btn btn-primary" type="submit" id="button-addon2">
                            <i class="fas fa-search-plus"></i>
                        </button>
                    </div>
                </form>

                <div class="btn-group">
                    <button type="button" class="btn btn-primary dropdown-toggle" data-bs-toggle="dropdown"
                            aria-expanded="false" style="height: fit-content;">
                        Sort by :
                    </button>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item <?php if ($sort == 'daterecent') {
                                echo 'active';
                            } ?>" href="?sort=daterecent#custom">
                                Most recent date
                            </a>
                        </li>
                        <li><a class="dropdown-item <?php if ($sort == 'dateoldest') {
                                echo 'active';
                            } ?>" href="?sort=dateoldest#custom">
                                Oldest date
                            </a>
                        </li>
                        <li><a class="dropdown-item <?php if ($sort == 'board') {
                                echo 'active';
                            } ?>" href="?sort=board#custom">
                                Board name
                            </a>
                        </li>
                        <li><a class="dropdown-item <?php if ($sort == 'creator') {
                                echo 'active';
                            } ?>" href="?sort=creator#custom">
                                Creator Name
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item <?php if ($sort == 'record') {
                                echo 'active';
                            } ?>" href="?sort=record#custom">
                                Last record
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <?php if (empty($data_plateaux_custom)) { //il n'y a pas de plateau corespondant aux critères de recherche ?>
                <h5>No result found !<h5>
            <?php } else {
                foreach ($data_plateaux_custom as $plateau_courant) { ?>

                    <div class="custom">
                            <table>
                                <tr>
                                    <td rowspan="3">
                                    <div id="plateau" class="customplateau">
                                    <?php $plateau_courant['plateau']=unserialize($plateau_courant['plateau']);
                                    foreach ($plateau_courant['plateau'] as $case) { //pour chaque case, on regarde s'il faut mettre un trou et (si oui) une bille en plus
                                        if ($case['b']) { ?>
                                            <div class="custombille">
                                                <img src="img/billetrou.png">
                                            </div>
                                        <?php }

                                        else if ($case['t']) { ?>
                                            <div class="customtrou">
                                                <img src="img/trou.png">
                                            </div>
                                        <?php }
                                        

                                        else { ?>
                                            <div class="customvide">
                                                <img src="img/vide.gif">
                                            </div>
                                        <?php }
                                            
                                        }?>
                                    </div>
                                    </td>
                                    <th><h3>Board's name: <?php echo $plateau_courant['nom'] ?></h3></th>
                                </tr>
                                <tr>
                                    <th><h3>Creator: <?php echo $plateau_courant['pseudo_creation'] ?></h3></th>
                                    <th>
                                        <div class="btn-jeu">
                                            <a href="game.php?board=<?php echo $plateau_courant['m_id'] ?>#game">
                                                <button type="button" class="btn btn-primary" data-bs-dismiss="modal">
                                                    Play
                                                </button>
                                            </a>
                                        </div>
                                    </th>
                                </tr>
                                <tr>
                                    <th><h3>Date: <?php echo $plateau_courant['date_creation'] ?></h3></th>
                                    <th>
                                        <div class="btn-info">
                                            <a href="#modalboard<?php echo $plateau_courant['m_id'] ?>">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
                                                    More info  <i class="fas fa-info-circle ms-2"></i>
                                                </button>
                                            </a>
                                        </div>
                                        <!--<th><a href="#modalboard<?php /*echo $plateau_courant['m_id'] */?>" class="openModal">more info</a>-->
                                        <div id="modalboard<?php echo $plateau_courant['m_id'] ?>" class="modal">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="container">
                                                        <a href="#false" class="closebtn">×</a>
                                                        <ul>
                                                            <li><?php echo $plateau_courant['nb_billes'] ?> marbles</li>
                                                            <li><?php echo $plateau_courant['nb_trous'] ?> holes</li>
                                                            <br>
                                                            <li class="info-record-custom">
                                                                <?php if ($plateau_courant['date_record'] != null) { ?>
                                                                    <div class="fs-4 rounded-pill bg-primary text-center py-1">
                                                                        <i class="fas fa-medal"></i>
                                                                        Best score: <?php echo $plateau_courant['billes_record'] ?>
                                                                    </div>
                                                                    <ul>
                                                                        <li>
                                                                            <i class="fas fa-user"> </i>
                                                                            <?php echo $plateau_courant['pseudo_record'] ?>
                                                                        </li>
                                                                        <li>
                                                                            <i class="fas fa-calendar-day"> </i>
                                                                            <?php echo $plateau_courant['date_record'] ?>
                                                                        </li>
                                                                        <li>
                                                                            <i class="fas fa-stopwatch"> </i>
                                                                            <?php echo $plateau_courant['time_record'] ?>
                                                                        </li>
                                                                    </ul>
                                                                <?php } else { ?>
                                                                    Nobody has tried this board yet, be the first!
                                                                <?php } ?>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </th>
                                </tr>
                            </table>
                    </div>
                <?php }
            } ?>
        </div>
    </div>
</main>
<footer>
    <?php require 'partials/footer.php' ?>
</footer>
</body>
</html> 