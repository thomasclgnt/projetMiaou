package service;

import bdd.*;
import data.ListUser;
import data.Message;

import java.util.ArrayList;
import java.util.Comparator;

import static java.lang.Integer.parseInt;

public class DatabaseController {

    public static void createDBmiaoudb() {
        CreateDatabase.createNewDatabase();
        System.out.println("[db_controller] La Base de donnée a été créée") ;
    }

    public static void createTableMsg() {
        CreateTable.createTableMessageDB();
        System.out.println("[db_controller] La table MessageDB a été créée dans la base de donnée") ;
    }

    public static void createTableMyself() {
        CreateTable.createTableMyself();
        System.out.println("[db_controller] La table Myself a été créée dans la base de donnée") ;
    }

    public static void dropTableMyself() {
        CreateTable.dropTableMyself();
        System.out.println("[db_controller] La table Myself a été supprimée de la base de donnée") ;
    }

    public static void dropTableListUsers() {
        CreateTable.dropTableListUsers();
        System.out.println("[db_controller] La table Users a été supprimée de la base de donnée") ;
    }

    public static void createTableUsers() {
        CreateTable.createTableListUsers();
        System.out.println("[db_controller] La table Users a été créée dans la base de donnée") ;
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
        System.out.println("[db_controller] L'historique de la conversation a été chargé"); //TODO voir si on garde le message ?

        //affichage et futurs tests :
        System.out.println("[db_controller] Taille historique : " + listeRes.size());


       /* for (int i=0 ; i<listeRes.size() ; i++) {
            //String aux = listeRes.toArray()[i].toString() ;
            //System.out.println(aux);
        }*/

        return listeRes ;

        //TODO ajouter ce qu'on récupère à listMessageOut

    }

    public static void addMessage(Message m) {
        Insert.add_data(m.source, m.IPsource, m.dest, m.IPdest, m.text, m.horodatage);
        System.out.println("[db_controller] Le message à " + m.dest + " a été ajouté à la base de donnée") ;
    }

//TABLE LISTUSERS
    public static ListUser restoreListUsers() { //TODO void ou ListUser ?
        ListUser listUsers  ;
        listUsers = Select.restoreUsers() ;
        System.out.println("[db_controller] La liste des utilisateurs a été chargée :");
        String aux = listUsers.listToString() ;
        System.out.println(aux) ;

        return listUsers ;

       // if (aux.contains("thomas")) { // peut être pratique de tchecker si le pseudo est libre comme ça
          //  System.out.println("c'est dedans");
        //}
    }


    public static void addUser(String username, String addressIP) {
        Insert.add_user(username, addressIP);
        System.out.println("[db_controller] L'utilisateur " + username + " d'adresse IP : " + addressIP + " a été ajouté à la base de donnée") ;
    }

    public static void updateUser(String new_username, String ip) {
        Update.runUpdateListUsers(new_username, ip);
        System.out.println("[db_controller] L'utilisateur d'adresse IP : " + ip + " a changé de pseudo pour : " + new_username) ;
    }

    public static void deleteUser(String ip) {
        Delete.deleteUser(ip);
        System.out.println("[db_controller] L'utilisateur d'adresse IP : " + ip + " a été supprimé de la base de donnée") ;
    }

//TABLE MYSELF
    public static void addMyself(String name) {
        Insert.addMyself(name);
        System.out.println("[db_controller] Le pseudo local a été ajouté à la base de donnée Myself") ;
    }

    public static String getMyName() {
        System.out.println("[db_controller] Le pseudo local a été récupéré") ;
        return Select.restoreMyself() ;
    }

    public static void updateMyself(String new_name) {
        Update.runUpdateMyself(getMyName(), new_name);
        System.out.println("[db_controller] Le pseudo local a été mis à jour") ;
    }


    public static void main(String[] args) {
        //restoreConversation("192.168.1.71","192.168.1.79");
        //restoreListUsers();
        //System.out.println(getMyName());
    }

}
