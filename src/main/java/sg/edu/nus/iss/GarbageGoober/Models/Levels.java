package sg.edu.nus.iss.GarbageGoober.Models;

public enum Levels {

    NORMIE("Normie"), INTERMEDIATE("Intermediate"), CRAFTSMAN("Craftsman");

    private String levelNames;

    private Levels(String s){
        levelNames = s;
    }

    public String getLevelNames(){
        return levelNames;
    }





    

    
}
