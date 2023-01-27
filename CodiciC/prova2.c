#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

// prototipi delle funzioni
int main();
// inizializzazione delle variabili globali

void initialize_global() {
}

//-----------Implementazione funzioni-----------
int main ( ){
	initialize_global();
	// Dichiarazione variabili
	int i;
	// Assegnazione variabili
	i = 1;

	// Inizio Statement
	while((i < 3)){


		// Inizio Statement
		printf("%d",( -i));
		fflush(stdout);
		printf("%s"," ");
		fflush(stdout);
		i = (i + 1);

	}
		// Dichiarazione variabili
		char*  messaggio;
		int j;

		messaggio = "ciao";
		j = 1;

		do{
		printf("%d",i);
		fflush(stdout);
		printf("%s"," ");
		fflush(stdout);
		i = (i + 1);
		j = (j + 1);
		if((i == 5)){


			// Inizio Statement
			i = 0;
			printf("%s\n",messaggio);
			fflush(stdout);

		}
		} while (!(i < 3) && (j < 3));
}
