import java.util.ArrayList;

public class Roi extends Piece {// Roi descend de Pièce
    public Roi() {// initialisation par défault d'un roi blanc en A1
        super('B', "A1");
    }

    public Roi(char c, Position pos) {// initialisation d'un roi de couleur c en position pos
        super(c, pos);
    }

    public String getType() {// retourne le type de la pièce
        return "roi";
    }

    public ArrayList<Position> getDeplacementPossible(Plateau grille) {// vérification des coups possible
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        int rx = this.getPosition().getX(); int ry = this.getPosition().getY();// récupération de la position
        char color = this.getCouleur();// récuparation de la couleur
        char c_manger;// déclaration de la couleur qui pourra être mangée
        if (color=='B'){// définition de la couleur de la pièce qui pourra être mangée
            c_manger = 'N';
        }
        else {
            c_manger = 'B';
        }
        if (rx <= 6) {// si la pièce n'est pas tout en haut du plateau
            if ((grille.getCase(rx + 1, ry) == null) || (grille.getCase(rx + 1, ry).getCouleur() == c_manger)) { // vérification que la case est vide et mangeable
                d_possible.add(new Position(rx + 1, ry));
            }

            if (ry >= 1) { //si la pièce n'est pas tout a gauche du plateau
                if ((grille.getCase(rx + 1, ry - 1) == null) || (grille.getCase(rx + 1, ry - 1).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                    d_possible.add(new Position(rx + 1, ry - 1));
                }
            }

            if (ry <= 6) {//si la pièce n'est pas tout a droite du plateau
                if ((grille.getCase(rx + 1, ry + 1) == null) || (grille.getCase(rx + 1, ry + 1).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                    d_possible.add(new Position(rx + 1, ry + 1));
                }
            }
        }
        if (rx >= 1) {// si la pièce n'est pas tout en bas du plateau
            if ((grille.getCase(rx - 1, ry) == null) || (grille.getCase(rx - 1, ry).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                d_possible.add(new Position(rx - 1, ry));
            }
            if (ry >= 1) {//si la pièce n'est pas tout a gauche du plateau
                if ((grille.getCase(rx - 1, ry - 1) == null) || (grille.getCase(rx - 1, ry - 1).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                    d_possible.add(new Position(rx - 1, ry - 1));
                }
            }
            if (ry <= 6) {//si la pièce n'est pas tout a droite du plateau
                if ((grille.getCase(rx - 1, ry + 1) == null) || (grille.getCase(rx - 1, ry + 1).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                    d_possible.add(new Position(rx - 1, ry + 1));
                }
            }
        }
        if (ry >= 1) {
            if ((grille.getCase(rx, ry - 1) == null) ||(grille.getCase(rx, ry - 1).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                d_possible.add(new Position(rx, ry - 1));
            }
        }
        if (ry <= 6) {
            if ((grille.getCase(rx, ry + 1) == null) || (grille.getCase(rx, ry + 1).getCouleur() == c_manger)) {// vérification que la case est vide et mangeable
                d_possible.add(new Position(rx, ry + 1));
            }
        }
        return d_possible;// retourne la liste des coups possibles
    }
}
