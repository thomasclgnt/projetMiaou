package data;

import bdd.Select;

public class History {

    public void restoreConversation(String IPsource, String IPdest) {
        Select.restore(IPsource, IPdest) ;
        System.out.println("L'historique de la conversation a été chargé"); //TODO voir si on garde le message ?
        //ajouter ce qu'on récupère à listMessage

    }

}
