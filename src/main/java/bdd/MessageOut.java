package bdd;

import data.Message;

import static java.lang.Integer.parseInt;

public class MessageOut extends Message {

    /** Text of the message. Public because it is immutable (final + String type). */
    public String rowid ;
    public MessageOut(String source, String IPsource, String dest, String IPdest, String text, String horodatage, String rowid){
        super(source, IPsource, dest, IPdest, text, horodatage) ;
        this.rowid = rowid ;
    }

    @Override
    public String toString() { // récupérer le texte du message avec message.toSring()
        return this.rowid + ", " + this.text + ", " + this.horodatage ;
    }

    public int compareTo(MessageOut msg) {
        int res ;
        res= parseInt(this.rowid)-parseInt(msg.rowid) ;
        return res ;
    }

    public String getRowid() {
        return this.rowid ;
    }


}

