package test;

import javax.smartcardio.*;
import java.util.List;
import java.lang.UnsupportedOperationException;

// consider renaming to 'ScannerSessionManager'
// make custom exception class? (CardException on its own is not always accurate)
public class CardSessionManager {

    private CardTerminal terminal;
    private CardChannel channel;

    public CardSessionManager() throws Exception { 
        terminal = null;
        try {
            terminal = this.getFirstTerminal();
        } catch (CardException e) {
            System.err.println("CardException: " + e.getMessage());
        } finally {
            if (terminal == null) {
                throw new Exception("No card terminals found.");
            }
        }
    
        channel = null;
        try {
            channel = this.identifyCard(terminal);
        } catch (CardException e) {
            System.err.println("CardException: " + e.getMessage());
        } finally {
            if (channel == null) {
                throw new Exception("No card found in terminal.");
            }
        }
    }

    /**
     * prints all connected terminals and returns an unmodifable list
     * containing each connected terminal object
     */
    private List<CardTerminal> getLocalTerminals() throws Exception {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        // printing terminal statuses
        // System.out.println("Terminals: " + terminals);
        return terminals;
    }

    /**
     * returns the first terminal found
     */
    private CardTerminal getFirstTerminal() throws Exception {
        List<CardTerminal> terminals = this.getLocalTerminals();
        if (terminals.isEmpty()) {
            throw new CardException("No card terminals found.");
        }

        System.out.println("Card terminal found.");
        CardTerminal terminal = terminals.get(0);
        return terminal;
    }

    /**
     * given a terminal, prints whether a card is detected
     * then, establishes a connection to a card if one exists and prints all of its
     * data
     */
    private CardChannel identifyCard(CardTerminal terminal) throws Exception {
        if (!terminal.isCardPresent()) {
            throw new CardException("No card found in terminal: " + terminal.getName() + ".");
        }

        System.out.println("Card found in terminal: " + terminal.getName() + ".");
        // connecting using any available protocol
        // T=0 is character oriented protocol, allows for apdus
        Card localCard = terminal.connect("*");
        System.out.println("Local card protocol: " + localCard.getProtocol());
        // card found: return channel to facilitate apdus
        return localCard.getBasicChannel();
    }

    /**
     * connects to a card terminal and sends apdus
     */
    public void connect() throws Exception {
        // send apdus
        System.out.println("Sending APDUs...");
        throw new UnsupportedOperationException("Card communication not implemented yet.");
    }

}
