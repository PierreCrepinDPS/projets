import java.util.ArrayList;


public class Plateau {
    private ArrayList<Piece> tab_p = new ArrayList<Piece>();

    public Plateau() { // attribution des pièces sur l'échéquier
        this.tab_p.add(new Tour('B', new Position(0, 0)));
        this.tab_p.add(new Cavalier('B', new Position(1, 0)));
        this.tab_p.add(new Fou('B',new Position( 2, 0)));
        this.tab_p.add(new Dame('B', new Position(3, 0)));
        this.tab_p.add(new Roi('B', new Position(4, 0)));
        this.tab_p.add(new Fou('B', new Position(5, 0)));
        this.tab_p.add(new Cavalier('B', new Position(6, 0)));
        this.tab_p.add(new Tour( 'B', new Position(7, 0)));

        this.tab_p.add(new PionBlanc(new Position(0, 1)));
        this.tab_p.add(new PionBlanc(new Position(1, 1)));
        this.tab_p.add(new PionBlanc(new Position(2, 1)));
        this.tab_p.add(new PionBlanc(new Position(3, 1)));
        this.tab_p.add(new PionBlanc(new Position(4, 1)));
        this.tab_p.add(new PionBlanc(new Position(5, 1)));
        this.tab_p.add(new PionBlanc(new Position(6, 1)));
        this.tab_p.add(new PionBlanc(new Position(7, 1)));

        this.tab_p.add(new PionNoir(new Position(0, 6)));
        this.tab_p.add(new PionNoir(new Position(1, 6)));
        this.tab_p.add(new PionNoir(new Position(2, 6)));
        this.tab_p.add(new PionNoir(new Position(3, 6)));
        this.tab_p.add(new PionNoir(new Position(4, 6)));
        this.tab_p.add(new PionNoir(new Position(5, 6)));
        this.tab_p.add(new PionNoir(new Position(6, 6)));
        this.tab_p.add(new PionNoir(new Position(7, 6)));

        this.tab_p.add(new Tour('N',new Position(0, 7)));
        this.tab_p.add(new Cavalier('N',new Position(1, 7)));
        this.tab_p.add(new Fou('N',new Position(2, 7)));
        this.tab_p.add(new Dame('N', new Position(3, 7)));
        this.tab_p.add(new Roi('N', new Position(4, 7)));
        this.tab_p.add(new Fou('N', new Position(5, 7)));
        this.tab_p.add(new Cavalier('N', new Position(6, 7)));
        this.tab_p.add(new Tour('N', new Position(7, 7)));
    }

    public Piece getCase(Integer valx, Integer valy) { //recherche de la case situé en position définit avec les entiers valx et valy
        Position pos = new Position(valx, valy);
        for (int i = 0; i < tab_p.size(); i++) { // parcours du tableau de pièces
            if (pos.equals(tab_p.get(i).getPosition())) { // si une correspondance est trouvée
                return (tab_p.get(i)); // retourne la pièce
            }
        }
        return null; // ne retourne rien
    }
    
    public Piece getCase(Position pos) {//recherche de la case situé en position pos
        for (int i = 0; i < tab_p.size(); i++) {// parcours du tableau de pièces
            if (pos.equals(tab_p.get(i).getPosition())) {// si une correspondance est trouvée
                return (tab_p.get(i));// retourne la pièce
            }
        }
        return null;// ne retourne rien
    }

    public Piece getCase(String str_pos) {//recherche de la case situé en position définit avec la chaine de charactère str_pos
        Position pos = new Position(str_pos);
        for (int i = 0; i < tab_p.size(); i++) {// parcours du tableau de pièces
            if (pos.equals(tab_p.get(i).getPosition())) {// si une correspondance est trouvée
                return (tab_p.get(i));// retourne la pièce
            }
        }
        return null;// ne retourne rien
    }

    public String toString() {// affichage du plateau dans le panneau de commande
        String sep = " |---|---|---|---|---|---|---|---|\n"; // définition de la séparation entre les lignes
        String grille = "  A   B   C   D   E   F   G   H\n" + sep; // initialisation de la grille avec l'affichage des colones et la séparation
        for (int i = 7; i >= 0; i--) {// parcour du tableau en x
            String l = (i + 1) + "|";// ajout d'une barre entre chaque pièce

            for (int j = 0; j <= 7; j++) { // parcour du tableau en y
                String c = "   ";// ajout d'un espace avant la ligne
                for (int k = 0; k < tab_p.size(); k++) {// parcour de la liste des pièces
                    if (tab_p.get(k).getPosition().equals(new Position(j, i))) { // si une pièce est situé en position i,j
                        c = tab_p.get(k).getType();// récupération du type de la pièce
                    }
                }
                l = l + c + "|"; // ajout de la pièce à la ligne du tableau
            }
            grille = grille + l + "\n" + sep;// ajout de la ligne a chaque fois qu'elle a été complétée
        }
        grille = grille + "  A   B   C   D   E   F   G   H";// ajout de l'affichage des colones a la fin du tableau
        return grille; // retourne la grille complétée
    }
    
