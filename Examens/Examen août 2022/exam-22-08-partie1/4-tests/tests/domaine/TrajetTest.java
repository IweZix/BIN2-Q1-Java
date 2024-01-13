package domaine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TrajetTest {

    private Trajet trajet;
    // créer une date dans le passé
    private LocalDate date = LocalDate.now().plusDays(1);

    private Caisse mauvaisDepart = new Caisse("mauvaisDépart", date, "PAR", "ATH", 200);
    private Caisse caisse = new Caisse("caisse", date, "BXL", "ATH", 200);

   @BeforeEach
    void setUp() {
       trajet = new Trajet("1", date, "BXL", "ATH");
    }

    @Test
    void peutAjouter() {
       assertAll(
               () -> assertThrows(IllegalArgumentException.class,
                       () -> trajet.peutAjouter(null)),
               () -> assertFalse(trajet.peutAjouter(mauvaisDepart)),
               () -> assertTrue(trajet.peutAjouter(caisse))
       );
    }
}