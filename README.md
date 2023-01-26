# Progetto del corso di studi di compilatori dell'università degli studi di Salerno

All'interno della cartella release è presente la documentazione e JAR file e script bash per avviare il compilatore.

NewLang2C è lo script bash che rappresenta l’eseguibile del nostro compilatore. 
Per utilizzare New-Lang2C bisogna installare i seguenti applicativi:
* JAVA versione 19.0.1;
* Scaricare il JAR file NewLang presente al seguente link: NewLang.jar
* Compilatore gcc;

Una volta installato tutto l’occorrente si può invocare lo script come segue:

./NewLang2C nome_file [-com_opz1 -com_opz2 -com_opz3 -com_opz4 ]

I parametri opzionali (com_opz) utilizzabili sono i seguenti:

* -help: mostra una guida su come utilizzare il compilatore;
* -sp: stampa lo scoping del codice analizzato (lettura dal basso verso l’alto);
* -sc: salva il file C prodotto;
* -xml: salva il file xml prodotto.
* per ogni altro parametro opzionale restituisce un messaggio: comando non trovato.

Lo script bash per eseguire il compilatore è presente al seguente link: NewLang2C.
