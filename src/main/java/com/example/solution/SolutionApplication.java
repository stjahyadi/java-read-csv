package com.example.solution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.solution.service.ProcessService;

@SpringBootApplication
public class SolutionApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(SolutionApplication.class);

	@Autowired
	private ProcessService processService;

	@Value("${version}")
	private String version;

	@Value("${csv.file}")
	private String fileName;

	public static void main(String[] args) {
		SpringApplication.run(SolutionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Version Program: " + version);
		LOGGER.info("Print file name: " + fileName);
		processService.readCsv(fileName);
	}

}
