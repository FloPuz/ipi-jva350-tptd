package com.ipi.jva350.repository;

import com.ipi.jva350.model.SalarieAideADomicile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import javax.persistence.NonUniqueResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Test()
    void findByNomWhereDuplicateNameThrowsErrorTest() throws IncorrectResultSizeDataAccessException {
        String nom = "test";
        SalarieAideADomicile salarie = new SalarieAideADomicile(nom, LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,25,21);
        SalarieAideADomicile salarietest = new SalarieAideADomicile(nom, null,null,
                0,0,0,0,0);
        salarieAideADomicileRepository.save(salarie);
        salarieAideADomicileRepository.save(salarietest);
        IncorrectResultSizeDataAccessException exception = assertThrows(
                IncorrectResultSizeDataAccessException.class,() ->salarieAideADomicileRepository.findByNom(nom)
        );
        assertTrue(exception.getMessage().contains("query did not return a unique result"));
    }

    @Test
    void partCongesPrisTotauxAnneeNMoins1ForOneSalarieTest() {
        //select sum(congesPayesPrisAnneeNMoins1)/sum(congesPayesAcquisAnneeNMoins1) from SalarieAideADomicile
        SalarieAideADomicile salarie = new SalarieAideADomicile("test", LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,25,21);
        if(salarie.getCongesPayesAcquisAnneeNMoins1() != 0)
        {
            salarieAideADomicileRepository.save(salarie);
            double part = salarie.getCongesPayesPrisAnneeNMoins1()/salarie.getCongesPayesAcquisAnneeNMoins1();
            assertEquals(salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1(), part);
        }
    }
    @Test
    void partCongesPrisTotauxAnneeNMoins1ForManySalarieTest() {
        //select sum(congesPayesPrisAnneeNMoins1)/sum(congesPayesAcquisAnneeNMoins1) from SalarieAideADomicile
        SalarieAideADomicile salarie = new SalarieAideADomicile("test", LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,25,21);
        SalarieAideADomicile salarie1 = new SalarieAideADomicile("test", LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,20,16);
        SalarieAideADomicile salarie2 = new SalarieAideADomicile("test", LocalDate.of(2017,1,1),LocalDate.of(2022,11,1),
                257,20,320,15,11);

        List<SalarieAideADomicile> list = new ArrayList<>();
        list.add(salarie);
        list.add(salarie1);
        list.add(salarie2);
        double congesPayesPris = 0;
        double congesPayesAcquis = 0;

        salarieAideADomicileRepository.saveAll(list);
        for (SalarieAideADomicile item:
             list) {
            congesPayesPris += item.getCongesPayesPrisAnneeNMoins1();
            congesPayesAcquis += item.getCongesPayesAcquisAnneeNMoins1();
        }
        if (congesPayesAcquis == 0)
            congesPayesAcquis=1;
        double part = congesPayesPris/congesPayesAcquis;
        assertEquals(salarieAideADomicileRepository.partCongesPrisTotauxAnneeNMoins1(), part);

    }
}