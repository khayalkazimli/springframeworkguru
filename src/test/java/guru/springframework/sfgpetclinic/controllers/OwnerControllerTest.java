package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";

    @Mock
    OwnerService ownerService;

    @InjectMocks
    OwnerController ownerController;

    @Mock
    Model model;

    @Mock
    BindingResult bindingResult;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(argumentCaptor.capture()))
                .willAnswer(invocation -> {
                    List<Owner> owners = new ArrayList<>();
                    String name = invocation.getArgument(0);

                    if (name.equals("%Kazimli%")) { // 1 owner found
                        owners.add(new Owner(1L, "Khayal", "Kazimli"));
                        return owners;
                    } else if (name.equals("%DontFindMe%")) { // owner not found
                        return owners;
                    } else if (name.equals("%FindMe%")) { // more than one owner found
                        owners.add(new Owner(1L, "Khayal", "Kazimli"));
                        owners.add(new Owner(2L, "Hesen", "Oruclu"));
                        return owners;
                    }
                    throw new RuntimeException("Invalid Argument");
                });
    }

    @Test
    void processFindFormWildCardStringAnnotationMultipleFound() {
        // given
        Owner owner = new Owner(1L, "Rauf", "FindMe");
        InOrder order = inOrder(ownerService, model);
        // when
        String viewName = ownerController.processFindForm(owner, bindingResult, model);
        // then
        assertEquals("%FindMe%", argumentCaptor.getValue());
        assertEquals("owners/ownersList", viewName);
        // inorder asserts
        order.verify(ownerService).findAllByLastNameLike(anyString());
        order.verify(model, times(1)).addAttribute(anyString(), anyList());
        verifyNoMoreInteractions(model);
    }

    @Test
    void processFindFormWildCardStringAnnotationFound() {
        // given
        Owner owner = new Owner(1L, "Khayal", "Kazimli");
        // when
        String viewName = ownerController.processFindForm(owner, bindingResult, null);
        // then
        assertEquals("%Kazimli%", argumentCaptor.getValue());
        assertEquals("redirect:/owners/" + owner.getId(), viewName);
        verifyNoInteractions(model);
        // optional:
        // assertFalse(bindingResult.hasErrors());
        // assertEquals(1L, owner.getId());
    }

    @Test
    void processFindFormWildCardStringAnnotationNotFound() {
        // given
        Owner owner = new Owner(1L, "Khayal", "DontFindMe");
        // when
        String viewName = ownerController.processFindForm(owner, bindingResult, null);

        verifyNoMoreInteractions(ownerService);
        // then
        assertEquals("%DontFindMe%", argumentCaptor.getValue());
        assertEquals("owners/findOwners", viewName);
        verifyNoInteractions(model);
        // optional:
        // assertFalse(bindingResult.hasErrors());
        // assertEquals(1L, owner.getId());
    }

    // uses without @BeforeEach
//    @Test
//    void processFindFormWildCardString() {
//        // given
//        Owner owner = new Owner(130101033L, "Khayal", "Kazimli");
//        List<Owner> ownerList = new ArrayList<>();
//        final ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
//        given(ownerService.findAllByLastNameLike(argumentCaptor.capture())).willReturn(ownerList);
//        // when
//        ownerController.processFindForm(owner, bindingResult, Mockito.mock(Model.class));
//        // then
//        assertEquals("%Kazimli%", argumentCaptor.getValue());
//        // optional:
//        //  assertFalse(bindingResult.hasErrors());
//        //  assertEquals(130101033, owner.getId());
//    }
//
//    @Test
//    void processCreationFormSuccess() {
//        // given
//        Owner owner = new Owner(130101033L, "Khayal", "Kazimli");
//        given(bindingResult.hasErrors()).willReturn(false);
//        given(ownerService.save(owner)).willReturn(owner);
//        // when
//        String viewName = ownerController.processCreationForm(owner, bindingResult);
//        // then
//        assertEquals("redirect:/owners/" + owner.getId(), viewName);
//    }
//
//    @Test
//    void processCreationFormFailed() {
//        // given
//        Owner owner = new Owner(140503028L, "Tural", "Quliyev");
//        given(bindingResult.hasErrors()).willReturn(true);
//        // when
//        String viewName = ownerController.processCreationForm(owner, bindingResult);
//        // then
//        assertEquals(OWNERS_CREATE_OR_UPDATE_OWNER_FORM, viewName);
//    }



}