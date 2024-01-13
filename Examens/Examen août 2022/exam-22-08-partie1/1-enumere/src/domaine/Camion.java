package domaine;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class Camion  {

    public enum Gabarit {

        DEUX_ESSIEUX(16400, 20),
        TROIS_ESSIEUX(22000, 30),
        QUATRE_ESSIEUX(28600, 40);
        int chargeMaximal, nbrMaxCaisses;

        Gabarit(int chargeMaximal, int nbrMaxCaisses) {
            this.chargeMaximal = chargeMaximal;
            this.nbrMaxCaisses = nbrMaxCaisses;
        }
    }

    private String immatriculation;
    private Gabarit gabarit;

    // private Set<Trajet> trajets = new HashSet<>();
    private SortedMap<LocalDate, Trajet> trajets = new TreeMap<>();

    public Camion(String immatriculation, Gabarit gabarit) {
        this.immatriculation = immatriculation;
        this.gabarit = gabarit;
    }

    /**
     * ajoute un trajet pour le camion
     * @param trajet le trajet à ajouter
     * @return false
     * la date du jour n'est pas antérieure à la date du trajet
     * - s'il y a déjà un trajet prévu ce jour-là pour le camion
     * - s'il y a déjà un trajet prévu la veille et que la ville d'arrivée de ce trajet ne correspond
     * pas à la ville de départ du trajet à ajouter
     * - s'il y a déjà un trajet prévu le lendemain et que la ville de départ de ce trajet ne correspond
     * pas à la ville d'arrivée du trajet à ajouter
     * - s'il y a trop de palettes à transporter par rapport à la capacité du camion
     * - si le poids total à transporter est supérieur à la charge maximale du camion
     */
    public boolean ajouterTrajet(Trajet trajet){
        LocalDate dateActuelle = LocalDate.now();
        if (!dateActuelle.isBefore(trajet.getDate())) return false;
        if (gabarit.chargeMaximal < trajet.calculerPoidsTotal()) return false;
        if (gabarit.nbrMaxCaisses < trajet.nbCaisses()) return false;
        for (Trajet trajetPrevu : trajets.values()){

            if (trajetPrevu.getDate().equals(trajet.getDate())) return false;
            if (trajetPrevu.getDate().plusDays(1).equals(trajet.getDate())
                    && !trajetPrevu.getVilleArrivee().equals(trajet.getVilleDepart()))
                return false;
            if (trajetPrevu.getDate().minusDays(1).equals(trajet.getDate())
                    && !trajetPrevu.getVilleDepart().equals(trajet.getVilleArrivee()))
                return false;
        }
        trajets.put(dateActuelle, trajet);
        return true;
    }

    /**
     * renvoie, s'il existe, le trajet prévu à la date passée en paramètre
     * @param dateTrajet la date du trajet recherché
     * @return le trajet prévu à cette date s'il existe et null sinon
     */
    public Trajet trouverTrajet(LocalDate dateTrajet){
        for (Trajet trajetPrevu : trajets.values()){
            if (trajetPrevu.getDate().equals(dateTrajet)) return trajetPrevu;
        }
        return null;
    }


    /**
     * recherche le premier trajet, dont la date est ultérieure à la date du jour, auquel la caisse peut être
     * ajoutée et, s'il en a un, lui ajoute la caisse.
     * @date date du trajet recherché pour ajouter la caisse
     * @param caisse caisse à ajouter
     * @return false s'il n'y a pas de trajet auquel la caisse peut être ajoutée
     */
    public boolean ajouterCaisse(Caisse caisse){
        Trajet trajet = rechercherTrajet(caisse);
        if (trajet == null) return false;
        return trajet.ajouter(caisse);
    }

    /**
     * renvoie, s'il existe, le premier trajet, dont la date est ultérieure à la date du jour, auquella caisse
     * peut lui être ajoutée
     * @param caisse
     * @return null s'il n'y a pas de trajet auquel la caisse peut être ajoutée
     */
    private Trajet rechercherTrajet(Caisse caisse) {
        Trajet trajet = null;
        for (Trajet trajetPrevu : trajets.values()){
            if (trajetPrevu.peutAjouter(caisse)
                && trajetPrevu.nbCaisses() < gabarit.nbrMaxCaisses
                && trajetPrevu.calculerPoidsTotal() + caisse.getPoids() <= gabarit.chargeMaximal) {
                if (trajet == null ||trajetPrevu.getDate().isBefore(trajet.getDate())) {
                    trajet = trajetPrevu;
                }
            }
        }
        return trajet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Camion camion = (Camion) o;
        return Objects.equals(immatriculation, camion.immatriculation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(immatriculation);
    }

    public Trajet trouverTrajetAvecPlaceRestante() {
        return trajets.values().stream()
                .filter(t -> t.nbCaisses() < gabarit.nbrMaxCaisses)
                .findFirst().orElse(null);
    }

    public Trajet trouverDateTrajet(LocalDate date) {
        return trajets.values().stream()
                .filter(t -> t.getDate().equals(date))
                .findFirst().orElse(null);
    }



}
