package data;

import bdd.Insert;

import java.util.ArrayList;

public class ListMessageIn {

    ArrayList<MessageIn> messagesSession = null;

    public ListMessageIn(){
        this.messagesSession = new ArrayList<MessageIn>();
    }

    // ajoute le message à la liste des messages ListMessageIn pour l'afficher sur l'interface et le transmet aussi à la BDD
    public void addMessage(String source, String IPsource, String dest, String IPdest, String text, String horodatage) {
        this.messagesSession.add(new MessageIn(source, IPsource, dest, IPdest, text, horodatage)) ;
        System.out.println("message ajouté liste");
        Insert.add_data(source, IPsource, dest, IPdest, text, horodatage);
        System.out.println("message ajouté bdd");

    }

    public String listToString(){
        String listDeb = "[" ;
        String list = "" ;
        for (MessageIn m : this.messagesSession) {
            list = list + m.text + ", " + m.source + ", " + m.dest + ", " + m.horodatage + " ; \n";
        }
        String listFin = "]" ;
        return listDeb + list + listFin ;
    }

    public void printList(){
        System.out.println(listToString());
    }
}
