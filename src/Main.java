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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
