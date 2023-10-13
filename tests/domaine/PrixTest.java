package domaine;

import domaine.Prix;
import domaine.TypePromo;
import exceptions.QuantiteNonAutoriseeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Type;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Classe de tests de la classe Prix (AJ_atelier05")

class PrixTest {

    private Prix prixAucune;
    private Prix prixPub;
    private Prix prixSolde;

    @BeforeEach
    void setUp() {
        prixAucune = new Prix();
        prixAucune.definirPrix(1,20);
        prixAucune.definirPrix(10, 10);

        prixPub = new Prix(TypePromo.PUB, 7);
        prixPub.definirPrix(3, 15);

        prixSolde = new Prix(TypePromo.SOLDE, 13);
    }

    @DisplayName("Test du constructeur avec en paramètre un TypePromo null || valeurPromo <= 0")
    @ParameterizedTest
    @ValueSource(doubles = {0.0,-0.1,-1,-2})
    void testPrix(double price) {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () ->
                        new Prix(null, 5)),
                () -> assertThrows(IllegalArgumentException.class, () ->
                        new Prix(TypePromo.PUB, price))
        );
    }

    @DisplayName("Test du getter")
    @Test
    void testGetters() {
        assertAll(
                () -> assertEquals(0, new Prix().getValeurPromo()),
                () -> assertEquals(7, prixPub.getValeurPromo()),
                () -> assertNull(new Prix().getTypePromo()),
                () -> assertEquals(TypePromo.SOLDE, new Prix(TypePromo.SOLDE, 5).getTypePromo())
        );
    }

    @DisplayName("Test de la méthode definirPrix()")
    @ParameterizedTest
    @ValueSource(doubles = {0.0,-0.1,-1,-2})
    void testDefeinirPrix(double price) {
        prixAucune.definirPrix(10, 6);
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () ->
                        prixPub.definirPrix((int) price, 5)),
                () -> assertThrows(IllegalArgumentException.class, () ->
                        prixPub.definirPrix(10, price)),
                () -> assertEquals(6, prixAucune.getPrix(10))
        );
    }

    @DisplayName("Test de la méthode getPrix()")
    @Test
    void testGetPrix() {
        assertAll(
                // 1
                () -> assertThrows(IllegalArgumentException.class, () ->
                        prixPub.getPrix(-1)),
                () -> assertThrows(IllegalArgumentException.class, () ->
                        prixPub.getPrix(0)),
                // 2
                () -> assertEquals(20, prixAucune.getPrix(1)),
                () -> assertEquals(20, prixAucune.getPrix(5)),
                () -> assertEquals(20, prixAucune.getPrix(9)),
                () -> assertEquals(10, prixAucune.getPrix(10)),
                () -> assertEquals(10, prixAucune.getPrix(15)),
                () -> assertEquals(10, prixAucune.getPrix(20)),
                () -> assertEquals(10, prixAucune.getPrix(25)),
                // 3
                () -> assertThrows(QuantiteNonAutoriseeException.class, () ->
                        prixPub.getPrix(2)),
                // 4
                () -> assertThrows(QuantiteNonAutoriseeException.class, () ->
                        prixSolde.getPrix(1))
        );
    }






}