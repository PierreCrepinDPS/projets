MvtEnCours = false;
idSelectedBille = '';
hasBeenShuffle = false;
isGamestart = false;
var Timer;

document.addEventListener("DOMContentLoaded", function (){
    if (hasHelp) {//au chargement, s'il y a eu de l'aide, il faut lancer le chrono
        isGamestart = true;
        Timer = setInterval(fctTimer, 1000);
    }
    if (isFinished()) {
        endOfGame();
    }
});

function MouvementBille(elementClique) {//fonction générale qui gère tout le déplacement en appelant d'autres fonctions
    if (!isGamestart) {
        isGamestart = true;
        document.getElementById('timerdiv').className = 'has-rebond-animation-once';
        Timer = setInterval(fctTimer, 1000);
    }

    if (!MvtEnCours) {//s'il n'y a pas de mvt en cours

        //on recupere la position de la bille cliquée
        let posX = elementClique.getAttribute("posx");
        let posY = elementClique.getAttribute("posy");
        let MvtPossible = isMvtPossible(posX, posY);
        if (MvtPossible[4] === 0) {
            //il n'y a pas de mouvement possible
            return;
        } else if (MvtPossible[4] > 1) { //il y a plus d'un mouvement qui est possible, il faut proposer à l'utilisateur de choisir
            MvtEnCours = true;
            idSelectedBille = elementClique.id;

            if (MvtPossible[0]) {
                let futurX = posX;
                let futurY = posY - 2;
                createSpinner(document.getElementById('t' + futurX + futurY), "top");
            }
            if (MvtPossible[1]) {
                let futurX = parseInt(posX) + 2;//le parse est nescessaire pour forcer l'utilisation du "+" en opérateur arithmétique et non de concaténation
                let futurY = posY;
                createSpinner(document.getElementById('t' + futurX + futurY), "right");
            }
            if (MvtPossible[2]) {
                let futurX = posX;
                let futurY = parseInt(posY) + 2;
                createSpinner(document.getElementById('t' + futurX + futurY), "bottom");
            }
            if (MvtPossible[3]) {
                let futurX = posX - 2;
                let futurY = posY;
                createSpinner(document.getElementById('t' + futurX + futurY), "left");
            }
            return;
        }

        //on détermine la direction du mvt
        if (MvtPossible[0]) {
            var direction = "top";
        } else if (MvtPossible[1]) {
            var direction = "right";
        } else if (MvtPossible[2]) {
            var direction = "bottom";
        } else if (MvtPossible[3]) {
            var direction = "left";
        }
        var billeToMove = elementClique;

    } else {
        //s'il y a un mvt en cours (donc c'est le deuxième clic pour indiquer le mvt)
        if (idSelectedBille === elementClique.id) {//si on reclique sur la bille selectionnée, cela annule le mvt en cours
            removeAllSpinner();
            MvtEnCours = false;
            idSelectedBille = "";
            return;
        } else if (elementClique.getAttribute("type") === "spinner") {
            var direction = elementClique.getAttribute("direction");
            var billeToMove = document.getElementById(idSelectedBille);
            removeAllSpinner();
            MvtEnCours = false;
            idSelectedBille = "";
        } else { //si on a cliqué ni sur la bille selectionnée ni sur un spinner
            return;
        }
    }

    displayMvtBille(billeToMove, direction);
    if (isFinished()) {
        endOfGame();
    }
}

function isTimingBeaten() {//renvoie vrai si tout les timer sont inférieurs ou égaux au record
    return timer_hour <= hour_record && timer_minute <= minute_record && timer_second <= second_record;
}

var fctTimer = function () {
    timer_second++;
    if (timer_second === 60) {
        timer_second = 0;
        timer_minute++;
        if (timer_minute === 60) {
            timer_minute = 0;
            timer_hour++;
        }
    }
    document.getElementById("displaySecond").innerText = (timer_second < 10 ? "0" + timer_second : timer_second);
    document.getElementById("displayMinute").innerText = (timer_minute < 10 ? "0" + timer_minute : timer_minute);
    document.getElementById("displayHour").innerText = (timer_hour < 10 ? "0" + timer_hour : timer_hour);
}

function RemoveBille(x, y) {
    let idToRemove = 'b' + x + y;
    let currentBille = document.getElementById(idToRemove);
    currentBille.parentElement.removeChild(currentBille);
    remainingBilles--;
    document.getElementById("remaining_billes").innerText = remainingBilles;
}

