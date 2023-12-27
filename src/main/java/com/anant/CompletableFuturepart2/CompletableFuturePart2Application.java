package com.anant.CompletableFuturepart2;

import java.io.File;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.anant.CompletableFuturepart2.async.EmployeeReminderService;
import com.anant.CompletableFuturepart2.async.RunAsyncDemo;

@SpringBootApplication
public class CompletableFuturePart2Application {

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		SpringApplication.run(CompletableFuturePart2Application.class, args);
		
		   RunAsyncDemo runAsyncDemo = new RunAsyncDemo();
	        runAsyncDemo.saveEmployees(new File("employees.json"));
	        runAsyncDemo.saveEmployeesWithCustomExecutor(new File("employees.json"));
	        EmployeeReminderService service=new EmployeeReminderService();
	        service.sendReminderToEmployee().get();
	}

}
