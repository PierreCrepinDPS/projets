import MG2D.*;
import MG2D.geometrie.*;
import java.util.ArrayList;

public class MainGraphique {
    public static void dessinerPlateau(Plateau p, Fenetre f){ // affichage du plateau de jeu
        f.effacer(); // on efface la fenêtre
        for (int i=0; i<8; i++){ // dessin du damier
            for (int j=0; j<8; j++){
                if ((i+j)%2==0){ // alternance entre la couleur claire et la couleur foncée pour le damier
                    Rectangle r = new Rectangle(new Couleur(94, 131, 88), new Point(i*100, j*100), 100, 100, true);
                    f.ajouter(r);
                }
                else{
                    Rectangle r = new Rectangle(new Couleur(209, 212, 208), new Point(i*100, j*100), 100, 100, true);
                    f.ajouter(r);
                }
            }
        }
        // initialisation des textures des pièces
        String PiB = "./images/pion_B.png";
        String PiN = "./images/pion_N.png";
        String ToB = "./images/tour_B.png";
        String ToN = "./images/tour_N.png";
        String FoB = "./images/fou_B.png";
        String FoN = "./images/fou_N.png";
        String CaB = "./images/cavalier_B.png";
        String CaN = "./images/cavalier_N.png";
        String DaB = "./images/dame_B.png";
        String DaN = "./images/dame_N.png";
        String RoB = "./images/roi_B.png";
        String RoN = "./images/roi_N.png";
        String piece = "";
        // attribution des textures pour toutes les pièces présentes dans le plateau
        for (int j=0; j<8; j++){ // parcours du damier en hauteur
            for (int i=0; i<8; i++){ // parcours du damier en largeur
                if (p.getCase(i,j)!=null){ // on vérifie que la case ne soit pas vide
                    if (p.getCase(i,j).getCouleur()=='N'){ // si la pièce est une pièce noire alors on lui donne sa texture
                        if (p.getCase(i,j).getType()=="pion"){
                            piece = PiN;
                        }
                        else if (p.getCase(i,j).getType()=="tour"){
                            piece = ToN;
                        }
                        else if (p.getCase(i,j).getType()=="cavalier"){
                            piece = CaN;
                        }
                        else if (p.getCase(i,j).getType()=="fou"){
                            piece = FoN;
                        }
                        else if (p.getCase(i,j).getType()=="dame"){
                            piece = DaN;
                        }
                        else if (p.getCase(i,j).getType()=="roi"){
                            piece = RoN;
                            if (p.estEchec('N')) { // si le roi noir est en échec alors la case devient vert
                                Rectangle r = new Rectangle(new Couleur(Couleur.GREEN), new Point(i*100, j*100), 100, 100, true);
                                f.ajouter(r);
                            }
                            
                        }
                    }
                    else if (p.getCase(i,j).getCouleur()=='B'){ // si la pièce est une pièce blanche alors on lui donne sa texture 
                        if (p.getCase(i,j).getType()=="pion"){
                            piece = PiB;
                        }
                        else if (p.getCase(i,j).getType()=="tour"){
                            piece = ToB;
                        }
                        else if (p.getCase(i,j).getType()=="cavalier"){
                            piece = CaB;
                        }
                        else if (p.getCase(i,j).getType()=="fou"){
                            piece = FoB;
                        }
                        else if (p.getCase(i,j).getType()=="dame"){
                            piece = DaB;
                        }
                        else if (p.getCase(i,j).getType()=="roi"){
                            piece = RoB;
                            if (p.estEchec('B')) { // si le roi blanc est en échec alors la case devient vert
                                Rectangle r = new Rectangle(new Couleur(Couleur.GREEN), new Point(i*100, j*100), 100, 100, true);
                                f.ajouter(r);
                            }
                        }
                    }
                Texture t = new Texture(piece, new Point(i*100, j*100), 100, 100); // on ajoute la pièce sur la fenêtre
                f.ajouter(t); // ajout de la pièce à la fenêtre
                }
            }
        }
        f.rafraichir(); // on rafraichi la fenêtre
    } 