    public ArrayList<Piece> getPiecesBlanches() { // liste les pièces blanches présentes dans le tableau
        ArrayList<Piece> tab_pb = new ArrayList<Piece>(); // initialisation d'un tableau dynalique vide listant les déplacements possibles
        for (int i = 0; i < tab_p.size(); i++) { // parcour la liste des pièces du tableau
            if (tab_p.get(i).getCouleur() == 'B') { // si la pièces est blanche
                tab_pb.add(tab_p.get(i));// on ajoute la pièce a la liste des pièces blanches
            }
        }
        return tab_pb; // retourne la liste des pièces blanches
    }

    public ArrayList<Piece> getPiecesNoires() {// liste les pièces noires présentes dans le tableau
        ArrayList<Piece> tab_pn = new ArrayList<Piece>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        for (int i = 0; i < tab_p.size(); i++) {// parcour la liste des pièces du tableau
            if (tab_p.get(i).getCouleur() == 'N') {// si la pièces est noire
                tab_pn.add(tab_p.get(i));// on ajoute la pièce a la liste des pièces noires
            }
        }
        return tab_pn;// retourne la liste des pièces noires
    }

    public boolean deplacer(Position from, Position to) {// déplacement d'une pièce
        boolean move = false;// initialisation du mouvement a faux
        ArrayList<Position> deplacementsPossibles = new ArrayList<Position>(this.getCase(from).getDeplacementPossible(this));// initialisation d'un tableau dynalique listant les déplacements possibles
        for (int i = 0; i < deplacementsPossibles.size(); i++) {// parcour du tableau dynamique
            if (deplacementsPossibles.get(i).equals(to)) { // si le déplacement est possible
                move = true;// le mouvement a été fait
                this.tab_p.remove(this.getCase(to));// on retire la pièce sur la case où l'on déplace la pièce
                this.getCase(from).setPosition(to);// on attribue une nouvelle position à la pièce qu'on bouge
                break;// quitte la boucle
            }
        }
        return move; // retourne l'état du mouvement
    }
    
    public Piece getRoi(char color) {// cherche la position d'un roi d'une couleur color
        Piece R = null;// initialise la position du roi a null
        for (int i = 0; i < tab_p.size(); i++) { // parcour du tableau de pièces
            if ((tab_p.get(i).getType() == "roi") && (tab_p.get(i).getCouleur() == color)) { // si le roi correspondant a la couleur
                R = tab_p.get(i);// la position du roi est sortie
            }
        }
        return R;// on retourne la position du roi
    }

    public boolean estEchec(char color) {// vérifie si le roi de couleur color est mis en échec
        Position roi = getRoi(color).getPosition();// récupération de la position du roi
        boolean echec = false; // initialise la situation d'échec a faux
        ArrayList<Piece> tab_pe = new ArrayList<Piece>();// initialisation d'un tableau dynalique vide listant les pièces d'une couleur
        if (color == 'B'){// si le roi est blanc
            tab_pe = getPiecesNoires();// liste des pièces noires
        }
        else{
            tab_pe = getPiecesBlanches();// liste des pièces blanches
        }
        ArrayList<Position> d_possible = new ArrayList<Position>();// initialisation d'un tableau dynalique vide listant les déplacements possibles
        for (int i = 0; i < tab_pe.size(); i++) {// parcour du tableau de pièces de couleur
            d_possible = new ArrayList<Position>(tab_pe.get(i).getDeplacementPossible(this));
            for (int j = 0; j < d_possible.size(); j++) {// parcour du tableau de déplacements possibles des pièces de couleur
                if (d_possible.get(j).equals(roi)){// si le roi est listé dedans
                    echec = true;// il y a échec
                    break;// quitte la boucle
                } 
            }
        }
        return echec;// retourne la situation d'échec
    }

    public boolean end(){// vérifie si le jeu est fini
        if ((getRoi('N')==null)||getRoi('B')==null){// si un des roi est abscent
            return true;// le jeu est fini
        }
        return false;// sinon le jeu continu
    }
}