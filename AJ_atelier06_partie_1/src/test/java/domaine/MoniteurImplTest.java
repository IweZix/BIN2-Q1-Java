package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoniteurImplTest {

    private Sport sport;
    private Moniteur moniteur;
    private Stage stageValide;

    @BeforeEach
    void setUp() {
        sport = new SportStub(true);
        moniteur = new MoniteurImpl("Dupont");
        stageValide = new StageStub(8, sport, null);
    }

    private void amenerALEtat(int etat, Moniteur moniteur) {
        for (int i = 1; i <= etat; i++) {
            moniteur.ajouterStage(new StageStub(i, sport, null));
        }
    }

    @Test
    void testMoniteurTC1() {
        assertAll(
                () -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(1, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC2() {
        amenerALEtat(1, moniteur);
        assertAll(
                () -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(2, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC3() {
        amenerALEtat(2, moniteur);
        assertAll(
                () -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(3, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC4() {
        amenerALEtat(3, moniteur);
        assertAll(
                () -> assertTrue(moniteur.ajouterStage(stageValide)),
                () -> assertTrue(moniteur.contientStage(stageValide)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC5() {
        amenerALEtat(3, moniteur);
        moniteur.ajouterStage(stageValide);
        assertAll(
                () -> assertTrue(moniteur.supprimerStage(stageValide)),
                () -> assertFalse(moniteur.contientStage(stageValide)),
                () -> assertEquals(3, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC6() {
        amenerALEtat(2, moniteur);
        moniteur.ajouterStage(stageValide);
        assertAll(
                () -> assertTrue(moniteur.supprimerStage(stageValide)),
                () -> assertFalse(moniteur.contientStage(stageValide)),
                () -> assertEquals(2, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC7() {
        amenerALEtat(1, moniteur);
        moniteur.ajouterStage(stageValide);
        assertAll(
                () -> assertTrue(moniteur.supprimerStage(stageValide)),
                () -> assertFalse(moniteur.contientStage(stageValide)),
                () -> assertEquals(1, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC8() {
        amenerALEtat(0, moniteur);
        moniteur.ajouterStage(stageValide);
        assertAll(
                () -> assertTrue(moniteur.supprimerStage(stageValide)),
                () -> assertFalse(moniteur.contientStage(stageValide)),
                () -> assertEquals(0, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC9() {
        amenerALEtat(3, moniteur);
        moniteur.ajouterStage(stageValide);
        assertAll(
                () -> assertFalse(moniteur.ajouterStage(stageValide)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC10() {
        amenerALEtat(4, moniteur);
        StageStub stageNonValide = new StageStub(1, sport, moniteur);
        assertAll(
                () -> assertFalse(moniteur.ajouterStage(stageNonValide)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC11() {
        amenerALEtat(4, moniteur);
        StageStub stageNonPresent = new StageStub(1, sport, moniteur);
        assertAll(
                () -> assertFalse(moniteur.supprimerStage(stageNonPresent)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC12() {
        amenerALEtat(4, moniteur);
        Moniteur autreMoniteur = new MoniteurImpl("Snake");
        Stage stageAutreMoniteur = new StageStub(8, sport, autreMoniteur);
        assertAll(() -> assertFalse(moniteur.ajouterStage(stageAutreMoniteur)),
                () -> assertFalse(moniteur.contientStage(stageAutreMoniteur)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC13() {
        amenerALEtat(4, moniteur);
        Stage stageBonMoniteur = new StageStub(8, sport, moniteur);
        assertAll(() -> assertTrue(moniteur.ajouterStage(stageBonMoniteur)),
                () -> assertTrue(moniteur.contientStage(stageBonMoniteur)),
                () -> assertEquals(5, moniteur.nombreDeStages())
        );
    }

    @Test
    void testMoniteurTC14() {
        amenerALEtat(4, moniteur);
        Sport sportHorsCompetence = new SportStub(false);
        Stage stageMauvaisSport = new StageStub(8, sportHorsCompetence, null);
        assertAll(() -> assertFalse(moniteur.ajouterStage(stageMauvaisSport)),
                () -> assertFalse(moniteur.contientStage(stageMauvaisSport)),
                () -> assertEquals(4, moniteur.nombreDeStages())
        );
    }
}