    public static void main(String[] args) {
        Fenetre fenetre = new Fenetre("Jeu d'échec", 800, 800); // création de la fenêtre
        Souris souris = fenetre.getSouris(); // prise en compte des inputs de la souris
        String joueur = "blancs"; // on initialise le joueur a blanc
        Plateau grille = new Plateau(); // initialisation du plateau
        boolean enlever = false; // boolean pour savoir si on retire ou ajoute les cercles de déplacements possibles
        ArrayList<Position> cercles = new ArrayList<Position>(); // création du tableau dynamique de position des coups possibles
        dessinerPlateau(grille, fenetre); // affichage du tableau
        while (!grille.end()){ // tant que les 2 rois sont présents sur le plateau le code tourne
            if (souris.getClicGauche()){ // quand le joueur va cliquer sur la fenêtre
                Point p = new Point(souris.getPosition()); // on prend les coordonées du clic
                int x=p.getX()/100; // division par 10 pour avoir la place dans la grille en largeur
                int y=p.getY()/100;// division par 10 pour avoir la place dans la grille en hauteur
                if (enlever==true){ // si les cercles de coups possible sont affichés
                    if (cercles.size()!=0){ // on vérifie que des coups son possibles
                        for (int k=1; k<cercles.size(); k++){ // parcours du tableau de coups possible
                            int tabx = cercles.get(k).getX(); // on prend le x
                            int taby = cercles.get(k).getY(); // on prend le y
                            // on définit les cercles sur les positions des coups
                            Cercle c = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+50,(taby*100)+50), 48);
                            Cercle c1 = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+51,(taby*100)+50), 48);
                            Cercle c2 = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+51,(taby*100)+51), 48);
                            Cercle c3 = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+50,(taby*100)+51), 48);
                            // on supprime les cercles qui sont présents
                            fenetre.supprimer(c); fenetre.supprimer(c1); fenetre.supprimer(c2); fenetre.supprimer(c3);
                        }
                        if (cercles.get(0)!=null){ // on vérifie que la pièce qui doit être déplacé n'est pas une case vide
                            if ((joueur=="blancs") && (grille.getCase(cercles.get(0)).getCouleur()=='B')){ // on vérifie que c'est aux blancs de jouer et que c'est une pièce blanche qui est sélectionnée
                                if (grille.deplacer(cercles.get(0), new Position(x,y))){ // si le déplacement de la pièce est possible
                                    dessinerPlateau(grille, fenetre); // on redessine le tableau
                                    joueur="noirs"; // c'est aux noirs de jouer
                                }
                            }
                            else if ((joueur=="noirs") && (grille.getCase(cercles.get(0)).getCouleur()=='N')){ // on vérifie que c'est aux noirs de jouer et que c'est une pièce noire qui est sélectionnée
                                if (grille.deplacer(cercles.get(0), new Position(x,y))){ // si le déplacement de la pièce est possible
                                    dessinerPlateau(grille, fenetre); // on redessine le tableau
                                    joueur="blancs"; // c'est aux noirs de jouer
                                }
                            }
                        }
                    }
                    enlever=false; // on a enlevé les coups possibles donc on les ajoutera au prochain clic
                }
                else if (enlever==false){ // si on ne doit pas enlever les coups possibles
                    if (grille.getCase(x,y)!=null){ // on vérifie que la case n'est pas vide
                        ArrayList<Position> tab = new ArrayList<Position>(grille.getCase(x,y).getDeplacementPossible(grille)); // on initialise une liste dynamique avec les déplacements possible
                        cercles = new ArrayList<Position>(); // on initialise un tableau dynamique pour stocker les coups possibles
                        cercles.add(new Position(x, y)); // on ajoute la position de la pièce de base pour la stocker en 1ere position
                        for (int i=0; i<tab.size(); i++){ // on parcours le tableau de coups possible
                            int tabx = tab.get(i).getX(); // on prend le x
                            int taby = tab.get(i).getY(); // on prend le y
                            // on définit les cercles sur les positions des coups
                            Cercle c = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+50,(taby*100)+50), 48);
                            Cercle c1 = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+51,(taby*100)+50), 48);
                            Cercle c2 = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+51,(taby*100)+51), 48);
                            Cercle c3 = new Cercle(new Couleur(Couleur.ROUGE), new Point((tabx*100)+50,(taby*100)+51), 48);
                            // on ajoute les cercles qui sont présents
                            fenetre.ajouter(c); fenetre.ajouter(c1); fenetre.ajouter(c2); fenetre.ajouter(c3);
                            // on ajoute la position a la liste des coups
                            cercles.add(new Position(tabx, taby));
                        } 
                    }
                    enlever=true; // on a ajouté les cercles donc le prochain clic sera pour retirer les cercles rouges
                }
                fenetre.rafraichir(); // on rafraichi la fenêtre
            }
            try{Thread.sleep(50);} // on attend un petit temps avant de reprendre une commande
            catch (Exception e) {
            } 
        }  
    }
}
