package com.excilys.cdb.ui;

public enum DisplayValues {
	FALSE(-1), EXIT(0), GET_LIST_COMPUTERS(1), GET_LIST_COMPANIES(2), GET_ONE_COMPUTER(3), CREATE_COMPUTER(4),UPDATE_COMPUTER(5), DELETE_COMPUTER(6);
	
	public final int indice;
	
	DisplayValues(int indice) {
		this.indice = indice;
	}
	
	public int getIndice() {return indice;}
	
    
    public static DisplayValues fromPropertyName(int i) throws Exception {
        for (DisplayValues currentType : DisplayValues.values()) {
            if (currentType.getIndice() == i) {
                return currentType;
            }
        }
        throw new Exception("Unmatched Type: " + i);
    }
	
}
