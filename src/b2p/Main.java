package b2p;

import b2p.client.B2PTablutClient;

public class Main {

    public static void main(String[] args) {

        // The competition rules state that we need 3 input parameters:
        // player = white | black
        String player = "white";

        // timeout in seconds (the client constructor wants it in millis)
        int timeout = 15000;

        // server IP
        String serverIP = "localhost";

        // Parameters are optional for test purposes
        if (args.length > 0) {
            player = args[0];
        }

        if (args.length > 1) {
            timeout = Integer.parseInt(args[1]) * 1000;
        }

        if (args.length > 2) {
            serverIP = args[2];
        }

        try {
            B2PTablutClient client = new B2PTablutClient(player, timeout, serverIP);
            client.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}