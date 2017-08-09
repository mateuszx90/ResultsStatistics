import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Correlation {

    private final List<FunctionValue> functionValues;

    private static List<FunctionValue> readData(String path) throws IOException, NumberFormatException {
        List<FunctionValue> data = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        int lineIndex = 1;
        while (line != null) {
            String[] split = line.split("\t");
            if (split.length != 2) {
                throw new RuntimeException("W wierszu: " + lineIndex + " powinny być 2 wartości a są: " + split.length);
            }

            data.add(new FunctionValue(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]))
            );

            lineIndex++;
            line = bufferedReader.readLine();
        }

        for (FunctionValue datum : data) {
            System.out.println(datum);
        }

        return data;
    }

    public Correlation(String path) throws IOException {
        functionValues = readData(path);
    }

    public double calculateCorrelation() {
        double a = 0;
        for (FunctionValue functionValue : functionValues) {
            a =  a + (functionValue.x * functionValue.y);
        }

        a = a * functionValues.size();

        double sumXi = 0, sumYi = 0;
        for (FunctionValue functionValue : functionValues) {
            sumXi += functionValue.x;
            sumYi += functionValue.y;
        }

        double top = a - sumXi * sumYi;

        double sumBottomLeft = 0;
        for (FunctionValue functionValue : functionValues) {
            sumBottomLeft += functionValue.x * functionValue.x;
        }

        sumBottomLeft *= functionValues.size();

        double sumX = 0;
        for (FunctionValue functionValue : functionValues) {
            sumX += functionValue.x;
        }

        sumX = sumX * sumX;//do kwadratu
        sumBottomLeft = Math.sqrt(sumBottomLeft - sumX);


         double sumBottomRight = 0;
        for (FunctionValue functionValue : functionValues) {
            sumBottomRight += functionValue.y * functionValue.y;
        }

        sumBottomRight *= functionValues.size();

        double sumY = 0;
        for (FunctionValue functionValue : functionValues) {
            sumY += functionValue.y;
        }

        sumY = sumY * sumY;//do kwadratu
        sumBottomRight = Math.sqrt(sumBottomRight - sumY);

        return top / (sumBottomLeft * sumBottomRight);

    }


}
