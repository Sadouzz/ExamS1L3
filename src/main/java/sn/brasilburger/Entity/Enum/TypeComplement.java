package sn.brasilburger.Entity.Enum;

public enum TypeComplement {
    BOISSON(1), FRITE(2);
    private int value;
    private TypeComplement(int value){
        this.value=value;
    }
    public int getValue(){
        return value;
    }

    public static TypeComplement getOptionByValue(int value){
        TypeComplement[] options=TypeComplement.values();
        for (int index = 0; index < options.length; index++) {
            if (options[index].getValue()==value) {
                return  options[index];
            }
        }
        return null;
    }
}
