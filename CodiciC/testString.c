#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void test();
char*  funz1(char*  *p);
char*  funz2(char*  *p);
char*  funz3(char*  *p);

// dichiarazione variabili globali

// inizializzazione delle variabili

//-----------Implementazione funzioni-----------
char*  funz1 (char*  * p ){

// Dichiarazione variabili
int da;
* p = strcpy(malloc(5*sizeof(char)),"ciao");
da = 5;
* p = strcpy(malloc(8*sizeof(char)),"carmine");
}
char*  funz3 (char*  * p ){

// Dichiarazione variabili
char*  d;
int da;
d = strcpy(malloc(5*sizeof(char))," car");
* p = strcpy(malloc(5*sizeof(char)),"ciao");
* p = strcat(* p,d);
}
char*  funz2 (char*  * p ){

// Dichiarazione variabili
char*  d;
int da;
d = strcpy(malloc(5*sizeof(char))," car");
* p = strcpy(malloc(5*sizeof(char)),"ciao");
* p = d;
return d;
}
int main(int argc, char *argv[]){
test();
}
void test ( ){

// Dichiarazione variabili
char*  c;
char*  p;
c = strcpy(malloc(2*sizeof(char)),"s");
funz1(&p);
printf("%s %s\n",strcpy(malloc(40*sizeof(char)),"Funz1 eseguita: la variabile 'p' vale: "), p);
fflush(stdout);
printf("%s %s\n",strcpy(malloc(45*sizeof(char)),"Funz2 in esecuzione: la variabile 'c' vale: "), c);
fflush(stdout);
c = funz2(&p);
printf("%s %s %s %s\n",strcpy(malloc(40*sizeof(char)),"Funz2 eseguita: la variabile 'p' vale: "), p, strcpy(malloc(31*sizeof(char)),"mentre la variabile 'c' vale: "), c);
fflush(stdout);
funz3(&p);
printf("%s %s\n",strcpy(malloc(40*sizeof(char)),"Funz3 eseguita: la variabile 'p' vale: "), p);
fflush(stdout);
printf("%s %f\n",strcpy(malloc(6*sizeof(char)),"pow: "), pow(2,2));
fflush(stdout);
printf("Inserisci una stringa in 'p'\n");
fflush(stdout);
p = malloc(20*sizeof(char));
scanf("%s", p);
printf("%s %s\n",strcpy(malloc(15*sizeof(char)),"Hai inserito: "), p);
fflush(stdout);
}
