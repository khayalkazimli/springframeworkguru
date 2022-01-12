package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import guru.springframework.sfgpetclinic.services.map.PetMapService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {
    @Mock
    VisitService visitService;

//    @Mock
//    PetService petService;

    @Spy
    PetMapService petMapService;

    @InjectMocks
    VisitController controller;



    @Test
    void loadPetWithVisitSpy(){
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L); // used for return value
        Pet pet2 = new Pet(2L);

        petMapService.save(pet);
        petMapService.save(pet2);

        given(petMapService.findById(anyLong())).willCallRealMethod();

        // when
        Visit foundVisit = controller.loadPetWithVisit(pet2.getId(), model);

        // then
        assertNotNull(foundVisit);
        assertNotNull(foundVisit.getPet());
        assertEquals(2L, foundVisit.getPet().getId());
        assertNotNull(model);
        then(petMapService).should().findById(anyLong());

    }

    @Test
    void loadPetWithVisitSpyStubbing(){
        // given
        Map<String, Object> model = new HashMap<>();
        Pet pet = new Pet(1L); // used for return value
        Pet pet2 = new Pet(2L);

        petMapService.save(pet);
        petMapService.save(pet2);

        given(petMapService.findById(anyLong())).willReturn(pet2);

        // when
        Visit foundVisit = controller.loadPetWithVisit(anyLong(), model);

        // then
        assertNotNull(foundVisit);
        assertNotNull(foundVisit.getPet());
        assertEquals(2L, foundVisit.getPet().getId());
        assertNotNull(model);
        then(petMapService).should().findById(anyLong());

    }

// uses without @Spy
//    @Test
//    void loadPetWithVisitMock(){
//        // given
//        Map<String, Object> model = new HashMap<>();
//        Pet pet = new Pet(1L); // used for return value
//        given(petService.findById(anyLong())).willReturn(pet);
//        // when
//        Visit foundVisit = controller.loadPetWithVisit(anyLong(), model);
//        // then
//        assertNotNull(foundVisit);
//        assertNotNull(foundVisit.getPet());
//        assertEquals(1L, foundVisit.getPet().getId());
//        assertNotNull(model);
//        then(petService).should().findById(anyLong());
//        verifyNoMoreInteractions(petService); // then(petService).shouldHaveNoMoreInteractions();
//    }
}