package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitSDJpaServiceTest {

    @Mock
    VisitRepository visitRepository;
    // without @Mock annotation :
    // VisitRepository visitRepository = mock(VisitRepository.class);

    @InjectMocks
    VisitSDJpaService service;

//    without @ExtendWith(MockitoExtension.class) :
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    void findAll() {
        // given
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>(); // used for return value
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);
        // or:  when(visitRepository.findAll()).thenReturn(visits);

        // when
        Set<Visit> foundVisit = service.findAll();
        // then
        assertEquals(1, foundVisit.size());
        then(visitRepository).should().findAll();
        // or:   verify(visitRepository).findAll();

    }

    @Test
    void findById() {
        // given
        Visit visit = new Visit();
        given(visitRepository.findById(anyLong())).willReturn(Optional.of(visit));
        // or:   when(visitRepository.findById(1L)).thenReturn(Optional.of(visit)); // or instead of 1L -> anyLong()

        // when
        Visit foundVisit = service.findById(1L);
        // then
        assertNotNull(foundVisit);
        then(visitRepository).should().findById(anyLong());
        // or:   verify(visitRepository).findById(anyLong());
    }

    @Test
    void save() {
        // given
        Visit visit = new Visit(); // used for return value
        given(visitRepository.save(any(Visit.class))).willReturn(visit);
        // or:   when(visitRepository.save(any(Visit.class))).thenReturn(visit);

        // when
        Visit foundVisit = service.save(visit);
        // then
        assertNotNull(foundVisit);
        then(visitRepository).should().save(foundVisit);
        // or:   verify(visitRepository).save(visit);

    }

    @Test
    void delete() {
        // given
        Visit visit = new Visit();
        // when
        service.delete(visit);
        // then
        then(visitRepository).should().delete(visit);
        // or:  verify(visitRepository).delete(visit);
    }

    @Test
    void deleteById() {
        // when
        service.deleteById(1L);
        // then
        then(visitRepository).should().deleteById(anyLong());
        // or:   verify(visitRepository).deleteById(anyLong()); // or intead of anyLong() -> 1L
    }
}