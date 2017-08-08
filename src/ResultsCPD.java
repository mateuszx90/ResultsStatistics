import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
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

        double sum = 0;
        double average = calculateCPDAverage();
        for (DataCPD cpd : filteredDataCPD) {
            sum += Math.pow(cpd.cpd - average, 2);
        }

        return Math.sqrt(sum / (double) (filteredDataCPD.size()));
    }

    public double calculateCPDAverage() {
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
        List<DataCPD> data = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        int lineIndex = 1;
        while (line != null) {
            String[] split = line.split("\t");
            if (split.length != 3) {
                throw new RuntimeException("W wierszu: " + lineIndex + " powinny być 3 wartości a są: " + split.length);
            }

            data.add(new DataCPD(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]),
                    Float.parseFloat(split[2]))
            );

            lineIndex++;
            line = bufferedReader.readLine();
        }

        for (DataCPD datum : data) {
            System.out.println(datum);
        }

        return data;
    }

}
