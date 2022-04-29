from tkinter import *

"""
B=Blanc
N=Noir

P=Pion
T=Tour
C=Cavalier
F=Fou
Q=Dame
K=Roi

"""
import numpy

tableau=numpy.array([
["TN","CN","FN","QN","KN","FN","CN","TN"],
["PN","PN","PN","PN","PN","PN","PN","PN"],
["  ","  ","  ","  ","  ","  ","  ","  "],
["  ","  ","  ","  ","  ","  ","  ","  "],
["  ","  ","  ","  ","  ","  ","  ","  "],
["  ","  ","  ","  ","  ","  ","  ","  "],
["PB","PB","PB","PB","PB","PB","PB","PB"],
["TB","CB","FB","QB","KB","FB","CB","TB"]])

def deplacement(x,y,a,b): #def fonction de deplacement
    
    if tableau[x][y]=="  ":
        print("Case vide")
    elif (tableau[x][y]=="PB" or tableau[x][y]=="PN"):
        pion(x,y,a,b)
    elif (tableau[x][y]=="TB" or tableau[x][y]=="TN"):
        tour(x,y,a,b)
    elif (tableau[x][y]=="CB" or tableau[x][y]=="CN"):
        cavalier(x,y,a,b)
    elif (tableau[x][y]=="FB" or tableau[x][y]=="FN"):
        fou(x,y,a,b)
    elif (tableau[x][y]=="QB" or tableau[x][y]=="QN"):
        dame(x,y,a,b)
    elif (tableau[x][y]=="KB" or tableau[x][y]=="KN"):
        roi(x,y,a,b)


def pion(x,y,a,b): #def pions en ligne droite
    if tableau[x][y]=="PB":
        c=manger(x,y,a,b)
        if x-a==1 and abs(y-b)==1:
            if c==True:
                tableau[x][y]="  "
                tableau[a][b]="PB"
        elif x==6 and y==b: #def regle 2 cases pour 1er mouvement
            if x-a==1 or x-a==2: #regle mouvement pion
                if tableau[a][b]=="  ":
                    tableau[x][y]="  "
                    tableau[a][b]="PB"
        elif x-a==1 and y==b: #regle mouvement pion
            if tableau[a][b]=="  ":
                tableau[x][y]="  "
                tableau[a][b]="PB"
    elif tableau[x][y] == "PN":
        if a-x==1 and abs(y-b)==1:
            c=manger(x,y,a,b)
            if c==True:
                tableau[x][y]="  "
                tableau[a][b]="PN"
        if x==1 and y==b: #def regle 2 cases pour 1er mouvement
           if a-x==1 or a-x==2: #regle mouvement pion
            if tableau[a][b]=="  ":
                tableau[x][y]="  "
                tableau[a][b]="PN" 
        elif a-x==1 and y==b: #regle mouvement pion
            if tableau[a][b]=="  ":
                tableau[x][y]="  "
                tableau[a][b]="PN"
    #print(tableau)

def tour(x,y,a,b): #def fonction deplacement tour
    if x!=a and y==b:
        r=obs_verticale(x,y,a,b)
    if x==a and y!=b:
        r=obs_horizontal(x,y,a,b)
    if ((x!=a and y==b) or (x==a and y!=b)) and r==True: #regle deplacement tour
        if tableau[x][y]=="TB":
            tableau[x][y]="  "
            tableau[a][b]="TB"
        elif tableau[x][y]=="TN":
            tableau[x][y]="  "
            tableau[a][b]="TN"
    #print(tableau)

def cavalier(x,y,a,b): #def fonction deplacement cavalier
    if (abs(x-a)==2 and abs(y-b)==1) or (abs(x-a)==1 and abs(y-b)==2): #regle deplacement cavalier
        c=manger(x,y,a,b)
        if tableau[x][y]=="CB":
            if tableau[a][b]=="  " or c==True:
                tableau[x][y]="  "
                tableau[a][b]="CB"
        elif tableau[x][y]=="CN" or c==True:
            if tableau[a][b]=="  ":
                tableau[x][y]="  "
                tableau[a][b]="CN"
    #print(tableau)

def fou(x,y,a,b): #def fonction deplacement fou
    if abs(x-a)==abs(y-b): #regle deplacement fou
        r=obs_diagonale(x,y,a,b)
        if r==True:
            if tableau[x][y]=="FB":
                tableau[x][y]="  "
                tableau[a][b]="FB"
            elif tableau[x][y]=="FN":
                tableau[x][y]="  "
                tableau[a][b]="FN"
    #print(tableau)

def dame(x,y,a,b): #def fonction deplacement dame
    if x!=a and y==b:
        r=obs_verticale(x,y,a,b)
    if x==a and y!=b:
        r=obs_horizontal(x,y,a,b)
    if abs(x-a)==(y-b):
        rd=obs_diagonale(x,y,a,b)
    if (abs(x-a)==abs(y-b) and rd==True) or (((x!=a and y==b) or (x==a and y!=b))and r==True): #regle deplacement dame
        if tableau[x][y]=="QB":
            tableau[x][y]="  "
            tableau[a][b]="QB"
        elif tableau[x][y]=="QN":
            tableau[x][y]="  "
            tableau[a][b]="QN"
    #print(tableau)

def roi(x,y,a,b): #def fonction deplacement roi
    c=manger(x,y,a,b)
    if ((abs(x-a)==1 and abs(y-b)==1) or ((abs(x-a)==1 and y==b) or (x==a and abs(y-b)==1)))and c==True: #regle deplacement roi
            if tableau[x][y]=="KB":
                if tableau[a][b]=="  " or c==True:
                    tableau[x][y]="  "
                    tableau[a][b]="KB"
            elif tableau[x][y]=="KN":
                if tableau[a][b]=="  " or c==True:
                    tableau[x][y]="  "
                    tableau[a][b]="KN"
    #print(tableau)

