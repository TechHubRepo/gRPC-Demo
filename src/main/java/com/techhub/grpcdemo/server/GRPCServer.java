package com.techhub.grpcdemo.server;

import com.techhub.grpcdemo.config.Constant;
import com.techhub.grpcdemo.services.EmployeeService;
import com.techhub.grpcdemo.services.OrderService;
import com.techhub.grpcdemo.services.ReceiptService;
import com.techhub.grpcdemo.util.CMDLArgumentParser;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;

import io.grpc.Server;
import io.grpc.netty.shaded.io.netty.util.internal.StringUtil;
import io.grpc.protobuf.services.ProtoReflectionService;
import lombok.extern.slf4j.Slf4j;

/**
 * The GRPC Server class for GRPC services
 *
 * @author Ram Niwash
 */
@Slf4j
public class GRPCServer {

    /* PORT_NUMBER of Server */
    private final int portNumber;

    /**
     * Constructor to initialize the server port.
     */
    public GRPCServer() {
        this.portNumber = this.getPortNumber();
    }

    /**
     * Initialize and Starts the GRPC server
     *
     * @throws Exception if something went wrong.
     */
    public void initializeAndStart() throws Exception {
        Server server = Grpc.newServerBuilderForPort(this.portNumber, InsecureServerCredentials.create())
                .addService(ProtoReflectionService.newInstance())
                /* Add Services here */
                .addService(new EmployeeService())
                .addService(new OrderService())
                .addService(new ReceiptService())
                .build();

        /* Another Alternative approach to create the server */
        //Server server = ServerBuilder.forPort(this.PORT_NUMBER).addService(new EmployeeService()).build();

        /* Starting Server */
        server.start();
        ServerReadyEvent.onServerReady(this.portNumber);
        server.awaitTermination();
    }

    /**
     * Get the server port number
     *
     * @return integer, the port number
     */
    private int getPortNumber() {
        String port;
        try {
            port = CMDLArgumentParser.getArgumentValue(Constant.PORT_ARG);
        } catch (RuntimeException exception) {
            port = System.getenv(Constant.SYSTEM_ENV_GRPC_PORT);
        }
        return parsePortNumber(port);
    }

    /**
     * Parse the Port number from String to integer.
     *
     * @param port String type argument
     * @return int value as port number
     */
    private int parsePortNumber(String port) {
        if (!StringUtil.isNullOrEmpty(port) && port.matches(Constant.PORT_ARG_REGEX)) {
            return Integer.parseInt(port);
        } else {
            return Constant.DEFAULT_GRPC_SERVER_PORT;
        }
    }
}