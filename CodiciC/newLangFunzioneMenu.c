#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void sottrai(float a,float b,float *result);
float somma(float a,float b,float *result);
int dividi(float a,float b,float *result);
void stampaMenu();
void moltiplica(float a,float b,float *result);
int main();

// dichiarazione variabili globali

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
void stampaMenu ( ){

// Dichiarazione variabili
char*  p;
printf("%s\n",strcpy(malloc(281*sizeof(char)),"Che operazione vuoi eseguire"));
printf("%s\n",strcpy(malloc(91*sizeof(char)),"2 - Somma"));
printf("%s\n",strcpy(malloc(151*sizeof(char)),"3 - Sottrazione"));
printf("%s\n",strcpy(malloc(191*sizeof(char)),"4 - Moltiplicazione"));
printf("%s\n",strcpy(malloc(131*sizeof(char)),"5 - Divisione"));
printf("Scegli loperazione da effettuare\n");
scanf("%ms", &p);
}
float somma (float a, float b, float * result ){

// Dichiarazione variabili
float ldl;
float wdw;
int plal;
int x;
float ez;
float l;
plal = 2;
x = 3;
* result = (a + b);
return 2.2;
}
void sottrai (float a, float b, float * result ){

// Dichiarazione variabili
* result = (a - b);
}
void moltiplica (float a, float b, float * result ){

// Dichiarazione variabili
* result = (a * b);
}
int dividi (float a, float b, float * result ){

// Dichiarazione variabili
if((b == 0)){

// Dichiarazione variabili
* result = 0;
return 0;
}
else{

// Dichiarazione variabili
* result = (a / b);
return 1;
}
}
int main ( ){

// Dichiarazione variabili
int continuaScelta;
int casa;
float num1;
float num2;
int sceltaOp;
char*  ciao;
float result;
continuaScelta = 1;
num1 = 0;
num2 = 0.0;
ciao = strcpy(malloc(91*sizeof(char)),"CISIDIIDI");
casa = (2 + (2 + (2.2 * (2 /  -1))));
stampaMenu();
printf("Scegli l'operazione da effettuare\n");
scanf("%d", &sceltaOp);
while(((continuaScelta != 0) && (sceltaOp <= 5))){

// Dichiarazione variabili
printf("Inserisci il primo numero\n");
scanf("%f", &num1);
printf("Inserisci il secondo numero\n");
scanf("%f", &num2);
if((sceltaOp == 2)){

// Dichiarazione variabili
somma(num1, num2, &result);
}
if((sceltaOp == 3)){

// Dichiarazione variabili
sottrai(num1, num2, &result);
}
if((sceltaOp == 4)){

// Dichiarazione variabili
moltiplica(num1, num2, &result);
}
if((sceltaOp == 5)){

// Dichiarazione variabili
if( !dividi(num1, num2, &result)){

// Dichiarazione variabili
printf("%s",strcpy(malloc(501*sizeof(char)),"Errore operazione di divisione non effettuabile!!!"));
}
}
printf("%s %f %f %s %f",strcpy(malloc(401*sizeof(char)),"Il risultato dell'operazione scelta tra "), num1, num2, strcpy(malloc(11*sizeof(char)),"è"), result);
printf("Vuoi continuare? (0: NO, 1: SI)\n");
scanf("%d", &continuaScelta);
while(((continuaScelta != 0) && (continuaScelta != 1))){

// Dichiarazione variabili
printf("Inserire 0 per terminare, 1 per continuare!\n");
scanf("%d", &continuaScelta);
}
if((continuaScelta == 1)){

// Dichiarazione variabili
stampaMenu();
printf("Scegli loperazione da effettuare\n");
scanf("%d", &sceltaOp);
}
}
}
