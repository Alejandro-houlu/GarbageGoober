package sg.edu.nus.iss.GarbageGoober.Models;

public enum Levels {

    NORMIE("Normie"), INTEMEDIATE("Intemediate"), CRAFTSMAN("Craftsman");

    private String levelNames;

    private Levels(String s){
        levelNames = s;
    }

    public String getLevelNames(){
        return levelNames;
    }





    

    
}
