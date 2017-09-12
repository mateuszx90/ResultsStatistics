import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ResultsCPD {

    List<DataCPD> dataCPD;
    List<DataCPD> filteredDataCPD;

    public ResultsCPD(String path) throws IOException, NumberFormatException{
        dataCPD = readData(path);
    }

    public void filterPoint1() {
        //point 1 error < 0.01 and gradient >= -0.102 and gradient < -0.099
        filteredDataCPD = new ArrayList<>();
        for (DataCPD cpd : dataCPD) {
            if (cpd.error < 0.01d && cpd.gradient >= -0.102 && cpd.gradient <= -0.099) {
                filteredDataCPD.add(cpd);
            }
        }
    }

    public void filterPoint2() {
        //point 1 error < 0.01 and gradient >= -0.102 and gradient < -0.099
        filteredDataCPD = new ArrayList<>();
        for (DataCPD cpd : dataCPD) {
            if (cpd.gradient >= -0.103 && cpd.gradient <= -0.098) {
                filteredDataCPD.add(cpd);
                System.out.println(cpd);
            }
        }
    }

    public double standardDeviation() {
        return calculateStandardDeviation(filteredDataCPD);
    }

    public double calculateStandardDeviation(List<DataCPD> list) {
        double sum = 0;
        double average = calculateCPDAverage();
        for (DataCPD cpd : list) {
            //Math.pow jest to wpudowana w jave funkcja
            //pierwszy argument to podstawa a drugi element to wykładnik
            sum += Math.pow(cpd.cpd - average, 2);
        }

        // Math. podobnie jak pow tylko że przyjmuje jeden arguemnt i robi z niego pierwiastek
        return Math.sqrt(sum / (double) (list.size()));
    }

    public double calculateCPDAverage() {
        return calculateAverage(filteredDataCPD);
    }

    private static double calculateAverage(List<DataCPD> filteredDataCPD) {
        double sumCpd = 0;
        for (DataCPD cpd : filteredDataCPD) {
            sumCpd += cpd.cpd;
        }

        return sumCpd / ((double) filteredDataCPD.size());
    }

    public double calculateErrorAverage() {
        double sumError = 0;
        for (DataCPD cpd : filteredDataCPD) {
            sumError += cpd.error;
        }

        return sumError / ((double) filteredDataCPD.size());
    }

    private static List<DataCPD> readData(String path) throws IOException, NumberFormatException {
        //tworzenie listy po to aby ta funkcja mogła zwrócić wczytane dane w postaci listy obiektów DataCPD
        List<DataCPD> data = new ArrayList<>();

        //BufferedReader i FileReader są to klasy które są wbudowane w jave i umożliwiają nam czytanie lini jak niżej widać
        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        //bufferedReader.readLine(); to wczytuje linię z pliku, z każdym następnym wywołaniem jej będziemy dostawać
        // nową następną linie null znaczy w javie brak obiektu czyli że już więcej lini nie ma w tym przypadku
        String line = bufferedReader.readLine();
        int lineIndex = 1;
        //pętla while ( dopuki warunek jest true czyli spełniony
        while (line != null) {
            //podzielenie wczytanej lini na elementy które są odzielone tabem z wyłączeniem tych znaków
            String[] split = line.split("\t");
            if (split.length != 3) {
                //jeśli tablica (czyli String[] split) wielkość jej będzie różna od 3 oznacza to błąd danych w pliku
                // i rzucamy wyjątek tym samym przerywamy działanie programu chyba że jest try catch wtedy wypiszemy błąd
                // i program wykona się dalej.
                throw new RuntimeException("W wierszu: " + lineIndex + " powinny być 3 wartości a są: " + split.length);
            }

            //dodajemy nowo wczytaną i podzieloną na 3 częsci linijkę
            //DataCPD jest to obiekt który ma w sobie cpd, gradient i error które wczytujemy z pliku
            data.add(new DataCPD(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]),
                    Float.parseFloat(split[2]))
            );

            //ta linijka jest tylko po to żebyśmy mogli w błędzie wypisać w której lini w pliku jest coś źle.
            //jak zoobaczysz wyżej pisze "W wierszu: " + lineIndex i tak dalej
            lineIndex++;
            //wczytanie next linii
            line = bufferedReader.readLine();
        }

        for (DataCPD datum : data) {
            System.out.println(datum);
        }

        return data;
    }

     double calculateQ2() {
         return calculateMedianaList(filteredDataCPD);
    }
    //Q1 = -0.3155
    double calculateQ1() {
        double Q2 = calculateMedianaList(filteredDataCPD);
        List<DataCPD> left = new ArrayList<>();
        for (DataCPD dataCPD : filteredDataCPD) {
            if (dataCPD.cpd < Q2) {
                left.add(dataCPD);
            }
        }

        return calculateMedianaList(left);
    }

    //Q3 = -0.3015
    double calculateQ3() {
        double Q2 = calculateMedianaList(filteredDataCPD);
        List<DataCPD> left = new ArrayList<>();
        for (DataCPD dataCPD : filteredDataCPD) {
            if (dataCPD.cpd > Q2) {
                left.add(dataCPD);
            }
        }

        return calculateMedianaList(left);
    }

    private double calculateMedianaList(List<DataCPD> list) {
        // Tutaj robimy sortowanie zbioru po to żeby wybrać środkowy wyraz
        Collections.sort(list, new Comparator<DataCPD>() {
            @Override
            public int compare(DataCPD o1, DataCPD o2) {
                // tutaj pokazujemy jak porównać jesen element w liście z następnym
                // aby program widział k†óry element jest większy
                // Double.compare jest wbudowane w javie więc nie musimy się przejmować
                // a porównujemy na podstawie cpd
                return Double.compare(o1.cpd, o2.cpd);
            }
        });

        //% oznacza modulo a to oznacza reszta z dzielenie
        //czyli wiemy że jeśli mamy parzystą liczbe elementów czyli reszta z dzielenia przez 2 jest zero to
        // mamy 2 środkowe wyrazy i z nich średnia
        if (list.size() % 2 == 0) {
            double sum = list.get(((list.size()) / 2) - 1).cpd + list.get(((list.size()) / 2)).cpd;
            return sum / 2d;
        } else {
            // i w przeciwnym razie mamy tylko środkowy wyraz
            return list.get(((list.size() + 1) / 2 ) - 1).cpd;
        }
    }

    public double calculaetPoint2_5Average() {
        double Q1 = calculateQ1();
        double Q3 = calculateQ3();

        List<DataCPD> Q1Q3 = new ArrayList<>();
        for (DataCPD dataCPD : filteredDataCPD) {
            if (dataCPD.cpd > Q1 && dataCPD.cpd < Q3) {
                Q1Q3.add(dataCPD);
            }
        }

        return calculateAverage(Q1Q3);
    }

    public double calculaetPoint2_5StandardDeviation() {
        double Q1 = calculateQ1();
        double Q3 = calculateQ3();

        List<DataCPD> Q1Q3 = new ArrayList<>();
        for (DataCPD dataCPD : filteredDataCPD) {
            if (dataCPD.cpd > Q1 && dataCPD.cpd < Q3) {
                Q1Q3.add(dataCPD);
            }
        }

        return calculateStandardDeviation(Q1Q3);
    }

}
