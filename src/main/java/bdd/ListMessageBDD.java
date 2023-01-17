package bdd;

import java.util.ArrayList;

public class ListMessageOut {
    ArrayList<MessageOut> messagesSession = null;

    public ListMessageOut(){
        this.messagesSession = new ArrayList<MessageOut>();
    }

    public void addMessage(String source, String IPsource, String destination, String IPdest, String text, String horodatage) {
        this.messagesSession.add(new MessageOut(text, source, dest, horodatage)) ;
        //TODO le add à ma BDD peut se faire directement ici
    }

    public String listToString(){
        String listDeb = "[" ;
        String list = "" ;
        for (MessageOut m : this.messagesSession) {
            list = list + m.text + ", " + m.source + ", " + m.dest + ", " + m.horodatage + " ; \n";
        }
        String listFin = "]" ;
        return listDeb + list + listFin ;
    }

    public void printList(){
        System.out.println(listToString());
    }

}
