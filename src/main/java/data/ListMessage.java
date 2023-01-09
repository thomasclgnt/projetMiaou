package data;

import java.util.ArrayList;

public class ListMessage {

    ArrayList<Message> messagesSession = null;

    public ListMessage(){
        this.messagesSession = new ArrayList<Message>();
    }

    public void addMessage(String text, User source, User dest, String horodatage) {
        this.messagesSession.add(new Message(text, source, dest, horodatage)) ;
    }

    public String listToString(){
        String listDeb = "[" ;
        String list = "" ;
        for (Message m : this.messagesSession) {
            list = list + m.text + ", " + m.source + ", " + m.dest + ", " + m.horodatage + " ; \n";
        }
        String listFin = "]" ;
        return listDeb + list + listFin ;
    }

    public void printList(){
        System.out.println(listToString());
    }
}
