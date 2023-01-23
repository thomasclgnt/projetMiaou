package observer;

import java.util.ArrayList;

public interface Observable {

    ArrayList<Observer> observers = new ArrayList<Observer>();
    void suscribe(Observer observer);
    void notifyChangeListUsers();
    void notifyChangeConversation();


}
