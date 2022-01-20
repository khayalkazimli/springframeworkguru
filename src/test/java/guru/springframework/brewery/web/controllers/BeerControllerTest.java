package guru.springframework.brewery.web.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import guru.springframework.brewery.services.BeerService;
import guru.springframework.brewery.web.model.BeerDto;
import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import org.apache.tomcat.util.file.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(MockitoExtension.class)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

//    @InjectMocks
//    BeerController beerController;

//    @Mock
//    BeerService beerService;
    @MockBean
    BeerService beerService;

//    MockMvc mockMvc;
    @Autowired
    MockMvc mockMvc;

    BeerDto validBeer;

    @BeforeEach
    void setUp() {
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .version(1)
                .beerName("Beer1")
                .beerStyle(BeerStyleEnum.PILSNER)
                .price(BigDecimal.valueOf(12)) // new BigDecimal(12)
                .quantityOnHand(3)
                .upc(1432231L)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .build();

//        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
//                .setMessageConverters(jackson2HttpMessageConverter())
//                .build();
    }

    @AfterEach
    void tearDown() {
        reset(beerService);
    }

    @Test
    void listBeers() {
    }

    @Test
    void getBeerById() throws Exception {
        // given
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
        given(beerService.findBeerById(any())).willReturn(validBeer);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/beer/" + validBeer.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(validBeer.getId().toString())) // hamcrest: jsonPath("$.id", is(validBeer.getId().toString()))
                .andExpect(jsonPath("$.beerName", is("Beer1")))
                .andExpect(jsonPath("$.price").value(12))
                .andExpect(jsonPath("$.quantityOnHand").value(3))
                .andExpect(jsonPath("$.createdDate", is(formatter.format(validBeer.getCreatedDate()))))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        //        then(beerService).should().findBeerById(any());
    }
    @DisplayName("List Ops - ")
    @Nested
    public class TestListOperations {
        @Captor
        ArgumentCaptor<String> beerNameCaptor;

        @Captor
        ArgumentCaptor<BeerStyleEnum> beerStyleCaptor;

        @Captor
        ArgumentCaptor<PageRequest> pageRequestCaptor;

        BeerPagedList beerPagedList;

        @BeforeEach
        void setUp(){
            List<BeerDto> beers = new ArrayList<>();
            beers.add(validBeer);
            beers.add(BeerDto.builder().id(UUID.randomUUID())
                    .version(1)
                    .beerName("Beer2")
                    .beerStyle(BeerStyleEnum.IPA)
                    .price(BigDecimal.valueOf(15))
                    .quantityOnHand(2)
                    .upc(1453432L)
                    .createdDate(OffsetDateTime.now())
                    .lastModifiedDate(OffsetDateTime.now())
                    .build()
            );
            beerPagedList = new BeerPagedList(beers, PageRequest.of(1,1),2L);

            given(beerService.listBeers(beerNameCaptor.capture(),
                    beerStyleCaptor.capture(),
                    pageRequestCaptor.capture()
            )).willReturn(beerPagedList);
        }
        @DisplayName("Test list beers - no parameter")
        @Test
        void listBeersTest() throws Exception {
             mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.content.length()").value(2)) // hamcrest: jsonPath("$.content", hasSize(2))
                    .andExpect(jsonPath("$.content[0].version").value(1))
                    .andExpect(jsonPath("$.content[0].price").value(beerPagedList.stream().collect(Collectors.toList()).get(0).getPrice()));
                    // or: .andExpect(jsonPath("$.content[0].price").value(beerPagedList.stream().findFirst().get().getPrice()));
        }
    }
//    public MappingJackson2HttpMessageConverter jackson2HttpMessageConverter(){
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, true);
//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//
//        objectMapper.registerModule(new JavaTimeModule());
//        return new MappingJackson2HttpMessageConverter(objectMapper);
//    }
}