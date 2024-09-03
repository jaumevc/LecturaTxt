package com.fitxers;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fitxers.gestio.FilesMangement;

@SpringBootApplication
public class LecturaTxtApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(LecturaTxtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hola Jaume");
		FilesMangement management = new FilesMangement();
		management.getFileFromFolder();
		System.out.println("Adeu Jaume");
	}
}
