package data;

import java.sql.Timestamp;

public class Message {

    /** Text of the message. Public because it is immutable (final + String type). */
    public final String text;
    public User source ;
    public User dest ;
    public String horodatage ;

    public Message(String text, User source, User dest, String horodatage){
        this.text = text;
        this.source = source;
        this.dest = dest;
        this.horodatage = horodatage;
    }

    @Override
    public String toString() { // récupérer le texte du message avec message.toSring()
        return this.text;
    }

}
