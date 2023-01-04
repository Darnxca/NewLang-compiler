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
* p = strcpy(malloc(41*sizeof(char)),"ciao");
da = 5;
* p = strcpy(malloc(71*sizeof(char)),"carmine");
}
char*  funz3 (char*  * p ){

// Dichiarazione variabili
char*  d;
int da;
d = strcpy(malloc(41*sizeof(char))," car");
* p = strcpy(malloc(41*sizeof(char)),"ciao");
* p = strcat(* p,d);
}
char*  funz2 (char*  * p ){

// Dichiarazione variabili
char*  d;
int da;
d = strcpy(malloc(41*sizeof(char))," car");
* p = strcpy(malloc(41*sizeof(char)),"ciao");
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
c = strcpy(malloc(11*sizeof(char)),"s");
funz1(&p);
printf("%s %s\n",strcpy(malloc(391*sizeof(char)),"Funz1 eseguita: la variabile 'p' vale: "), p);
printf("%s %s\n",strcpy(malloc(441*sizeof(char)),"Funz2 in esecuzione: la variabile 'c' vale: "), c);
c = funz2(&p);
printf("%s %s %s %s\n",strcpy(malloc(391*sizeof(char)),"Funz2 eseguita: la variabile 'p' vale: "), p, strcpy(malloc(301*sizeof(char)),"mentre la variabile 'c' vale: "), c);
funz3(&p);
printf("%s %s\n",strcpy(malloc(391*sizeof(char)),"Funz3 eseguita: la variabile 'p' vale: "), p);
printf("%s %f\n",strcpy(malloc(51*sizeof(char)),"pow: "), pow(2,2));
}
