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

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
void stampaMenu ( ){

// Dichiarazione variabili
printf("%s\n",strcpy(malloc(29*sizeof(char)),"Che operazione vuoi eseguire"));
fflush(stdout);
printf("%s\n",strcpy(malloc(10*sizeof(char)),"2 - Somma"));
fflush(stdout);
printf("%s\n",strcpy(malloc(16*sizeof(char)),"3 - Sottrazione"));
fflush(stdout);
printf("%s\n",strcpy(malloc(20*sizeof(char)),"4 - Moltiplicazione"));
fflush(stdout);
printf("%s\n",strcpy(malloc(14*sizeof(char)),"5 - Divisione"));
fflush(stdout);
}
float somma (float a, float b, float * result ){

// Dichiarazione variabili
float l;
float ldl;
float wdw;
float ez;
int plal = 2;
int x = 3;
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
int sceltaOp;
float result;
int continuaScelta = 1;
float num1 = 0;
float num2 = 0.0;
char*  ciao = strcpy(malloc(10*sizeof(char)),"CISIDIIDI");
int casa = (2 + (2 + (2.2 * (2 /  -1))));
stampaMenu();
printf("Scegli l'operazione da effettuare\n");
fflush(stdout);
scanf("%d", &sceltaOp);
while(((continuaScelta != 0) && (sceltaOp <= 5))){

// Dichiarazione variabili
printf("Inserisci il primo numero\n");
fflush(stdout);
scanf("%f", &num1);
printf("Inserisci il secondo numero\n");
fflush(stdout);
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
printf("%s",strcpy(malloc(51*sizeof(char)),"Errore operazione di divisione non effettuabile!!!"));
fflush(stdout);
}
}
printf("%s %f %f %s %f",strcpy(malloc(41*sizeof(char)),"Il risultato dell'operazione scelta tra "), num1, num2, strcpy(malloc(2*sizeof(char)),"è"), result);
fflush(stdout);
printf("Vuoi continuare? (0: NO, 1: SI)\n");
fflush(stdout);
scanf("%d", &continuaScelta);
while(((continuaScelta != 0) && (continuaScelta != 1))){

// Dichiarazione variabili
printf("Inserire 0 per terminare, 1 per continuare!\n");
fflush(stdout);
scanf("%d", &continuaScelta);
}
if((continuaScelta == 1)){

// Dichiarazione variabili
stampaMenu();
printf("Scegli loperazione da effettuare\n");
fflush(stdout);
scanf("%d", &sceltaOp);
}
}
}
