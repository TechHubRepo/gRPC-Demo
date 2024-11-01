package com.techhub.grpcdemo;

import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * The Main class and starting point of the application.
 */
@Slf4j
public class Start {

    /** The Default Port number of Server */
    private static final int DEFAULT_PORT_NUMBER = 8400;
    private static final String SYSTEM_ENV_PORT_NUMBER = "GRPC_SERVER_PORT";

    /**
     * The Starting point of application.
     *
     * @param args The command line arguments
     */
    public static void main(String[] args) {
        log.info("::::::: WELCOME TO GRPC SERVER :::::::");
        int portNumber;
        String port;
        if (args == null || args.length == 0) {
            port = System.getenv(SYSTEM_ENV_PORT_NUMBER);
        } else {
            port = args[0];
        }
        portNumber = parsePortNumber(port);

        System.out.println("PORT NUMBER = "+portNumber);

        try{
            GRPCServer server = new GRPCServer(portNumber);
            server.initialize();
            server.start();
        }catch (IOException exception){
            exception.printStackTrace();
        }
    }

    /**
     * Parse the Port number from String to integer.
     *
     * @param port String type argument
     * @return int value as port number
     */
    private static int parsePortNumber(String port){
        if(!StringUtil.isNullOrEmpty(port) && port.matches("[0-9]{4}")){
            return Integer.parseInt(port);
        }else{
            return DEFAULT_PORT_NUMBER;
        }
    }
}