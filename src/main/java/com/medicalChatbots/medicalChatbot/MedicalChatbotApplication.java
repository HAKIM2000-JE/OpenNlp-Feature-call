package com.medicalChatbots.medicalChatbot;

// import opennlp.tools.cmdline.parser.ParserTool;
// import opennlp.tools.namefind.NameFinderME;
// import opennlp.tools.namefind.TokenNameFinderModel;
// import opennlp.tools.parser.Parse;
// import opennlp.tools.parser.Parser;
// import opennlp.tools.parser.ParserFactory;
// import opennlp.tools.parser.ParserModel;
// import opennlp.tools.util.Span;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

// import opennlp.tools.sentdetect.SentenceDetectorME;
// import opennlp.tools.sentdetect.SentenceModel;
// import java.io.FileInputStream;
// import java.io.InputStream;
// import opennlp.tools.tokenize.SimpleTokenizer;

@SpringBootApplication
public class MedicalChatbotApplication {

	public MedicalChatbotApplication() throws IOException {
	}

	public static void main(String[] args) throws IOException {
		SpringApplication.run(MedicalChatbotApplication.class, args);


		

	// 	String sentence = " Hi. How are you? Welcome to Tutorialspoint. "
	// 			+ "We provide free tutorials on various technologies." +"other sentece to test number of sentences";

	// 	String simple = "[.?!]";
	// 	String[] splitString = (sentence.split(simple));
	// 	for (String string : splitString)
	// 		System.out.println(string);


	// 	//Loading sentence detector model
	// 	InputStream inputStream = new FileInputStream("C:/OpenNLP_models/en-sent.bin");
	// 	SentenceModel model = new SentenceModel(inputStream);

	// 	//Instantiating the SentenceDetectorME class
	// 	SentenceDetectorME detector = new SentenceDetectorME(model);

	// 	//Detecting the sentence
	// 	String sentences[] = detector.sentDetect(sentence);

	// 	//Printing the sentences
	// 	for(String sent : sentences)
	// 		System.out.println("sent from open nlp :   "+ sent);


	// 	String paragraph = "Hi. How are you? Welcome to Tutorialspoint. "
	// 			+ "We provide free tutorials on various technologies";


	// 	//Loading sentence detector model
	// 	InputStream inputStream1 = new FileInputStream("C:/OpenNLP_models/en-sent.bin");
	// 	SentenceModel model1 = new SentenceModel(inputStream1);

	// 	//Instantiating the SentenceDetectorME class
	// 	SentenceDetectorME detector1 = new SentenceDetectorME(model1);

	// 	//Detecting the position of the sentences in the raw text
	// 	Span spans[] = detector.sentPosDetect(paragraph);

	// 	//Printing the spans of the sentences in the paragraph

	// 	//Printing the spans of the sentences in the paragraph
	// 	for (Span span : spans)
	// 		System.out.println(span);




	// 	//Instantiating SimpleTokenizer class
	// 	SimpleTokenizer simpleTokenizer = SimpleTokenizer.INSTANCE;

	// 	//Tokenizing the given sentence
	// 	String tokens[] = simpleTokenizer.tokenize(sentence);

	// 	//Printing the tokens
	// 	for(String token : tokens) {
	// 		System.out.println(token);
	// 	}


	// 	Span[] tokens1 = simpleTokenizer.tokenizePos(sentence);


	// 	//Printing the spans of tokens
	// 	for( Span token : tokens1)
	// 		System.out.println(token +" "+sentence.substring(token.getStart(), token.getEnd()));



	// 	InputStream inputStream2 = new FileInputStream("C:/OpenNLP_models/es-ner-person.bin");
	// 	TokenNameFinderModel model2 = new TokenNameFinderModel(inputStream2);

	// 	//Instantiating the NameFinder class
	// 	NameFinderME nameFinder = new NameFinderME(model2);

	// 	//Getting the sentence in the form of String array
	// 	String [] sentence2 = new String[]{
	// 			"Mike",
	// 			"and",
	// 			"Smith",
	// 			"are",
	// 			"good",
	// 			"friends"
	// 	};

	// 	//Finding the names in the sentence
	// 	Span nameSpans[] = nameFinder.find(sentence2);

	// 	//Printing the spans of the names in the sentence
	// 	for(Span s: nameSpans)
	// 		System.out.println(s.toString());



	// 	//Loading parser model
	// 	InputStream inputStream3 = new FileInputStream("C:/OpenNLP_models/en-parser-chunking.bin");
	// 	ParserModel model3 = new ParserModel(inputStream3);

	// 	//Creating a parser
	// 	Parser parser = ParserFactory.create(model3);

	// 	//Parsing the sentence
	// 	String sentence3 = "Tutorialspoint is the largest tutorial library.";
	// 	Parse topParses[] = ParserTool.parseLine(sentence, parser, 1);

	// 	for (Parse p : topParses)
	// 		p.show();
	// }





}