def manger(x,y,a,b): #def fonction manger les pions adverses
    couleur1=tableau[x][y]
    couleur2=tableau[a][b]
    if couleur1[1]==couleur2[1]: #vérifie que les couleurs sont différentes
        c=False
    if couleur1[1]!=couleur2[1]:
        c=True
    return c

def obs_horizontal(x,y,a,b):
    couleur1=tableau[x][y]
    couleur2=tableau[a][b]
    if x==a and y!=b:
        k=y-b
        if abs(k)==1:
            if tableau[a][b]=="  " or couleur1[1]!=couleur2[1]:
                r=True
            else:
                r=False
        else:
            if k<0:
                for i in range(1,abs(k)):
                    if tableau[x][y+i]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                    else:
                        r=False
                        break
            elif k>0:
                for i in range(1,k):
                    if tableau[x][y-i]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                    else:
                        r=False
                        break
    return r

def obs_verticale(x,y,a,b):
    couleur1=tableau[x][y]
    couleur2=tableau[a][b]
    if x!=a and y==b:
        k=x-a
        if abs(k)==1:
            if tableau[a][b]=="  " or couleur1[1]!=couleur2[1]:
                r=True
            else:
                r=False
        else:
            if k<0:
                for i in range(1,abs(k)):
                    if tableau[x+i][y]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                    else:
                        r=False
                        break
            elif k>0:
                for i in range(1,k):
                    if tableau[x-i][y]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                    else:
                        r=False
                        break
    return r

def obs_diagonale(x,y,a,b):
    couleur1=tableau[x][y]
    couleur2=tableau[a][b]
    k=x-a
    l=y-b
    if abs(k)==1 and abs(l)==1:
        if tableau[a][b]=="  " or couleur1[1]!=couleur2[1]:
            r=True
        else:
            r=False
    else:
        if abs(x-a)==abs(y-b):
            if k<0 and l<0:
                m=1
                for i in range(1,abs(k)):
                    if tableau[x+i][y+m]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                        m=m+1
                    else:
                        r=False
                        break
            elif k<0 and l>0:
                m=1
                for i in range(1,abs(k)):
                    if tableau[x+i][y-m]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                        m=m+1
                    else:
                        r=False
                        break
            elif k>0 and l<0:
                m=1
                for i in range(1,abs(k)):
                    if tableau[x-i][y+m]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                        m=m+1
                    else:
                        r=False
                        break
            elif k>0 and l>0:
                m=1
                for i in range(1,abs(k)):
                    if tableau[x-i][y-m]=="  " and (tableau[a][b]=="  " or couleur1[1]!=couleur2[1]):
                        r=True
                        m=m+1
                    else:
                        r=False
                        break
    return r



# -----   PARTIE GRAPHIQUE   ----- #

def clicGauche(event) :
    # La variable cadre provient du programme principal
    global c, d, a, b

    
    # On récupère les coordonnées du clic.
    x = event.x
    y = event.y
    # On fixe la taille du rayon du cercle qui sera créé
    # Affichage d'un cercle bleu à l'endroit cliqué
    # et affichage dans le terminal des infos
    vary=(int)('{}'.format(x))
    varx=(int)('{}'.format(y))
    for i in range(0,8):
        if i*80<vary<(i+1)*80:
            vary=i
            break
    for i in range(0,8):
        if i*80<varx<(i+1)*80:
            varx=i
            break
    if a==-1 & b==-1:
        a=varx
        b=vary
        damier.create_rectangle(b*80, a*80, (b+1)*80, (a+1)*80, fill = "grey",outline="")
        damier.create_text(40+b*80,40+a*80, text=tableau[a,b], fill="black")
    else:
        c=varx
        d=vary
    if c!=-1:
        deplacement(a,b,c,d)
        couleur="white"
        t_couleur="black"
        for j in range(0,8):
            for i in range(0,8):
                damier.create_rectangle(0+i*80,0+j*80,80+i*80,80+j*80,fill=couleur,outline="",activefill="grey")#credit by maxime pour outline & activefill
                damier.create_text(40+i*80,40+j*80, text=tableau[j,i], fill=t_couleur)
                if couleur=="white":
                    couleur="black"
                    t_couleur="white"
                else:
                    couleur="white"
                    t_couleur="black"
            if couleur=="white":
                    couleur="black"
                    t_couleur="white"
            else:
                couleur="white"
                t_couleur="black"
        damier.pack()
        a=-1
        b=-1
        c=-1
        d=-1

    
a=-1
b=-1
c=-1
d=-1
fenetre=Tk()
fenetre.title("Jeu d'Echec")
fenetre.resizable(width=False, height=False)
fenetre.positionfrom("user")
damier=Canvas(fenetre, width=640, height=640)
couleur="white"
t_couleur="black"
for j in range(0,8):
    for i in range(0,8):
        damier.create_rectangle(0+i*80,0+j*80,80+i*80,80+j*80,fill=couleur,outline="",activefill="grey")#credit by maxime pour outline & activefill
        damier.create_text(40+i*80,40+j*80, text=tableau[j,i], fill=t_couleur)
        if couleur=="white":
            couleur="black"
            t_couleur="white"
        else:
            couleur="white"
            t_couleur="black"
    if couleur=="white":
            couleur="black"
            t_couleur="white"
    else:
        couleur="white"
        t_couleur="black"
damier.pack()
damier.bind("<Button-1>", clicGauche)
fenetre.mainloop()
