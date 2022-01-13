package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Component;

@Component // part of element
public class YannyWordProducer implements WordProducer{
    @Override
    public String getWord() {
        return "Yanny";
    }
}
