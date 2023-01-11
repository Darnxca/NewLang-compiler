#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void test();
char*  compareString(char*  a,char*  b,int *isEq, int *isGt, int *isLt, int *isGe, int *isLe, int *isNe);
char*  funz1(char*  *p);
char*  funz2(char*  *p);
char*  funz3(char*  *p);
// inizializzazione delle variabili globali
char*  stringa;
char*  dkdkd;

void initialize_global() {
	dkdkd = "clclc";
}

//-----------Implementazione funzioni-----------
char* funz1 (char* *p ){
	// Dichiarazione variabili
	int da;
	// Assegnazione variabili

	// Inizio Statement
	*p = "ciao";
	da = 5;
	*p = "carmine";

}
char* funz3 (char* *p ){
	// Dichiarazione variabili
	char*  d;
	int da;
	// Assegnazione variabili
	d = " car";

	// Inizio Statement
	*p = "ciao";
	*p = strcat(*p=strdup(*p) , d=strdup(d));

}
char* funz2 (char* *p ){
	// Dichiarazione variabili
	char*  d;
	int da;
	// Assegnazione variabili
	d = " car";

	// Inizio Statement
	*p = "ciao";
	*p = d;
	return d;

}
char* compareString (char* a, char* b, int *isEq, int *isGt, int *isLt, int *isGe, int *isLe, int *isNe ){


	// Inizio Statement
	*isEq =  strcmp(a, b) == 0;
	*isGt =  strcmp(a, b) > 0;
	*isLt =  strcmp(a, b) < 0;
	*isGe =  strcmp(a, b) >= 0;
	*isLe =  strcmp(a, b) <= 0;
	*isNe =  strcmp(a, b) != 0;
	return strcat(a=strdup(a) , b=strdup(b));

}
int main(int argc, char *argv[]){
	initialize_global();
	test();
}
void test ( ){
	// Dichiarazione variabili
	int isGt;
	char*  pa;
	char*  concatenata;
	int isLe;
	char*  c;
	int isEq;
	int isGe;
	char*  p;
	int isLt;
	int isNe;
	// Assegnazione variabili
	p = "a";
	c = "a";

	// Inizio Statement
	concatenata = compareString(c, p, &isEq, &isGt, &isLt, &isGe, &isLe, &isNe);
	printf("%d\n",isEq);
	fflush(stdout);
	printf("%d\n",isGt);
	fflush(stdout);
	printf("%d\n",isLt);
	fflush(stdout);
	printf("%d\n",isGe);
	fflush(stdout);
	printf("%d\n",isLe);
	fflush(stdout);
	printf("%d\n",isNe);
	fflush(stdout);
	printf("%s\n",concatenata);
	fflush(stdout);
	printf("%s\n",strcat(dkdkd=strdup(dkdkd) , "ciaooooooo"));
	fflush(stdout);

}
