package com.ipi.jva350.repository;

import com.ipi.jva350.model.SalarieAideADomicile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SalarieAideADomicileRepositoryTest {

    @Autowired
    private SalarieAideADomicileRepository salarieAideADomicileRepository;

    public SalarieAideADomicileRepositoryTest(){

    }
    @Test
    void findByNomIsNameEqualTest() {
        String nom = "test";
        SalarieAideADomicile salarie = new SalarieAideADomicile(nom, LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,25,21);
        salarieAideADomicileRepository.save(salarie);
        SalarieAideADomicile toTest = salarieAideADomicileRepository.findByNom(nom);
        assertEquals(salarie,toTest);
    }


    @Test
    void partCongesPrisTotauxAnneeNMoins1() {
    }
}