package app.statistics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import lombok.Getter;

@Getter
public class StatisticContainer{

    private List<StatisticGrupResult> listaStatistici;
    public long aparitieSum;
    public double procentajEsantionSum;
    public double procentajTotalPerioadaSum;
    public double procentajTotalSum;
    public double valoareManopereSum;
    public double valoareTratamenteSum;
    public double valoareTotalaSum;

    public StatisticContainer(){
        listaStatistici = new ArrayList<>();
    }

    public boolean add(StatisticGrupResult e) {

        this.aparitieSum += e.getAparitie();
        this.procentajEsantionSum += e.getProcentajEsantion();
        this.procentajTotalPerioadaSum += e.getProcentajTotalPerioada();
        this.procentajTotalSum += e.getProcentajTotal();
        this.valoareManopereSum += e.getValoareManopere();
        this.valoareTratamenteSum += e.getValoareTratamente();
        this.valoareTotalaSum += e.getValoareTotala();

        return listaStatistici.add(e);
    }

    public boolean addAll(Collection<StatisticGrupResult> c) {
        c.stream().forEach(e -> {
            this.aparitieSum += e.getAparitie();
            this.procentajEsantionSum += e.getProcentajEsantion();
            this.procentajTotalPerioadaSum += e.getProcentajTotalPerioada();
            this.procentajTotalSum += e.getProcentajTotal();
            this.valoareManopereSum += e.getValoareManopere();
            this.valoareTratamenteSum += e.getValoareTratamente();
            this.valoareTotalaSum += e.getValoareTotala();
        });
        return listaStatistici.addAll(c);
    }
}