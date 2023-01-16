package data;

import bdd.MessageBDD;
import bdd.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.lang.Integer.parseInt;

public class History {

    public static void restoreConversation(String IPsource, String IPdest) {
        ArrayList<MessageBDD> listeRecu = new ArrayList<MessageBDD>();
        ArrayList<MessageBDD> listeEnvoi = new ArrayList<MessageBDD>();
        ArrayList<MessageBDD> listeRes = new ArrayList<MessageBDD>();

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

        //TODO ajouter ce qu'on récupère à listMessage

    }

    public static void main(String[] args) {
        restoreConversation("100","200");
    }

}
