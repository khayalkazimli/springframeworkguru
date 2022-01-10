package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import guru.springframework.sfgpetclinic.services.SpecialtyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service; // false : SpecialtyRepository specialtyRepository

    @Test
    void delete1() {
        specialtyRepository.delete(new Speciality());
    }

    @Test
    void delete2() {
        service.delete(new Speciality());
    }

    @Test
    void deleteByIdNotVerifyWithoutMock() {
        service.deleteById(2l);
        // false: verify(service).deleteById(2l) - SpecialitySDJpaService service - is not a mock
        verify(specialtyRepository).deleteById(2l); // correct
    }

    @Test
    void deleteById() {
        // given - none

        // when
        service.deleteById(1l);
        // then
        then(specialtyRepository).should().deleteById(anyLong());
        // or:  verify(specialtyRepository).deleteById(1l); // default times(1)

        // when
        service.deleteById(2l);
        service.deleteById(2l);
        // then
        then(specialtyRepository).should(times(2)).deleteById(2L); // say verirsense, deqiq neyin silineceyini bildirmelisen
        // or:  verify(specialtyRepository, times(2)).deleteById(2l); // absolute 2 times
    }

    @Test
    void deleteByObject() {
        // given
        Speciality speciality = new Speciality();
        // when
        service.delete(speciality);

        // then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void deleteByIdAtLeast() {
        // given - none

        // when
        service.deleteById(1l);
        service.deleteById(1l);

        // then
        then(specialtyRepository).should(atLeastOnce()).deleteById(1L);
        then(specialtyRepository).should(atLeast(1)).deleteById(1L);
        // or:
//        verify(specialtyRepository, atLeastOnce()).deleteById(1l);
//        verify(specialtyRepository, atLeast(1)).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);
        // then
        then(specialtyRepository).should(atMost(5)).deleteById(1L);
        // or:  verify(specialtyRepository, atMost(5)).deleteById(1L);
    }

    @Test
    void deleteByIdNever() {
        // when
        service.deleteById(1L);
        service.deleteById(1L);
        // then
        then(specialtyRepository).should(never()).deleteById(2L);
        // or:  verify(specialtyRepository, never()).deleteById(2L);
    }

    @Test
    void findByIdTestSuccess() {
        // given
        Speciality speciality = new Speciality(); // used for return value
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality)); // or instead of 1L -> anyLong()
        // or: when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        // when
        Speciality foundSpeciality = service.findById(1L);
        // then
        assertNotNull(foundSpeciality);
        then(specialtyRepository).should().findById(anyLong());
        // or: verify(specialtyRepository).findById(1L); // or instead of 1l -> anyLong()
    }

    @Test
    void findByIdBDDTestSuccess() {
        // given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(anyLong())).willReturn(Optional.of(speciality));

        //when
        Speciality foundSpeciality = service.findById(1L);

        //then
        assertNotNull(foundSpeciality);
        then(specialtyRepository).should().findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
//or without BDD context:  verify(specialtyRepository).findById(anyLong());
    }

    @Test
    void saveTestSuccess() {
        // given
        Speciality speciality = new Speciality(); // used for return value
        given(specialtyRepository.save(speciality)).willReturn(speciality);
        // or:  when(specialtyRepository.save(speciality)).thenReturn(speciality);

        // when
        Speciality savedSpeciality = service.save(speciality);
        // then
        assertNotNull(savedSpeciality);
        then(specialtyRepository).should().save(savedSpeciality);
        // or:   verify(specialtyRepository).save(savedSpeciality);
    }

    @Test
    void deleteTestSuccess() {
        // given
        Speciality speciality = new Speciality();
        // when
        service.delete(speciality);
        // then
        then(specialtyRepository).should().delete(any(Speciality.class));
        // or:  verify(specialtyRepository).delete(any(Speciality.class)); // or instead of any(Speciality.class) -> speciality
    }
}