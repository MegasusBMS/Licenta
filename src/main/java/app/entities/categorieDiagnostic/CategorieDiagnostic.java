package app.entities.categorieDiagnostic;

import java.util.List;

import org.springframework.stereotype.Component;

import app.entities.categorieDiagnostic.dt.CategorieDiagnosticTable;
import app.entities.diagnostic.Diagnostic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class CategorieDiagnostic {
    @Id
    @SequenceGenerator(
        name = "categorie_diagnostic_sequence",
        sequenceName = "categorie_diagnostic_sequence",
        allocationSize = 1
        )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "categorie_diagnostic_sequence"
    )
    private long id;
    private String numeCategorieDiagnostic;
    @OneToMany(mappedBy = "categorieDiagnostic")
    List<Diagnostic> diagnostice;

    public CategorieDiagnostic(String numeCategorieDiagnostic){
        this.numeCategorieDiagnostic=numeCategorieDiagnostic;
    }

    public CategorieDiagnosticTable toCategorieDiagnosticTable(){
        return new CategorieDiagnosticTable(id, numeCategorieDiagnostic);
    }
}
