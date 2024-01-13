/**
 * La classe LigneCommande représente une ligne de commande. Elle connaît la
 * pizza commandée, la quantité commandée de cette pizza et retient son prix
 * unitaire au moment de la commande.

 */
public class LigneCommande {

    /**
     * la pizza commandée
     */
    private Pizza pizza;
    /**
     * la quantité de pizza commandée
     */
    private int quantite;
    /**
     * le prix unitaire de la pizza commandée
     */
    private double prixUnitaire;

    /**
     * Crée une ligne de commande pour la pizza passée en paramètre et dans la
     * quantité passée en paramètre.
     *
     * @param quantite la quantité de pizza commandée
     * @param pizza    la pizza commandée
     */
    public LigneCommande(Pizza pizza, int quantite) {
        Util.checkObject(pizza);
        setQuantite(quantite);
        this.pizza = pizza;
        this.prixUnitaire = pizza.calculerPrix();
    }

    /**
     * renvoie la pizza de la ligne de commande
     * @return la pizza
     */
    public Pizza getPizza() {
        return pizza;
    }

    /**
     * renvoie la quantité de la pizza de la ligne de commande
     *
     * @return la pizza
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * met à jour la quantité
     * @return la pizza
     */
    public void setQuantite(int quantite) {
        Util.checkStrictlyPositive(quantite);
        this.quantite = quantite;
    }

    /**
     * le prix unitaire de la pizza de la ligne de commande
     *
     * @return le prix unitaire de la pizza
     */
    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    /**
     * calcule le prix total de la ligne de commande en considérant les quantités
     * commandées.
     * @return le prix total de la ligne de commande
     */

    public double calculerPrixTotal() {
        return quantite * prixUnitaire;
    }

    /**
     * Convertit la ligne de commande sous forme de String:
     * @return représentation textuelle de la ligne de commande
     */
    @Override
    public String toString() {
        return  quantite + " " + pizza.getTitre() + "  à " + prixUnitaire ;
    }

}
