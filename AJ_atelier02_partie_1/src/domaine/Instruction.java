package domaine;

import java.time.Duration;

public class Instruction {

    private String description;
    private Duration dureeEnMinute;

    /**
     * Create an instruction with 2 params
     * @param description description of the instruction
     * @param dureeEnMinute duration in minutes of the instruction
     */
    public Instruction(String description, int dureeEnMinute) {
        if (description == null || description.equals(""))
            throw new IllegalArgumentException();
        if (dureeEnMinute < 0)
            throw new IllegalArgumentException();
        this.description = description;
        this.dureeEnMinute = Duration.ofMinutes(dureeEnMinute);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description == null || description.equals(""))
            throw new IllegalArgumentException();
        this.description = description;
    }

    public Duration getDureeEnMinute() {
        return dureeEnMinute;
    }

    public void setDureeEnMinute(int dureeEnMinute) {
        if (dureeEnMinute <= 0)
            throw new IllegalArgumentException();
        this.dureeEnMinute = Duration.ofMinutes(dureeEnMinute);
    }

    public String toString() {
        long nbHeure = dureeEnMinute.toHours();
        int nbMinutes = dureeEnMinute.toMinutesPart();
        /*
        % indique le début du formatage
        0 indique que l'on souhaites remplir avec des 0 si il y a des espaces vides
        2 indique le nombre minimum de caractère
        d indique type qui signifie que vous formatez un entier (nombre décimal)
         */
        return "(" + String.format("%02d:%02d",nbHeure,nbMinutes) + ") " + description ;
    }
}
