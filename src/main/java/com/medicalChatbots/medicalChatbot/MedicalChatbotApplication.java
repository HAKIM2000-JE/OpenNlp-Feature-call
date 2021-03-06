

package com.medicalChatbots.medicalChatbot;

		import java.io.File;
		import java.io.FileInputStream;
		import java.io.FileNotFoundException;
		import java.io.IOException;
		import java.io.InputStream;
		import java.nio.charset.StandardCharsets;
		import java.util.*;
		import java.util.stream.Collectors;

		import com.medicalChatbots.medicalChatbot.model.Message;
		import com.medicalChatbots.medicalChatbot.repository.DiscussionRepository;
		import com.medicalChatbots.medicalChatbot.repository.EmployeeRepository;
		import com.medicalChatbots.medicalChatbot.repository.MessageRepository;
		import opennlp.tools.doccat.BagOfWordsFeatureGenerator;
		import opennlp.tools.doccat.DoccatFactory;
		import opennlp.tools.doccat.DoccatModel;
		import opennlp.tools.doccat.DocumentCategorizerME;
		import opennlp.tools.doccat.DocumentSample;
		import opennlp.tools.doccat.DocumentSampleStream;
		import opennlp.tools.doccat.FeatureGenerator;
		import opennlp.tools.lemmatizer.LemmatizerME;
		import opennlp.tools.lemmatizer.LemmatizerModel;
		import opennlp.tools.postag.POSModel;
		import opennlp.tools.postag.POSTaggerME;
		import opennlp.tools.sentdetect.SentenceDetectorME;
		import opennlp.tools.sentdetect.SentenceModel;
		import opennlp.tools.tokenize.TokenizerME;
		import opennlp.tools.tokenize.TokenizerModel;
		import opennlp.tools.util.InputStreamFactory;
		import opennlp.tools.util.InvalidFormatException;
		import opennlp.tools.util.MarkableFileInputStreamFactory;
		import opennlp.tools.util.ObjectStream;
		import opennlp.tools.util.PlainTextByLineStream;
		import opennlp.tools.util.TrainingParameters;
		import opennlp.tools.util.model.ModelUtil;

		import org.springframework.beans.factory.annotation.Autowired;
		import org.springframework.boot.CommandLineRunner;
		import org.springframework.boot.SpringApplication;
		import org.springframework.boot.autoconfigure.SpringBootApplication;
		import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MedicalChatbotApplication {

    @Autowired
	public DiscussionRepository dr;



	@Autowired
	public EmployeeRepository er;
	public MedicalChatbotApplication() throws IOException {
	}


	@Autowired
	MessageRepository mr;


	private static Map<String, String> questionAnswer = new HashMap<>();

	static {
		questionAnswer.put("greeting", "Hello, how can I help you?");
		questionAnswer.put("product-inquiry",
				"Product is a TipTop mobile phone. It is a smart phone with latest features like touch screen, blutooth etc.");
		questionAnswer.put("price-inquiry", "Price is $300");
		questionAnswer.put("job", "We are lead company in software solution , we have more than 50 employes with average salary of 3000$ /month , you wanna apply ? ");
		questionAnswer.put("localisation", "North Africa");
		questionAnswer.put("offer", "how many years of experience do you have ");
		questionAnswer.put("experience", "Perfect ! You can apply on this link https://jobs.offer.com");
		questionAnswer.put("speciality", "great , what is your speciality ?");
		questionAnswer.put("conversation-continue", "What else can I help you with?");
		questionAnswer.put("conversation-complete", "Nice chatting with you. Bbye.");

	}


	public static void main(String[] args) throws IOException {
		SpringApplication.run(MedicalChatbotApplication.class, args);





	}


	@Bean
	public CommandLineRunner console() {
		return args -> {

			// Train categorizer model to the training data we created.
			DoccatModel model = trainCategorizerModel();



			// Take chat inputs from console (user) in a loop.
			Scanner scanner = new Scanner(System.in);

			while (true) {

				// Get chat input from user.
				System.out.println("--> Me:");
				String userInput = scanner.nextLine();

				// Break users chat input into sentences using sentence detection.
				String[] sentences = breakSentences(userInput);


                System.out.println(mr.findAll().size());


				Random rd = new Random();

				Message message = new Message(rd.nextInt(), rd.nextInt(), userInput  );
				mr.save(message);
				questionAnswer.put("Employees","we have more than "+ er.findAll().size());

				String answer = "";
				boolean conversationComplete = false;

				// Loop through sentences.
				for (String sentence : sentences) {

					// Separate words from each sentence using tokenizer.
					String[] tokens = tokenizeSentence(sentence);

					// Tag separated words with POS tags to understand their gramatical structure.
					String[] posTags = detectPOSTags(tokens);

					// Lemmatize each word so that its easy to categorize.
					String[] lemmas = lemmatizeTokens(tokens, posTags);

					// Determine BEST category using lemmatized tokens used a mode that we trained
					// at start.
					String category = detectCategory(model, lemmas);

					// Get predefined answer from given category & add to answer.
					answer = answer + " " + questionAnswer.get(category);

					// If category conversation-complete, we will end chat conversation.
					if ("conversation-complete".equals(category)) {
						conversationComplete = true;
					}
				}

				// Print answer back to user. If conversation is marked as complete, then end
				// loop & program.
				System.out.println("-->RH Chat Bot: " + answer);
				if (conversationComplete) {
					break;
				}

			}



		};

	}

	 private static void   AddMessage (Message message , MessageRepository mr){
		mr.save(message);

	 }
	/**
	 * Train categorizer model as per the category sample training data we created.
	 *
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static DoccatModel trainCategorizerModel() throws FileNotFoundException, IOException {
		// faq-categorizer.txt is a custom training data with categories as per our chat
		// requirements.
		InputStreamFactory inputStreamFactory = new MarkableFileInputStreamFactory(new File("faq-categorizer.txt"));
		ObjectStream<String> lineStream = new PlainTextByLineStream(inputStreamFactory, StandardCharsets.UTF_8);
		ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

		DoccatFactory factory = new DoccatFactory(new FeatureGenerator[] { new BagOfWordsFeatureGenerator() });

		TrainingParameters params = ModelUtil.createDefaultTrainingParameters();
		params.put(TrainingParameters.CUTOFF_PARAM, 0);

		// Train a model with classifications from above file.
		DoccatModel model = DocumentCategorizerME.train("en", sampleStream, params, factory);
		return model;
	}

	/**
	 * Detect category using given token. Use categorizer feature of Apache OpenNLP.
	 *
	 * @param model
	 * @param finalTokens
	 * @return
	 * @throws IOException
	 */
	private static String detectCategory(DoccatModel model, String[] finalTokens) throws IOException {

		// Initialize document categorizer tool
		DocumentCategorizerME myCategorizer = new DocumentCategorizerME(model);

		// Get best possible category.
		double[] probabilitiesOfOutcomes = myCategorizer.categorize(finalTokens);
		String category = myCategorizer.getBestCategory(probabilitiesOfOutcomes);
		System.out.println("Category: " + category);

		return category;

	}

	/**
	 * Break data into sentences using sentence detection feature of Apache OpenNLP.
	 *
	 * @param data
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String[] breakSentences(String data) throws FileNotFoundException, IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("en-sent.bin")) {

			SentenceDetectorME myCategorizer = new SentenceDetectorME(new SentenceModel(modelIn));

			String[] sentences = myCategorizer.sentDetect(data);
			System.out.println("Sentence Detection: " + Arrays.stream(sentences).collect(Collectors.joining(" | ")));

			return sentences;
		}
	}

	/**
	 * Break sentence into words & punctuation marks using tokenizer feature of
	 * Apache OpenNLP.
	 *
	 * @param sentence
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static String[] tokenizeSentence(String sentence) throws FileNotFoundException, IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("en-token.bin")) {

			// Initialize tokenizer tool
			TokenizerME myCategorizer = new TokenizerME(new TokenizerModel(modelIn));

			// Tokenize sentence.
			String[] tokens = myCategorizer.tokenize(sentence);
			System.out.println("Tokenizer : " + Arrays.stream(tokens).collect(Collectors.joining(" | ")));

			return tokens;

		}
	}

	/**
	 * Find part-of-speech or POS tags of all tokens using POS tagger feature of
	 * Apache OpenNLP.
	 *
	 * @param tokens
	 * @return
	 * @throws IOException
	 */
	private static String[] detectPOSTags(String[] tokens) throws IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("en-pos-maxent.bin")) {

			// Initialize POS tagger tool
			POSTaggerME myCategorizer = new POSTaggerME(new POSModel(modelIn));

			// Tag sentence.
			String[] posTokens = myCategorizer.tag(tokens);
			System.out.println("POS Tags : " + Arrays.stream(posTokens).collect(Collectors.joining(" | ")));

			return posTokens;

		}

	}

	/**
	 * Find lemma of tokens using lemmatizer feature of Apache OpenNLP.
	 *
	 * @param tokens
	 * @param posTags
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	private static String[] lemmatizeTokens(String[] tokens, String[] posTags)
			throws InvalidFormatException, IOException {
		// Better to read file once at start of program & store model in instance
		// variable. but keeping here for simplicity in understanding.
		try (InputStream modelIn = new FileInputStream("en-lemmatizer.bin")) {

			// Tag sentence.
			LemmatizerME myCategorizer = new LemmatizerME(new LemmatizerModel(modelIn));
			String[] lemmaTokens = myCategorizer.lemmatize(tokens, posTags);
			System.out.println("Lemmatizer : " + Arrays.stream(lemmaTokens).collect(Collectors.joining(" | ")));

			return lemmaTokens;

		}
	}



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







