package org.example;

public class Main {

    public static void main(String[] args) {
        String NomDeFichier = "Annexe.txt";

        Dictionary myDictionary;
        myDictionary = Dictionary.parseFile(NomDeFichier);
        assert myDictionary != null;
        System.out.println(myDictionary.getTraduction("Mansion"));
        System.out.println(myDictionary.getTraduction("chevalier"));
        System.out.println(myDictionary.getTraduction("JackHammer"));
        System.out.println(myDictionary.getTraduction("été"));
        System.out.println(myDictionary.getTraduction("grenade"));
        System.out.println(myDictionary.getTraduction("square"));
        System.out.println(myDictionary.getTraduction("break"));
        System.out.println(myDictionary.getTraduction("Gun"));
    }
}