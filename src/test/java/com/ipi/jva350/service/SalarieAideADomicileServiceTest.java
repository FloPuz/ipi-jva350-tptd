package com.ipi.jva350.service;

import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SalarieAideADomicileServiceTest {

    @Autowired
    private SalarieAideADomicileService aideADomicileService;
    @Autowired
    private SalarieAideADomicileRepository aideADomicileRepository;

    @ParameterizedTest()
    @CsvSource({
            "nom",
            "nomtest",
    })
    void creerSalarieAideADomicileTest(String nom) throws SalarieException
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile(nom, LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
            257,20,320,25,21);
        aideADomicileService.creerSalarieAideADomicile(salarie);
        assertEquals(aideADomicileRepository.findByNom(nom),salarie);
    }

    @ParameterizedTest()
    @CsvSource({
            "'2022-01-07',25,'2022-01-01','2022-07-07','2022-08-01','Lucia'",
            "'2022-02-08',17,'2000-01-01','2022-10-01','2022-10-25','Lucas'",
            "'2022-03-09',18,'2018-01-01','2022-10-01','2022-10-25','Lucinda'",
            "'2022-04-10',12,'2021-01-01','2022-10-01','2022-10-25','Lucifer'",
            "'2022-05-11',21,'2012-01-01','2022-10-01','2022-10-25','Lucienne'",
            "'2022-06-18',7,'2020-06-01','2022-10-01','2022-10-25','Lucien'",
            "'2022-07-12',15,'2004-01-01','2022-10-01','2022-10-25','Lucie'",
            "'2022-08-13',20,'2014-03-01','2022-10-01','2022-10-25','Luc'",

    })
    void calculeLimiteEntrepriseCongesPermisReturnSomethingTest(LocalDate moisEnCours, double congesPayesAcquisAnneeNMoins1,
                                                 LocalDate moisDebutContrat, LocalDate premierJourDeConge, LocalDate dernierJourDeConge, String nom) {
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile(nom,moisDebutContrat,moisEnCours,150,2,250,congesPayesAcquisAnneeNMoins1,25);
        aideADomicileRepository.save(salarieAideADomicile);
        long result = aideADomicileService.calculeLimiteEntrepriseCongesPermis(moisEnCours,congesPayesAcquisAnneeNMoins1,moisDebutContrat,premierJourDeConge,dernierJourDeConge);
        //Très dur de comprendre ce que cela retourne exactement donc on vérifie juste que ça retourne bien quelque chose
        assertTrue(result != 0);
    }

    @ParameterizedTest()
    @CsvSource({
            "'2022-01-07',25,'2022-01-01','2022-07-07','2022-08-01','Lucia'",
            "'2022-02-08',17,'2000-01-01','2022-10-01','2022-10-25','Lucas'",
            "'2022-03-09',18,'2018-01-01','2022-10-01','2022-10-25','Lucinda'",
            "'2022-04-10',12,'2021-01-01','2022-10-01','2022-10-25','Lucifer'",
            "'2022-05-11',21,'2012-01-01','2022-10-01','2022-10-25','Lucienne'",
            "'2022-06-18',7,'2020-06-01','2022-10-01','2022-10-25','Lucien'",
            "'2022-07-12',15,'2004-01-01','2022-10-01','2022-10-25','Lucie'",
            "'2022-08-13',20,'2014-03-01','2022-10-01','2022-10-25','Luc'",

    })
    //Par pur hasard tout ces salaries ont tous autant travaillés et cotisés de jours ainsi que posés des congés
    void calculeLimiteEntrepriseCongesPermisIntegrationTest(LocalDate moisEnCours, double congesPayesAcquisAnneeNMoins1,
                                                                LocalDate moisDebutContrat, LocalDate premierJourDeConge, LocalDate dernierJourDeConge, String nom) {
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile(nom,moisDebutContrat,moisEnCours,150,2,250,congesPayesAcquisAnneeNMoins1,25);
        aideADomicileRepository.save(salarieAideADomicile);
        SalarieAideADomicile salarieAideADomicileFromBdd = aideADomicileRepository.findByNom(salarieAideADomicile.getNom());
        long result = aideADomicileService.calculeLimiteEntrepriseCongesPermis(moisEnCours,salarieAideADomicileFromBdd.getCongesPayesAcquisAnneeNMoins1(),salarieAideADomicileFromBdd.getMoisDebutContrat(),premierJourDeConge,dernierJourDeConge);
        //Très dur de comprendre ce que cela retourne exactement donc on vérifie juste que ça retourne bien quelque chose
        assertTrue(result != 0);
    }
    @ParameterizedTest(name="Début : {0} - Fin : {1}")
    @CsvSource({
            "'Jeanne','2022-07-07','2022-07-22'",
            "'Jean','2022-10-01','2022-10-25'",
    })
    void ajouteCongeIsTrueTest(String nom,LocalDate jourDebut, LocalDate jourFin) throws SalarieException {
        SalarieAideADomicile salarie = new SalarieAideADomicile(nom, LocalDate.of(2017,1,1),LocalDate.of(2022,6,1),
                257,20,320,25,2);
        aideADomicileService.creerSalarieAideADomicile(salarie);
        aideADomicileService.ajouteConge(salarie,jourDebut,jourFin);
        int nbCongesAPrendre = salarie.calculeJoursDeCongeDecomptesPourPlage(jourDebut,jourFin).size();

        assertEquals(salarie.getCongesPayesPris().size(),nbCongesAPrendre);
    }

    @ParameterizedTest(name="TestCase de : {0} Début : {1} - Fin : {2}")
    @CsvSource({
/*         NOM    DebutContrat   EnCours  jourWn  cpAn  jourWn-1  cpAn-1  cpPn-1  DebutConges  FinConges  */
            "'Jeannot','2017-01-01','2022-01-01',30,2,120,10,10,'2022-11-13','2022-11-13'",//Pas besoin de conges
            "'Jules','2021-12-17','2022-01-01',8,0,9,10,10,'2022-10-01','2022-10-25'",//N'a pas légalement droit à des congés payés !
            "'Hakim','2017-01-01','2022-10-30',350,20,120,10,10,'2022-10-01','2022-10-25'",//Pas possible de prendre de congé avant le mois en cours !
            "'Vasthi','2017-01-01','2022-01-01',350,20,120,10,10,'2023-10-01','2023-10-25'",//Pas possible de prendre de congé dans l'année de congés suivante (hors le premier jour)
            "'Monique','2017-01-01','2022-04-01',150,5,120,20,1,'2022-04-10','2022-05-05'",//dépassement des congés acquis en année N-1
            "'Alex','2017-01-01','2022-01-01',250,5,350,25,1,'2022-04-10','2022-05-08'",//dépassent la limite des règles de l'entreprise
    })
    void ajouteCongeIsFalseTest(String nom,LocalDate debutContrat, LocalDate moisEnCours,
                                double joursTravailesAnneeN, double congesPayesAcquisAnneeN, double joursTravaillesAnneeNMois1,
                                double congesPayesAcquisAnneeNMoins1, double congesPayesPrisAnneeNMoins1,LocalDate jourDebut, LocalDate jourFin) throws SalarieException {
        SalarieAideADomicile salarie = new SalarieAideADomicile(nom, debutContrat,moisEnCours,
                                                                joursTravailesAnneeN,congesPayesAcquisAnneeN,joursTravaillesAnneeNMois1,
                                                                congesPayesAcquisAnneeNMoins1,congesPayesPrisAnneeNMoins1);

        aideADomicileService.creerSalarieAideADomicile(salarie);

        SalarieException salarieException = assertThrows(SalarieException.class,()-> aideADomicileService.ajouteConge(salarie,jourDebut,jourFin));
        System.out.print(salarieException.getMessage());

    }

    @Test
    void clotureMoisTest() throws SalarieException {
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
        salarieAideADomicile.setJoursTravaillesAnneeNMoins1(9);
        salarieAideADomicile.setMoisEnCours(LocalDate.now());

        aideADomicileService.clotureMois(salarieAideADomicile,21);


        assertEquals(21,salarieAideADomicile.getJoursTravaillesAnneeN());
    }

}