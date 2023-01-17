package data;

import java.sql.Timestamp;

public class MessageMere {

        /** Text of the message. Public because it is immutable (final + String type). */
        public final String text;
        public String source ;
        public String IPsource ;
        public String dest ;
        public String IPdest ;
        public String horodatage ;

        public MessageMere(String source, String IPsource, String dest, String IPdest, String text, String horodatage){
            this.source = source ;
            this.IPsource = IPsource ;
            this.dest = dest ;
            this.IPdest = IPdest ;
            this.text = text;
            this.horodatage = horodatage;
        }

        public String getText() {
            return text;
        }

    @Override
        public String toString() { // récupérer le texte du message avec message.toSring()
            return this.text;
        }

}
