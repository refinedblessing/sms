package jpa;

import jpa.util.ConnectionFactory;

public class App {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = ConnectionFactory.GET_SESSION.getInstance();
    }
}
