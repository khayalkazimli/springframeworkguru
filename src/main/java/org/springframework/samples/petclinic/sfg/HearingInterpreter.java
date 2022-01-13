package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Service;
// Interpreter - tercumeci
@Service
public class HearingInterpreter {
    private final WordProducer wordProducer;

    public HearingInterpreter(WordProducer wordProducer) {
        this.wordProducer = wordProducer;
    }
    public String StringwhatIHeard(){
        String word = wordProducer.getWord();

        System.out.println(word);
        return word;
    }
}
