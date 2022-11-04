package com.ipi.jva350.service;

import com.ipi.jva350.exception.SalarieException;
import com.ipi.jva350.model.SalarieAideADomicile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

    @Test
    void creerSalarieAideADomicile() {
    }

    @Test
    void calculeLimiteEntrepriseCongesPermis() {
    }

    @Test
    void ajouteConge() {
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