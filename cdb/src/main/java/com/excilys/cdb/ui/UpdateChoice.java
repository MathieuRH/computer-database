package com.excilys.cdb.ui;

import com.excilys.cdb.exceptions.EnumException;

public enum UpdateChoice {
	
	CHANGE_NAME(1), CHANGE_INTRODUCED(2), CHANGE_DISCONTINUED(3), CHANGE_COMPANY(4);
	
	public final int indice;
	
	UpdateChoice(int indice) {
		this.indice = indice;
	}
	
	public int getIndice() {return indice;}
    
    public static UpdateChoice fromPropertyName(int i) throws EnumException {
        for (UpdateChoice currentType : UpdateChoice.values()) {
            if (currentType.getIndice() == i) {
                return currentType;
            }
        }
        throw new EnumException("Unmatched Type: " + i);
    }
}
