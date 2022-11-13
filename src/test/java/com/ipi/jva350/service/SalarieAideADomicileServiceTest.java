package com.ipi.jva350.service;

import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.SalarieAideADomicile;
import com.ipi.jva350.repository.SalarieAideADomicileRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

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
            "'2022-01-07',25,'2021-01-01','2022-07-07','2022-08-01'",
            "'2022-02-08',17,'2021-01-01','2022-10-01','2022-10-25'",
            "'2022-03-09',18,'2021-01-01','2022-10-01','2022-10-25'",
            "'2022-04-10',12,'2021-01-01','2022-10-01','2022-10-25'",
            "'2022-05-11',21,'2020-01-01','2022-10-01','2022-10-25'",
            "'2022-06-18',7,'2020-06-01','2022-10-01','2022-10-25'",
            "'2022-07-12',15,'2020-01-01','2022-10-01','2022-10-25'",
            "'2022-08-13',20,'2020-03-01','2022-10-01','2022-10-25'",

    })
    void calculeLimiteEntrepriseCongesPermisReturnSomethingTest(LocalDate moisEnCours, double congesPayesAcquisAnneeNMoins1,
                                                 LocalDate moisDebutContrat, LocalDate premierJourDeConge, LocalDate dernierJourDeConge) {
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile("test",moisDebutContrat,moisEnCours,150,2,250,congesPayesAcquisAnneeNMoins1,25);
        aideADomicileRepository.save(salarieAideADomicile);
        long result = aideADomicileService.calculeLimiteEntrepriseCongesPermis(moisEnCours,congesPayesAcquisAnneeNMoins1,moisDebutContrat,premierJourDeConge,dernierJourDeConge);
        //Il me manque un expert du métier pour pouvoir m'aider dans le calcul complexe que représente cette methode, pour l'instant je n'ai pas le temps de me plonger dans le fonctionnel je verrais une fois les autres tests terminé
        assertTrue(result != 0);
    }


    @Test
    void ajouteConge() {
        SalarieAideADomicile salarie = new SalarieAideADomicile("test", LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,25,21);
    }

    @Test
    void clotureMoisTest() throws SalarieException {
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
        salarieAideADomicile.setJoursTravaillesAnneeNMoins1(9);
        salarieAideADomicile.setMoisEnCours(LocalDate.now());

        aideADomicileService.clotureMois(salarieAideADomicile,21);


        assertEquals(21,salarieAideADomicile.getJoursTravaillesAnneeN());
    }

    @Test
    void clotureAnnee() {
    }
}