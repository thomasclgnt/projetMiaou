package bdd;

import static java.lang.Integer.parseInt;

public class MessageBDD {

    /** Text of the message. Public because it is immutable (final + String type). */
    public final String text;
    public String horodatage ;
    public String rowid ;


    public MessageBDD(String rowid, String text, String horodatage){
        this.rowid = rowid ;
        this.text = text;
        this.horodatage = horodatage;

    }

    @Override
    public String toString() { // récupérer le texte du message avec message.toSring()
        return this.rowid + ", " + this.text + ", " + this.horodatage ;
    }


    public int compareTo(MessageBDD msg) {
        int res ;
        res= parseInt(this.rowid)-parseInt(msg.rowid) ;
        return res ;
    }
    public String getRowid() {
        return this.rowid ;
    }


}

