<?php
require 'partials/bdd_connexion.php';
require 'partials/functions.php';

//récupération des données du formulaire et stockage dans la bdd
if (!empty($_POST)) {
    if (empty($_POST['pseudo-creation']) || empty($_POST['name-creation'])) {//si l'une des variables n'est pas définie -> erreur
        header('location: create-your-game.php?error=true');
    } else {
        if (strlen($_POST['pseudo-creation']) < 1 || strlen($_POST['pseudo-creation']) > 20 || strlen($_POST['name-creation']) < 1 || strlen($_POST['name-creation']) > 20) {//vérification que les champs sont biens remplis
            header('location: create-your-game.php?error=true');
        }
        $pseudo_creation = securisation($_POST['pseudo-creation']);
        $nom_creation = securisation($_POST['name-creation']);

        $new_plateau = array();
        $nb_billes = 0;
        $nb_trous = 0;
        //on parcour la grille et pour chaque case, on regarde s'il y a une bille et/ou un trou et on l'ajoute dans le tableau
        for ($y = 0; $y < 9; $y++) {
            for ($x = 0; $x < 9; $x++) {
                $case_tmp = array();
                $case_tmp['x'] = $x;
                $case_tmp['y'] = $y;
                $case_tmp['t'] = false;
                $case_tmp['b'] = false;
                if (!empty($_POST['t' . $y . $x])) {//pour le trou
                    $case_tmp['t'] = true;
                    $nb_trous++;
                    if (!empty($_POST['b' . $y . $x])) {//pour la bille
                        $case_tmp['b'] = true;
                        $nb_billes++;
                    }
                }
                $new_plateau[] = $case_tmp;
            }
        }
        $new_plateau_serialize = serialize($new_plateau);

        $req = $bdd->prepare("INSERT INTO modeles (nom, plateau, nb_billes, nb_trous, date_creation, pseudo_creation, date_record) VALUES (:nom, :plateau, :nb_billes, :nb_trous, DEFAULT, :pseudo_creation, NULL)");
        $req->bindParam(':nom', $nom_creation);
        $req->bindParam(':plateau', $new_plateau_serialize);
        $req->bindParam(':nb_billes', $nb_billes);
        $req->bindParam(':nb_trous', $nb_trous);
        $req->bindParam(':pseudo_creation', $pseudo_creation);
        $req->execute();
        $new_id = $bdd->lastInsertId();
        header('Location: game.php?board=' . $new_id);
    }

}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <?php require 'partials/head.php' ?>
    <script type="text/javascript" src="JS/create-your-game.js"></script>
    <title>Level Editor | Solidaire</title>
</head>
<body>
<header>
    <?php require 'partials/nav.php' ?>
</header>

