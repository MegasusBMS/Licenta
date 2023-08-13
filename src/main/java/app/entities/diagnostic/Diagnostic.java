package app.entities.diagnostic;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import app.entities.animal.Animal;
import app.entities.categorieDiagnostic.CategorieDiagnostic;
import app.entities.diagnostic.dt.DiagnosticTable;
import app.entities.tratament.Tratament;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Entity
@Table(indexes = @Index(columnList = "dataDiagnostic"))
@NoArgsConstructor
@Getter
public class Diagnostic {
    @Id
    @SequenceGenerator(
        name = "diagnostic_sequence",
        sequenceName = "diagnostic_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "diagnostic_sequence"
    )
    private long id;
    private String numeDiagnostic;
    @Column(length = 1000)
    private String specificatiiDiagnostic;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataDiagnostic;
    @ManyToOne
    private Animal animal;
    @ManyToOne
    private CategorieDiagnostic categorieDiagnostic;
    @OneToMany(mappedBy = "diagnostic", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Tratament> tratamente;

    @Deprecated
    public Diagnostic(String numeDiagnostic, String specificatiiDiagnostic,Animal animal,CategorieDiagnostic categorieDiagnostic){
        this.numeDiagnostic=numeDiagnostic;
        this.specificatiiDiagnostic = specificatiiDiagnostic;
        this.animal = animal;
        this.categorieDiagnostic = categorieDiagnostic;
        // dataDiagnostic = new Date();
        Random random = new Random();
        dataDiagnostic = new Date(random.nextLong(
            Date.from(Instant.parse("2022-01-01T00:00:00Z")).getTime(),
            Date.from(Instant.parse("2023-05-30T00:00:00Z")).getTime()));
    }

    public DiagnosticTable toDiagnosticTable(){
        String data = new SimpleDateFormat("yyyy-MM-dd").format(dataDiagnostic);
        return new DiagnosticTable(id, numeDiagnostic, categorieDiagnostic.getNumeCategorieDiagnostic(), data,animal.getNumeAnimal(),animal.getId());
    }

}
