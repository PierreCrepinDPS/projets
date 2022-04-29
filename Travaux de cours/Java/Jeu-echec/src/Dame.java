import java.util.ArrayList;

public class Dame extends Piece {// Dame descend de Pièce
    public Dame() {// initialisation par défault d'une dame blanc en A1
        super('B', "A1");
    }

    public Dame(char c, Position pos) {// initialisation d'une dame de couleur c en position pos
        super(c, pos);
    }

    public String getType() {// retourne le type de la pièce
        return "dame";
    }

    public ArrayList<Position> getDeplacementPossible(Plateau grille) {// vérification des coups possible
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        boolean hd = false; boolean hg = false; boolean bd = false; boolean bg = false;// initilisation des déplacements en diagonale
        boolean h = false; boolean b = false; boolean d = false; boolean g = false;// initilisation des déplacements cardinaux
        int dx = this.getPosition().getX(); int dy = this.getPosition().getY();// récupération de la position
        char color = this.getCouleur();// récuparation de la couleur
        char c_manger;// déclaration de la couleur qui pourra être mangée
        if (color == 'N') {// définition de la couleur de la pièce qui pourra être mangée
            c_manger = 'B';
        } 
        else {
            c_manger = 'N';
        }
        if (dx == 0) {// vérification de la possibilité de se déplacer vers les points cardinaux et en diagonale
            hg = true;
            bg = true;
            g = true;
        }
        if (dx == 7) {// vérification de la possibilité de se déplacer vers les points cardinaux et en diagonale
            hd = true;
            bd = true;
            d = true;
        }
        if (dy == 0) {// vérification de la possibilité de se déplacer vers les points cardinaux et en diagonale
            bg = true;
            bd = true;
            b = true;
        }
        if (dy == 7) {// vérification de la possibilité de se déplacer vers les points cardinaux et en diagonale
            hg = true;
            hd = true;
            h = true;
        }

        for (int i = 1; i <= 7; i++) {// parcour du tableau
            if (!g) {// vers la gauche si possible
                if (grille.getCase(dx - i, dy) == null) {// si la case à gauche est vide
                    d_possible.add(new Position(dx - i, dy));
                }
                else if (grille.getCase(dx - i, dy).getCouleur() == c_manger) {// si la case a gauche est mangeable
                    d_possible.add(new Position(dx - i, dy));
                    g = true; // on arrête de vérifier sur la gauche
                } 
                else if (grille.getCase(dx - i, dy).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    g = true;// on arrête de vérifier sur la gauche
                } 
                if (dx - i == 0) {// si la pièce a atteint le bord gauche du plateau
                    g = true;// on arrête de vérifier sur la gauche
                }
            }

            if (!d) {// vers la droite si possible
                if (grille.getCase(dx + i, dy) == null) {// si la case à droite est vide
                    d_possible.add(new Position(dx + i, dy));
                }
                else if (grille.getCase(dx + i, dy).getCouleur() == c_manger) {// si la case a droite est mangeable
                    d_possible.add(new Position(dx + i, dy));
                    d = true;// on arrête de vérifier sur la droite
                } 
                else if (grille.getCase(dx + i, dy).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    d = true;// on arrête de vérifier sur la droite
                } 
                if (dx + i == 7) {// si la pièce a atteint le bord droite du plateau
                    d = true;// on arrête de vérifier sur la droite
                }
            }

            if (!b) {// vers le bas si possible
                if (grille.getCase(dx, dy - i) == null) {// si la case en bas est vide
                    d_possible.add(new Position(dx, dy - i));
                }
                else if (grille.getCase(dx, dy - i).getCouleur() == c_manger) {// si la case en bas est mangeable
                    d_possible.add(new Position(dx, dy - i));
                    b = true;// on arrête de vérifier sur le bas
                } 
                else if (grille.getCase(dx, dy - i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    b = true;// on arrête de vérifier sur le bas
                } 
                if (dy - i == 0) {// si la pièce a atteint le bord en bas du plateau
                    b = true;// on arrête de vérifier sur le bas
                }
            }

            if (!h) {// vers le haut si possible
                if (grille.getCase(dx, dy + i) == null) {// si la case en haut est vide
                    d_possible.add(new Position(dx, dy + i));
                }
                else if (grille.getCase(dx, dy + i).getCouleur() == c_manger) {// si la case en haut est mangeable
                    d_possible.add(new Position(dx, dy + i));
                    h = true;// on arrête de vérifier sur le haut
                }
                else if (grille.getCase(dx, dy + i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    h = true;// on arrête de vérifier sur le haut
                } 
                if (dy + i == 7) {// si la pièce a atteint le bord en haut du plateau
                    h = true;// on arrête de vérifier sur le haut
                }
            }
            
            if (!hg) {// en diagonale haut gauche si possible
                if (grille.getCase(dx - i, dy + i) == null) {// si la case en diagonale haut gauche est vide
                    d_possible.add(new Position(dx - i, dy + i));
                }
                else if (grille.getCase(dx - i, dy + i).getCouleur() == c_manger) {// si la case en diagonale haut gauche est mangeable
                    d_possible.add(new Position(dx - i, dy + i));
                    hg = true;// on arrête de vérifier en diagonale haut gauche
                }
                else if (grille.getCase(dx - i, dy + i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    hg = true;// on arrête de vérifier en diagonale haut gauche
                }
                if ((dx - i == 0) || (dy + i == 7)) {// si la pièce a atteint le bord en diagonale haut gauche du plateau
                    hg = true;// on arrête de vérifier en diagonale haut gauche
                }
            }

            if (!hd) {// en diagonale haut droite si possible
                if (grille.getCase(dx + i, dy + i) == null) {// si la case en diagonale haut droite est vide
                    d_possible.add(new Position(dx + i, dy + i));
                }
                else if (grille.getCase(dx + i, dy + i).getCouleur() == c_manger) {// si la case en diagonale haut droite est mangeable
                    d_possible.add(new Position(dx + i, dy + i));
                    hd = true;// on arrête de vérifier en diagonale haut droite
                }
                else if (grille.getCase(dx + i, dy + i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    hd = true;// on arrête de vérifier en diagonale haut droite
                }
                if ((dx + i == 7) || (dy + i == 7)) {// si la pièce a atteint le bord en diagonale haut droite du plateau
                    hd = true;// on arrête de vérifier en diagonale haut droite
                }
            }

            if (!bg) {// en diagonale bas gauche si possible
                if (grille.getCase(dx - i, dy - i) == null) {// si la case en diagonale bas gauche est vide
                    d_possible.add(new Position(dx - i, dy - i));
                }
                else if (grille.getCase(dx - i, dy - i).getCouleur() == c_manger) {// si la case en diagonale bas gauche est mangeable
                    d_possible.add(new Position(dx - i, dy - i));
                    bg = true;// on arrête de vérifier en diagonale bas gauche
                }
                else if (grille.getCase(dx - i, dy - i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    bg = true;// on arrête de vérifier en diagonale bas gauche
                }
                if ((dy - i == 0) || (dx - i == 0)) {// si la pièce a atteint le bord en diagonale bas gauche du plateau
                    bg = true;// on arrête de vérifier en diagonale bas gauche
                }
            }

            if (!bd) {// en diagonale bas droite si possible
                if (grille.getCase(dx + i, dy - i) == null) {// si la case en diagonale bas droite est vide
                    d_possible.add(new Position(dx + i, dy - i));
                }
                else if (grille.getCase(dx + i, dy - i).getCouleur() == c_manger) {// si la case en diagonale bas droite est mangeable
                    d_possible.add(new Position(dx + i, dy - i));
                    bd = true;// on arrête de vérifier en diagonale bas droite
                }
                else if (grille.getCase(dx + i, dy - i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    bd = true;// on arrête de vérifier en diagonale bas droite
                }
                if ((dy - i == 0) || (dx + i == 7)) {// si la pièce a atteint le bord en diagonale bas droite du plateau
                    bd = true;// on arrête de vérifier en diagonale bas droite
                }
            }
        }
        return d_possible;// retourne la liste des coups possibles
    }
}
