package data;

import java.net.InetAddress;
import java.util.ArrayList;

public class ListSessions {

    ArrayList<Session> activeSessions = null;

    public ListSessions(){
        this.activeSessions = new ArrayList<Session>();
    }

    public void addSession(ListMessageIn conversation, User courant, User distant) {
        this.activeSessions.add(new Session(conversation, courant, distant)) ;
    }

    public void deleteSession(Session session) {
        this.activeSessions.remove(session);
    }

}
