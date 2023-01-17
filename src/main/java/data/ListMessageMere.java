package data;

import java.util.ArrayList;


public class ListMessageMere {

        ArrayList<MessageMere> messagesSession = null;

        public ListMessageMere(){
            this.messagesSession = new ArrayList<MessageMere>();
        }

        public void addMessage(String source, String IPsource, String dest, String IPdest, String text, String horodatage) {
            this.messagesSession.add(new MessageMere(source, IPsource, dest, IPdest, text, horodatage)) ;
            //TODO le add à ma BDD peut se faire directement ici
        }

        public String listToString(){
            String listDeb = "[" ;
            String list = "" ;
            for (MessageMere m : this.messagesSession) {
                list = list + m.text + ", " + m.source + ", " + m.dest + ", " + m.horodatage + " ; \n";
            }
            String listFin = "]" ;
            return listDeb + list + listFin ;
        }

        public void printList(){
            System.out.println(listToString());
        }
    }


}
