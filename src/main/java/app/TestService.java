package app;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import app.entities.animal.Animal;
import app.entities.animal.AnimalService;
import app.entities.animal.dt.AnimalInput;
import app.entities.categorie.CategorieAnimal;
import app.entities.categorie.CategorieAnimalService;
import app.entities.categorieDiagnostic.CategorieDiagnosticService;
import app.entities.categorieDiagnostic.dt.CategorieDiagnosticInput;
import app.entities.categorieMedicament.CategorieMedicamentService;
import app.entities.categorieMedicament.dt.CategorieMedicamentInput;
import app.entities.diagnostic.Diagnostic;
import app.entities.diagnostic.DiagnosticService;
import app.entities.diagnostic.dt.DiagnosticInput;
import app.entities.medic.Medic;
import app.entities.medic.MedicService;
import app.entities.medic.dt.MedicSignIn;
import app.entities.medicament.Medicament;
import app.entities.medicament.MedicamentService;
import app.entities.medicament.dt.MedicamentInput;
import app.entities.rasa.RasaAnimalService;
import app.entities.rasa.dt.RasaAnimalInput;
import app.entities.specializare.SpecializareService;
import app.entities.specializare.dt.SpecializareInput;
import app.entities.tratament.TratamentService;
import app.entities.tratament.dt.TratamentInput;
import app.entities.user.User;
import app.entities.user.UserService;
import app.entities.user.dt.UserInput;

@Deprecated
//  @Service
public class TestService {

	MedicService medicService;
	CategorieAnimalService categorieAnimalService;
	RasaAnimalService rasaAnimalService;
	UserService userService;
	AnimalService animalService;
	MedicamentService medicamentService;
	TratamentService tratamentService;
	DiagnosticService diagnosticService;
	CategorieDiagnosticService categorieDiagnosticService;
	CategorieMedicamentService categorieMedicamentService;
	SpecializareService specializareService;

	TestService(MedicService medicService, CategorieMedicamentService categorieMedicamentService,
			SpecializareService specializareService,
			CategorieAnimalService categorieAnimalService,
			CategorieDiagnosticService categorieDiagnosticService, DiagnosticService diagnosticService,
			RasaAnimalService rasaAnimalService, UserService userService, AnimalService animalService,
			MedicamentService medicamentService, TratamentService tratamentService) {
		this.medicService = medicService;
		this.categorieAnimalService = categorieAnimalService;
		this.rasaAnimalService = rasaAnimalService;
		this.userService = userService;
		this.animalService = animalService;
		this.medicamentService = medicamentService;
		this.tratamentService = tratamentService;
		this.diagnosticService = diagnosticService;
		this.categorieDiagnosticService = categorieDiagnosticService;
		this.categorieMedicamentService = categorieMedicamentService;
		this.specializareService = specializareService;
		test();
		addAnimale();
		addDiagnostice();
		addTratamente();
	}

