package guru.springframework.brewery.web.controllers;

import guru.springframework.brewery.web.model.BeerPagedList;
import guru.springframework.brewery.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void BeerControllerlistBeersTest() {
        BeerPagedList beerPagedList = restTemplate.getForObject("/api/v1/beer", BeerPagedList.class);

        assertEquals(3, beerPagedList.getContent().size()); // JUnit 5
//        assertThat(beerPagedList.getContent()).hasSize(3); // AssertJ
        assertEquals("Mango Bobs", beerPagedList.getContent().get(0).getBeerName());
        assertEquals(BeerStyleEnum.PORTER, beerPagedList.getContent().get(2).getBeerStyle());
        System.out.println(beerPagedList.getContent().get(0).getBeerName());
    }
}
