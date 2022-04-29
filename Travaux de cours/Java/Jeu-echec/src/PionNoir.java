import java.util.ArrayList;

public class PionNoir extends Pion {//PionNoir descend de Pion
    public PionNoir() {// initialisation d'un pion noir en position par défault définit dans Pion
        super();
    }

    public PionNoir(Position pos) { // initialisation d'un pion noir en position pos
        super('N', pos);
    }

    public ArrayList<Position> getDeplacementPossible(Plateau grille) {// vérification des coups possible du pion noir
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        int px = getPosition().getX(); int py = getPosition().getY();// récupération de la position du pion noir sélectionnée
        char color = this.getCouleur();// récupération de la couleur du pion noir
        if ((this.getPosition().getY() == 6) && (grille.getCase(px, py - 1) == null)) {// si le pion noir est sur sa ligne de départ
            d_possible.add(new Position(px, py - 1));
            if (grille.getCase(px, py - 2) == null) {// si la case 2 cases plus loin est vide
                d_possible.add(new Position(px, py - 2));
            }
        } 
        else if ((grille.getCase(px, py - 1) == null) && (py != 0)) {// ajout de la case devant le pion si vide et que le pion n'est pas au bout de l'échequier
            d_possible.add(new Position(px, py - 1));
        }

        if ((py >= 1) && (px <= 6)) {
            if (grille.getCase(px + 1, py - 1) != null) {
                if (grille.getCase(px + 1, py - 1).getCouleur() != color) {// vérification que la case en diagonale droite est vide ou de couleur différente
                    d_possible.add(new Position(px + 1, py - 1));// ajout du coup possible
                }
            }
        }
        if ((py >= 1) && (px >= 1)) {
            if (grille.getCase(px - 1, py - 1) != null) {
                if (grille.getCase(px - 1, py - 1).getCouleur() != color) {// vérification que la case en diagonale gauche est vide ou de couleur différente
                    d_possible.add(new Position(px - 1, py - 1));// ajout du coup possible
                }
            }
        }
        return d_possible;// retourne le tableau des coups possibles
    }
}
