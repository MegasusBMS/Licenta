package app.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class StatisticGrupResult {
    
    private String grupa;
    private long aparitie;
    private double procentajEsantion;
    private double procentajTotalPerioada;
    private double procentajTotal;
    private double valoareManopere;
    private double valoareTratamente;
    private double valoareTotala;

}
