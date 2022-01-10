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
        service.deleteById(1l);
        verify(specialtyRepository).deleteById(1l); // default times(1)

        service.deleteById(2l);
        service.deleteById(2l);
        verify(specialtyRepository, times(2)).deleteById(2l); // absolute 2 times
    }

    @Test
    void deleteByIdAtLeast() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialtyRepository, atLeastOnce()).deleteById(1l);
        verify(specialtyRepository, atLeast(1)).deleteById(1l);
    }

    @Test
    void deleteByIdAtMost() {
        service.deleteById(1l);
        service.deleteById(1l);
        verify(specialtyRepository, atMost(5)).deleteById(1l);
    }

    @Test
    void deleteByIdNever() {
        service.deleteById(1l);
        service.deleteById(1l);

        verify(specialtyRepository, never()).deleteById(2l);
    }

    @Test
    void findByIdTestSuccess() {
        Speciality speciality = new Speciality(); // used for return value

        when(specialtyRepository.findById(1l)).thenReturn(Optional.of(speciality)); // or instead of 1l -> anyLong()

        Speciality foundSpeciality = service.findById(1l);
        assertNotNull(foundSpeciality);
        verify(specialtyRepository).findById(1l); // or instead of 1l -> anyLong()
    }

    @Test
    void saveTestSuccess() {
        Speciality speciality = new Speciality(); // used for return value

        when(specialtyRepository.save(speciality)).thenReturn(speciality);

        Speciality savedSpeciality = service.save(speciality);
        assertNotNull(savedSpeciality);

        verify(specialtyRepository).save(savedSpeciality);
    }

    @Test
    void deleteTestSuccess() {
        Speciality speciality = new Speciality();
        service.delete(speciality);
        verify(specialtyRepository).delete(any(Speciality.class)); // or instead of any(Speciality.class) -> speciality
    }
}