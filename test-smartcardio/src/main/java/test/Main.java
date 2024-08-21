package test;

import javax.smartcardio.*;

import java.security.cert.CRLException;
import java.util.List;

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

    /**
     * prints all connected terminals and returns an unmodifable list
     * containing each connected terminal object
     */
    public static List<CardTerminal> getLocalTerminals() throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        // printing terminal statuses
        System.out.println("Terminals: " + terminals);
        return terminals;
    }

    // gets the first terminal
    public static CardTerminal getFirstTerminal() throws CardException {
        List<CardTerminal> terminals = getLocalTerminals();
        // get the first terminal
        if (terminals.isEmpty()) {
            System.out.println("No card terminals found.");
            return null;
        } else {
            System.out.println("Card terminal found.");
            CardTerminal terminal = terminals.get(0);
            return terminal;
        }
    }

    /**
     * given a terminal, prints whether a card is detected
     * then, establishes a connection to a card if one exists and prints all of its
     * data
     */
    public static CardChannel identifyCard(CardTerminal terminal) throws CardException {
        if (terminal.isCardPresent()) {
            System.out.println("Card found in terminal: " + terminal.getName() + ".");
            // connecting using any available protocol
            // T=0 is character oriented protocol, allows for apdus
            Card localCard = terminal.connect("*");
            System.out.println("Local card protocol: " + localCard.getProtocol());
            // card found: return channel to facilitate apdus
            return localCard.getBasicChannel();
        } else {
            System.out.println("Card not found in terminal: " + terminal.getName() + ".");
            return null;
        }

    }

}