package be.vinci.aj;

import domaine.Query;
import domaine.QueryFactory;
import server.ProxyServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        QueryFactory queryFactory = new QueryFactory();
        ProxyServer proxyServer = new ProxyServer(queryFactory);
        proxyServer.startServer();
    }
}