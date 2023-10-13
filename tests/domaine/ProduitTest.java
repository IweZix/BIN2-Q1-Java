package domaine;

import exceptions.DateDejaPresenteException;
import exceptions.PrixNonDisponibleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.lang.model.element.ModuleElement;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProduitTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;
    private Produit produit1;
    private Produit produit2;

    private static final LocalDate LOCAL_DATE_POUR_TESTS = LocalDate.of(2023, 10, 12);
    private static final Prix PRIX_POUR_TESTS = new Prix(TypePromo.PUB, 7);

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1,20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 7);
        prixPub.definirPrix(3, 15);

        prixSolde = new Prix(TypePromo.SOLDE, 13);

        produit1 = new Produit("iPhone 15", "Apple", "tech");
        produit1.ajouterPrix(LocalDate.of(2000, 1, 1), prixAucune);
        produit1.ajouterPrix(LocalDate.of(2000, 1, 3), prixPub);
        produit1.ajouterPrix(LocalDate.of(2000, 1, 4), prixSolde);

        produit2 = new Produit("iPhone 15 Pro", "Apple", "tech");
    }

    @DisplayName("Test du constructeur & getter")
    @Test
    void testConstructeurEtGetter() {
        assertAll(
                // 1
                () -> assertThrows(IllegalArgumentException.class, () ->
                        new Produit(null, null, null)),
                () -> assertThrows(IllegalArgumentException.class, () ->
                        new Produit("", "", "")),
                () -> assertThrows(IllegalArgumentException.class, () ->
                        new Produit(" ", " ", " ")),
                // 2
                () -> assertEquals("iPhone 15", produit1.getNom()),
                () -> assertEquals("Apple", produit1.getMarque()),
                () -> assertEquals("tech", produit1.getRayon())
        );
    }

    @DisplayName("Test de la méthode ajouterPrix() & getPrix()")
    @Test
    void testAjouterPrixEtGetPrix() {
        produit2.ajouterPrix(LOCAL_DATE_POUR_TESTS, PRIX_POUR_TESTS);
        assertAll(
                // 1
                () -> assertThrows(IllegalArgumentException.class, () ->
                        produit2.ajouterPrix(LOCAL_DATE_POUR_TESTS, null)),
                () -> assertThrows(IllegalArgumentException.class, () ->
                        produit2.ajouterPrix(null, PRIX_POUR_TESTS)),
                // 2
                () -> assertThrows(DateDejaPresenteException.class, () ->
                        produit1.ajouterPrix(LocalDate.of(2000, 1, 1), prixSolde)),
                // 3
                () -> assertEquals(PRIX_POUR_TESTS, produit2.getPrix(LOCAL_DATE_POUR_TESTS)),
                // 4
                () -> assertThrows(PrixNonDisponibleException.class, () ->
                        produit1.getPrix(LocalDate.of(1999, 1, 4))),
                // 5
                () -> assertThrows(PrixNonDisponibleException.class, () ->
                        new Produit("iPhone 15 Pro Max", "Apple", "tech").getPrix(LOCAL_DATE_POUR_TESTS)),
                // 6
                () -> assertEquals(prixAucune, produit1.getPrix(LocalDate.of(2000, 1, 2)))
        );
    }

    @DisplayName("Test de la méthode equals & hascode")
    @Test
    void testEqualsETHashCode() {
        Produit p1 = new Produit("p", "p", "p");
        Produit p2 = new Produit("p", "p", "p");
        Produit p3 = new Produit("p4", "p", "p");
        Produit p4 = new Produit("p", "p6", "p");
        Produit p5 = new Produit("p", "p", "p5");
        assertAll(
                // 1
                () -> assertEquals(p1, p2),
                // 2
                () -> assertNotEquals(p1, p3),
                // 3
                () -> assertNotEquals(p1, p4),
                // 4
                () -> assertNotEquals(p1,p5),
                // 5
                () -> assertEquals(p1.hashCode(), p2.hashCode())
        );
    }
}