package test;

import javax.smartcardio.*;
import java.util.List;
import java.lang.Exception;
import java.lang.UnsupportedOperationException;

public class CardSessionManager {

    public CardSessionManager() {}

    /**
     * prints all connected terminals and returns an unmodifable list
     * containing each connected terminal object
     */
    private List<CardTerminal> getLocalTerminals() throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        // printing terminal statuses
        System.out.println("Terminals: " + terminals);
        return terminals;
    }

    // gets the first terminal
    private CardTerminal getFirstTerminal() throws CardException {
        List<CardTerminal> terminals = this.getLocalTerminals();
        // get the first terminal
        if (terminals.isEmpty()) {
            System.err.println("No card terminals found.");
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
    private CardChannel identifyCard(CardTerminal terminal) throws CardException {
        if (terminal.isCardPresent()) {
            System.out.println("Card found in terminal: " + terminal.getName() + ".");
            // connecting using any available protocol
            // T=0 is character oriented protocol, allows for apdus
            Card localCard = terminal.connect("*");
            System.out.println("Local card protocol: " + localCard.getProtocol());
            // card found: return channel to facilitate apdus
            return localCard.getBasicChannel();
        } else {
            System.err.println("Card not found in terminal: " + terminal.getName() + ".");
            return null;
        }

    }

    public void connect() throws Exception {
        CardTerminal terminal = null;
        try {
            terminal = this.getFirstTerminal();
        } catch (CardException e) {
            System.err.println("CardException: " + e.getMessage());
        } finally {
            if (terminal == null) {
                throw new Exception("No card terminals found.");
            }
        }
    
        CardChannel channel = null;
        try {
            channel = this.identifyCard(terminal);
        } catch (CardException e) {
            System.err.println("CardException: " + e.getMessage());
        } finally {
            if (channel == null) {
                throw new Exception("No card found in terminal.");
            }
        }

        // send apdus
        System.out.println("Sending APDUs...");
        throw new UnsupportedOperationException("Card communication not implemented yet.");
    }

}
