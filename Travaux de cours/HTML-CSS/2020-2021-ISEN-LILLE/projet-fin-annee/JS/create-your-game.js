function ChangerEtatCase(checkbox, type) {
    let x = checkbox.getAttribute("posx");
    let y = checkbox.getAttribute("posy");
    let CurentCase = document.getElementById("c" + y + x);
    if (type === "t") {
        if (checkbox.checked) {
            CurentCase.className = "cases bg-trou";
        } else {
            let otherCheckbox = document.getElementById('b' + y + x);
            CurentCase.className = "cases";
            otherCheckbox.checked = false;
        }
    } else if (type === "b") {
        if (checkbox.checked) {
            let otherCheckbox = document.getElementById('t' + y + x);
            CurentCase.className = "cases bg-bille";
            otherCheckbox.checked = true;
        } else {
            CurentCase.className = "cases bg-trou";
        }
    }
}

function AfficheRange() {
    let nb_marble = document.getElementById("RangeMarble").value;
    let nb_hole = document.getElementById("RangeHole").value;

    if (nb_marble > nb_hole - 1) {
        nb_marble = nb_hole - 1;
        document.getElementById("RangeMarble").value = nb_marble;
    }
    document.getElementById("ValueMarble").innerHTML = nb_marble;
    document.getElementById("ValueHole").innerHTML = nb_hole;
}

function randomGenerator() {

    //randomizeTrou_Bille(bille_centre,trou_centre);

    var newBille = 0;
    var newTrou = 0;
    var nb_marble = document.getElementById("RangeMarble").value;
    let nb_hole = document.getElementById("RangeHole").value;
    console.log(oui);
    if (oui === true) {
        var bille_centre = document.getElementById("b44");
        var trou_centre = document.getElementById("t44");
        var posx = bille_centre.getAttribute("posx");
        var posy = bille_centre.getAttribute("posy");
        var dir = ' ';
        var dirTmp = ' ';

        //bille_centre.checked = true;
        trou_centre.checked = true;
        //nb_marble-=1;
        nb_hole -= 1;
        superieur = false;
        ChangerEtatCase(bille_centre, 'b');
        //while(nb_marble>0 || nb_hole>0){
        //for(i=0;i<50;i++){
        while (nb_hole > 0 || nb_marble > 0) {
            var alea1 = Math.floor(Math.random() * 4);
            if (alea1 === 0) {
                if (posx == 0) {
                    dir = 'O'
                } else {
                    dir = 'haut';
                    dirTmp = 'haut';
                }
            }

            if (alea1 === 1) {
                if (posy == 0) {
                    dir = 'O';
                } else {
                    dir = 'gauche';
                    dirTmp = 'gauche';
                }

            }
            if (alea1 === 2) {
                if (posx == 8) {
                    dir = 'O';
                } else {
                    dir = 'bas';
                    dirTmp = 'bas';
                }
            }
            if (alea1 === 3) {
                if (posy == 8) {
                    dir = 'O';
                } else {
                    dir = 'droite';
                    dirTmp = 'droite';
                }
            }
            if (dir === 'gauche') {
                posy = Number(posy) - 1;
            }
            if (dir === 'haut') {
                posx = Number(posx) - 1;
            }
            if (dir === 'droite') {
                posy = Number(posy) + 1;
            }
            if (dir === 'bas') {
                posx = Number(posx) + 1;
            }
            //console.log(dir);

            newBille = document.getElementById("b" + posx + posy);
            newTrou = document.getElementById("t" + posx + posy);
            //console.log(newBille.checked,newTrou.checked);
            if (nb_marble > 0 && nb_hole > 0) {
                var alea = Math.floor(Math.random() * 6);
                if (alea === 0 || alea === 1 || alea === 2 || alea === 3 || alea === 4) {
                    if (newBille.checked == false) {
                        nb_marble -= 1
                    }
                    ;
                    if (newTrou.checked == false) {
                        nb_hole -= 1
                    }
                    ;
                    newTrou.checked = true;
                    newBille.checked = true;
                    ChangerEtatCase(newBille, 'b');
                } else {
                    if (newBille.checked == false) {
                        if (newTrou.checked == false) {
                            nb_hole -= 1
                        }
                        ;
                        newTrou.checked = true;
                        ChangerEtatCase(newTrou, 't');
                    }
                }
            }
            if (nb_marble < 1 && nb_hole > 0) {
                if (newBille.checked == false && newTrou.checked == false) {
                    if (newTrou.checked == false) {
                        nb_hole -= 1;
                    }
                    ;
                    newTrou.checked = true;
                    ChangerEtatCase(newTrou, 't');
                }
            }
            if (nb_marble > 0 && nb_hole < 1) {
                var alea = Math.floor(Math.random() * 6);
                if (newTrou.checked == true) {
                    if (alea === 0 || alea === 1 || alea === 2 || alea === 3 || alea === 4) {
                        if (newBille.checked == false) {
                            nb_marble -= 1
                        }
                        ;
                        newBille.checked = true;
                        ChangerEtatCase(newBille, 'b');
                    }
                }
            }
            console.log(nb_marble, nb_hole, dir, alea);

        }
        let form = document.getElementById("form-random");
        form.parentElement.removeChild(form);
    }

    oui = false;
}
    

                    



