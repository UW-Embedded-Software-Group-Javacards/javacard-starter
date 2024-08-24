package test;

import javax.smartcardio.*;
import java.util.List;
import java.lang.UnsupportedOperationException;
import java.lang.Exception;

// consider renaming to 'ScannerSessionManager'
// make custom exception class? (CardException on its own is not always accurate)
public class CardSessionManager {

    private CardTerminal terminal;
    private CardChannel channel;

    public CardSessionManager() throws CardException {
        terminal = this.getFirstTerminal();
        channel = this.identifyCard(terminal);
    }

    /**
     * prints all connected terminals and returns an unmodifable list
     * containing each connected terminal object
     */
    private List<CardTerminal> getLocalTerminals() throws CardException {
        TerminalFactory factory = TerminalFactory.getDefault();
        List<CardTerminal> terminals = factory.terminals().list();
        // printing terminal statuses
        // System.out.println("Terminals: " + terminals);
        return terminals;
    }

    /**
     * returns the first terminal found
     */
    private CardTerminal getFirstTerminal() throws CardException {
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
    private CardChannel identifyCard(CardTerminal terminal) throws CardException {
        if (!terminal.isCardPresent()) {
            throw new CardException("No card found in terminal: " + terminal.getName() + ".");
        }

        System.out.println("Card found in terminal: " + terminal.getName() + ".");
        // connecting using any available protocol
        // T=0 is character oriented protocol, allows for apdus
        Card localCard = terminal.connect("DIRECT");

        System.out.println("Local card protocol: " + localCard.getProtocol());
        System.out.println("Card ATR: " + localCard.getATR());

        // card found: return channel to facilitate apdus
        return localCard.getBasicChannel();
    }

    /**
     * connects to a card terminal and sends apdus
     */
    // rename this to avoid name collision
    public void communicate() throws Exception {
        // send apdus
        System.out.println("Sending APDUs...");

        // hardcoded select master file command (testing)
        CommandAPDU selectAPDU = new CommandAPDU(
                0x00, 0xA4, 0x00, 0x00,
                new byte[] { (byte) 0xF3, (byte) 0x00 });

        // get the response after the select command
        ResponseAPDU response = channel.transmit(selectAPDU);
        System.out.println(response);

        // Check the status word (SW)
        if (response.getSW() == 0x9000) {
            System.out.println("Master File selected successfully.");
        } else {
            System.out.println("Failed to select Master File: " + Integer.toHexString(response.getSW()));
        }
    }

}
