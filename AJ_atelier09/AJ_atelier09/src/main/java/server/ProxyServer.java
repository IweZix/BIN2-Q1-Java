package server;

import domaine.Query;
import domaine.QueryFactory;

import java.util.Scanner;

public class ProxyServer {

    private Scanner scn = new Scanner(System.in);

    private QueryFactory queryFactory;

    public ProxyServer(QueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public void startServer() {

        while (true) {
            System.out.print("Enter a URL: ");
            String url = scn.nextLine();
            Query queryImpl = queryFactory.getQuery();
            queryImpl.setUrl(url);
            queryImpl.setMethod(Query.QueryMethod.GET);
            QueryHandler queryHandler = new QueryHandler(queryImpl);
            queryHandler.start();

        }
    }
}
