import org.example.Dictionary;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class DicitionaryTest
{
   //puisque Je travaille avec une class j'ai fournis à la classe un test qui permet de voir si une instance
   //est null
    @Test
    public void testDictionaryNotNull()
    {
        Dictionary dictionary = new Dictionary("MonDicitionnaire");
        assertNotNull(dictionary);
    }
    @Test
    public void testAddTraduction()
    {
        // Création d'un nouveau dictionnaire
        Dictionary d = new Dictionary("TestDictionary");
        // Ajout de la traduction du mot "Quantité De mouvement" en anglais "Momentum"
        d.addTraduction("Quantité De mouvement", Collections.singletonList("Momentum"));
        // Vérification que la traduction du mot "Quantité De mouvement" est correctement stockée dans le dictionnaire
        assertEquals(Collections.singletonList("Momentum"), d.getTraduction("Quantité De mouvement"));
    }

    @Test
    public void testMultipleTranslations()
    {
        // Création d'un nouveau dictionnaire
        Dictionary d = new Dictionary("TestDictionary");
        // Ajout de plusieurs traductions du mot "Crédit" en anglais "Credit" et "Loan"
        d.addTraduction("Crédit", Arrays.asList("Credit", "Loan"));
        // Vérification que toutes les traductions du mot "Crédit" sont correctement stockées dans le dictionnaire
        List<String> expectedTranslations = Arrays.asList("Credit", "Loan");
        List<String> actualTranslations = d.getTraduction("Crédit");
        assertEquals(expectedTranslations, actualTranslations);
    }
    @Test
    public void testBidirectionalTranslation() {
        // Création d'un nouveau dictionnaire
        Dictionary d = new Dictionary("TestDictionary");

        // Ajout de la traduction du mot "Mausolée" en anglais "Mausoleum"
        d.addTraduction("Mausolée", Collections.singletonList("Mausoleum"));

        // Ajout de la traduction du mot "Mausoleum" en français "Mausolée"
        d.addTraduction("Mausoleum", Collections.singletonList("Mausolée"));

        // Vérification que les traductions sont correctement stockées dans le dictionnaire dans les deux sens
        assertEquals(Collections.singletonList("Mausoleum"), d.getTraduction("Mausolée"));
        assertEquals(Collections.singletonList("Mausolée"), d.getTraduction("Mausoleum"));
    }
    @Test
    public void testGetTranslationNotFoundInEmptyDictionary() {
        // Créer un dictionnaire vide pour le test
        Dictionary dictionary = new Dictionary("EmptyDictionary");

        // Obtenir la traduction pour un mot qui n'existe pas dans le dictionnaire
        List<String> translation = dictionary.getTraduction("nonexistingword");

        // Vérifier que la liste de traductions est vide
        assertTrue(translation.isEmpty());
    }
    private File createMockFile(String content) throws IOException {
        File mockFile = File.createTempFile("mock", ".txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(mockFile));
        writer.write(content);
        writer.close();
        return mockFile;
    }

    @Test
    void testParseFileWithEmptyFile()
    {
        //Je declare un srtring dans lequel se trouve
        String fileName = "test.txt";
        //Je crée un fichier
        File emptyFile = new File(fileName);
        try {
            //Je le mets en place qui va être un fichier vide
            emptyFile.createNewFile();
            //Je crée un dicitionnaire qui analyse le un fichier annexe nommé test.txt
            //C'est un fichier temporaire pour le test
            Dictionary dictionary = Dictionary.parseFile(fileName);
            //J'assure qu'il est null
            assertNull(dictionary);
        } catch (IOException e)
        {
            //et si cela échoue,une exception IOException a été levée
            //Un message d'erreur s'affiche
            fail("IOException thrown: " + e.getMessage());
        } finally
        {
            //Je le supprime via le delete
            emptyFile.delete();
        }
    }
    @Test
    public void testParseFileWithEmptyDictionaryId() throws IOException {
        //Je crée une variable de type string qui contient un document annexe sans nom
        String mockFileContent = """

                 ;Traduction1
                mot1;trad1;trad2
                mot2;trad3;trad4
                ;Traduction2
                mot3;trad5;trad6
                mot4;trad7;trad8
                """;

        File mockFile = createMockFile(mockFileContent);
        //Je crée un fichier mock
        Dictionary dictionary = Dictionary.parseFile(mockFile.getPath());
        //Je crée un dicitionnaire qui analyse un fichier mock
        assertNull(dictionary);
        // la méthode doit retourner null en cas d'identifiant de dictionnaire vide
    }
}
