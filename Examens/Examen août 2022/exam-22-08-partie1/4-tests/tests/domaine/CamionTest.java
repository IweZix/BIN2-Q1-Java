package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CamionTest {

    Camion camion;
    Trajet trajet;

    @BeforeEach
    void setUp() {
        camion = new Camion("abc", 20, 16400);
        trajet = Mockito.mock(Trajet.class);
    }

    @Test
    void ajouterTrajet() {
        // if (!dateActuelle.isBefore(trajet.getDate())) return false;
        Mockito.when(trajet.getDate()).thenReturn(LocalDate.now().plusDays(1));
        // if (this.chargeMaximale < trajet.calculerPoidsTotal()) return false;
        Mockito.when(trajet.calculerPoidsTotal()).thenReturn(100.0);
        // if (this.nbMaxCaisses < trajet.nbCaisses()) return false;
        Mockito.when(trajet.nbCaisses()).thenReturn(50);
        assertFalse(camion.ajouterTrajet(trajet));
    }
}