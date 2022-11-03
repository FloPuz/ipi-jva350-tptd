package com.ipi.jva350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

class SalarieAideADomicileTest {


    /***
     * Le test passe si a le droits a des congés payés (salarie.getJoursTravaillesAnneeNMoins1()>=10)
     */
    @Test
    void aLegalementDroitADesCongesPayesIsTrue()
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        //En ayant travaillé 10 jours débloque droit aux congés
        salarie.setJoursTravaillesAnneeNMoins1(10);
        assertTrue(salarie.aLegalementDroitADesCongesPayes());
    }

    /***
     * Le test passe si n'a pas le droits a des congés payés (salarie.getJoursTravaillesAnneeNMoins1()<10)
     */
    @Test
    void aLegalementDroitADesCongesPayesIsWrong()
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(9);
        Assertions.assertFalse(salarie.aLegalementDroitADesCongesPayes());
    }

    /***
     * Le test passe si n'a pas le droits a des congés payés (salarie.getJoursTravaillesAnneeNMoins1()<10)
     */
    @Test
    void aLegalementDroitADesCongesPayesIsNull()
    {
        SalarieAideADomicile salarie = new SalarieAideADomicile();

        Assertions.assertFalse(salarie.aLegalementDroitADesCongesPayes());
    }


    @ParameterizedTest(name = "dateDebut {0}, dateFin {1}, expectedValue {2}")
    @CsvSource({
            "'2021-07-01', '2021-07-27', 22",
            "'2021-08-01', '2021-08-27', 24",
            "'2021-09-01', '2021-09-27', 23"
    })
    void calculeJoursDeCongeDecomptesPourPlage(LocalDate dateDebut, LocalDate dateFin, int expectedValue)
    {
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
        int joursDecomptes = salarieAideADomicile.calculeJoursDeCongeDecomptesPourPlage(dateDebut,dateFin).size();
        //Sans les dimanches par rapport au calendrier il y a normalement 22 jours
        assertTrue(joursDecomptes == expectedValue);

    }

    @Test
    void estJourOuvrable() {
    }

    @Test
    void estHabituellementTravaille() {
    }
}