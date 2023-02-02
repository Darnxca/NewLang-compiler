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


//-----------Implementazione funzioni-----------
int main ( ){
	initialize_global();
	// Dichiarazione variabili
	char ciao;
	// Assegnazione variabili
	ciao = 'c';

	// Inizio Statement
	switch(ciao){
		case 'c':
		printf("%s",strcat(char_to_string(ciao),int_to_string(2)));
	fflush(stdout);
		break;
		case 'i':
		printf("%s",strcat(char_to_string(ciao),int_to_string(3)));
	fflush(stdout);
		break;
	}
	return 0;

}
