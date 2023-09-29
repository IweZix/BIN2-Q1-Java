package domaine;

import util.Util;

import java.time.Duration;
import java.util.*;

public class Plat {

    /**
     * Give difficulty of a "Plat"
     */
    public enum Difficulte {
        X, XX, XXX, XXXX, XXXXX;

        public String toString() {
            return super.toString().replace("X", "*");
        }
    }

    /**
     * Give cost of a "Plat"
     */
    public enum Cout {
        $, $$, $$$, $$$$, $$$$$;

        public String toString() {
            return super.toString().replace("$", "€");
        }
    }

    private String nom;
    private int nbPersonnes;
    private Difficulte niveauDeDifficulte;
    private Cout cout;
    private Duration dureeEnMinutes;
    private List<Instruction> recette = new ArrayList<Instruction>();
    private Set<IngredientQuantifie> ingredients = new HashSet<IngredientQuantifie>();

    /**
     * Create a "Plat" with 4 params
     * @param nom name of the "Plat"
     * @param nbPersonnes number of people for the "Plat"
     * @param niveauDeDifficulte difficulty of the "Plat"
     * @param cout cost of the "Plat"
     */
    public Plat(String nom, int nbPersonnes, Difficulte niveauDeDifficulte, Cout cout) {
        if (nom == null || nom.equals(""))
            throw new IllegalArgumentException();
        if (nbPersonnes <= 0)
            throw new IllegalArgumentException();
        if (niveauDeDifficulte == null)
            throw new IllegalArgumentException();
        if (cout == null)
            throw new IllegalArgumentException();
        this.nom = nom;
        this.nbPersonnes = nbPersonnes;
        this.niveauDeDifficulte = niveauDeDifficulte;
        this.cout = cout;
        this.dureeEnMinutes = Duration.ZERO;
    }


    public String getNom() {
        return nom;
    }

    public int getNbPersonnes() {
        return nbPersonnes;
    }

    public Difficulte getNiveauDeDifficulte() {
        return niveauDeDifficulte;
    }

    public Cout getCout() {
        return cout;
    }

    public Duration getDureeEnMinute() {
        return dureeEnMinutes;
    }


    /**
     * Add an instruction at a position
     * @param position position of the instruction in the list
     * @param instruction instruction to add
     */
    public void insererInstruction(int position, Instruction instruction){
        Util.checkStrictlyPositive(position);
        Util.checkObject(instruction);
        if (position > recette.size() + 1) throw new IllegalArgumentException();
        recette.add(position - 1, instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinute());
    }

    /**
     * Add an instruction at the end of the instructions list
     * @param instruction instruction to add
     */
    public void ajouterInstruction(Instruction instruction){
        Util.checkObject(instruction);
        recette.add(instruction);
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinute());
    }

    /**
     * Reaplce an instruction to the instructions list
     * @param instruction instruction to add
     * @param position position of the replace
     * @return the instruction replaced
     */
    public Instruction remplacerInstruction(int position, Instruction instruction){
        Util.checkStrictlyPositive(position);
        Util.checkObject(instruction);
        if (position > recette.size()) throw new IllegalArgumentException();
        Instruction replaceInstruction = recette.set(position-1, instruction);
        dureeEnMinutes = dureeEnMinutes.minus(replaceInstruction.getDureeEnMinute());
        dureeEnMinutes = dureeEnMinutes.plus(instruction.getDureeEnMinute());
        return replaceInstruction;
    }

    /**
     * Add an ingredient to the "Plat"
     * @param position position of the ingredient in the list
     * @return the instruction removed
     */
    public Instruction supprimerInstruction(int position){
        Util.checkStrictlyPositive(position);
        if (position > recette.size() ) throw new IllegalArgumentException();
        Instruction deletedInstruction = recette.remove(position-1);
        dureeEnMinutes = dureeEnMinutes.minus(deletedInstruction.getDureeEnMinute());
        return deletedInstruction;
    }

    public Iterator<Instruction> instructions(){
        return Collections.unmodifiableList(recette).iterator();
    }

    public boolean ajouterIngredient(Ingredient ingredient, int quantite, Unite unite){
        IngredientQuantifie ingredientQuantifie = new IngredientQuantifie(ingredient, quantite, unite);
        return ingredients.add(ingredientQuantifie);
    }

    public boolean ajouterIngredient(Ingredient ingredient, int quantite){
        IngredientQuantifie ingredientQuantifie = new IngredientQuantifie(ingredient, quantite, Unite.NEANT);
        return ingredients.add(ingredientQuantifie);
    }

    public boolean modifierIngredient(Ingredient ingredient, int quantite, Unite unite){
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            if (ingredientQuantifie.getIngredient().equals(ingredient)){
                ingredients.remove(ingredientQuantifie);
                ingredients.add(new IngredientQuantifie(ingredient, quantite, unite));
                return true;
            }
        }
        return false;
    }

    public boolean supprimerIngredient(Ingredient ingredient){
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            if (ingredientQuantifie.getIngredient().equals(ingredient)){
                ingredients.remove(ingredientQuantifie);
                return true;
            }
        }
        return false;
    }

    public IngredientQuantifie trouverIngredientQuantifie(Ingredient ingredient){
        for (IngredientQuantifie ingredientQuantifie : ingredients) {
            if (ingredientQuantifie.getIngredient().equals(ingredient))
                return ingredientQuantifie;
        }
        return null;
    }

    @Override
    public String toString() {
        String hms = String.format("%d h %02d m", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        String res = this.nom + "\n\n";
        res += "Pour " + this.nbPersonnes + " personnes\n";
        res += "Difficulté : " + this.niveauDeDifficulte + "\n";
        res += "Coût : " + this.cout + "\n";
        res += "Durée : " + hms + " \n\n";
        res += "Ingrédients :\n";
        for (IngredientQuantifie ing : this.ingredients) {
            res += ing + "\n";
        }
        int i = 1;
        res += "\n";
        for (Instruction instruction : this.recette) {
            res += i++ + ". " + instruction + "\n";
        }
        return res;
    }


}
