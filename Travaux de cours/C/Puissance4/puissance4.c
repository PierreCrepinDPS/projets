#include <stdio.h>
#include <stdlib.h>
#define ligne 6
#define colonne 7
typedef char tableau[ligne][colonne];


char initGrille(tableau g){
    for (int i = 0; i < ligne; i ++){
        for (int j = 0; j < colonne; j ++){
            g[i][j] = ' ';
        }
    }
}

void afficheGrille(tableau g){
    printf("    0   1   2   3   4   5   6\n");
    for (int i = 0; i < ligne; i ++){
        printf("%d ", i+1);
        for (int j = 0; j < colonne; j ++){
            printf("| %c ", g[i][j]);
        }
        printf("|");
        printf("\n ");
        for (int j=0; j<(ligne); j++){
            printf("-----");
        }
        printf("\n");
    }
}

int grillePleine(tableau g){
    int i, j;
    for (i = 0; i < ligne; i ++){
        for (j = 0; j < colonne; j ++){
            if (g[i][j]==' '){
                return 0;
            }
        }
    }
    return 1;
}

int jeuFini( tableau g){
    int victoire = 0;
    for( int i = 0 ; i < ligne; i++ ){
        for( int j = 0; j < colonne; j++ ){
            if ( (g[i][j]=='X') && (g[i][j-1]=='X') && (g[i][j-2]=='X') && (g[i][j-3]=='X')){
                printf( "Le joueur 1 a gagné !\n" );
                victoire = 1;
            }
            else if ((g[i][j]=='O') && (g[i][j-1]=='O') && (g[i][j-2]=='O') && (g[i][j-3]=='O')){
                printf( "Le joueur 2 a gagné !\n" );
                victoire = 1;
            }
            if ((g[i][j]=='X') && (g[i+1][j]=='X') && (g[i+2][j]=='X') && (g[i+3][j]=='X')){
                printf( "Le joueur 1 a gagné !\n" );
                victoire = 1;
            }
            else if ((g[i][j]=='O') && (g[i+1][j]=='O') && (g[i+2][j]=='O') && (g[i+3][j]=='O')){
                printf( "Le joueur 2 a gagné !\n" );
                victoire = 1;
            }
            if ((g[i][j]=='X') && (g[i+1][j+1]=='X') && (g[i+2][j+2]=='X') && (g[i+3][j+3] == 'X')){
                printf( "Le joueur 1 a gagné !\n" );
                victoire = 1;
            }
            else if ((g[i][j]=='O') && (g[i+1][j+1]=='O') && (g[i+2][j+2]=='O') && (g[i+3][j+3]=='O')){
                printf( "Le joueur 2 a gagné !\n" );
                victoire = 1; 
            }
            if ((g[i][j]=='X') && (g[i+1][j-1]=='X') && (g[i+2][j-2]=='X') && (g[i+3][j-3]=='X')){
                printf( "Le joueur 1 a gagné !\n" );
                victoire = 1;
            }
            else if ((g[i][j]=='O') && (g[i+1][j-1]=='O') && (g[i+2][j-2]=='O') && (g[i+3][j-3]=='O')){
                printf( "Le joueur 2 a gagné !\n" );
                victoire = 1; 
            }
        }
    }
    if ((grillePleine(g)==1)&&(victoire==0)){
        printf( "Egalité !\n" );
        victoire = 1;  
    }
    return victoire;
}

void sauvegarde(int joueur, tableau g, char nf[]){
    FILE* f1= fopen(nf, "w");
    fwrite(g,1,42,f1);
    for (int i=0; i<ligne; i++){
        for (int j=0; j<colonne; j++){
            printf("%d",g[i][j]);
        }
    }
    fclose(f1);
    FILE* f2= fopen(nf, "a");
    if (joueur == 1){
        fwrite( "1", 1, 1, f2 );
    }
    else{
        fwrite( "2", 1, 1, f2 );
    }
    fclose(f2);
    printf("\nSauvegarde effectuée !\n");
}

void lecturePartie( int joueur, tableau g, char nf[] ){
    FILE* sg = fopen( nf, "r" );
    for ( int i = 0; i < ligne; i++ ){
        for ( int j = 0; j < colonne; j++ ){
            fscanf( sg, "%c", &g[i][j] );
        }
    }
    fscanf( sg, "%i", &joueur );
} 

void ajouteJeton(tableau g, int joueur){
    int coll;
    do{
        printf("Entrez le numéro de la colonne pour placer votre pion (8 pour sauvegarder, 9 pour quitter):\n");
        scanf("%d", &coll);
        if (coll==8){
            char nf[] = "";
            printf( "Indiquez le nom de la sauvegarde :\n" );
            scanf( "%s", &nf );
            sauvegarde( joueur, g, nf );
        }
        if (coll==9){
            exit;
        }
        else if ((coll < 0)||(coll>6)){
            printf("Le numéro de colonne est incorrecte\n");
        }
        else{
            if (g[0][coll] != ' '){
                afficheGrille(g);
                printf("La colonne %d est pleine\n", coll);
            }  
        }  
    }
    while (((coll<0) || (coll>6)) || (g[0][coll]!=' '));
    int i = ligne - 1;
    int depot = 0;
    while ((i>-1)&&(depot==0)){
        if (g[i][coll]==' '){
            if (joueur==1){
                g[i][coll] = 'X';
            }
            else if (joueur==2){
                g[i][coll] = 'O';
            }
            afficheGrille(g);
            depot = 1;
        }
        else{
            i--;
        }
    }
}

int main(){
    tableau grille;
    initGrille(grille);
    int victoire=0;
    int c;
    int joueur=1;
    printf("Tapez 1 pour démarrer une nouvelle partie \n");
    printf("Tapez 2 pour charger une partie sauvegardée \n");
    printf("Tapez 3 pour quitter le jeu \n");
    scanf("%d", &c);
    if ((c==1)||(c==2)){
        if ((c==2)){
            char nf[] = "";
        printf( "Indiquez le nom du fichier a charger :\n" );
        scanf( "%s", &nf );
        lecturePartie( joueur, grille, nf );
        }
        afficheGrille(grille);
        while (victoire==0){
            if (victoire == 0)
                ajouteJeton(grille, joueur);
                afficheGrille(grille);
                victoire = jeuFini(grille);
            if (joueur == 2){
                joueur = 1;
            }
            else{
                joueur = 2;
            }
        }
    }
    else if(c==3){
        printf("Merci d'avoir joué !\nA bientôt !");
    }
}