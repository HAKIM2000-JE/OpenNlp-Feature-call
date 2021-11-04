package com.medicalChatbots.medicalChatbot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;


@SpringBootApplication
public class MedicalChatbotApplication {

	public MedicalChatbotApplication() throws IOException {
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MedicalChatbotApplication.class, args);

		String sentence = " Hi. How are you? Welcome to Tutorialspoint. "
				+ "We provide free tutorials on various technologies." + "other sentece to test number of sentences";

		String simple = "[.?!]";
		String[] splitString = (sentence.split(simple));
		for (String string : splitString)
			System.out.println(string);


		//Loading sentence detector model


		//Instantiating the SentenceDetectorME class


	}

}




