import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LeastSquares {

    List<Data> data;
    public float a;
    public float b;

    public LeastSquares(String path) throws IOException {
        data = readData(path);
    }

    private List<Data> readData(String path) throws IOException, NumberFormatException {
        List<Data> data = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
        String line = bufferedReader.readLine();
        int lineIndex = 1;
        while (line != null) {
            String[] split = line.split("\t");
            if (split.length != 2) {
                throw new RuntimeException("W wierszu: " + lineIndex + " powinny być 3 wartości a są: " + split.length);
            }

            data.add(new Data(
                    Float.parseFloat(split[0]),
                    Float.parseFloat(split[1]))
            );

            lineIndex++;
            line = bufferedReader.readLine();
        }

        for (Data datum : data) {
            System.out.println(datum);
        }

        return data;
    }

    //tak samo jak przy koorelacji na podstawie wzorów wyliczamy parametry chyba to jest z tej prezentaji AGH już nie pamiętam
    public void calculateParameters() {
        float sumXY = 0;
        for (Data d : data) {
            sumXY += d.x * d.y;
        }

        float sumX = 0;
        float sumY = 0;
        for (Data d : data) {
            sumX += d.x;
            sumY += d.y;
        }

        float sumSquareX = 0;
        for (Data d : data) {
            sumSquareX += d.x * d.x;
        }

        a = (data.size() * sumXY - sumX * sumY) / (data.size() * sumSquareX - sumX * sumX);
        b = (sumSquareX * sumY - sumX * sumXY) / (data.size() * sumSquareX - sumX * sumX);
    }

}
