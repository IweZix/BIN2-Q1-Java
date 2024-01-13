import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Objects;

public class PizzaComposable extends Pizza{
    private LocalDateTime date;
    private Client createur;

    public PizzaComposable(Client createur){
        super("Pizza composable du client " + createur.getNumero(), "Pizza de " + createur.getNom()+" " + createur.getPrenom());
        this.date = LocalDateTime.now();
        this.createur = createur;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Client getCreateur() {
        return createur;
    }

    @Override
    public String toString() {
        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        return super.toString() + "\nPizza créée le " + formater.format(date);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PizzaComposable that = (PizzaComposable) o;
        return date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), date);
    }
}
