package app.statistics;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Prognozare {
    private LinkedHashMap<String, Double> data;
    private double alpha;

    public Prognozare(LinkedHashMap<String, Double> data, double alpha) {
        this.data = data;
        if (alpha > 1)
            alpha = 1;
        if (alpha <= 0)
            alpha = 0.01;
        this.alpha = alpha;
    }

    public double lunaUrmatoare() {
        if (data.size() < 2) {
            throw new IllegalArgumentException("Serie de date insuficiente pentru a face o prognoză.");
        }

        return prognozare(data, 0) / (1 - Math.pow(1 - alpha, data.size()));
    }

    private double prognozare(LinkedHashMap<String, Double> data, int index) {
        if (index == data.size()) {
            return 0;
        }

        double valoare = 0;

        int i = 0;
        for (Entry<String, Double> entry : data.entrySet()) {
            if (i == index) {
                valoare = entry.getValue();
                break;
            }
            i++;
        }

        double prognoza = alpha * Math.pow((1 - alpha), index) * valoare;
        
        return prognoza + prognozare(data, index + 1);
    }

}
