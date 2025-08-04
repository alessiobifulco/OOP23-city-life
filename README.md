# City-Simulation: Simulazione Urbana Agent-Based
Progetto per il corso di Programmazione Orientata agli Oggetti (OOP), sviluppato in Java seguendo il pattern architetturale MVC.

---

## Concetto di Base
Questo progetto consiste in una simulazione basata su agenti (Agent-Based Model) per l'osservazione delle dinamiche di un ecosistema urbano nel tempo. L'obiettivo è modellare il comportamento di una città, con un focus sul traffico, rapporto tra persone e aziende e divisione in zone.

* **Ambiente:** La città, composta da strade e aziende.
* **Agenti:** Le persone, ogni agente ha degli obiettivi e prende decisioni.
* **Infrastrutture:** Aziende che offrono posti di lavoro e linee di trasporto pubblico che gli agenti possono utilizzare per i loro spostamenti.

## Architettura del Software (MVC)
L'applicazione è stata progettata seguendo il pattern architetturale **Model-View-Controller (MVC)**, un modello per lo sviluppo di interfacce utente che suddivide un'applicazione in tre parti interconnesse.

* **Model:** È la componente centrale che gestisce i dati e la logica di business dell'applicazione. È responsabile di mantenere lo stato e di applicare le regole, indipendentemente dall'interfaccia utente.
* **View:** Si occupa di presentare i dati all'utente. Rende visibile lo stato del Model e fornisce l'interfaccia grafica con cui l'utente interagisce.
* **Controller:** Riceve l'input dell'utente dalla View e lo traduce in azioni da far eseguire al Model, agendo come intermediario tra i due.

## Design Pattern Utilizzati
Per la progettazione del software sono stati utilizzati diversi design pattern per migliorare la flessibilità e la manutenibilità del codice.

* **Factory Method:** Un pattern creazionale che fornisce un'interfaccia per creare oggetti in una superclasse, ma permette alle sottoclassi di alterare il tipo di oggetti che verranno creati.
* **Strategy:** Un pattern comportamentale che permette di definire una famiglia di algoritmi, incapsularli e renderli intercambiabili. Consente di variare l'algoritmo utilizzato da un oggetto indipendentemente dai client che lo utilizzano.
* **Observer:** Un pattern comportamentale in cui un oggetto (il "soggetto") mantiene una lista dei suoi dipendenti (gli "osservatori") e li notifica automaticamente di qualsiasi cambiamento al suo stato.
* **Decorator:** Un pattern strutturale che consente di aggiungere nuove funzionalità a un oggetto dinamicamente, senza alterare il comportamento degli altri oggetti della stessa classe.

## Tecnologie e Funzionalità Chiave
* **Linguaggio: Java**
    * Linguaggio di programmazione ad alto livello, orientato agli oggetti e basato su classi.
* **GUI: (specificare qui la libreria usata, es. JavaFX o Swing)**
* **Lambda Expressions**
    * Una funzionalità che permette di trattare una funzione come un argomento di un metodo, o di scrivere codice come se fosse un dato. Permettono di creare funzioni anonime e concise.
* **Streams API**
    * Un'interfaccia per eseguire operazioni in stile funzionale su sequenze di elementi, come le collezioni. Permette di scrivere codice per l'elaborazione dei dati in modo dichiarativo e compatto.
* **Optional**
    * Un tipo contenitore utilizzato per rappresentare la presenza o l'assenza di un valore. Aiuta a prevenire errori `NullPointerException` forzando una gestione esplicita dei casi in cui un valore potrebbe non esistere.

## Contatti
* Alessio Bifulco: `alessio.bifulco@studio.unibo.it`

