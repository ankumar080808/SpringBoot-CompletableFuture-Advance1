package com.anant.CompletableFuturepart2.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import com.anant.CompletableFuturepart2.Database.EmployeeDatabase;
import com.anant.CompletableFuturepart2.dto.Employee;
import java.util.concurrent.Executor;

public class EmployeeReminderService {
	
	
	public CompletableFuture<Void> sendReminderToEmployee() {
		
		Executor executor=Executors.newFixedThreadPool(5);
		
		CompletableFuture<Void> voidCompletableFuture =	CompletableFuture.supplyAsync(()->{
			
			System.out.println("Fetch Employyess :" + Thread.currentThread().getName());
			
			return EmployeeDatabase.fetchEmployees();
			
		},executor).thenApplyAsync((employees)->{
			
		     System.out.println("filter new joiner employee  : " + Thread.currentThread().getName());
			return employees.stream().filter(employee->"True".equals(employee.getNewJoiner()))
					.collect(Collectors.toList());
		},executor).thenApplyAsync((employees)->{
			
			 System.out.println("filter training not complete employee  : " + Thread.currentThread().getName());
			return employees.stream().filter(employee->"True".equals(employee.getLearningPending()))
					.collect(Collectors.toList());
		},executor).thenApplyAsync((employees)->{
			System.out.println("get emails  : " + Thread.currentThread().getName());
			 return employees.stream().map(Employee::getEmail).collect(Collectors.toList());
			
		},executor).thenAcceptAsync((emails)->{
			
			 System.out.println("send email  : " + Thread.currentThread().getName());
			 
			 emails.forEach(EmployeeReminderService::sendEmail);
		},executor);
		
		
		 return voidCompletableFuture;
		
		
	}
	 public static void sendEmail(String email) {
	        System.out.println("sending training reminder email to : " + email);
	    }
}
