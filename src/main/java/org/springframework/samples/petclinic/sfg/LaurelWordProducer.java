package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Component;

@Component // part of element
public class LaurelWordProducer implements WordProducer{
    @Override
    public String getWord() {
        return "Laurel";
    }
}
