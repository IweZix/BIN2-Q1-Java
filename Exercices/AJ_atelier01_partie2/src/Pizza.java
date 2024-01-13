import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public abstract class Pizza implements Iterable<Ingredient>{
    public static final double PRIX_BASE = 5;
    private String titre;
    private String description;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Pizza(String titre, String description) {
        Util.checkString(titre);
        Util.checkString(description);
        this.titre = titre;
        this.description = description;
    }

    public Pizza(String titre, String description,ArrayList<Ingredient> ingredients) {
        this(titre,description);
        Util.checkObject(ingredients);
        if (ingredients.isEmpty())
            throw new IllegalArgumentException("Il faut au minimum un ingrédient pour une pizza");
        for (Ingredient i : ingredients) {
            if (this.ingredients.contains(i))
                throw new IllegalArgumentException("Il ne peut pas y avoir deux fois le même ingrédient dans une pizza.");
            this.ingredients.add(i);
        }
    }

    public String getTitre() {
        return titre;
    }

    public String getDescription() {
        return description;
    }


    @Override
    public Iterator<Ingredient> iterator() {
        return ingredients.iterator();
    }

    public boolean ajouter(Ingredient ingredient){
        Util.checkObject(ingredient);
        if (ingredients.contains(ingredient)) return false;
        return ingredients.add(ingredient);
    }

    public boolean supprimer(Ingredient ingredient){
        Util.checkObject(ingredient);
        return ingredients.remove(ingredient);
    }

    public double calculerPrix(){
        double prix = PRIX_BASE;
        for (Ingredient i: ingredients) {
            prix += i.getPrix();
        }
        return prix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza that = (Pizza) o;
        return titre.equals(that.titre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titre);
    }

    @Override
    public String toString() {
        String infos = titre + "\n" + description + "\nIngrédients : ";
        for (Ingredient ingredient : ingredients){
            infos +="\n" + ingredient.getNom();
        }
        infos +="\nprix : " + calculerPrix() + " euros";
        return infos;
    }
}
