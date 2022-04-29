public class Position {
    private Integer x;// définion d'un entier x
    private Integer y;// définion d'un entier y
    private String[] lettres={"A","B","C","D","E","F","G","H"};// définion des lettres de l'échequier

    public Position(){// initialisation d'une position par défault en 0,0
        this.x=0;
        this.y=0;
    }

    public Position(Position xy){// initialisation d'une position depuis une position xy
        this.x = xy.x;
        this.y = xy.y;
    }

    public Position(Integer x, Integer y){// initialisation d'une position depuis deux entiers x et y
        if ((x>=0)&&(y>=0)&&(x<=7)&&(y<=7)){// si x et y sont compris entre 0 et 7
            this.x = x;
            this.y = y;
        }
        else{
            System.out.println("Problème de position");// si erreur de x ou y alors retourne une erreur
        }
    }

    public Position(String p){// initialisation d'une position a partir d'une chaine de charactère p
        String[] pos = p.split("");// divise la chaine de charactère
        Integer valy;// initialisation d'une valy
        for (int i=0; i<lettres.length; i++){// parcours des lettres de l'échéquier
            if (pos[0].equals(lettres[i])){// si la lettre dans la chaine de charactère p appartient aux lettres de l'échéquier
                this.x= i;// attribution du x
            }
        }
        valy = Integer.parseInt(pos[1]);// attribution de la valeur de valy
        if ((valy>=0)&&(valy<=7)){// si vay est compris entre 0 et 7
            this.y = valy;
        }
        else if (this.x==null){ // sinon retourne une erreur
            System.out.println("Problème de position");
        }
    }

    public int getX(){// récupération de la valeur x de la position
        return this.x;
    }

    public int getY(){// récupération de la valeur y de la position
        return this.y;
    }

    public void setX(Integer x){// définit la valeur x de la position
        if ((x<=0)&&(x>=7)){// si x est compris entre 0 et 7
            this.x=x;
        }
    }

    public void setY(Integer y){// définit la valeur y de la position
        if ((y<=0)&&(y>=7)){// si y est compris entre 0 et 7
            this.y=y;
        }
    }

    public boolean equals(Object o){// fonction de vérification pour savoir si deux positions sont les mêmes
        if (o == this){// vérification que les deux positions sont les mêmes
            return true;
        }
        if ((o instanceof Position)==false){// vérifie que l'objet est une position
            return false;
        }
        Position p = (Position)(o);//transformation de l'objet o en position
        if(this.x!=(p.x)){ //vérification que le x des deux positions est le même
            return false;
        }   
        if(this.y!=(p.y)){// vérification que le y des deux positions est le même
            return false;
        }
        return true;// si tout est valide alors les deux positions sont les mêmes
    }

    public String toString() {// traduction de la position en string
        String pos = lettres[this.x]+(Integer.toString(this.y));// affichage du x en lettre et du y
        return pos;// retourne la position
    }
    
    public static void main(String[] args) {
        Position pos = new Position("A1");
        System.out.println(pos.x+","+pos.y);
        System.out.println(pos);
    }
}