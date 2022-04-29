import java.util.ArrayList;

public abstract class Piece {
    private char couleur; // définition de la couleur de la pièce
    private Position position; // définition de la position de la pièce

    public Piece(){ // initialisation d'une pièce blanche en A2 par défault
        this.couleur='B';
        this.position= new Position("A2");
    }

    public Piece(Piece p){ // initialisation d'une pièce a partir d'une autre pièce
        this.couleur= p.couleur;
        this.position= p.position;
    }

    public Piece(char c, Integer x, Integer y){ // initialisation d'une pièce avec sa couleur et sa position en x et y
        if ((c=='B')||(c=='N')){ // vérification que la pièce est soit blanche soit noire
            this.couleur = c;
        }
        else{
            System.out.println("Erreur de couleur");// retour d'une erreur si la pièce n'a pas la bonne couleur
        }
        this.position= new Position(x,y); // définition de la position de la pièce en x et y
    }

    public Piece(char c, Position xy){ // initialisation d'une pièce à partir de sa couleur est d'une position xy
        if ((c=='B')||(c=='N')){ // vérification que la couleur de la pièce est soit blanche soit noire
            this.couleur = c;
        }
        else {
            System.out.println("Erreur de couleur"); // retour d'une erreur si la pièce n'a pas la bonne couleur
        }
        this.position= new Position(xy); // initialisation de la position de la pièce depuis une position xy
    }
    
    public Piece(char c, String xy){ // initialisation d'une pièce a partir de sa couleur et de la position de la pièce en string (ex: A2)
        if ((c=='B')||(c=='N')){// vérification que la couleur de la pièce est soit blanche soit noire
            this.couleur = c;
        }
        else {
            System.out.println("Erreur de couleur");// retour d'une erreur si la pièce n'a pas la bonne couleur
        }
        this.position=new Position(xy); // initialisation de la position de la pièce depuis un string xy
    }

    public abstract String getType(); // déclaration de l'appel du type de la pièce pour une utilité dans un autre code du projet

    public char getCouleur(){ //savoir la couleur d'une pièce désignée
        return this.couleur; // retourne la couleur de la pièce
    }
    
    public void setCouleur(char c) { // définir une nouvelle couleur à la pièce désignée
        if ((c == 'B') || (c == 'N')) { // vérification que la couleur de la pièce est soit blanche soit noire
            this.couleur = c; // nouvelle attribution de la couleur
        }
        else {
            System.out.print("Erreur de couleur");// retour d'une erreur si la pièce n'a pas la bonne couleur
        }
    }

    public Position getPosition(){ // connaitre la position d'une pièce désignée
        return this.position; // retourne la position de la pièce
    }

    public void setPosition(Integer x, Integer y) { // définir une nouvelle position à partir de deux entiers x et y à un pièce désignée
        this.position = new Position(x,y); // attribution de la nouvelle position à la pièce
    }

    public void setPosition(Position xy) { // définir une nouvelle position à parti d'une position xy à un pièce désignée
        this.position = new Position(xy);// attribution de la nouvelle position à la pièce
    }

    public void setPosition(String xy) { // définir une nouvelle position à partir d'une chaine de charactère xy à un pièce désignée
        this.position = new Position(xy);// attribution de la nouvelle position à la pièce
    }

    public boolean equals(Object o){// fonction de vérification pour savoir si deux pièces sont les mêmes
        if (o == this){ // vérification que les deux pièces sont les mêmes
            return true;
        }
        if ((o instanceof Piece)==false){ // vérifie que l'objet est une pièce
            return false;
        }
        Piece p = (Piece)(o); //transformation de l'objet o en Pièce
        if(this.couleur!=p.couleur){ //vérification que la couleur des deux pièces est la même
            return false;
        }
        if(this.position!=p.position){ // vérification que la position des deux pièces est la même
            return false;
        }
        return true; // si tout est valide alors les deux pièces sont les mêmes
    }

    public String toString(){ // sortie de la position de la pièce sous forme de phrase
        String color;
        if ((this.couleur=='B')){ // vérification de la couleur de la pièce
            color="blanc";
        }
        else{
            color="noir";
        }
        return this.getType() +" "+ color + " en " + this.position.toString(); // retourne la phrase avec le type et la couleur de la pièce
    }

    public abstract ArrayList<Position> getDeplacementPossible(Plateau p); // déclaration de l'appel des déplacements de la pièce pour une utilité dans un autre code du projet
}