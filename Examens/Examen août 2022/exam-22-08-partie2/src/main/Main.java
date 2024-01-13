package main;

import domain.RequestFactoryImpl;

public class Main {

    public static void main(String[] args) {
        RequestFactoryImpl requestFactory = new RequestFactoryImpl();
        Server server = new Server(requestFactory);
        server.listenKeyboard();
    }

}
