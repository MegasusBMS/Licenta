package app.entities.tratament;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import app.entities.diagnostic.Diagnostic;
import app.entities.medic.Medic;
import app.entities.medicament.Medicament;
import app.entities.medicamentTratament.MedicamentTratament;
import app.entities.tratament.dt.TratamentTable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table
@NoArgsConstructor
@Getter
public class Tratament {
    public Tratament(Medic medic, Diagnostic diagnostic, double pretManopera) {
        this.medic = medic;
        this.diagnostic = diagnostic;
        // dataTratament = new Date();
        Random random = new Random();
        dataTratament = new Date(random.nextLong(
            diagnostic.getDataDiagnostic().getTime(),
            Date.from(Instant.parse("2023-05-30T00:00:00Z")).getTime()));
        this.pretManopera=pretManopera;
    }
    @SequenceGenerator(
        name = "tratament_sequence",
        sequenceName = "tratament_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "tratament_sequence"
    )
    @Id
    private  long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataTratament;
    private double pretManopera;
    @ManyToOne
    private Medic medic;
    @ManyToOne 
    private Diagnostic diagnostic;
    @OneToMany(mappedBy = "tratament")
    private List<MedicamentTratament> medicamente;

    public TratamentTable toTratamentTable(){
        List<String> numeMedicamente = medicamente.stream().map(MedicamentTratament::getMedicament).map(Medicament::getNume).collect(Collectors.toList());
        return new TratamentTable(id,medic.getFullName(), diagnostic.getAnimal().getNumeAnimal(),numeMedicamente,dataTratament,pretManopera);
    }
}