function isMvtPossible(posX, posY) {//fonction qui determine dans quelle direction on peut bouger

    let MvtPossible = [false, false, false, false, 0];//on initialise un tableau qui contient 4 booléens (haut, droite, bas, gauche) et un entier pour le nombre de mouvements possibles

    if (plateau[posY][posX] === 2) { // on vérifie qu'il y a bien une bille là où on a cliqué

        //vérification si le mvt en haut est possible
        if (posY - 2 >= 0) { //on commence par vérifier ici  qu'on ne sortirait pas du plateau en haut
            if (plateau[posY - 2][posX] === 1 && plateau[posY - 1][posX] === 2) {
                //si la deuxième case en haut est vide (1) et qu'il y a une bille à survoler (2 juste en haut), le mvt est possible
                MvtPossible[0] = true;
                MvtPossible[4]++;
            }
        }

        //vérification si le mvt à droite est possible
        if (parseInt(posX) + 2 <= 8) {
            if (plateau[posY][parseInt(posX) + 2] === 1 && plateau[posY][parseInt(posX) + 1] === 2) {
                MvtPossible[1] = true;
                MvtPossible[4]++;
            }
        }

        //vérification si le mvt en bas est possible
        if (parseInt(posY) + 2 <= 8) {
            if (plateau[parseInt(posY) + 2][posX] === 1 && plateau[parseInt(posY) + 1][posX] === 2) {
                MvtPossible[2] = true;
                MvtPossible[4]++;
            }
        }

        //vérification si le mvt à gauche est possible
        if (posX - 2 >= 0) {
            if (plateau[posY][posX - 2] === 1 && plateau[posY][posX - 1] === 2) {
                MvtPossible[3] = true;
                MvtPossible[4]++;
            }
        }
    }
    return MvtPossible;
}

function displayMvtBille(elemetToMove, direction) { //fonction qui bouge la bille passée en paramètre dans la direction passée en paramètre (top, right, bottom, left) et supprime la bille survolée

    let posX = parseInt(elemetToMove.getAttribute("posx"));
    let posY = parseInt(elemetToMove.getAttribute("posy"));

    switch (direction) {
        //en fonction de la direction, on définit les coordonnées nescessaire pour le chagement de place
        case "top":
            var futurX = posX;
            var futurY = posY - 2;
            var removedX = posX;
            var removedY = posY - 1;
            break;
        case "right":
            var futurX = posX + 2;
            var futurY = posY;
            var removedX = posX + 1;
            var removedY = posY;
            break;
        case "bottom":
            var futurX = posX;
            var futurY = posY + 2;
            var removedX = posX;
            var removedY = posY + 1;
            break;
        case "left":
            var futurX = posX - 2;
            var futurY = posY;
            var removedX = posX - 1;
            var removedY = posY;
            break;
    }
    //on actualise le tableau en mémoire
    plateau[posY][posX] = 1;//la bille quitte son trou....
    plateau[futurY][futurX] = 2;//... est arrive dans un autre
    plateau[removedY][removedX] = 1;//la bille survolée est supprimée
    elemetToMove.setAttribute("posy", futurY);
    elemetToMove.setAttribute("posx", futurX);
    elemetToMove.id = 'b' + futurX + futurY;
    RemoveBille(removedX, removedY);
}

function isFinished() {//fonction vérifiant que le jeu est terminé

    //on récupère la liste de toutes les billes encore existantes
    let listeBilles = document.getElementById("plateau").querySelectorAll(".bille");
    let thereIsAMvtPossible = false;
    //pour chaque bille, on regarde si elle peut bouger
    listeBilles.forEach(function (billeCourante) {
            let Mvt = isMvtPossible(billeCourante.getAttribute("posx"), billeCourante.getAttribute("posy"));
            if (Mvt[4] !== 0) { //si une bille peut bouger, on stoke l'info dans la variable
                thereIsAMvtPossible = true;
            }
        }
    );
    //on retourne le contraire de la variable
    return !thereIsAMvtPossible;
}

