package test;

import javax.smartcardio.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws CardException {
        System.out.println("Hello world!");
        // testing smartcardio
        // code sampled from javax.smartcardio docs

        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        System.out.println("Terminals: " + terminals);
        // get the first terminal
        if (terminals.isEmpty()) {
            System.out.println("No card terminals found.");
        } else {
            CardTerminal terminal = terminals.get(0);
            System.out.println("Card terminal found.");
            if (terminal.isCardPresent()) {
                System.out.println("Card found.");
            } else {
                System.out.println("Card not found.");
            }
        }

    }
}