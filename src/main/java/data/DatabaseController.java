package data;

import bdd.*;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Integer.parseInt;

public class DatabaseController {

    public static void createDBmiaoudb() {
        CreateDatabase.createNewDatabase("miaoudb.db");
    }

    public static void createTableMsg() {
        CreateTable.createTableMessageDB();
    }

    public static void createTableUsers() {
        CreateTable.createTableListUsers();
    }

    public static ArrayList<MessageOut> restoreConversation(String IPsource, String IPdest) { //TODO void ou ArrayList ?
        ArrayList<MessageOut> listeRecu  ;
        ArrayList<MessageOut> listeEnvoi ;
        ArrayList<MessageOut> listeRes = new ArrayList<>() ;

        listeRecu = Select.restore(IPdest, IPsource) ;
        listeEnvoi = Select.restore(IPsource, IPdest) ;
        listeRes.addAll(listeRecu) ;
        listeRes.addAll(listeEnvoi) ;

        listeRes.sort(Comparator.comparing((m) -> parseInt(m.rowid))); //TODO pour trier les messages dans la liste finale de l'objet message
        System.out.println("L'historique de la conversation a été chargé"); //TODO voir si on garde le message ?

        //affichage et futurs tests :
        System.out.println("taille historique : " + listeRes.size()); //vérifier que taille finale est bien la somme des deux tailles
        //System.out.println(listeRes.toArray()[3].toString()) ;

        for (int i=0 ; i<listeRes.size() ; i++) {
            String aux = listeRes.toArray()[i].toString() ;
            System.out.println(aux);
        }

        return listeRes ;

        //TODO ajouter ce qu'on récupère à listMessageOut

    }

    public static ListUser restoreListUsers() { //TODO void ou ListUser ?
        ListUser listUsers  ;
        listUsers = Select.restoreUsers() ;
        System.out.println("La liste des utilisateurs a été chargée :");
        String aux = listUsers.listToString() ;
        System.out.println(aux) ;

        return listUsers ;

       // if (aux.contains("thomas")) { // peut être pratique de tchecker si le pseudo est libre comme ça
          //  System.out.println("c'est dedans");
        //}
    }

    public static void deleteUser(String ip) {
        Delete.deleteUser(ip);
    }

    public static void addMessage(Message m) {
        Insert.add_data(m.source, m.IPsource, m.dest, m.IPdest, m.text, m.horodatage);
    }

    public static void addUser(String username, String addressIP) {
        Insert.add_user(username, addressIP);
    }

    public static void addMyself(String name) {
        Insert.add_Myself(name);
    }

    public static String getMyName() {
        return Select.restoreMyself();
    }

    public static void changeMyname(String new_name) {
        Update.runUpdateMyself(getMyName(), new_name);
    }


    public static void main(String[] args) {
        //restoreConversation("100","200");
        //restoreListUsers();
    }

}
