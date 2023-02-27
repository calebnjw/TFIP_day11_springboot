package com.example.demo;

import java.util.Collections;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {
	private static String portNumber = null;
	private static String DEFAULT_PORTNUM = "3000";

	public static void main(String[] args) {
		// to handle cmd line arguments from
		// > mvn clean spring-boot:run -Dspring-boot.run.arguments="[...]=xxx"
		// or
		// > java -jar ... --D[...]=xxx
		for (String arg : args) {
			if (arg.contains("--Dport=") || arg.contains("--port=")) {
				String[] components = arg.split("=", 0);
				portNumber = components[1];
				System.out.println("CONTAINS: " + components[0]);
				System.out.println("PORT NUMBER: " + portNumber);
			}
		}

		// to handle cmd line arguments from
		// > mvn clean spring-boot:run -Dspring-boot.run.arguments="[...]=xxx"
		ApplicationArguments appArgs = new DefaultApplicationArguments(args);
		if (appArgs.containsOption("--Dport=")) {
			portNumber = appArgs.getOptionValues("port").get(0);
			System.out.println("PORT NUMBER: " + portNumber);
		}

		// getting port number from system variable
		// for running without specifying port number
		if (portNumber == null) {
			portNumber = System.getenv("SERVER_PORT");
			System.out.println("PORT NUMBER: " + portNumber);
		}

		// setting a default port number
		// for running without specifying port number
		if (portNumber == null || portNumber.isBlank()) {
			portNumber = DEFAULT_PORTNUM;
			System.out.println("DEFAULT PORT: " + portNumber);
		}

		SpringApplication app = new SpringApplication(DemoApplication.class);
		// setting default port number from the application properties
		app.setDefaultProperties(Collections.singletonMap("server.port", portNumber));
		app.run(args);
	}

}