function endOfGame() {//s'occupe de la fin du jeu
    clearInterval(Timer);//on arrete le temps
    removeEventBille();
    document.getElementById('helpBtn').removeAttribute('onclick');

    let differenceBilles = remainingBilles - recordBille;
    if ((differenceBilles < 0 || (differenceBilles === 0 && isTimingBeaten())) && !hasBeenShuffle && !hasHelp) {//si nb billes du record est atteind, que timing est meilleur et que le plateau n'a pas été mélangé, record battu !
        let popup = new bootstrap.Modal(document.getElementById("popopwin"), {
            backdrop: false
        });

        document.getElementById("nb-billes-form").setAttribute("value", remainingBilles);
        document.getElementById("nb-billes-score").innerText = remainingBilles;
        document.getElementById("hour-record-form").setAttribute("value", timer_hour);
        document.getElementById("hour-score").innerText = timer_hour;
        document.getElementById("minute-record-form").setAttribute("value", timer_minute);
        document.getElementById("minute-score").innerText = timer_minute;
        document.getElementById("second-record-form").setAttribute("value", timer_second);
        document.getElementById("second-score").innerText = timer_second;

        popup.show();
    } else {// pas de nouveau record !
        let popup = new bootstrap.Modal(document.getElementById("popupend"), {
            backdrop: false
        });

        document.getElementById("nb-billes-score-not-win").innerText = remainingBilles;
        document.getElementById("hour-score-not-win").innerText = timer_hour;
        document.getElementById("minute-score-not-win").innerText = timer_minute;
        document.getElementById("second-score-not-win").innerText = timer_second;

        if (!hasBeenShuffle || !hasHelp) {
            document.getElementById("info-modal-lost").innerText = 'As you asked for help or made a random move, you can\'t save your score.';
        } else {
            document.getElementById("info-modal-lost").innerText = 'You did not reach the score...';
        }
        popup.show();
    }
}

function removeEventBille() {
    //on récupère la liste de toutes les billes encore existantes
    let listeBilles = document.getElementById("plateau").querySelectorAll(".bille");
    //pour chaque bille, on supprime le onclick
    listeBilles.forEach(function (billeCourante) {
            billeCourante.removeAttribute('onclick');
        }
    );
}

function createSpinner(trouToPlaceSpinner, direction) {
    let spinner = document.createElement("button");
    spinner.className = "spinner-grow text-primary btn";
    spinner.setAttribute("onclick", "MouvementBille(this)");
    spinner.setAttribute("type", "spinner");
    spinner.setAttribute("direction", direction);
    trouToPlaceSpinner.appendChild(spinner);
}

function removeAllSpinner() {//fonction supprimant tous les spinner présents sur la page
    let divPlateau = document.getElementById("plateau");
    let listSpinner = divPlateau.querySelectorAll("button.spinner-grow");
    listSpinner.forEach(function (currentSpinner) {
        currentSpinner.parentElement.removeChild(currentSpinner);
    });
}

function mixGame() {
    hasBeenShuffle = true;
    if (!isGamestart) {
        isGamestart = true;
        document.getElementById('timerdiv').className = 'has-rebond-animation-once';
        Timer = setInterval(fctTimer, 1000);
    }
    let numberMove = document.getElementById("number-randomBtn").value;
    if (numberMove <= 0) {//si nombre n'est pas correct
        return;
    }

    while (numberMove > 0) {
        let listeBilles = document.getElementById("plateau").querySelectorAll(".bille");
        if (isFinished()) {
            endOfGame();
            return;
        }
        listeBilles.forEach(function (billeCourante) {
                let possibleMvt = isMvtPossible(billeCourante.getAttribute('posx'), billeCourante.getAttribute('posy'));
                if (possibleMvt[4] !== 0 && numberMove > 0) { // si un mvt est possible pour la bille...
                    if (Math.random() * 8 >= 7) {//...on a une chance sur 8 de faire une mouvement avec celle-ci
                        let direction = '';
                        while (direction === '') {//on continue tant que la direction n'est pas déterminée
                            if (possibleMvt[0]) {
                                if (Math.floor(Math.random() * 4) === 0) {
                                    direction = "top";
                                }
                            } else if (possibleMvt[1]) {
                                if (Math.floor(Math.random() * 4) === 1) {
                                    direction = "right";
                                }
                            } else if (possibleMvt[2]) {
                                if (Math.floor(Math.random() * 4) === 2) {
                                    direction = "bottom";
                                }
                            } else if (possibleMvt[3]) {
                                if (Math.floor(Math.random() * 4) === 3) {
                                    direction = "left";
                                }
                            }
                        }
                        displayMvtBille(billeCourante, direction);
                        numberMove--;
                    }
                    if (isFinished()) {
                        endOfGame();
                        return;
                    }
                }
            }
        );
    }
}

function callHelp() {
    let chaine = '';

    for (let y = 0; y < 9; y++) {
        for (let x = 0; x < 9; x++) {
            chaine = chaine + plateau[y][x];
        }
    }
    document.location = "solveur/appel-solveur.php?tableau=" + chaine + '&board=' + idModele + '&hour=' + timer_hour + '&minute=' + timer_minute + '&second=' + timer_second;
}