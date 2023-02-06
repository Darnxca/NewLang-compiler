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
int main();
// inizializzazione delle variabili globali

void initialize_global() {
}

//-----------Implementazione funzioni-----------
int main ( ){
	initialize_global();


	// Inizio Statement
	{
				// Dichiarazione variabili
		int i;
		int j;
		i = 1;
		j = 1;
				do{
				printf("%s","ciao, ");
		fflush(stdout);
				printf("%d",i);
		fflush(stdout);
				printf("%s",", ");
		fflush(stdout);
				printf("%d\n",j);
		fflush(stdout);
				(i = (i + 1));
				(j = (j - 1));
				}while(((i < 20) && (j > ( -20))));
		}
	{
						do{
				}while(1);
		}
	{
				// Dichiarazione variabili
		float i;
		i = 1.1;
				do{
				printf("%s","ciao, ");
		fflush(stdout);
				printf("%f\n",i);
		fflush(stdout);
				(i = (i + 1));
				}while((i < 10.1));
		}
	return 10;

}
