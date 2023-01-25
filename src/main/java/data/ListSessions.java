package data;

import java.net.InetAddress;
import java.util.ArrayList;

public class ListSessions {

    ArrayList<Session> activeSessions = null;

    public ListSessions(){
        this.activeSessions = new ArrayList<Session>();
    }

    public ArrayList<Session> convertToArrayList() {
        ArrayList<Session> list = new ArrayList<>();
        list.addAll(this.activeSessions);
        return list ;
    }

    public boolean isLoaded (String remoteUsername){
        boolean loaded = false ;
        for (Session session : this.activeSessions) {
            if (session.remoteUsername.equals(remoteUsername)) {
                loaded = session.load ;
            }
        }
        return loaded ;
    }

    public Session getSession (String remoteUsername){
        Session session = null ;
        for (Session s : this.activeSessions) {
            if (s.remoteUsername.equals(remoteUsername)) {
                session = s ;
            }
        }
        return session ;
    }

    public boolean contains (String remoteUsername){
        boolean inList = false ;
        for (Session s : this.activeSessions) {
            if (s.remoteUsername.equals(remoteUsername)) {
                inList = true ;
            }
        }
        return inList ;
    }


    public void addSession(String remoteUsername, boolean load) {
        this.activeSessions.add(new Session(remoteUsername, load)) ;
    }

    public void deleteSession(Session session) {
        this.activeSessions.remove(session);
    }

}
