/**
 * Stack interface. En lista som inrättar LIFO (last in first out).
 *
 * @author Erik Vanhainen
 * @version 28-01-2019
 **/
public interface Stack<T> {

    /**
     * Lägger till ett element till stacken.
     * @param elem, element av typ T som läggs till.
     **/
    void push(T elem);

    /**
     * Tar bort och returnerar det första elementet i stacken.
     * @return element av typ T som tas bort.
     **/
    T pop();

    /**
     * Returnerar det första elementet i stacken.
     * @return element av typ T som returneras.
     **/
    T top();

    /**
     * Returnerar antal element i stacken.
     * @return antal element.
     **/
    int size();

    /**
     * Returnerar om stacken är tom eller inte.
     * @return true om staken är tom.
     **/
    boolean isEmpty();
}
