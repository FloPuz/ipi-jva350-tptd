package com.ipi.jva350.model;

import com.ipi.jva350.exception.EntrepriseException;




import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EntrepriseTest {

    private static Entreprise entreprise;

    /****
     * Au vu de la méthode prenant en paramètre un mois, on test chaque option possible en vérifiant que la méthod retourne le même résultat que le calcul fournit
     * @param moisDuConge
     * @param expectedValue
     */
    @ParameterizedTest(name = "Date {0}, expectedValue {1}")
    @CsvSource({
            "'2022-01-01',0.7333333333333333",
            "'2022-02-02',0.8",
            "'2022-03-03',0.8666666666666666",
            "'2022-04-04',0.9333333333333333",
            "'2022-05-04',1",
            "'2022-06-05',0.06666666666666667",
            "'2022-07-06',0.23333333333333334",
            "'2022-08-07',0.4",
            "'2022-09-08',0.4666666666666667",
            "'2022-10-09',0.5333333333333333",
            "'2022-11-10',0.6",
            "'2022-12-11',0.6666666666666667",
    })
    void proportionPondereeDuMoisTestEachMois(LocalDate moisDuConge, double expectedValue) {
        assertEquals(entreprise.proportionPondereeDuMois(moisDuConge), expectedValue);
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
    void getPremierJourAnneeDeCongesEstCetteAnneeTest(LocalDate date) {
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
    void getPremierJourAnneeDeCongesEstPasCetteAnneeTest(LocalDate date) {
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
        "'2021-11-01'",
        "'2021-11-11'",
        "'2021-01-01'",
        "'2021-05-01'",
        "'2021-07-14'",
        "'2021-12-25'",
        "'2020-11-01'",
        "'2020-11-11'",
        "'2020-01-01'",
        "'2020-05-01'",
        "'2020-07-14'",
        "'2020-12-25'",
        "'2019-11-01'",
        "'2019-11-11'",
        "'2019-01-01'",
        "'2019-05-01'",
        "'2019-07-14'",
        "'2019-12-25'",
        "'2018-11-01'",
        "'2018-11-11'",
        "'2018-01-01'",
        "'2018-05-01'",
        "'2018-07-14'",
        "'2018-12-25'",
        "'2017-11-01'",
        "'2017-11-11'",
        "'2017-01-01'",
        "'2017-05-01'",
        "'2017-07-14'",
        "'2017-12-25'",
    })
    void estJourFerieTest(LocalDate now) {
        assertTrue(entreprise.estJourFerie(now));
    }

    @ParameterizedTest(name = "jour {0} est férié")
    @CsvSource({
            "'2022-10-01'",
            "'2022-12-11'",
            "'2022-02-01'",
            "'2022-05-02'",
            "'2022-07-13'",
            "'2022-12-28'",
            "'2021-10-01'",
            "'2021-12-11'",
            "'2021-02-01'",
            "'2021-05-02'",
            "'2021-07-13'",
            "'2021-12-28'",
            "'2020-10-01'",
            "'2020-12-11'",
            "'2020-02-01'",
            "'2020-05-02'",
            "'2020-07-13'",
            "'2020-12-28'",
            "'2019-10-01'",
            "'2019-12-11'",
            "'2019-02-01'",
            "'2019-05-02'",
            "'2019-07-13'",
            "'2019-12-28'",
            "'2018-10-01'",
            "'2018-12-11'",
            "'2018-02-01'",
            "'2018-05-02'",
            "'2018-07-13'",
            "'2018-12-28'",
            "'2017-10-01'",
            "'2017-12-11'",
            "'2017-02-01'",
            "'2017-05-02'",
            "'2017-07-13'",
            "'2017-12-28'",
    })
    void estPasJourFerieTest(LocalDate now) {
        assertFalse(entreprise.estJourFerie(now));
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
        throw exception;
    }
}