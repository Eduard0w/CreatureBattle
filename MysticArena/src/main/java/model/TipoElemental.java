package model;

//Deixei como enum. Pesquisei e vi que era melhor.
public enum TipoElemental {
	FOGO, AGUA, TERRA, AR, LUZ, TREVAS;
	
	public double calcularVantagem(TipoElemental atacado) {
		if (this == FOGO) {
            if (atacado == TERRA) return 2.0; 
            if (atacado == AR) return 1.5; 
            if (atacado == AGUA) return 0.5;
        } else if (this == AGUA) {
            if (atacado == FOGO || atacado == TERRA) return 1.5;
            if (atacado == AR) return 0.5; 
        } else if (this == TERRA) {
            if (atacado == FOGO || atacado == LUZ) return 1.5; 
            if (atacado == AGUA) return 0.5; 
        } else if (this == AR) {
            if (atacado == TERRA) return 1.5; 
            if (atacado == FOGO) return 0.5; 
        } else if (this == LUZ) {
            if (atacado == TREVAS) return 1.5;
            if (atacado == TERRA) return 0.5;
        } else if (this == TREVAS) {
            if (atacado == LUZ) return 1.5;
            if (atacado == AGUA) return 0.5;
        }
        return 1.0;
	}
}


/*
	Fogo ganha de terra e ar
	agua ganha de fogo e terra;
	Terra ganha de fogo e da luz
	Ar ganha da terra 
	Luz ganha de trevas
	Trevas ganha da 
	  
	  
	  
	Isso aqui não precissa fazer
	Luz toma downDEF e downATK de dia(das 18:00 as 5:59) e Trevas recebe upDEF e upATK
	Trevas toma downDEF e downATK de dia(das 6:00 as 17:59) e Luz recebe upDEF e upATK
 */