<form action="create-your-game.php" method="post" class="needs-validation" novalidate>
    <div class="background-edit">

        <?php
        if (!empty($_GET['error']) && $_GET['error'] == 'true') { ?>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong><i class="fas fa-exclamation-triangle fs-3"></i> Ouch...</strong> One of the submited fields
                contained
                an error, please try again
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        <?php } ?>

        <div class="create-your-game-page-container-title">
            <h4>Level Editor</h4>
        </div>
        <div class="create-your-game-page-container">
            <div class="plateau-conception">
                <?php
                for ($y = 0; $y < 9; $y++) { ?>
                    <div>
                        <?php for ($x = 0; $x < 9; $x++) { ?>
                            <div class="cases" id="c<?php echo $y . $x ?>">
                                <div class="form-check form-switch mt-2">
                                    <input class="form-check-input" type="checkbox" name="t<?php echo $y . $x ?>"
                                           id="t<?php echo $y . $x ?>" value="true" posx="<?php echo $x ?>"
                                           posy="<?php echo $y ?>" onchange="ChangerEtatCase(this, 't')">
                                </div>

                                <div class="form-check form-switch">
                                    <input class="form-check-input" type="checkbox" name="b<?php echo $y . $x ?>"
                                           id="b<?php echo $y . $x ?>" value="true" posx="<?php echo $x ?>"
                                           posy="<?php echo $y ?>" onchange="ChangerEtatCase(this, 'b')">
                                </div>
                            </div>
                        <?php } ?>
                    </div>
                <?php } ?>
            </div>
            
            <div class="exemple-checkbox">
                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault" checked>
                    <label class="form-check-label" for="flexSwitchCheckDefault">put a hole</label>
                </div>

                <div class="form-check form-switch">
                    <input class="form-check-input" type="checkbox" id="flexSwitchCheckDefault">
                    <label class="form-check-label" for="flexSwitchCheckDefault">put a marble</label>
                </div>
            </div>

            <div class="formulaire-enregistrement">

                <div class="input-group mb-3">
                    <span class="input-group-text"><i class="fas fa-user-edit fs-5"></i></span>
                    <input type="text" name="pseudo-creation" class="form-control rounded-end"
                            placeholder="Tell us your username" required maxlength="20" onkeyup="cleanInput(this)" >
                    <div class="invalid-feedback">
                        Please give a username with a maximum of 20 letters
                    </div>
                    <div class="valid-feedback">
                        Looks good !
                    </div>
                </div>


                <div class="input-group mb-3 has-validation">
                    <span class="input-group-text"><i class="fas fa-pen-square fs-5"></i></span>
                    <input type="text" name="name-creation" class="form-control rounded-end"
                            placeholder="Give here a custom name for your board" required maxlength="20" onkeyup="cleanInput(this)">
                    <div class="invalid-feedback">
                        Please give to your board a name with a maximum of 20 letters
                    </div>
                    <div class="valid-feedback">
                        Looks good !
                    </div>
                </div>

                <div class="text-center">
                    <button type="submit" class="btn btn-primary">Save and play ! <i class="fas fa-save fs-5"></i>
                    </button>
                </div>
            </div>
            <div class="advise">
                    <br>
                    <I>* For a proper functioning of the level, please put at least one free hole and the balls next to each other.</I>
            </div>
        </div>
</form>

<form id="form-random" method="post" action="#">
    <div class="create-your-game-page-container2">
        <fieldset class="curseur-concepteur">
            <legend class="fs-5">
                Make a random level
                <button type="button" id="info-random-generate" class="btn fs-4" data-bs-toggle="popover" data-bs-trigger="hover" title="Let's make it for you !" data-bs-content="Give the number of holes and marbles you want, a tray will be generated automatically. You can modify it before saving.">
                    <i class="far fa-question-circle"></i>
                </button>
            </legend>

            <div class="group-range">
                <label for="RangeHole" class="form-label badge rounded-pill bg-primary fs-6 me-3 my-0 p-2 fw-light"
                       style="width: 80px">Holes</label>
                <input id="RangeHole" class="form-range" type="range" step="1" value="60"
                       min="3"
                       max="81" onchange="AfficheRange()">
                <span id="ValueHole">60</span>
            </div>

            <div class="group-range mt-4">
                <label for="RangeMarble" class="form-label badge rounded-pill bg-primary fs-6 me-3 my-0 p-2 fw-light"
                       style="width: 80px">Marbles</label>
                <input id="RangeMarble" class="form-range" type="range" step="1" value="45"
                       min="2"
                       max="80" onchange="AfficheRange()">
                <span id="ValueMarble">45</span>
            </div>
            <div class="mx-auto" style="width: fit-content; width: -moz-fit-content">
                            <script>var oui = true;</script>
                <button type="button" class="btn btn-primary mt-3" onclick="randomGenerator()">
                    Make it ! <i class="fas fa-pencil-ruler"></i>
                </button>
            </div>
        </fieldset>
    </div>
    </div>
</form>
<footer>
    <?php require 'partials/footer.php' ?>
</footer>
</html>