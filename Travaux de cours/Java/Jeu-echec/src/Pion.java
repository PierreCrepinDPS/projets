import java.util.ArrayList;

public abstract class Pion extends Piece { //annonce que le pion descend de la pièce
    public Pion() { // initialisation du pion par défault de couleur blanche en A1
        super('B', "A1");
    }

    public Pion( char c, Position pos) { // initialisation du pion avec sa couleur une position pos
        super(c, pos);
    }

    public String getType() { // retourne le type de la pièce
        return "pion";
    }

    public abstract ArrayList<Position> getDeplacementPossible(Plateau plateau);// déclaration de l'appel des déplacements de la pièce pour une utilité dans un autre code du projet
}
