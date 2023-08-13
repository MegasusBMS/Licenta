package app.entities.animal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import app.statistics.StatisticContainer;
import app.statistics.StatisticGrupResult;
import app.utils.StatisticDateSorter;
import jakarta.transaction.Transactional;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAllByDiagnosticeDataDiagnosticBetween(Date min, Date max);

    Page<Animal> findAllByStapanCnpEquals(long cnp, Pageable pageable);

    Page<Animal> findAllByStapanUserNameContains(String userName, Pageable pageable);

    Page<Animal> findAllByNumeAnimalContains(String numeAnimal, Pageable pageable);

    @Query(nativeQuery = true, value = "" +
            "SELECT grupa, COUNT(grupa) AS valoare FROM (" +
            "SELECT " +
            "    CASE " +
            "        WHEN :categorie = 'categorieAnimal' THEN C.numeCategorie " +
            "        WHEN :categorie = 'rasa' THEN R.numeRasa " +
            "        ELSE TIMESTAMPDIFF(YEAR, A.dataNastere, CURDATE()) " +
            "    END AS grupa " +
            "FROM Animal A " +
            "JOIN Rasa_Animal R ON A.rasa_id = R.id " +
            "JOIN Categorie_Animal C ON R.categorieAnimal_id = C.id " +
            ") AS AnimalExtins " +
            "GROUP BY grupa")
    List<Object[]> groupByPropertyWithConditionsNative(String categorie);

    public default StatisticContainer groupByPropertyWithConditionsAsStatisticGrups(String categorie,
            Set<Long> rase, Set<Long> specii, Set<Long> diagnostice, Set<Long> medicamente,
            Set<Long> specificatii, Set<Long> medici, Date startDate, Date endDate, String dateLocation) {
        rase.add(0L);
        specii.add(0L);
        diagnostice.add(0L);
        medicamente.add(0L);
        specificatii.add(0L);
        medici.add(0L);

        StatisticContainer sc = new StatisticContainer();
        List<Object[]> result = new ArrayList<>();
        List<String> tratamente = new ArrayList<>();
        tratamente.add("tratament-luna");tratamente.add("tratament-an");tratamente.add("medic");
        List<String> tratamenteMedicamente = new ArrayList<>();
        tratamenteMedicamente.add("medicament");tratamenteMedicamente.add("medicament-luna");tratamenteMedicamente.add("medicament-an");
        List<String> diagnostice2 = new ArrayList<>();
        diagnostice2.add("diagnostic");diagnostice2.add("categorieDiagnostic");diagnostice2.add("diagnostic-an");diagnostice2.add("diagnostic-luna");
        if(tratamente.contains(categorie)){
                result = groupByPropertyWithConditionsForTratamente(categorie, rase, rase.size(), specii, specii.size(),
                        diagnostice, diagnostice.size(), medicamente, medicamente.size(), specificatii, specificatii.size(),
                        medici, medici.size(), startDate, endDate, dateLocation);
                result.stream()
                        .map(row -> new StatisticGrupResult((String) row[0], (long) row[1],
                                ((Float) row[2]).doubleValue()/100,((Float)  row[3]).doubleValue()/100,
                                ((Float) row[4]).doubleValue()/100,(double) (row[5]!=null ? row[5] : 0.0),
                                (double) (row[6]!=null ? row[6] : 0.0), (double) (row[5]!=null ? row[5] : 0.0) + (double) (row[6]!=null ? row[6] : 0.0)))
                        .forEach(element -> sc.add(element));

                if(categorie.contains("-luna") || categorie.contains("-an"))
                        StatisticDateSorter.sortByDateString(sc);
                return sc;
        }

        if(tratamenteMedicamente.contains(categorie)){
                result = groupByPropertyWithConditionsForMedicamente(categorie, rase, rase.size(), specii, specii.size(),
                        diagnostice, diagnostice.size(), medicamente, medicamente.size(), specificatii, specificatii.size(),
                        medici, medici.size(), startDate, endDate, dateLocation);
                result.stream()
                        .map(row -> new StatisticGrupResult((String) row[0], (long) row[1],
                                ((Float) row[2]).doubleValue()/100,((Float)  row[3]).doubleValue()/100,
                                ((Float) row[4]).doubleValue()/100,(double) (row[5]!=null ? row[5] : 0.0),
                                (double) (row[6]!=null ? row[6] : 0.0), (double) (row[5]!=null ? row[5] : 0.0) + (double) (row[6]!=null ? row[6] : 0.0)))
                        .forEach(element -> sc.add(element));

                if(categorie.contains("-luna")  || categorie.contains("-an"))
                        StatisticDateSorter.sortByDateString(sc);
                return sc;
        }

        if(diagnostice2.contains(categorie)){
                result = groupByPropertyWithConditionsForDiagnostice(categorie, rase, rase.size(), specii, specii.size(),
                        diagnostice, diagnostice.size(), medicamente, medicamente.size(), specificatii, specificatii.size(),
                        medici, medici.size(), startDate, endDate, dateLocation);
                result.stream()
                        .map(row -> new StatisticGrupResult((String) row[0], (long) row[1],
                                ((Float) row[2]).doubleValue()/100,((Float)  row[3]).doubleValue()/100,
                                ((Float) row[4]).doubleValue()/100,(double) (row[5]!=null ? row[5] : 0.0),
                                (double) (row[6]!=null ? row[6] : 0.0), (double) (row[5]!=null ? row[5] : 0.0) + (double) (row[6]!=null ? row[6] : 0.0)))
                        .forEach(element -> sc.add(element));

                if(categorie.contains("-luna")  || categorie.contains("-an"))
                        StatisticDateSorter.sortByDateString(sc);
                return sc;
        }


                result = groupByPropertyWithConditionsForAnimale(categorie, rase, rase.size(), specii, specii.size(),
                                diagnostice, diagnostice.size(), medicamente, medicamente.size(), specificatii, specificatii.size(),
                                medici, medici.size(), startDate, endDate, dateLocation);
                result.stream()
                        .map(row -> new StatisticGrupResult((String) row[0], (long) row[1],
                                ((Float) row[2]).doubleValue()/100,((Float)  row[3]).doubleValue()/100,
                                ((Float) row[4]).doubleValue()/100,(double) (row[5]!=null ? row[5] : 0.0),
                                (double) (row[6]!=null ? row[6] : 0.0), (double) (row[5]!=null ? row[5] : 0.0) + (double) (row[6]!=null ? row[6] : 0.0)))
                        .forEach(element -> sc.add(element));

        if(categorie.equals("luna"))
                StatisticDateSorter.sortByDateString(sc);

        return sc;

    }

    /*
     * @Query("SELECT " +
     * "   CASE " +
     * "       WHEN :categorie = 'categorieAnimal' THEN rasa.categorieAnimal.numeCategorie "
     * +
     * "       WHEN :categorie = 'rasa' THEN rasa.numeRasa " +
     * "       ELSE FUNCTION('TIMESTAMPDIFF', YEAR, animal.dataNastere, CURRENT_DATE()) "
     * +
     * "   END AS grupa, " +
     * "   COUNT(*) AS valoare, " +
     * "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Animal ) AS procentajEsantion, "
     * +
     * "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Animal) AS procentajTotal " +
     * "FROM Animal animal " +
     * "JOIN animal.rasa rasa " +
     * "Where (:rase IS EMPTY OR rasa.id IN :rase) " +
     * "AND (:specii IS EMPTY OR rasa.categorieAnimal.id IN :specii) " +
     * //
     * "AND (:diagnostice IS EMPTY OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE d.numeDiagnostic IN :diagnostice)) "
     * +
     * //
     * "AND (:medicamente IS EMPTY OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE EXISTS (SELECT 1 FROM t.medicamente m WHERE m.medicament.id IN :medicamente)))) "
     * +
     * //
     * "AND (:specializari IS EMPTY OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.medic.specializare.id IN :specializari))) "
     * +
     * //
     * "AND (:medici IS EMPTY OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.medic.id IN :medici))) "
     * +
     * "GROUP BY grupa")
     */
    @Query("SELECT " +
    "   CASE " +
    "       WHEN :categorie = 'categorieAnimal' THEN rasa.categorieAnimal.numeCategorie " +
    "       WHEN :categorie = 'rasa' THEN rasa.numeRasa || ' - ' || rasa.categorieAnimal.numeCategorie  " +
    "       ELSE FUNCTION('TIMESTAMPDIFF', YEAR, animal.dataNastere, CURRENT_DATE()) " +
    "   END AS grupa, " +
    "   COUNT(*) AS valoare, " +
    "   (COUNT(*) * 100.0) / " +
    "       (SELECT COUNT(*) FROM Animal animal2 " +
    "        JOIN animal2.rasa rasa " +
    "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
    "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
    "          AND (:sizeDiagnostice < 2 OR EXISTS(SELECT 1 FROM animal2.diagnostice d WHERE d.categorieDiagnostic.id IN (:diagnostice))) " +
    "          AND (:sizeMedicamente < 2 OR EXISTS (SELECT 1 FROM animal2.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE EXISTS (SELECT 1 FROM t.medicamente m WHERE m.medicament.id IN :medicamente)))) " +
    "          AND (:sizeSpecializari < 2 OR EXISTS (SELECT 1 FROM animal2.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.medic.specializare.id IN :specializari))) " +
    "          AND (:sizeMedici < 2 OR EXISTS (SELECT 1 FROM animal2.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.medic.id IN :medici))) " +
    "          AND ((:dateType = 'tratament' AND EXISTS (SELECT 1 FROM animal2.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.dataTratament BETWEEN :startDate AND :endDate))) " +
    "               OR (:dateType = 'diagnostic' AND EXISTS (SELECT 1 FROM animal2.diagnostice d WHERE d.dataDiagnostic BETWEEN :startDate AND :endDate)) " +
    "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS procentajEsantion, " +
    "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Animal animal " +
    "       WHERE (:dateType = 'tratament' AND EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.dataTratament BETWEEN :startDate AND :endDate)) " +
    "               OR (:dateType = 'diagnostic' AND EXISTS (SELECT 1 FROM animal.diagnostice d WHERE d.dataDiagnostic BETWEEN :startDate AND :endDate)) " +
    "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS procentajTotalInPerioada, " +
    "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Animal) AS procentajTotal, " +
    "   (SELECT SUM(tratamente.pretManopera) FROM Animal A " +
    "        JOIN A.rasa rasa " +
    "        JOIN A.diagnostice diagnostice " +
    "        JOIN diagnostice.tratamente tratamente " +
    "        JOIN tratamente.medicamente medicamente " +
    "        JOIN medicamente.medicament medicament "+
    "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) "+
    "          AND (( :categorie = 'categorieAnimal' AND A.rasa.categorieAnimal.id = animal.rasa.categorieAnimal.id) " +
    "          OR ( :categorie = 'rasa' AND A.rasa.id = animal.rasa.id) " +
    "          OR ( :categorie NOT IN ('categorieAnimal', 'rasa') AND FUNCTION ('TIMESTAMPDIFF', YEAR, A.dataNastere, CURRENT_DATE()) = FUNCTION('TIMESTAMPDIFF', YEAR, animal.dataNastere, CURRENT_DATE()))) " +
    "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
    "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
    "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
    "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
    "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
    "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
    "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
    "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareManoperi, " +
    "   (SELECT SUM(medicament.pret) FROM Animal A " +
    "        JOIN A.rasa rasa " +
    "        JOIN A.diagnostice diagnostice " +
    "        JOIN diagnostice.tratamente tratamente " +
    "        JOIN tratamente.medicamente medicamente " +
    "        JOIN medicamente.medicament medicament "+
    "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
    "          AND (( :categorie = 'categorieAnimal' AND A.rasa.categorieAnimal.id = animal.rasa.categorieAnimal.id) " +
    "          OR ( :categorie = 'rasa' AND A.rasa.id = animal.rasa.id) " +
    "          OR ( :categorie NOT IN ('categorieAnimal', 'rasa') AND FUNCTION ('TIMESTAMPDIFF', YEAR, A.dataNastere, CURRENT_DATE()) = FUNCTION('TIMESTAMPDIFF', YEAR, animal.dataNastere, CURRENT_DATE()))) " +
    "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
    "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
    "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
    "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
    "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
    "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
    "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
    "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareMedicamente " +
    "FROM Animal animal " +
    "JOIN animal.rasa rasa " +
    "WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
    "   AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
    "   AND (:sizeDiagnostice < 2 OR EXISTS(SELECT 1 FROM animal.diagnostice d WHERE d.categorieDiagnostic.id IN (:diagnostice))) " +
    "   AND (:sizeMedicamente < 2 OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE EXISTS (SELECT 1 FROM t.medicamente m WHERE m.medicament.id IN :medicamente)))) " +
    "   AND (:sizeSpecializari < 2 OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.medic.specializare.id IN :specializari))) " +
    "   AND (:sizeMedici < 2 OR EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.medic.id IN :medici))) " +
    "   AND ((:dateType = 'tratament' AND EXISTS (SELECT 1 FROM animal.diagnostice d WHERE EXISTS (SELECT 1 FROM d.tratamente t WHERE t.dataTratament BETWEEN :startDate AND :endDate))) " +
    "        OR (:dateType = 'diagnostic' AND EXISTS (SELECT 1 FROM animal.diagnostice d WHERE d.dataDiagnostic BETWEEN :startDate AND :endDate)) " +
    "        OR :dateType NOT IN ('tratament', 'diagnostic')) " +
    "GROUP BY grupa " +
    "ORDER BY CASE WHEN :categorie = 'rasa' THEN rasa.categorieAnimal.numeCategorie END ASC"
    )
    List<Object[]> groupByPropertyWithConditionsForAnimale(String categorie, Set<Long> rase, int sizeRase, Set<Long> specii,
            int sizeSpecii, Set<Long> diagnostice, int sizeDiagnostice, Set<Long> medicamente,
            int sizeMedicamente, Set<Long> specializari, int sizeSpecializari, Set<Long> medici,
            int sizeMedici, Date startDate, Date endDate, String dateType);

            @Query("SELECT " +
            "   CASE " +
            "       WHEN :categorie = 'diagnostic' THEN diagnostic.numeDiagnostic " +
            "       WHEN :categorie = 'categorieDiagnostic' THEN categorie.numeCategorieDiagnostic " +
            "       WHEN :categorie = 'diagnostic-luna' THEN CONCAT(MONTHNAME(diagnostic.dataDiagnostic), '-', YEAR(diagnostic.dataDiagnostic))  " +
            "       WHEN :categorie = 'diagnostic-an' THEN YEAR(diagnostic.dataDiagnostic) " +
            "   END AS grupa, " +
            "   COUNT(*) AS valoare, " +
            "   (COUNT(*) * 100.0) / " +
            "       (SELECT COUNT(*) FROM Animal animal2 " +
            "        JOIN animal2.rasa rasa " +
            "        JOIN animal2.diagnostice diagnostice " +
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN (:diagnostice)) " +
            "          AND (:sizeMedicamente < 2 OR EXISTS (SELECT 1 FROM diagnostice.tratamente t WHERE EXISTS (SELECT 1 FROM t.medicamente m WHERE m.medicament.id IN :medicamente))) " +
            "          AND (:sizeSpecializari < 2 OR EXISTS (SELECT 1 FROM diagnostice.tratamente t WHERE t.medic.specializare.id IN :specializari)) " +
            "          AND (:sizeMedici < 2 OR EXISTS (SELECT 1 FROM diagnostice.tratamente t WHERE t.medic.id IN :medici)) " +
            "          AND ((:dateType = 'tratament' AND EXISTS (SELECT 1 FROM diagnostice.tratamente t WHERE t.dataTratament BETWEEN :startDate AND :endDate)) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS procentajEsantion, " +
            "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Diagnostic diagnostice " +
            "       WHERE (:dateType = 'tratament' AND EXISTS (SELECT 1 FROM diagnostice.tratamente t WHERE t.dataTratament BETWEEN :startDate AND :endDate)) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic')) AS procentajTotalInPerioada, " +
            "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Diagnostic) AS procentajTotal, " +
            "   (SELECT SUM(tratamente.pretManopera) FROM Animal A " +
            "        JOIN A.rasa rasa " +
            "        JOIN A.diagnostice diagnostice " +
            "        JOIN diagnostice.tratamente tratamente " +
            "        JOIN tratamente.medicamente medicamente " +
            "        JOIN medicamente.medicament medicament "+
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) "+
            "          AND (( :categorie = 'diagnostic' AND diagnostice.numeDiagnostic = diagnostic.numeDiagnostic) " +
            "          OR ( :categorie = 'categorieDiagnostic' AND diagnostice.categorieDiagnostic.id = diagnostic.categorieDiagnostic.id) " +
            "          OR ( :categorie = 'diagnostic-luna' AND CONCAT(FUNCTION('MONTHNAME', diagnostice.dataDiagnostic), '-', FUNCTION('YEAR', diagnostice.dataDiagnostic)) = CONCAT(FUNCTION('MONTHNAME', diagnostic.dataDiagnostic), '-', FUNCTION('YEAR', diagnostic.dataDiagnostic))) " +
            "          OR ( :categorie = 'diagnostic-an' AND YEAR(diagnostice.dataDiagnostic) = YEAR(diagnostice.dataDiagnostic))) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
            "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareManoperi, " +
            "   (SELECT SUM(medicament.pret) FROM Animal A " +
            "        JOIN A.rasa rasa " +
            "        JOIN A.diagnostice diagnostice " +
            "        JOIN diagnostice.tratamente tratamente " +
            "        JOIN tratamente.medicamente medicamente " +
            "        JOIN medicamente.medicament medicament "+
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "          AND (( :categorie = 'diagnostic' AND diagnostice.numeDiagnostic = diagnostic.numeDiagnostic) " +
            "          OR ( :categorie = 'categorieDiagnostic' AND diagnostice.categorieDiagnostic.id = diagnostic.categorieDiagnostic.id) " +
            "          OR ( :categorie = 'diagnostic-luna' AND CONCAT(FUNCTION('MONTHNAME', diagnostice.dataDiagnostic), '-', FUNCTION('YEAR', diagnostice.dataDiagnostic)) = CONCAT(FUNCTION('MONTHNAME', diagnostic.dataDiagnostic), '-', FUNCTION('YEAR', diagnostic.dataDiagnostic))) " +
            "          OR ( :categorie = 'diagnostic-an' AND YEAR(diagnostice.dataDiagnostic) = YEAR(diagnostice.dataDiagnostic))) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
            "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareMedicamente " +
            "FROM Animal animal " +
            "JOIN animal.rasa rasa " +
            "JOIN animal.diagnostice diagnostic " +
            "JOIN diagnostic.categorieDiagnostic categorie "+
            "WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "   AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
            "   AND (:sizeDiagnostice < 2 OR categorie.id IN (:diagnostice)) " +
            "   AND (:sizeMedicamente < 2 OR EXISTS (SELECT 1 FROM diagnostic.tratamente t WHERE EXISTS (SELECT 1 FROM t.medicamente m WHERE m.medicament.id IN :medicamente))) " +
            "   AND (:sizeSpecializari < 2 OR EXISTS (SELECT 1 FROM diagnostic.tratamente t WHERE t.medic.specializare.id IN :specializari)) " +
            "   AND (:sizeMedici < 2 OR EXISTS (SELECT 1 FROM diagnostic.tratamente t WHERE t.medic.id IN :medici)) " +
            "   AND ((:dateType = 'tratament' AND EXISTS (SELECT 1 FROM diagnostic.tratamente t WHERE t.dataTratament BETWEEN :startDate AND :endDate)) " +
            "        OR (:dateType = 'diagnostic' AND diagnostic.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "        OR :dateType NOT IN ('tratament', 'diagnostic')) " +
            "GROUP BY grupa")
            List<Object[]> groupByPropertyWithConditionsForDiagnostice(String categorie, Set<Long> rase, int sizeRase, Set<Long> specii,
                    int sizeSpecii, Set<Long> diagnostice, int sizeDiagnostice, Set<Long> medicamente,
                    int sizeMedicamente, Set<Long> specializari, int sizeSpecializari, Set<Long> medici,
                    int sizeMedici, Date startDate, Date endDate, String dateType);

            @Query("SELECT " +
            "   CASE " +
            "       WHEN :categorie = 'medic' THEN medic.fullName " +
            "       WHEN :categorie = 'tratament-luna' THEN CONCAT(MONTHNAME(tratament.dataTratament), '-', YEAR(tratament.dataTratament)) " +
            "       WHEN :categorie = 'tratament-an' THEN YEAR(tratament.dataTratament) " +
            "   END AS grupa, " +
            "   COUNT(*) AS valoare, " +
            "   (COUNT(*) * 100.0) / " +
            "       (SELECT COUNT(*) FROM Animal animal " +
            "        JOIN animal.rasa rasa " +
            "        JOIN animal.diagnostice diagnostic " +
            "        JOIN diagnostic.tratamente tratament " +
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostic.categorieDiagnostic.id IN (:diagnostice)) " +
            "          AND (:sizeMedicamente < 2 OR EXISTS (SELECT 1 FROM tratament.medicamente m WHERE m.medicament.id IN :medicamente)) " +
            "          AND (:sizeSpecializari < 2 OR tratament.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratament.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratament.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostic.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS procentajEsantion, " +
            "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Animal animal " +
            "        JOIN animal.rasa rasa " +
            "        JOIN animal.diagnostice diagnostic " +
            "        JOIN diagnostic.tratamente tratament " +
            "       WHERE (:dateType = 'tratament' AND tratament.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostic.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic')) AS procentajTotalInPerioada, " +
            "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Tratament ) AS procentajTotal, " +
            "   (SELECT SUM(tratamente.pretManopera) FROM Animal A " +
            "        JOIN A.rasa rasa " +
            "        JOIN A.diagnostice diagnostice " +
            "        JOIN diagnostice.tratamente tratamente " +
            "        JOIN tratamente.medicamente medicamente " +
            "        JOIN medicamente.medicament medicament "+
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) "+
            "          AND (( :categorie = 'medic' AND tratamente.medic.id = medic.id) " +
            "          OR (:categorie = 'tratament-luna' AND CONCAT(FUNCTION('MONTHNAME', tratamente.dataTratament), '-', FUNCTION('YEAR', tratamente.dataTratament)) = CONCAT(FUNCTION('MONTHNAME', tratament.dataTratament), '-', FUNCTION('YEAR', tratament.dataTratament))) " +
            "          OR ( :categorie = 'tratament-an' AND YEAR(tratamente.dataTratament) = YEAR(tratament.dataTratament))) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
            "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareManoperi, " +
            "   (SELECT SUM(medicament.pret) FROM Animal A " +
            "        JOIN A.rasa rasa " +
            "        JOIN A.diagnostice diagnostice " +
            "        JOIN diagnostice.tratamente tratamente " +
            "        JOIN tratamente.medicamente medicamente " +
            "        JOIN medicamente.medicament medicament "+
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "          AND (( :categorie = 'medic' AND tratamente.medic.id = medic.id) " +
            "          OR (:categorie = 'tratament-luna' AND CONCAT(FUNCTION('MONTHNAME', tratamente.dataTratament), '-', FUNCTION('YEAR', tratamente.dataTratament)) = CONCAT(FUNCTION('MONTHNAME', tratament.dataTratament), '-', FUNCTION('YEAR', tratament.dataTratament))) " +
            "          OR ( :categorie = 'tratament-an' AND YEAR(tratamente.dataTratament) = YEAR(tratament.dataTratament))) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
            "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareMedicamente " +
            "FROM Animal animal " +
            "JOIN animal.rasa rasa " +
            "JOIN animal.diagnostice diagnostic " +
            "JOIN diagnostic.tratamente tratament " +
            "JOIN tratamente.medic medic " +
            "WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "   AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
            "   AND (:sizeDiagnostice < 2 OR diagnostic.categorieDiagnostic.id IN (:diagnostice)) " +
            "   AND (:sizeMedicamente < 2 OR EXISTS (SELECT 1 FROM tratament.medicamente m WHERE m.medicament.id IN :medicamente)) " +
            "   AND (:sizeSpecializari < 2 OR tratament.medic.specializare.id IN :specializari) " +
            "   AND (:sizeMedici < 2 OR tratament.medic.id IN :medici) " +
            "   AND ((:dateType = 'tratament' AND tratament.dataTratament BETWEEN :startDate AND :endDate) " +
            "        OR (:dateType = 'diagnostic' AND diagnostic.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "        OR :dateType NOT IN ('tratament', 'diagnostic')) " +
            "GROUP BY grupa")
            List<Object[]> groupByPropertyWithConditionsForTratamente(String categorie, Set<Long> rase, int sizeRase, Set<Long> specii,
                    int sizeSpecii, Set<Long> diagnostice, int sizeDiagnostice, Set<Long> medicamente,
                    int sizeMedicamente, Set<Long> specializari, int sizeSpecializari, Set<Long> medici,
                    int sizeMedici, Date startDate, Date endDate, String dateType);

                    @Query("SELECT " +
            "   CASE " +
            "       WHEN :categorie = 'medicament-luna' THEN CONCAT(MONTHNAME(tratament.dataTratament), '-', YEAR(tratament.dataTratament)) " +
            "       WHEN :categorie = 'medicament'  THEN medicament.nume || ' - ' || medicament.categorieMedicament.numeCategorieMedicament " +
            "       WHEN :categorie = 'medicament-an' THEN YEAR(tratament.dataTratament) " +
            "   END AS grupa, " +
            "   COUNT(*) AS valoare, " +
            "   (COUNT(*) * 100.0) / " +
            "       (SELECT COUNT(*) FROM Animal animal2 " +
            "        JOIN animal2.diagnostice diagnostic2 " +
            "        JOIN diagnostic2.tratamente tratament2 " +
            "        JOIN tratament2.medicamente M " +
            "        JOIN M.medicament medicament2 " +
            "        WHERE (:sizeRase < 2 OR animal.rasa.id IN (:rase)) " +
            "          AND (:sizeSpecii < 2 OR animal2.rasa.categorieAnimal.id IN (:specii)) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostic2.categorieDiagnostic.id IN (:diagnostice)) " +
            "          AND (:sizeMedicamente < 2 OR medicament2.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratament2.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratament2.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratament2.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostic2.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS procentajEsantion, " +
            "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM Animal animal " +
            "        JOIN animal.rasa rasa " +
            "        JOIN animal.diagnostice diagnostic " +
            "        JOIN diagnostic.tratamente tratament " +
            "        JOIN tratamente.medicamente M " +
            "        JOIN M.medicament medicament " +
            "       WHERE (:dateType = 'tratament' AND tratament.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostic.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic')) AS procentajTotalInPerioada, " +
            "   (COUNT(*) * 100.0) / (SELECT COUNT(*) FROM MedicamentTratament ) AS procentajTotal, " +
            "   (SELECT SUM(tratamente.pretManopera) FROM Animal A " +
            "        JOIN A.rasa rasa " +
            "        JOIN A.diagnostice diagnostice " +
            "        JOIN diagnostice.tratamente tratamente " +
            "        JOIN tratamente.medicamente medicamente " +
            "        JOIN medicamente.medicament medicament2 "+
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) "+
            "          AND (( :categorie = 'medicament' AND medicament2.id = medicament.id) " +
            "          OR (:categorie = 'medicament-luna' AND CONCAT(FUNCTION('MONTHNAME', tratamente.dataTratament), '-', FUNCTION('YEAR', tratamente.dataTratament)) = CONCAT(FUNCTION('MONTHNAME', tratament.dataTratament), '-', FUNCTION('YEAR', tratament.dataTratament))) " +
            "          OR ( :categorie = 'medicament-an' AND YEAR(tratamente.dataTratament) = YEAR(tratament.dataTratament))) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
            "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareManoperi, " +
            "   (SELECT SUM(medicament2.pret) FROM Animal A " +
            "        JOIN A.rasa rasa " +
            "        JOIN A.diagnostice diagnostice " +
            "        JOIN diagnostice.tratamente tratamente " +
            "        JOIN tratamente.medicamente medicamente " +
            "        JOIN medicamente.medicament medicament2 "+
            "        WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "          AND (( :categorie = 'medicament' AND medicament2.id = medicament.id) " +
            "          OR (:categorie = 'medicament-luna' AND CONCAT(FUNCTION('MONTHNAME', tratamente.dataTratament), '-', FUNCTION('YEAR', tratamente.dataTratament)) = CONCAT(FUNCTION('MONTHNAME', tratament.dataTratament), '-', FUNCTION('YEAR', tratament.dataTratament))) " +
            "          OR ( :categorie = 'medicament-an' AND YEAR(tratamente.dataTratament) = YEAR(tratament.dataTratament))) " +
            "          AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN :specii) " +
            "          AND (:sizeDiagnostice < 2 OR diagnostice.categorieDiagnostic.id IN :diagnostice) " +
            "          AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "          AND (:sizeSpecializari < 2 OR tratamente.medic.specializare.id IN :specializari) " +
            "          AND (:sizeMedici < 2 OR tratamente.medic.id IN :medici) " +
            "          AND ((:dateType = 'tratament' AND tratamente.dataTratament BETWEEN :startDate AND :endDate) " +
            "               OR (:dateType = 'diagnostic' AND diagnostice.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "               OR :dateType NOT IN ('tratament', 'diagnostic'))) AS ValoareMedicamente " +
            "FROM Animal animal " +
            "JOIN animal.rasa rasa " +
            "JOIN animal.diagnostice diagnostic " +
            "JOIN diagnostic.tratamente tratament " +
            "JOIN tratamente.medicamente M " +
            "JOIN M.medicament medicament " +
            "WHERE (:sizeRase < 2 OR rasa.id IN (:rase)) " +
            "   AND (:sizeSpecii < 2 OR rasa.categorieAnimal.id IN (:specii)) " +
            "   AND (:sizeDiagnostice < 2 OR diagnostic.categorieDiagnostic.id IN (:diagnostice)) " +
            "   AND (:sizeMedicamente < 2 OR medicament.id IN :medicamente) " +
            "   AND (:sizeSpecializari < 2 OR tratament.medic.specializare.id IN :specializari) " +
            "   AND (:sizeMedici < 2 OR tratament.medic.id IN :medici) " +
            "   AND ((:dateType = 'tratament' AND tratament.dataTratament BETWEEN :startDate AND :endDate) " +
            "        OR (:dateType = 'diagnostic' AND diagnostic.dataDiagnostic BETWEEN :startDate AND :endDate) " +
            "        OR :dateType NOT IN ('tratament', 'diagnostic')) " +
            "GROUP BY grupa " +
            "ORDER BY CASE WHEN :categorie = 'medicament' THEN medicament.categorieMedicament.numeCategorieMedicament END ASC")
            List<Object[]> groupByPropertyWithConditionsForMedicamente(String categorie, Set<Long> rase, int sizeRase, Set<Long> specii,
                    int sizeSpecii, Set<Long> diagnostice, int sizeDiagnostice, Set<Long> medicamente,
                    int sizeMedicamente, Set<Long> specializari, int sizeSpecializari, Set<Long> medici,
                    int sizeMedici, Date startDate, Date endDate, String dateType);

    @Transactional
    @Modifying
    @Query("UPDATE Animal a SET a.numeAnimal = :#{#animal.numeAnimal}, a.dataNastere = :#{#animal.dataNastere}, a.stapan = :#{#animal.stapan}, a.rasa =:#{#animal.rasa} WHERE a.id= :#{#animal.id}")
    public void update(@Param("animal") Animal animal);
}