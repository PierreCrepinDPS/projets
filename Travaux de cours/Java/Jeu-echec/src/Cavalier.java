import java.util.ArrayList;

public class Cavalier extends Piece {// Cavalier descend de Pièce
    public Cavalier() {// initialisation par défault d'un cavalier blanc en A1
        super('B', "A1");
    }

    public Cavalier(char c, Position pos) {// initialisation d'un cavalier de couleur c en position pos
        super(c, pos);
    }

    public String getType() {// retourne le type de la pièce
        return "cavalier";
    }

    public ArrayList<Position> getDeplacementPossible(Plateau grille) {// vérification des coups possible
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        int cx = this.getPosition().getX(); int cy = this.getPosition().getY();// récupération de la position
        char color = this.getCouleur();// récuparation de la couleur
        char c_manger;// déclaration de la couleur qui pourra être mangée
        if (color == 'N') {// définition de la couleur de la pièce qui pourra être mangée
            c_manger = 'B';
        } else {
            c_manger = 'N';
        }
        if ((cx - 2) >= 0) {// vérication si le cavalier peut aller vers le haut de 2 cases
            if ((cy >=1) && ((grille.getCase(cx - 2, cy - 1) == null) || (grille.getCase(cx - 2, cy - 1).getCouleur() == c_manger))) {// si aller vers le haut de 2 et 1 vers la gauche est vide ou mangeable
                d_possible.add(new Position(cx - 2, cy - 1));
            }
            if ((cy <= 6) && ((grille.getCase(cx - 2, cy + 1) == null) || (grille.getCase(cx - 2, cy + 1).getCouleur() == c_manger))) {// si aller vers le haut de 2 et 1 vers la droite est vide ou mangeable
                d_possible.add(new Position(cx - 2, cy + 1));
            }
        }
        if ((cx + 2) <= 7) { // vérification si le cavalier peut aller vers le bas de 2 cases
            if ((cy >=1) && ((grille.getCase(cx + 2, cy - 1) == null) || (grille.getCase(cx + 2, cy - 1).getCouleur() == c_manger))) {// si aller vers le bas de 2 et 1 vers la gauche est vide ou mangeable
                d_possible.add(new Position(cx + 2, cy - 1));
            }
            if ((cy <= 6) && ((grille.getCase(cx + 2, cy + 1) == null) || (grille.getCase(cx + 2, cy + 1).getCouleur() == c_manger))) {// si aller vers le bas de 2 et 1 vers la droite est vide ou mangeable
                d_possible.add(new Position(cx + 2, cy + 1));
            }
        }
        if ((cy - 2) >= 0) {// vérication si le cavalier peut aller de 2 cases vers la gauche
            if ((cx >=1) && ((grille.getCase(cx - 1, cy - 2) == null) || (grille.getCase(cx - 1, cy - 2).getCouleur() == c_manger))) {// si aller vers la gauche de 2 et 1 vers le haut est vide ou mangeable
                d_possible.add(new Position(cx - 1, cy - 2));
            }
            if ((cx <= 6) && ((grille.getCase(cx + 1, cy - 2) == null) || (grille.getCase(cx + 1, cy - 2).getCouleur() == c_manger))) {// si aller ver la gauche de 2 et 1 vers le bas est vide ou mangeable
                d_possible.add(new Position(cx + 1, cy - 2));
            }
        }
        if ((cy + 2) <= 7) {// vérication si le cavalier peut aller de 2 cases vers la droite
            if ((cx >=1) && ((grille.getCase(cx - 1, cy + 2) == null) || (grille.getCase(cx - 1, cy + 2).getCouleur() == c_manger))) {// si aller vers la droite de 2 et 1 vers le haut est vide ou mangeable
                d_possible.add(new Position(cx - 1, cy + 2));
            }
            if ((cx <= 6) && ((grille.getCase(cx + 1, cy + 2) == null) || (grille.getCase(cx + 1, cy + 2).getCouleur() == c_manger))) {// si aller vers la droite de 2 et 1 vers le bas est vide ou mangeable
                d_possible.add(new Position(cx + 1, cy + 2));
            }
        }
        return d_possible;// retourne la liste des coups possibles
    }
}
