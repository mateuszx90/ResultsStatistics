import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //tak się definiuje zmienną (tj. tworzy się taki wskaźnik na obiekt aby móc sobie go używać)
        ResultsCPD resultsCPD = null;
        //try /catch jak tłumaczyłęm są po to bo wiemy że obiekt ResultsCPD przy tworzeniu może mieć błąd wczytywania pliku i jeśli on nastąpi to e.printStackTrace() wyświetli dokłądną ścieżke błędu tj. w której lini coś poszło nie tak i co poszło nie tak
        try {
            // do tego wskaźnika przypisuje się nowy (new) obiekt ResultsCPD który w środku będzie sobie liczyć średnie itp.
            resultsCPD = new ResultsCPD("results1.txt");
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(argument typu string w środku); ono wypisuje w konsoli co mu się poda jako argument
        // ln oznacza line new czyli nową linie. Tak samo jak \n to też jest nowa linia w śroku stringa czyli "\n"
        System.out.println("POINT 1");
        //wywołujemy funkcję która przefiltruje nam dane z pliku results1.txt, resztę opiszę w środku tej funkcji.
        // czyli w pliku ResultsCPD.java
        resultsCPD.filterPoint1();
        System.out.println("Srednie CPD: " + resultsCPD.calculateCPDAverage());
        System.out.println("Sredni error: " + resultsCPD.calculateErrorAverage());


        try {
            resultsCPD = new ResultsCPD("results2.txt");
            System.out.println("POINT 2");
            resultsCPD.filterPoint2();
            System.out.println("Srednie CPD: " + resultsCPD.calculateCPDAverage());
            System.out.println("Sredni Error: " + resultsCPD.calculateErrorAverage());
            System.out.println("Odchylenie standardowe " + resultsCPD.standardDeviation());
            System.out.println("Q2=  " + resultsCPD.calculateQ2());
            System.out.println("Q1=  " + resultsCPD.calculateQ1());
            System.out.println("Q3=  " + resultsCPD.calculateQ3());
            System.out.println("Q1 > x < Q3=  average : " + resultsCPD.calculaetPoint2_5Average());
            System.out.println("Q1 > x < Q3=  standard deviation: " + resultsCPD.calculaetPoint2_5StandardDeviation());

            Correlation correlation = new Correlation("data.txt");

            System.out.println("\nPoint 3");
            System.out.println("Coorelation: " + correlation.calculateCorrelation());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            LeastSquares leastSquares = new LeastSquares("data.txt");
            leastSquares.calculateParameters();

            System.out.println("\nPoint 4");
            System.out.println("parameter a: " + leastSquares.a);
            System.out.println("parameter b: " + leastSquares.b);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
