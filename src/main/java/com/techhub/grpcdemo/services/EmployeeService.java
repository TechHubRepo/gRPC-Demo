package com.techhub.grpcdemo.services;

import com.techhub.grpc.services.employee.EmployeeControl;
import com.techhub.grpc.services.employee.EmployeeRequestData;
import com.techhub.grpc.services.employee.EmployeeResponseData;
import com.techhub.grpc.services.employee.EmployeeServiceGrpc;

import com.techhub.grpcdemo.config.Constants;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

/**
 * Implements the EmployeeService
 *
 * @author Ram Niwash
 */
@Slf4j
public class EmployeeService extends EmployeeServiceGrpc.EmployeeServiceImplBase{

    @Override
    public StreamObserver<EmployeeControl> employeeStream(StreamObserver<EmployeeResponseData> responseObserver) {

        return new StreamObserver<EmployeeControl>() {
            @Override
            public void onNext(EmployeeControl value) {
                boolean enabled = value.getEnabled();
                log.info("Enabled : {}",enabled);
            }

            @Override
            public void onError(Throwable ex) {
                log.info(Constants.EXCEPTION_OCCURRED);
                log.error(ex.getMessage(), ex);
                log.info(Constants.STACK_TRACE_ENDS);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<EmployeeRequestData> addEmployee(StreamObserver<EmployeeResponseData> responseObserver) {

        log.info(">> Executing EmployeeService > addEmployee(StreamObserver) >>");
        return new StreamObserver<EmployeeRequestData>() {
            @Override
            public void onNext(EmployeeRequestData value) {
                String name = value.getName();
                log.info("NAME : {}",name);
            }

            @Override
            public void onError(Throwable ex) {
                log.info(Constants.EXCEPTION_OCCURRED);
                log.error(ex.getMessage(), ex);
                log.info(Constants.STACK_TRACE_ENDS);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
