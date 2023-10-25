package lambda;

import domaine.Employe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Lambda {

    /**
     * Retourne une liste contenant uniquement les Integer qui correspondent
     * au predicat match
     * @param list La liste d'Integer originale
     * @param match le predicat à respecter
     * @return une liste contenant les integer qui respectent match
     */
    // la méthode est utilisée de la façon suivantee :
    // result = Lambda.allMatches(list, condition);
    // result est une List<Integer>
    // list est une List<Integer>
    // condition est le .test
    public static <T> List<T> allMatches(List<T> list, Predicate<T> match) {
        List<T> aRenvoyer = new ArrayList<>();
        for (T integer : list) {
            if (match.test(integer))
                aRenvoyer.add(integer);
        }
        return aRenvoyer;
    }





    /**
     * Retourne une liste contenant tous les éléments de la liste originale, transformés
     * par la fonction transform
     * @param list La liste d'Integer originale
     * @param transform la fonction à appliquer aux éléments
     * @return une liste contenant les integer transformés par transform
     */
    // la méthode est utilisée de la façon suivantee :
    // result = Lambda.transformAll(list, ???);
    // result est une List<Integer>
    // list est une List<Integer>
    // condition est le .test
    public static <T, R> List<R> transformAll(List<T> list, Function<T, R> transform) {
        List<R> aRenvoyer = new ArrayList<>();
        for (T integer : list) {
            // ici on test la condition donnée dans TestLambda
            aRenvoyer.add(transform.apply(integer));
        }
        return aRenvoyer;
    }


    /**
     * Retourne une liste contenant uniquement les Integer qui correspondent
     * au predicat match
     * @param list la liste d'Integer originale
     * @param match le predeicat à respecter
     * @return une liste contenant les integer qui respectent match
     * @param <T> type de la liste
     */
    public static <T> List<T> filter(List<T> list, Predicate<T> match) {
        List<T> aRenvoyer = list
                .stream()
                .filter(match)              // filtrer selon la condition dans TestLambda
                .collect(toList());         // mise sous forme de list
        return aRenvoyer;
    }


    public static <T, R> List<R> map(List<T> list, Function<T, R> transform) {
        List<R> aRenvoyer = list
                .stream()
                .map(transform)
                .collect(toList());
        return aRenvoyer;
    }





}
