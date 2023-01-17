package data;

import java.sql.Timestamp;

public class MessageIn extends Message {

    /** Text of the message. Public because it is immutable (final + String type). */

    public MessageIn(String source, String IPsource, String dest, String IPdest, String text, String horodatage){
        super(source, IPsource, dest, IPdest, text, horodatage) ;
    }

    @Override
    public String toString() { // récupérer le texte du message avec message.toSring()
        return this.text;
    }

}
