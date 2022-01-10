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
        Visit visit = new Visit();
        Set<Visit> visits = new HashSet<>(); // used for return value
        visits.add(visit);

        when(visitRepository.findAll()).thenReturn(visits);
        assertEquals(1, service.findAll().size());
        verify(visitRepository).findAll();

    }

    @Test
    void findById() {
        Visit visit = new Visit();

        when(visitRepository.findById(1L)).thenReturn(Optional.of(visit)); // or instead of 1L -> anyLong()
        assertNotNull(service.findById(1L));

        verify(visitRepository).findById(anyLong());
    }

    @Test
    void save() {
        Visit visit = new Visit(); // used for return value

        when(visitRepository.save(any(Visit.class))).thenReturn(visit);
        assertNotNull(service.save(visit));

        verify(visitRepository).save(visit);

    }

    @Test
    void delete() {
        Visit visit = new Visit();
        service.delete(visit);
        verify(visitRepository).delete(visit);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(visitRepository).deleteById(anyLong()); // or intead of anyLong() -> 1L
    }
}