	public void test() {

		// specializari

		specializareService.createSpecializare(new SpecializareInput("Cardiologie"));
		specializareService.createSpecializare(new SpecializareInput("Medicina Interna"));
		specializareService.createSpecializare(new SpecializareInput("Neurologie"));
		specializareService.createSpecializare(new SpecializareInput("Oftalmologie"));
		specializareService.createSpecializare(new SpecializareInput("Stomatologie"));

		// medici
		medicService.createMedicIfNotExist(new MedicSignIn("Boieteanu Florin", "21111", "asd", "1"));
		medicService.createMedicIfNotExist(new MedicSignIn("Grabril Alex", "11111", "asd", "2"));
		medicService.createMedicIfNotExist(new MedicSignIn("Goku Mirela", "31112", "asd", "2"));
		medicService.createMedicIfNotExist(new MedicSignIn("Balan Eugen", "81111", "asd", "2"));
		medicService.createMedicIfNotExist(new MedicSignIn("Popescu Gabriela", "71111", "asd", "2"));
		medicService.createMedicIfNotExist(new MedicSignIn("Dobrescu Ionut", "31111", "asd", "1"));
		medicService.createMedicIfNotExist(new MedicSignIn("Dedescu Cornel", "71113", "asd", "4"));
		medicService.createMedicIfNotExist(new MedicSignIn("Carlig Alex", "91111", "asd", "3"));
		medicService.createMedicIfNotExist(new MedicSignIn("Popescu Mihai", "11112", "asd", "5"));
		medicService.createMedicIfNotExist(new MedicSignIn("Stoicescu David", "11113", "asd", "4"));

		// categorii animale
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Caine"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Pisica"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Iepure"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Arici"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Porumbel"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Chinchilla"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Hamster"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Perus"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Papagal"));
		categorieAnimalService.createCategorieIfNotExist(new CategorieAnimal("Sarpe"));

		// rase animal
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Bichon Maltez", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Bichon Frise", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Beagle", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Boxer German", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Brac German", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Bulldog Francez", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cane Corso", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Chihuahua", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ciobanesc German", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ciobanesc Belgian", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Dalmatian", "1"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Exotic Shorthair", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ragdoll", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("British Shorthair", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Persana", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Main Coon", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("American Shorthair", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Scottish Fold", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Sphynx", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Abyssinian", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Devon rex", "2"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Flander", "3"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("German", "3"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Chinchilla", "3"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Berbec", "3"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("African", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Asiatic", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("European", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Amur", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Burta Goala", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Brandt", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Daurian", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Hugh", "4"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Donek", "5"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Ghent Owl", "5"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Strasser", "5"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Gascogne", "5"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Long-Tail", "6"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Short-Tail", "6"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Syrian", "7"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Winter White", "7"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Campbell's Dwarf", "7"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Roborovski", "7"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Chinese", "7"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Fallow", "8"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cinnamon", "8"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Motat", "8"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Opalin", "8"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Laurel", "8"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("African Grey", "9"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cockatoos", "9"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Macaws", "9"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Parrotlet", "9"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Senegal", "9"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Cobra", "10"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Python", "10"));
		rasaAnimalService.createRasaIfNotExist(new RasaAnimalInput("Viper", "10"));

		// Utilizatori
		userService.createUser(new UserInput(1941020410827L, "Angelina Mihaela", "asd"));
		userService.createUser(new UserInput(1941020447421L, "Alexandru Bogdan", "asd"));
		userService.createUser(new UserInput(1941020441070L, "Mihail David", "asd"));
		userService.createUser(new UserInput(1941020447722L, "Soica Alex", "asd"));
		userService.createUser(new UserInput(1941020446686L, "Draghici Gabriel", "asd"));
		userService.createUser(new UserInput(1941020447323L, "Boieteanu Florin", "asd"));
		userService.createUser(new UserInput(1941020410828L, "Jhon Jhon", "asd"));
		userService.createUser(new UserInput(1941020447424L, "Jhon Jonson", "asd"));
		userService.createUser(new UserInput(1941020441010L, "Jonson Jonson", "asd"));
		userService.createUser(new UserInput(1941020447711L, "Jonson Jhon", "asd"));
		userService.createUser(new UserInput(1941020446612L, "Jhonny Jhon", "asd"));
		userService.createUser(new UserInput(1941020447313L, "Jhonny Jonson", "asd"));
		userService.createUser(new UserInput(1941020410814L, "Jonson Jhonny", "asd"));
		userService.createUser(new UserInput(1941020447415L, "Jhon Jhonny", "asd"));
		userService.createUser(new UserInput(1941020441016L, "Jhonny Jhonny", "asd"));
		userService.createUser(new UserInput(1941020447717L, "Jerry Jonson", "asd"));
		userService.createUser(new UserInput(1941020446619L, "Jeryy Jerry", "asd"));
		userService.createUser(new UserInput(1941020447320L, "Jerry Jhonny", "asd"));

		// Animale
		animalService.createAnimalIfNotExist(
				new AnimalInput("Wafi", RandomDateAnimale(), "1941020447323", "1"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Oscar", RandomDateAnimale(), "1941020447323", "14"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Leon", RandomDateAnimale(), "1941020447323", "7"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Winny", RandomDateAnimale(), "1941020447323", "36"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Ricu", RandomDateAnimale(), "1941020410827", "56"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Mitu", RandomDateAnimale(), "1941020447323", "54"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Roto", RandomDateAnimale(), "1941020447323", "42"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Ciufy", RandomDateAnimale(), "1941020447323", "14"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Grovy", RandomDateAnimale(), "1941020447323", "1"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Moto", RandomDateAnimale(), "1941020447323", "14"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Otto", RandomDateAnimale(), "1941020447323", "24"));
		animalService.createAnimalIfNotExist(
				new AnimalInput("Balan", RandomDateAnimale(), "1941020447323", "9"));

		// CategoriiDiagnostice
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Cardiac"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Pulmonar"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Neuronal"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Renal"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Hepatic"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Pancreatic"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Digestiv"));
		categorieDiagnosticService.createCategorieDiagnostic(new CategorieDiagnosticInput("Tumoral"));

		// CategoriiMedicamnte
		categorieMedicamentService.careateCategorieMedicament(new CategorieMedicamentInput("Antibiotic"));
		categorieMedicamentService.careateCategorieMedicament(new CategorieMedicamentInput("Antiparazitar"));
		categorieMedicamentService.careateCategorieMedicament(new CategorieMedicamentInput("Analgezic"));
		categorieMedicamentService.careateCategorieMedicament(new CategorieMedicamentInput("Antihemoragic"));
		categorieMedicamentService.careateCategorieMedicament(new CategorieMedicamentInput("AntiInflamatoare"));

		// Medicamente
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Ronaxan", 1, "50.0"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Noroclav",1, "15.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Xeden", 1, "17.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Therios", 1, "15.0"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Advantix", 2, "80.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Frontline", 2, "33.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Biheldon", 2, "13.0"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("AMFLEE", 2, "5.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Onsior", 3, "5.70"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Meloxican", 3, "12.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Keyvit", 4, "32.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Biodiar", 4, "16.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Carprodyl", 5, "45.5"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Eqvagel", 5, "23.3"));
		medicamentService.createMedicamentIfNotExist(new MedicamentInput("Alphaderm", 5, "21.0"));

		// Diagnostice
		diagnosticService.createDiagnostic(new DiagnosticInput("Insuficienta cardiaca congestiva",
				"este o afectiune in care inima devine slabita si incapabila sa pompeze sangele in mod eficient, ceea ce duce la o acumulare de lichid in organele si tesuturile corpului",
				"5", 1));
		diagnosticService.createDiagnostic(new DiagnosticInput("Cardiomiopatia hipertrofica",
				"Aceasta este o afectiune in care muschiul cardiac se ingroasa, ceea ce duce la o rezistenta crescuta la fluxul sanguin si la un risc crescut de probleme cardiace",
				"6", 1));
		diagnosticService.createDiagnostic(new DiagnosticInput("Cardiomiopatie dilatativa",
				"aceasta este o afectiune in care inima se mareste ca urmare a slabiciunii si ineficientei de a pompa sange eficient. Camerele inimii se largesc, iar peretii se subtiaza si se intind, ceea ce face dificila contractia si pomparea sangelui. Acest lucru poate duce la insuficienta cardiaca congestiva, in care sangele se acumuleaza in plamani si in alte parti ale corpului",
				"7", 1));
		diagnosticService.createDiagnostic(new DiagnosticInput("Encefalita",
				"Inflamatia creierului",
				"1", 3));
		diagnosticService.createDiagnostic(new DiagnosticInput("Pneumonie",
				"este o inflamarea acuta sau cronica a plamanilor si bronhiilor",
				"2", 2));
		diagnosticService.createDiagnostic(new DiagnosticInput("Bronsita",
				"inflamarea bronhiilor",
				"3", 2));
		diagnosticService.createDiagnostic(new DiagnosticInput("Menigita",
				"inflamarea meningelui ",
				"4", 3));
		diagnosticService.createDiagnostic(new DiagnosticInput("Infuficienta Renala",
				"inflamarea meningelui ",
				"8", 4));
		diagnosticService.createDiagnostic(new DiagnosticInput("Renichi Polichistic",
				"chisti la nivelul rinichiului",
				"9", 4));
		diagnosticService.createDiagnostic(new DiagnosticInput("Hepatita",
				"inflamarea ficatului",
				"10", 5));
		diagnosticService.createDiagnostic(new DiagnosticInput("Ciroza",
				"inflamarea ficatului",
				"11", 5));
		diagnosticService.createDiagnostic(new DiagnosticInput("Pancreatita",
				"inflamarea pancreasului",
				"12", 5));

		// Tratamente
		tratamentService.createTratement(new TratamentInput(1, 1, "1;2;3;","120.0"));
		tratamentService.createTratement(new TratamentInput(2, 2, "4;5;6;","80.0"));
		tratamentService.createTratement(new TratamentInput(1, 3, "7;5;3;","60.0"));

	}

	public void addAnimale() {
		List<Long> ownerCnps = userService.getAllUsers().stream().map(User::getCnp).collect(Collectors.toList());
		ownerCnps.remove(0L);
		// Generate 100 animals
		for (int i = 0; i < 1000; i++) {
			Long ownerCnp = ownerCnps.get(ThreadLocalRandom.current().nextInt(ownerCnps.size()));

			String birthDate = RandomDateAnimale();

			String breedId = Integer.toString(ThreadLocalRandom.current().nextInt(1, 57));

			AnimalInput animalInput = new AnimalInput("Animal" + i, birthDate, ownerCnp + "", breedId);
			animalService.createAnimalIfNotExist(animalInput);
		}
	}

	public void addDiagnostice() {
		List<Diagnostic> diagnostice = diagnosticService.getAllDiagnostice();
		Random random = new Random();
		for (Animal animal : animalService.getAllAnimals()) {
			int idDiagnostic = random.nextInt(1, diagnostice.size() - 1);
			diagnosticService.createDiagnostic(new DiagnosticInput(diagnostice.get(idDiagnostic).getNumeDiagnostic(),
					diagnostice.get(idDiagnostic).getSpecificatiiDiagnostic(), animal.getId() + "",
					diagnostice.get(idDiagnostic).getCategorieDiagnostic().getId()));
		}
	}

	public Date RandomDateDiagnostice() {
		Random random = new Random();
		return new Date(random.nextLong(
				Date.from(Instant.parse("2022-01-01T00:00:00Z")).getTime(),
				Date.from(Instant.parse("2023-05-30T00:00:00Z")).getTime()));
	}

	public String RandomDateAnimale() {
		Random random = new Random();
		Date date = new Date(random.nextLong(
				Date.from(Instant.parse("2018-01-01T00:00:00Z")).getTime(),
				Date.from(Instant.parse("2023-05-30T00:00:00Z")).getTime()));
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(date);
	}

	public void addTratamente(){
		List<Medicament> medicamente = medicamentService.getAllMedicamente();
		List<Diagnostic> diagnostice = diagnosticService.getAllDiagnostice();
		List<Medic> medici = medicService.getAllMedici();

		for(Diagnostic diagnostic : diagnostice){

			int times = new Random().nextInt(5);
			String listaMedicamente = "";

			for(int x=times+1; x>0 ; x--){
				int item = new Random().nextInt(medicamente.size());
				listaMedicamente+=medicamente.get(item).getId()+";";
			}
			if(listaMedicamente.length()>1)
			listaMedicamente = listaMedicamente.substring(0, listaMedicamente.length() - 1);

			tratamentService.createTratement(new TratamentInput(medici.get(new Random().nextInt(medici.size())).getId(), diagnostic.getId(),listaMedicamente,new DecimalFormat("#.##").format(new Random().nextDouble()*250+50)));
		}
	}
}
