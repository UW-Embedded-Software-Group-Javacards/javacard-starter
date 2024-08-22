package test;

import javax.smartcardio.*;

// testing smartcardio
// code sampled from javax.smartcardio docs
public class Main {

    public static void main(String[] args) throws CardException {
        CardTerminal terminal = getFirstTerminal();
        if (terminal != null) {
            CardChannel channel = identifyCard(terminal);
            if (channel != null) {
                // send apdus
            }
        }
    }


}
