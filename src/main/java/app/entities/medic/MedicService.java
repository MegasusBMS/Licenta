package app.entities.medic;

import java.util.List;
import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import app.entities.medic.dt.MedicLogIn;
import app.entities.medic.dt.MedicSignIn;
import app.entities.specializare.Specializare;
import app.entities.specializare.SpecializareService;

@Service
public class MedicService {

    private MedicRepository medicRepo;
    private SpecializareService specializareService;

    MedicService(MedicRepository mediRepo, SpecializareService specializareService) {
        this.medicRepo = mediRepo;
        this.specializareService = specializareService;
    }

    public Optional<Medic> LogInMedic(MedicLogIn medic) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "fullname", "specializare");
        Example<Medic> example = Example
                .of(new Medic(Long.parseLong(medic.idParafa()), DigestUtils.sha256Hex(medic.password())), matcher);

        return medicRepo.findOne(example);
    }

    public void createMedicIfNotExist(MedicSignIn medicSignIn) {

        Optional<Specializare> specializare = specializareService
                .getSpecializareById(Long.parseLong(medicSignIn.idSpecializare()));
        if (specializare.isEmpty())
            return;

        Medic medic = new Medic(medicSignIn.fullName(), Long.parseLong(medicSignIn.idParafa()),
                DigestUtils.sha256Hex(medicSignIn.password()), specializare.get());

        if (getMedicByName(medic).isPresent())
            return;
        medicRepo.save(medic);
    }

    public void updateMedic(Medic medic) {
        if (medic.getPassword().equals(""))
            medicRepo.update(medic);
        else{
            String password = DigestUtils.sha256Hex(medic.getPassword());
            medicRepo.updateWithPassword(medic,password);
        }
    }

    public Optional<Medic> getMedicByName(Medic medic) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "idParafa", "password",
                "specializare");
        Example<Medic> example = Example.of(medic, matcher);

        return medicRepo.findOne(example);
    }

    public Optional<Medic> getMedicByName(String numeMedic) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "idParafa", "password",
                "specializare");
        Example<Medic> example = Example.of(new Medic(numeMedic), matcher);

        return medicRepo.findOne(example);
    }

    public Optional<Medic> getMedicByIdParafa(long parafa) {
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnorePaths("id", "fullName", "password",
                "specializare");
        Example<Medic> example = Example.of(new Medic(parafa, null), matcher);

        return medicRepo.findOne(example);
    }

    public Page<Medic> getMedicPage(int page) {
        return medicRepo.findAll(PageRequest.of(page, 10));
    }

    public Optional<Medic> getMedicById(Long id) {
        return Optional.of(medicRepo.getReferenceById(id));
    }

    public List<Medic> getAllMedici() {
        return medicRepo.findAll();
    }

    public Optional<Medic> getMedicFromCookie(String cookie) {
        if (cookie == null)
            return Optional.empty();

        if (cookie.equals(""))
            return Optional.empty();

        long idMedic = 0;

        try {
            idMedic = Long.parseLong(cookie);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        return getMedicByIdParafa(idMedic);
    }
}
