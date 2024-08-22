package test;

import javax.smartcardio.*;

public class CardApplication {

    public static void main(String[] args) throws CardException {
        CardSessionManager manager = new CardSessionManager();

        try {
            manager.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
