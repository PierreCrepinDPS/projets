import java.util.ArrayList;

public class Tour extends Piece{// Tour descend de Pièce
    public Tour() {// initialisation par défault d'une tour blanche en A1
        super('B', "A1");
    }
    
    public Tour(char c, Position pos) { // initialisation d'une tour de couleur c en position pos
        super(c, pos);
    }

    public String getType() { // retourne le type de la pièce
        return "tour";
    }
    
    public ArrayList<Position> getDeplacementPossible(Plateau grille) {// vérification des coups possible de la tour
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        boolean h = false; boolean b = false; boolean d = false; boolean g = false; // initilisation des déplacements cardinaux
        int tx = this.getPosition().getX(); int ty = this.getPosition().getY();// récupération de la position de la tour sélectionnée
        char color = this.getCouleur(); // récuparation de la couleur de la tour
        char c_manger; // déclaration de la couleur qui pourra être mangé par la tour
        if (color=='B') { // définition de la couleur de la pièce que la tour pourra manger
            c_manger = 'N';
        } else {
            c_manger = 'B';
        }
        if (tx == 0) { // vérification de la possibilité de se déplacer vers les points cardinaux
            g = true;
        }
        if (ty == 0) {// vérification de la possibilité de se déplacer vers les points cardinaux
            b = true;
        }
        if (tx == 7) {// vérification de la possibilité de se déplacer vers les points cardinaux
            d = true;
        }
        if (ty == 7) {// vérification de la possibilité de se déplacer vers les points cardinaux
            h = true;
        }
        for (int i = 1; i <= 7; i++) { // parcour du tableau
            if (!g) { // vers la gauche si possible
                if (grille.getCase(tx - i, ty) == null) { // si la case à gauche est vide
                    d_possible.add(new Position(tx - i, ty));
                }
                else if (grille.getCase(tx - i, ty).getCouleur() == c_manger) { // si la case a gauche est mangeable
                    d_possible.add(new Position(tx - i, ty));
                    g = true; // on arrête de vérifier sur la gauche
                } 
                else if (grille.getCase(tx - i, ty).getCouleur() == color) { // vérification que la pièce est de la même couleur que la tour
                    g = true;// on arrête de vérifier sur la gauche
                } 
                if (tx - i == 0) { // si la tour a atteint le bord gauche du plateau
                    g = true;// on arrête de vérifier sur la gauche
                }
            }
            if (!d) {// vers la droite si possible
                if (grille.getCase(tx + i, ty) == null) {// si la case à droite est vide
                    d_possible.add(new Position(tx + i, ty));
                }
                else if (grille.getCase(tx + i, ty).getCouleur() == color) { // vérification que la pièce est de la même couleur que la tour
                    d = true;// on arrête de vérifier sur la droite
                }
                else if (grille.getCase(tx + i, ty).getCouleur() == c_manger) {// si la case a droite est mangeable
                    d_possible.add(new Position(tx + i, ty));
                    d = true;// on arrête de vérifier sur la droite
                } 
                if (tx + i == 7) {// si la tour a atteint le bord droite du plateau
                    d = true;// on arrête de vérifier sur la droite
                }
            }

            if (!b) {// vers le bas si possible
                if (grille.getCase(tx, ty - i) == null) {// si la case en bas est vide
                    d_possible.add(new Position(tx, ty - i));
                }
                else if (grille.getCase(tx, ty - i).getCouleur() == c_manger) {// si la case en bas est mangeable
                    d_possible.add(new Position(tx, ty - i));
                    b = true;// on arrête de vérifier sur le bas
                }
                else if (grille.getCase(tx, ty - i).getCouleur() == color) { // vérification que la pièce est de la même couleur que la tour
                    b = true;// on arrête de vérifier sur le bas
                }
                if (ty - i == 0) {// si la tour a atteint le bas du plateau
                    b = true;// on arrête de vérifier sur le bas
                }
            }

            if (!h) {// vers le haut si possible
                if (grille.getCase(tx, ty + i) == null) {// si la case en haut est vide
                    d_possible.add(new Position(tx, ty + i));
                }
                else if (grille.getCase(tx, ty + i).getCouleur() == color) {// vérification que la pièce est de la même couleur que la tour
                    h = true;// on arrête de vérifier sur le haut
                } 
                else if (grille.getCase(tx, ty + i).getCouleur() == c_manger) {// si la case en haut est mangeable
                    d_possible.add(new Position(tx, ty + i));
                    h = true;// on arrête de vérifier sur le haut
                }
                if (ty + i == 7) {// si la tour a atteint le haut du plateau
                    h = true;// on arrête de vérifier sur le haut
                }
            }
        }
        return d_possible; // retour de la liste des coups possible pour la tour
    }
}
