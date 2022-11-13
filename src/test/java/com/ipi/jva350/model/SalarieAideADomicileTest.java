package com.ipi.jva350.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfEnvironmentVariable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

class SalarieAideADomicileTest {


    /***
     * Le test passe si a le droits a des congés payés (salarie.getJoursTravaillesAnneeNMoins1()>=10)
     */
    @Test
    void aLegalementDroitADesCongesPayesIsTrueAtLimite()
    {
        //Given :
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(10);
        //When:
        // travaillé 10 jours débloque droit aux congés
        boolean res = salarie.aLegalementDroitADesCongesPayes();
        //Then:
        assertTrue(res);
    }

    /***
     * Le test passe si n'a pas le droits a des congés payés (salarie.getJoursTravaillesAnneeNMoins1()<10)
     */
    @Test
    void aLegalementDroitADesCongesPayesIsWrongAtLimit()
    {
        //Given
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(9);
        //When :
        boolean res = salarie.aLegalementDroitADesCongesPayes();
        //Then :
        assertFalse(res);
    }


    @Test
    void aLegalementDroitADesCongesPayesIsWrongAtLimitDouble()
    {
        //Given
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        salarie.setJoursTravaillesAnneeNMoins1(9.5);
        //When :
        boolean res = salarie.aLegalementDroitADesCongesPayes();
        //Then :
        assertFalse(res);
    }

    /***
     * Le test passe si n'a pas le droits a des congés payés (salarie.getJoursTravaillesAnneeNMoins1()<10)
     */
    @Test
    void aLegalementDroitADesCongesPayesIsNull()
    {
        //Given:
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        //When:Salarie Is NULL or Not Initialise
        boolean res = salarie.aLegalementDroitADesCongesPayes();
        //Then:
        Assertions.assertFalse(res);
    }


    @ParameterizedTest(name = "dateDebut {0}, dateFin {1}, expectedValue {2}")
    @CsvSource({
            "'2021-07-01', '2021-07-27', 22",
            "'2021-08-01', '2021-08-27', 24",
            "'2021-09-01', '2021-09-27', 23"
    })
    void calculeJoursDeCongeDecomptesPourPlageTest(LocalDate dateDebut, LocalDate dateFin, int expectedValue)
    {
        //Given:
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
        //When :
        int joursDecomptes = salarieAideADomicile.calculeJoursDeCongeDecomptesPourPlage(dateDebut,dateFin).size();
        //Then:
        //Sans les dimanches par rapport au calendrier il y a {expectedValue} jours de congés à payé
        assertEquals(joursDecomptes , expectedValue);

    }

    @ParameterizedTest(name = "dateDebut {0}, dateFin {1}, expectedValue {2}")
    @CsvSource({
            "'2022-07-02', '2022-07-27', 20",//Un jour férié dans cette tranche de jour - 14 Juillet + Cas tordu du 1er samedi qui n'est pas compté comme ouvrable
    })
    void calculeJoursDeCongeDecomptesPourPlageTestBeginSamedi(LocalDate dateDebut, LocalDate dateFin, int expectedValue)
    {
        //Given:
        SalarieAideADomicile salarieAideADomicile = new SalarieAideADomicile();
        //When :
        int joursDecomptes = salarieAideADomicile.calculeJoursDeCongeDecomptesPourPlage(dateDebut,dateFin).size();
        //Then:
        //Sans les dimanches par rapport au calendrier il y a {expectedValue} jours de congés à payé
        assertEquals(joursDecomptes , expectedValue);

    }
    @ParameterizedTest(name = "jour {0}, expectedValue {1}")
    @CsvSource({
            "'2022-11-07',true",
            "'2022-11-08',true",
            "'2022-11-09',true",
            "'2022-11-10',true",
            "'2022-11-11',false",//Jour ferié
            "'2022-11-18',true",//On test le vendredi de la semaine d'après
            "'2022-11-12',true",
            "'2022-11-13',false",

    })
    void estJourOuvrable(LocalDate jour, boolean expectedValue) {
        //Given
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        //When :
        boolean res = salarie.estJourOuvrable(jour);
        //Then:
        assertEquals(res,expectedValue);

    }

    @ParameterizedTest(name = "jour {0}, expectedValue {1}")
    @CsvSource({
            "'2022-11-07',true",
            "'2022-11-08',true",
            "'2022-11-09',true",
            "'2022-11-10',true",
            "'2022-11-11',true",//Jour ferié ne sont pas forcement prit en comptedans la methode('Habituellement') se basant sur le jour de la semaine et c'est tout
            "'2022-11-18',true",//On test le vendredi de la semaine d'après
            "'2022-11-12',false",//Le samedi n'est habituellement pas travaillé
            "'2022-11-13',false",

    })
    void estHabituellementTravaille(LocalDate jour, boolean expectedValue) {
        SalarieAideADomicile salarie = new SalarieAideADomicile();
        assertEquals(salarie.estHabituellementTravaille(jour),expectedValue);

    }
}