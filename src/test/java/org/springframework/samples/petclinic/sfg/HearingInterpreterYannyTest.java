package org.springframework.samples.petclinic.sfg;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.config.BaseConfig;
import org.springframework.samples.petclinic.sfg.config.YannyConfig;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {BaseConfig.class, YannyConfig.class})
public class HearingInterpreterYannyTest {

    @Autowired
    HearingInterpreter hearingInterpreter;

    // without configuration:
//    HearingInterpreter hearingInterpreter;
//    @Before
//    public void setUp() throws Exception {
//        hearingInterpreter = new HearingInterpreter(new YannyWordProducer());
//    }

    @Test
    public void whatIHeard() {
        String word = hearingInterpreter.whatIHeard();

        assertEquals("Yanny", word);
    }
}