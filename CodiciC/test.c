#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
void stampaParole(int quante);
void stampaSciocchezze(int n,int *volte);
void main();
char*  stampaNNumeri(int n);

// inizializzazione delle variabili
float pippo1;
float pippo2;
float kasa;
float lsl = 3;
float pippo = 10;
float c = (3 + 2);

//-----------Implementazione funzioni-----------
char*  stampaNNumeri (int n ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	char*  ciao = strcpy(malloc(5*sizeof(char)),"CIAO");
	char*  ccciao = strcpy(malloc(5*sizeof(char)),"CIAO");
	int i = 1;
	int okay = 1;
	while(((i <= n) || okay)){
		// Dichiarazione variabili
		int correctInputCheck = 0;
		okay = 0;
		if((i != (i / n))){
			// Dichiarazione variabili
			int correctInputCheck = 0;
			char*  ciao = strcpy(malloc(5*sizeof(char)),"CIAO");
			printf("%d\n",i);
			fflush(stdout);
			i = (i + 1);
			free(ciao);
		}
		else{
			// Dichiarazione variabili
			int correctInputCheck = 0;
			char*  ciao = strcpy(malloc(5*sizeof(char)),"CIAO");
			okay = 1;
			free(ciao);
		}
	}
	free(ciao);
	free(ccciao);
	return ciao;
}
void stampaSciocchezze (int n, int * volte ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	for( int i = 0; i <= 101010;i++){
		// Dichiarazione variabili
		int correctInputCheck = 0;
		int fr = 1;
		char*  x = strcat(strcpy(malloc(2*sizeof(char)),"1"),strcpy(malloc(6*sizeof(char)),"volta"));
		fr = ( !fr);
		* volte = (* volte + i);
		printf("%s\n",x);
		fflush(stdout);
		free(x);
	}
}
void stampaParole (int quante ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	char*  paroleACaso;
	int i = 0;
	while((i < quante)){
		// Dichiarazione variabili
		int correctInputCheck = 0;
		char*  parola = strcpy(malloc(7*sizeof(char)),"parola");
		printf("%s\n",parola);
		fflush(stdout);
		i = (i + 1);
		free(parola);
	}
	free(paroleACaso);
}
void main ( ){
	// Dichiarazione variabili
	int correctInputCheck = 0;
	int x;
	char*  p = strcpy(malloc(8*sizeof(char)),"CIAOOO!");
	printf("Inserisci quanti numeri vuoi stampare\n");
	fflush(stdout);
	correctInputCheck = scanf("%d", &x);
	if (correctInputCheck != 1) {
		do{
			while (getchar() != '\n' ); // ripulisce lo standard input
			printf("Inserisci quanti numeri vuoi stampare\n");
			fflush(stdout);
			correctInputCheck = scanf(" %d", &x);
			printf("\n");
		}while ( correctInputCheck !=1);

	}
	if(((x == 2) || (x < 0))){
		// Dichiarazione variabili
		int correctInputCheck = 0;
		int p;
		int quanti = 1000000;
		x = quanti;
		stampaNNumeri(x);
		stampaParole(quanti);
	}
	else{
		// Dichiarazione variabili
		int correctInputCheck = 0;
		int quanti = x;
		stampaNNumeri(quanti);
		stampaParole(quanti);
	}
	free(p);
}
