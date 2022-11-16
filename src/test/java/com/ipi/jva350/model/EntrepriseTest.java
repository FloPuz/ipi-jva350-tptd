package com.ipi.jva350.model;

import com.ipi.jva350.exception.EntrepriseException;
import com.ipi.jva350.exception.SalarieException;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

    private static Entreprise entreprise;
/*ToDo
    @ParameterizedTest(name = "Date {0}, expectedValue {1}")
    @CsvSource({
            "''2022-02-01'',1",
            "''2022-02-02'',1",
            "''2022-02-03'',1",
            "''2022-02-04'',1",
            "''2022-02-04'',1",
            "''2022-02-05'',1",
            "''2022-02-06'',1",
            "''2022-02-07'',1",
            "''2022-02-08'',1",
            "''2022-02-09'',1",
            "''2022-02-10'',1",
            "''2022-02-11'',1",
            "''2022-02-12'',1",
    })
    void proportionPondereeDuMoisTest(LocalDate moisDuConge) {
    }
*/
    @ParameterizedTest(name = "jour {0}, expectedValue {1}")
    @CsvSource({
            "'2022-11-01'",
            "'2022-11-11'",
            "'2022-08-01'",
            "'2022-06-01'",
            "'2022-07-14'",
            "'2022-12-25'",
    })
    void getPremierJourAnneeDeCongesEstCetteAnnéeTest(LocalDate date) {
        LocalDate d = entreprise.getPremierJourAnneeDeConges(date);
        assertEquals(d,LocalDate.of(d.getYear(), 6, 1));
    }

    @ParameterizedTest(name = "jour {0}, expectedValue {1}")
    @CsvSource({
            "'2022-11-01'",
            "'2022-11-11'",
            "'2022-08-01'",
            "'2022-06-01'",
            "'2022-07-14'",
            "'2022-12-25'",
    })
    void getPremierJourAnneeDeCongesEstPasCetteAnnéeTest(LocalDate date) {
        LocalDate d = entreprise.getPremierJourAnneeDeConges(date);
        assertNotEquals(d,LocalDate.of(d.getYear()-1, 6, 1));
    }

    @ParameterizedTest(name = "jour {0} est férié")
    @CsvSource({
        "'2022-11-01'",
        "'2022-11-11'",
        "'2022-01-01'",
        "'2022-05-01'",
        "'2022-07-14'",
        "'2022-12-25'",
    })
    void estJourFerieTest(LocalDate now) {
        List<LocalDate>jours = entreprise.joursFeries(LocalDate.now());
        assertTrue(jours.contains(now));
    }

    @ParameterizedTest(name = "jour {0} est férié")
    @CsvSource({
            "'2022-10-01'",
            "'2022-12-11'",
            "'2022-02-01'",
            "'2022-05-02'",
            "'2022-07-13'",
            "'2022-12-28'",
    })
    void estPasJourFerieTest(LocalDate now) {
        List<LocalDate>jours = entreprise.joursFeries(LocalDate.now());
        assertFalse(jours.contains(now));
    }

    //Sans faire les test avant j'aurais surement crée directement une nouvelle class ExceptionEntreprise
    //J'aurais aussi surement prit moins de temps à réfléchir à si la date debut > fin
    @ParameterizedTest(name = "jour {0} compris entre {1} et {2}")
    @CsvSource({
            "'2022-01-01','2022-01-01','2022-03-01'",
            "'2022-01-01','2022-01-01','2022-01-01'",
            "'2022-02-02','2022-02-01','2022-02-03'",
            "'2022-02-02','2022-01-02','2022-03-03'",
            "'2022-02-02','2021-02-02','2022-02-03'",
            "'2023-02-01','2022-02-01','2023-02-01'",
    })
    void estDansPlageWhenDebutBeforeFinIsTrueTest(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
        assertTrue(entreprise.estDansPlage(d,debut,fin));
    }

    @ParameterizedTest(name = "jour {0} pas compris entre {1} et {2}")
    @CsvSource({
            "'2022-01-01','2022-01-02','2022-02-01'",
            "'2022-02-01','2022-01-01','2022-01-30'",
            "'2022-02-01','2023-02-01','2023-02-01'",
            "'2022-02-01','2023-01-01','2023-02-02'",
            "'0000-02-01','2022-02-01','2022-02-01'",
    })
    void estDansPlageWhenDebutBeforeFinIsFalseTest(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
        assertFalse(entreprise.estDansPlage(d,debut,fin));
    }

    @ParameterizedTest(name = "jour {0} pas compris entre {1} et {2}")
    @CsvSource({
            "'2022-01-01','2022-02-02','2022-01-01'",
            "'2023-03-01','2023-02-02','2022-01-01'",
            "'2023-02-01','2023-02-02','2023-02-01'",

    })
    void estDansPlageWhenDebutBeforeFinThrowExceptionTest(LocalDate d, LocalDate debut, LocalDate fin) throws EntrepriseException {
        EntrepriseException exception = assertThrows(EntrepriseException.class,() -> entreprise.estDansPlage(d,debut,fin));
        System.out.print(exception.getMessage());
    }
}