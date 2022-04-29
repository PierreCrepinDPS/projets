import java.util.ArrayList;

public class Fou extends Piece {// Fou descend de Pièce
    public Fou() {// initialisation par défault d'un fou blanc en A1
        super('B', "A1");
    }

    public Fou(char c, Position pos) {// initialisation d'un fou de couleur c en position pos
        super(c, pos);
    }

    public String getType() {// retourne le type de la pièce
        return "fou";
    }

    public ArrayList<Position> getDeplacementPossible(Plateau grille) {// vérification des coups possible
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        boolean hd = false; boolean hg = false; boolean bd = false; boolean bg = false;// initilisation des déplacements en diagonale
        int fx = this.getPosition().getX(); int fy = this.getPosition().getY(); // récupération de la position
        char color = this.getCouleur();// récuparation de la couleur
        char c_manger;// déclaration de la couleur qui pourra être mangée
        if (color == 'N') {// définition de la couleur de la pièce qui pourra être mangée
            c_manger = 'B';
        } else {
            c_manger = 'N';
        }

        if (fx == 0) {// vérification de la possibilité de se déplacer en diagonale
            hg = true;
            bg = true;
        }
        if (fx == 7) {// vérification de la possibilité de se déplacer en diagonale
            hd = true;
            bd = true;
        }
        if (fy == 0) {// vérification de la possibilité de se déplacer en diagonale
            bg = true;
            bd = true;
        }
        if (fy == 7) {// vérification de la possibilité de se déplacer en diagonale
            hg = true;
            hd = true;
        }
        for (int i = 1; i <= 7; i++) {// parcour du tableau
            if (!hg) {// en diagonale haut gauche si possible
                if (grille.getCase(fx - i, fy + i) == null) {// si la case en diagonale haut gauche est vide
                    d_possible.add(new Position(fx - i, fy + i));
                }
                else if (grille.getCase(fx - i, fy + i).getCouleur() == c_manger) {// si la case en diagonale haut gauche est mangeable
                    d_possible.add(new Position(fx - i, fy + i));
                    hg = true;// on arrête de vérifier en diagonale haut gauche
                } 
                else if (grille.getCase(fx - i, fy + i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    hg = true;// on arrête de vérifier en diagonale haut gauche
                }
                if ((fx - i == 0) || (fy + i == 7)){// si le fou a atteint le bord en diagonale haut gauche du plateau
                    hg = true;// on arrête de vérifier en diagonale haut gauche
                }
            }
            if (!hd) {// en diagonale haut droite si possible
                if (grille.getCase(fx + i, fy + i) == null) {// si la case en diagonale haut droite est vide
                    d_possible.add(new Position(fx + i, fy + i));
                }
                else if (grille.getCase(fx + i, fy + i).getCouleur() == c_manger) {// si la case en diagonale haut droite est mangeable
                    d_possible.add(new Position(fx + i, fy + i));
                    hd = true;// on arrête de vérifier en diagonale haut droite
                } 
                else if (grille.getCase(fx + i, fy + i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    hd = true;// on arrête de vérifier en diagonale haut droite
                }
                if ((fx + i == 7) || (fy + i == 7)) {// si le fou a atteint le bord en diagonale haut droite du plateau
                    hd = true;// on arrête de vérifier en diagonale haut droite
                }
            }
            if (!bg) {// en diagonale bas gauche si possible
                if (grille.getCase(fx - i, fy - i) == null) {// si la case en diagonale bas gauche est vide
                    d_possible.add(new Position(fx - i, fy - i));
                }
                else if (grille.getCase(fx - i, fy - i).getCouleur() == c_manger) {// si la case en diagonale bas gauche est mangeable
                    d_possible.add(new Position(fx - i, fy - i));
                    bg = true;// on arrête de vérifier en diagonale bas gauche
                } 
                else if (grille.getCase(fx - i, fy - i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    bg = true;// on arrête de vérifier en diagonale bas gauche
                } 
                if ((fy - i == 0) || (fx - i == 0)) {// si le fou a atteint le bord en diagonale bas gauche du plateau
                    bg = true;// on arrête de vérifier en diagonale bas gauche
                }
            }
            if (!bd) {// en diagonale bas droite si possible
                if (grille.getCase(fx + i, fy - i) == null) {// si la case en diagonale bas droite est vide
                    d_possible.add(new Position(fx + i, fy - i));
                }
                else if (grille.getCase(fx + i, fy - i).getCouleur() == c_manger) {// si la case en diagonale bas droite est mangeable
                    d_possible.add(new Position(fx + i, fy - i));
                    bd = true;// on arrête de vérifier en diagonale bas droite
                } 
                else if (grille.getCase(fx + i, fy - i).getCouleur() == color) {// vérification que la pièce est de la même couleur
                    bd = true;// on arrête de vérifier en diagonale bas droite
                } 
                if ((fy - i == 0) || (fx + i == 7)) {// si le fou a atteint le bord en diagonale bas droite du plateau
                    bd = true;// on arrête de vérifier en diagonale bas droite
                }
            }
        }
        return d_possible; // retourne la liste des coups possibles
    }
}
