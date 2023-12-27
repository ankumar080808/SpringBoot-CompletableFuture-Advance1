package com.anant.CompletableFuturepart2.async;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Executor;

import com.anant.CompletableFuturepart2.dto.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RunAsyncDemo {

	 public Void saveEmployees(File jsonFile) throws InterruptedException, ExecutionException {
	        ObjectMapper mapper = new ObjectMapper();
	        CompletableFuture<Void> runAsyncFuture = CompletableFuture.runAsync(new Runnable() {

	            @Override
	            public void run() {
	                try {
	                    List<Employee> employees = mapper
	                            .readValue(jsonFile, new TypeReference<List<Employee>>() {
	                            });
	                    //write logic t save list of employee to database
	                    //repository.saveAll(employees);
	                    System.out.println("Thread : " + Thread.currentThread().getName());
	                    System.out.println(employees.size());
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        });

	        return runAsyncFuture.get();
	    }
	 
	 public Void saveEmployeesWithCustomExecutor(File jsonFile) throws InterruptedException, ExecutionException
	 {
		 ObjectMapper mapper=new ObjectMapper();
		 Executor executor=Executors.newFixedThreadPool(5);
		 
		   CompletableFuture<Void> runAsyncFuture=CompletableFuture.runAsync(()->{
		 try {
			 
			       List<Employee> employees=   mapper.readValue(jsonFile, new TypeReference<List<Employee>>() {});
			       System.out.println("Thread : " + Thread.currentThread().getName());
                   System.out.println(employees.size());
		 }
		 catch (IOException e) {
             e.printStackTrace();
         }
	 },executor);
		   
         return runAsyncFuture.get();

}
}
