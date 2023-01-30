#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

char* int_to_string(int num) {
   char* str = malloc(10 * sizeof(char));
   sprintf(str, "%d", num);
   return str;
}
char* float_to_string(float num) {
    char* str = malloc(10 * sizeof(char));
    snprintf(str,10,"%f", num);
    return str;
}
char* char_to_string(char c) {
    char* str = malloc(sizeof(char));
    snprintf(str,sizeof str,"%c", c);
    return str;
}
// prototipi delle funzioni
void stampaSequenzaFibonacci(int n);
int Nfibonacci(int n);
void main();
// inizializzazione delle variabili globali

void initialize_global() {
}

//-----------Implementazione funzioni-----------
int Nfibonacci (int n ){


	// Inizio Statement
	if((n == 0)){


		// Inizio Statement
		return 0;

	}
	if((n == 1)){


		// Inizio Statement
		return 1;

	}
	return (Nfibonacci((n - 1)) + Nfibonacci((n - 2)));

}
void stampaSequenzaFibonacci (int n ){
	// Dichiarazione variabili
	int i;
	// Assegnazione variabili
	i = 0;

	// Inizio Statement
	while((i < n)){


		// Inizio Statement
		printf("%d %s %d\n",i, " -> ", Nfibonacci(i));
		fflush(stdout);
		i = (i + 1);

	}

}
void main ( ){
	initialize_global();
	// Dichiarazione variabili
	int n;
	// Assegnazione variabili
	n = 7;

	// Inizio Statement
	printf("%s %d %s %d\n","Il ", n, " numero di fibonacci e'", Nfibonacci(n));
	fflush(stdout);
	printf("%s %d %s\n","La sequenza di fibonacci per ", n, " e':");
	fflush(stdout);
	stampaSequenzaFibonacci(7);

}
