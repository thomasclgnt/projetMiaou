package data;

import tcp.MessageReceivedCallback;
import service.TCPController;

import java.io.IOException;
import java.net.Socket;

public class Session {

    public ListMessageIn conversation ;
    public User courant ;
    public User distant ;

    public Session(ListMessageIn conversation, User courant, User distant) {
        this.conversation = conversation;
        this.courant = courant;
        this.distant = distant;
    }

    public Socket startSession (String startWithIP, int portTCP, MessageReceivedCallback callback) throws IOException, InterruptedException {
        TCPController.initListening(portTCP, callback);
        Thread.sleep(200);
        return TCPController.startConversation(startWithIP, portTCP, callback);
    }

    public void endSession (){

    }

    // quand on sélectionne un USer => créer une session si elle existe pas déjà => classe liste session à créer avec la liste des sessions ?
    //startSession, endSession (= au moins retirer la Session de la liste des Sessions
}
