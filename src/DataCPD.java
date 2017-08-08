public class DataCPD {

    public double cpd;
    public double error;
    public double gradient;

    public DataCPD(double cpd, double error, double gradient) {
        this.cpd = cpd;
        this.error = error;
        this.gradient = gradient;
    }

    @Override
    public String toString() {
        return "DataCPD{" +
                "cpd=" + cpd +
                ", error=" + error +
                ", gradient=" + gradient +
                '}';
    }
}
