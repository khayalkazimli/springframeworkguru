package org.springframework.samples.petclinic.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.*;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

    @Mock
    PetRepository petRepository;

    @Mock
    VetRepository vetRepository;

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    VisitRepository visitRepository;

    @InjectMocks
    ClinicServiceImpl clinicService;

    @Test
    void findPetTypes() {
        // given
        List<PetType> petTypes = new ArrayList<>();
        PetType petType = new PetType();
        petType.setName("Cat");
        petTypes.add(petType);
        given(petRepository.findPetTypes()).willReturn(petTypes);
        // when
        Collection<PetType> returnedPetTypes = clinicService.findPetTypes();
        // then
        then(petRepository).should().findPetTypes();
        assertEquals(1, returnedPetTypes.size());
        assertEquals("Cat", returnedPetTypes.iterator().next().toString());
        assertNotNull(returnedPetTypes);
    }

    @Test
    void findOwnerById() {
        // given
        Owner owner = new Owner();
        owner.setId(1);
        given(ownerRepository.findById(anyInt())).willReturn(owner);
        // when
        Owner foundOwner = clinicService.findOwnerById(1);
        // then
        then(ownerRepository).should().findById(anyInt());
        assertNotNull(foundOwner);
        assertEquals(1, foundOwner.getId().intValue());
    }

    @Test
    void findOwnerByLastName() {
        // given
        Collection<Owner> ownerCollection = new ArrayList<>();
        Owner owner = new Owner();
        owner.setLastName("Kazimli");
        ownerCollection.add(owner);
        given(ownerRepository.findByLastName(anyString())).willReturn(ownerCollection);
        // when
        clinicService.findOwnerByLastName(owner.getLastName());
        // then
        then(ownerRepository).should(times(1)).findByLastName(anyString());
//        assertEquals("Kazimli",clinicService.findOwnerByLastName(owner.getLastName()).iterator().next().getLastName());
    }

    @Test
    void saveOwner() {
        // given
        Owner owner = new Owner();
        // when
        clinicService.saveOwner(owner);
        // then
        then(ownerRepository).should().save(any()); // verify(ownerRepository).save(any());
    }

    @Test
    void saveVisit() {
        // given
        Visit visit = new Visit();
        // when
        clinicService.saveVisit(visit);
        // then
        then(visitRepository).should().save(any());
    }

    @Test
    void findPetById() {
        // given
        Pet pet = new Pet();
        given(petRepository.findById(anyInt())).willReturn(pet);
        // when
        clinicService.findPetById(anyInt());
        // then
        then(petRepository).should().findById(anyInt());
    }

    @Test
    void savePet() {
        Pet pet = new Pet();
        clinicService.savePet(pet);
        then(petRepository).should().save(pet); // verify(petRepository).save(pet);
    }

    @Test
    void findVets() {
        // given
        Collection<Vet> vetCollection = new ArrayList<>();
        given(vetRepository.findAll()).willReturn(vetCollection);
        // when
        clinicService.findVets();
        // then
        then(vetRepository).should().findAll();
    }

    @Test
    void findVisitsByPetId() {
        // given
        List<Visit> visitList = new ArrayList<>();
        given(visitRepository.findByPetId(anyInt())).willReturn(visitList);
        // when
        clinicService.findVisitsByPetId(anyInt());
        // then
        then(visitRepository).should().findByPetId(anyInt());
    }
}