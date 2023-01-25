package data;

import tcp.MessageReceivedCallback;
import service.TCPController;

import java.io.IOException;
import java.net.Socket;

public class Session {

    public String remoteUsername ;
    public boolean load ;

    public Session(String remoteUsername, boolean load) {
        this.remoteUsername = remoteUsername;
        this.load = load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public void startSession () {
    }

    public void endSession (){

    }

    // quand on sélectionne un USer => créer une session si elle existe pas déjà => classe liste session à créer avec la liste des sessions ?
    //startSession, endSession (= au moins retirer la Session de la liste des Sessions
}
