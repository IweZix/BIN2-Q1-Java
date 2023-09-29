package domaine;

import util.Util;

import java.util.*;

public class Livre {

    private Plat plat;
    private Map<Plat.Type, SortedSet<Plat>> plats = new HashMap<>();

    /**
     * Ajoute un plat dans le livre, s'il n'existe pas déjà dedans.
     * Il faut ajouter correctement le plat en fonction de son type.
     * @param plat le plat à ajouter
     * @return true si le plat a été ajouté, false sinon.
     */
    public boolean ajouterPlat(Plat plat) {
        Util.checkObject(plat);
        if (plats.containsKey(plat.getType()))
            return plats.get(plat.getType()).add(plat);
        else {
            SortedSet<Plat> set = new TreeSet<>(Comparator.comparing(Plat::getNom));
            set.add(plat);
            plats.put(plat.getType(), set);
            return true;
        }
    }

    /**
     * Supprime un plat du livre, s'il est dedans
     * Si le plat supprimé est le dernier de ce type de plat, il faut supprimer ce type de
     * plat de la Map.
     * @param plat le plat à supprimer
     * @return true si le plat a été supprimé, false sinon.
     */
    public boolean supprimerPlat (Plat plat) {
        Util.checkObject(plat);
        if (!plats.get(plat.getType()).contains(plat))
            return false;
        plats.get(plat.getType()).remove(plat);
        if (plats.get(plat.getType()).isEmpty())
            plats.remove(plat.getType());
        return true;
    }

    /**
     * Renvoie un ensemble contenant tous les plats d'un certain type.
     * L'ensemble n'est pas modifable.
     * @param type le type de plats souhaité
     * @return l'ensemble des plats
     */
    public SortedSet<Plat> getPlatsParType(Plat.Type type) {
        Util.checkObject(type);
        if (plats.containsKey(type))
            return Collections.unmodifiableSortedSet(plats.get(type));
        return null;
    }

    /**
     * ? Renvoie true si le livre contient le plat passé en paramètre. False sinon.
     * Pour cette recherche, un plat est identique à un autre si son type, son niveau de
     * difficulté et son nom sont identiques.
     * @param plat le plat à rechercher
     * @return true si le livre contient le plat, false sinon.
     */
    public boolean contient(Plat plat) {
        // Ne pas utiliser 2 fois la méthode get() de la map, et ne pas déclarer de variable locale !
        return plats.get(plat.getType()).contains(plat);
    }

    /**
     * Renvoie un ensemble contenant tous les plats du livre. Ils ne doivent pas être triés.
     * @return l'ensemble de tous les plats du livre.
     */
    public Set<Plat> tousLesPlats() {
        // Ne pas utiliser la méthode keySet() ou entrySet() ici !
        SortedSet<Plat> setPlats = new TreeSet<>(new PlatsComparator());
        for (Plat.Type type : Plat.Type.values()) {
            setPlats.addAll(plats.get(type));
        }
        return setPlats;
    }

    @Override
    public String toString() {
        String toString = "";
        for (Plat.Type type : plats.keySet()) {
            toString += type.getNom() + "\n====\n";
            SortedSet<Plat> iterable = plats.get(type);
            for (Plat plat : iterable) {
                toString += plat.getNom() + "\n";
            }
            toString += "\n";
        }
        return toString;
    }

    public class PlatsComparator implements Comparator<Plat> {
        public int compare(Plat o1, Plat o2) {
            if (o1.getType().compareTo(o2.getType()) == 0) {
                if (o1.getNom().compareTo(o2.getNom()) == 0) {
                    return o1.getNbPersonnes() - o2.getNbPersonnes();
                } else {
                    return o1.getNom().compareTo(o2.getNom());
                }
            }
            return o1.getType().compareTo(o2.getType());
        }
    }
}
