package org.example;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
    private final String nomeDeDictionnaire;
    private final HashMap<String, List<String>> language1Alanguage2 = new HashMap<>();
    private final HashMap<String, List<String>> language2Alanguage1 = new HashMap<>();

    public Dictionary(String nome) {
        this.nomeDeDictionnaire = nome;
    }

    public String getName() {
        return this.nomeDeDictionnaire;
    }

    public HashMap<String, List<String>> getLanguage1Alanguage2() {
        return this.language1Alanguage2;
    }

    public HashMap<String, List<String>> getLanguage2Alanguage1() {
        return this.language2Alanguage1;
    }

    public void addTraduction(String l1, List<String> l2List) {
        this.getLanguage1Alanguage2().put(l1, l2List);
        for (String s : l2List) {
            if (!this.getLanguage2Alanguage1().containsKey(s)) {
                this.getLanguage2Alanguage1().put(s, new ArrayList<>());
            }
            this.getLanguage2Alanguage1().get(s).add(l1);
        }
    }

    public List<String> getTraduction(String mot) {
        Set<String> traductions = new HashSet<>();
        List<String> l2List = this.getLanguage1Alanguage2().get(mot);
        if (l2List != null) {
            traductions.addAll(l2List);
        }
        List<String> l1List = this.getLanguage2Alanguage1().get(mot);
        if (l1List != null) {
            traductions.addAll(l1List);
        }
        return new ArrayList<>(traductions);
    }

    public static Dictionary parseFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String nomeDeDictionnaire = reader.readLine();
            if (nomeDeDictionnaire == null || nomeDeDictionnaire.trim().isEmpty()) {
                System.err.println("Invalid dictionary name");
                return null;
            }
            Dictionary dictionary = new Dictionary(nomeDeDictionnaire.trim());
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(";");
                if (parts.length >= 1) {
                    String l1 = parts[0].trim();
                    List<String> l2 = new ArrayList<>();
                    for (int i = 1; i < parts.length; i++) {
                        String translation = parts[i].trim();
                        if (!translation.isEmpty()) {
                            l2.add(translation);
                        }
                    }
                    if (l2.size() == 1) {
                        dictionary.addTraduction(l1, Collections.singletonList(l2.get(0)));
                    } else if (l2.size() > 1) {
                        dictionary.addTraduction(l1, l2);
                    } else {
                        dictionary.addTraduction(l1, Collections.singletonList("Pas de traduction possible"));
                    }
                }
                line = reader.readLine();
            }
            return dictionary;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + fileName);
            return null;
        } catch (IOException e) {
            System.err.println("Error while parsing the file: " + e.getMessage());
            return null;
        }
    }
}
