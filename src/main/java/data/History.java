package data;

import bdd.Select;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class History {

    public void restoreConversation(String IPsource, String IPdest) {
        List listeRecu = new ArrayList();
        List listeEnvoi = new ArrayList();
        //listeEnvoi.sort(Comparator.comparing((m) -> m.rawid)); TODO pour trier les messages dans la liste finale de l'objet message
        String historiqueBrut ;
        listeRecu = Select.restore(IPdest, IPsource) ;
        listeEnvoi = Select.restore(IPsource, IPdest) ;
        System.out.println("L'historique de la conversation a été chargé"); //TODO voir si on garde le message ?
        //ajouter ce qu'on récupère à listMessage

    }

}
