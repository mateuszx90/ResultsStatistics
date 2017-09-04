import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        ResultsCPD resultsCPD = null;
        try {
            resultsCPD = new ResultsCPD("results1.txt");
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("POINT 1");
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
