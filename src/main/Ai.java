package main;


import java.io.*;
 
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Ai {
  	private String out;
  	private String[] data;
  	private String[] getTokenizer;
  	private String[] getPOStagger;
  	private String[] getLemmatizer;
  	private int lang = 0;
  	private boolean trans = false;
  	
  	//KEYWORDS
  	private String[] greetings={
            	"hi",
            	"hello", 
            	"good morning",
            	"good afternoon",
            	"howdy"
            };
  
  	private String[] food={
            	"order",
            	"food",
			"meal",
            };
  	
  	private String[] delivery={
        	"package",
		"delivery",
        };
  
  	private String[] gratitude={
            	"thank",
            	"thank you", 
            	"thx",
            	"thnx",
              	"thankyou"
      };
  	private String[] money={
      	"money",
        	"transaction",
        	"charge"
      };
  	private String[] person={
        "person",
        "representative",
        "agent"
      };
  	private String[] sports={
  	        "baseball",
  	        "basketball",
  	        "football",
  	        "soccer",
  	        "sports"
  	      };
  

	
	public Ai() {   
      	data=new String[11];
        
		// DATA  of the order
        	data[0]="Muhammad McLovin";//drivers name
        	data[1]="McWendy's";//Food location
        	data[2]="911-XXX-XXXX";//Store number;
        	data[3]="107 street";//Driver Location
            data[4]="9X0 X69";//Drivers license plate number
        	data[5]="$15.99";//Amount Charged
        	data[6]="$2.00";//tip
        	data[7]="3192 Lower Residence Lane";//Address
        	data[8]="8";//referral number
        	data[9]="$3.25";//giftcard balance
        	data[10]="Nike";//Online Shopping location
  
	}
	public String getResponse(String input) {
        /*
		Purpose: takes a users input and corresponds with an appropriate response
		Input: input, A String that takes user input
		Output: output, A String that takes
         */
		
		//This checks if the language has been selected or not.
		if(!(trans)) {
			if(input.contains("trans") || input.contains("lang")){
				if(input.contains("en")){
	              	out+=("English Selected");
	              	lang = 0;              	
				}else if(input.contains("fr")) {
					out+=("French Selected");
					lang = 1;
				}
			}
		}
		//Translating the French user input into English in order for the Translator to understand.
		if(lang == 1) {
			try {
				input = translate("fr", "en", input);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
			
        	input=cleanInput(input);
       	out ="";     
        	boolean isOutput=false;
        	if(input.contains("exit")){
            	return "exiting";
            }
        	
        	for(int i=0; i< greetings.length; i++){
            	if(input.contains(greetings[i])){
                  	out+=(greetings[(int)(Math.random()*greetings.length)])+", ";
                  }
      	}
        	
        	
        	InputStream tokenModelIn = null;
            InputStream posModelIn = null;
             
            try {
            	
            	//
//            	GeoApiContext context = new GeoApiContext.Builder()
//            		    .apiKey("AIza...")
//            		    .build();
//            		GeocodingResult[] results =  GeocodingApi.geocode(context,"1600 Amphitheatre Parkway Mountain View, CA 94043").await();
//            		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            		System.out.println(gson.toJson(results[0].addressComponents));
//
//            		// Invoke .shutdown() after your application is done making requests
//            		context.shutdown();
            	
                String sentence = input;
                // tokenize the sentence
                tokenModelIn = new FileInputStream("src/main/opennlp-en-ud-ewt-tokens-1.0-1.9.3.bin");
                TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
                Tokenizer tokenizer = new TokenizerME(tokenModel);
                String tokens[] = tokenizer.tokenize(sentence);
                // Necessary for token tests
                getTokenizer = tokens;
     
                // Parts-Of-Speech Tagging
                // reading parts-of-speech model to a stream 
                posModelIn = new FileInputStream("src/main/en-pos-maxent.bin");
                // loading the parts-of-speech model from stream
                POSModel posModel = new POSModel(posModelIn);
                // initializing the parts-of-speech tagger with model 
                POSTaggerME posTagger = new POSTaggerME(posModel);
                // Tagger tagging the tokens
                String tags[] = posTagger.tag(tokens);
                getPOStagger = tags;
                // Getting the probabilities of the tags given to the tokens
                double probs[] = posTagger.probs();
                InputStream dictLemmatizer = new FileInputStream("src/main/en-lemmatizer.dict");
                DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
                String[] lemmas = lemmatizer.lemmatize(tokens, tags);
                // Necessary for Lemmatization tests
                getLemmatizer = tokens;

                 
                System.out.println("Token\t:\tTag\t:\tProbability\n---------------------------------------------");
                for(int i=0;i<tokens.length;i++){
                    System.out.println(tokens[i]+"\t:\t"+tags[i]+"\t:\t"+probs[i]);
                }
                
                System.out.println("\nPrinting lemmas for the given sentence...");
                System.out.println("WORD -POSTAG : LEMMA");
                for(int i=0;i< tokens.length;i++){
                    System.out.println(tokens[i]+" -"+tags[i]+" : "+lemmas[i]);
                }
                 
            }
            catch (IOException e) {
                // Model loading failed, handle the error
                e.printStackTrace();
            }
            finally {
                if (tokenModelIn != null) {
                    try {
                        tokenModelIn.close();
                    }
                    catch (IOException e) {
                    }
                }
                if (posModelIn != null) {
                    try {
                        posModelIn.close();
                    }
                    catch (IOException e) {
                    }
                }
            }
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        if(input.contains("trans") || input.contains("lang")){
			if(input.contains("en")){
              	out+=("English Selected");
              	isOutput=true;
			}else if(input.contains("fr")) {
				out+=("French Selected");
				isOutput=true;
			}
		}
  		if(input.contains("where")){
  			// SkipTheDish
              	for(int i=0; i< food.length; i++){
                  	if((input.contains(food[i]) || input.contains("driver") )&& (input.contains("going")||input.contains("headed"))){
                        	out+=("Your order is headed to " + data[7] + ".");
                          	isOutput=true;
                          	break;
                  	}else if(input.contains(food[i]) && input.contains("from")){
                        	out+=("Your order is from " + data[1] + ".");
                          	isOutput=true;
                          	break;
                        } else if(input.contains(food[i]) || input.contains("driver")){
                        	out+=("Your order is currently at " + data[3] + ".");
                          	isOutput=true;
                          	break;
                        }
                  }
              // Amazon
              	for(int i=0; i< delivery.length; i++){
                  	if((input.contains(delivery[i]) || input.contains("driver") )&& (input.contains("going")||input.contains("headed"))){
                        	out+=("Your package is on route to " + data[7] + ".");
                          	isOutput=true;
                          	break;
                  	}else if(input.contains(delivery[i]) && input.contains("from")){
                        	out+=("Your package is from " + data[10] + ".");
                          	isOutput=true;
                          	break;
                        } else if(input.contains(delivery[i]) || input.contains("driver")){
                        	out+=("Your package is currently at " + data[3] + ".");
                          	isOutput=true;
                          	break;
                        }
                  }
            }else if(input.contains("what")){
              	if(input.contains("number")){
                    	if(input.contains("driver")){
                  		if(input.contains("license") || input.contains("plate") || input.contains("car")){
                        		out+=("The driver's license plate number is" + data[4]);
                                	isOutput=true;
                        	}else if(input.contains("phone")){
                        		out+=("We cannot disclose the information.");
                                	isOutput=true;
                        	}
                        }else if(input.contains("store")){
                        	out+=("The store number is " + data[2] + ".");
                          	isOutput=true;
                        }
                  }else if(input.contains("driver") && (input.contains("car") || input.contains("vehical") || input.contains("driving"))){
                  	out+=("They are driving a blue truck");
                        isOutput=true;
                  }else if(input.contains("referral")){
                    	if(input.contains("code")){
                          	out+=("Your code is S74SJF");
                          	isOutput=true;
                        }else if(input.contains("uses") || input.contains("count")){
                          	out+=("You have referral count of " + data[8] + ".");
                          	isOutput=true;
                        }
                  }else if(input.contains("brand")) {
                	  out+=("Your delivery is from " + data[10] + ".");
                    	isOutput=true;
                  }
            }else if(input.contains("when")){
            	// SkipTheDish
            	for(int i=0; i<food.length; i++) {
            		if(input.contains(food[i]) || input.contains("driver")){
                        	out+="Your food will be coming shortly";
                        	isOutput=true;
                          	break;
                     	}
            	}
            	// Amazon
            	for(int i=0; i<delivery.length; i++) {
            		if(input.contains(delivery[i]) || input.contains("driver")){
                        	out+="Your package will be arriving soon";
                        	isOutput=true;
                          	break;
                     	}
            	}
      	}else if(input.contains("how")){
              	if(input.contains("tip") && input.contains("should")){
              		if(input.contains("skip")) {
                    	out+=("Your driver takes 100% of the delivery fee and tip. It depends on how much you would like to tip.");
              		}else {
                    	out+=("UPS drivers will not accept tips.");
              		}
                	isOutput=true;

                  }else if(input.contains("tip")){
                    	out+=("You tipped your driver " + data[6] + ".");
                    	isOutput=true;
                  }else if(input.contains("far") && input.contains("ship")){
                  	out+=("We ship anywhere around the globe, The delivery fee differs depending on the location.");
                    isOutput=true;
                  }else if(input.contains("far")){
                    	out+=("We deliver in 10km radius of the store, The delivery fee differs depending on the location.");
                        isOutput=true;
                  }else{
                    	for (int i = 0; i<money.length; i++){
                    		if(input.contains(money[i])){
                          		out+=("The amount charged for your most recent order was " + data[5] + ".");
                          		isOutput=true;
                                	break;
                    		}	
              		}
                  }
            }
        	if (input.contains("number")){
              	if(input.contains("how") || input.contains("where")){
                    	if(input.contains("change")){
                          	out+=("In the settings, you can press the change info button to change your phone number.");
                              isOutput=true;
                        }
                  }
            }else if (input.contains("who")){
            	if(input.contains("driver")){
                  	out+=("Your driver's name is "+data[0]);
                    	isOutput=true;
                  }if(input.contains("you")){
                  	out+=("I am a chat bot built to answer your questions");
                    	isOutput=true;
                  }
            }else if(input.contains("human") || input.contains("person") || input.contains("agent") || input.contains("representative")){
            	if(input.contains("talk") || input.contains("speak") || input.contains("ask")){
                  	out+=("I have sent your request to one of our representatives. They will be in contact shortly.");
                    	isOutput=true;
                  }
            }else if(input.contains("forgot")){
                	if(input.contains("password")){
                    	out+=("You can use our website to change your password by clicking FORGOT PASSWORD");
                    	isOutput=true;
                  }else if(input.contains("username")){
                    	out+=("We have sent you an email to inform you of your username.");
                    	isOutput=true;
                  }
            }
        	if(input.contains("apply") || input.contains("business")){ 
            	if(input.contains("hire") || input.contains("become a driver")){
              		out+=("You can apply for a position in our wonderful team here! https://google.com");
              		isOutput=true;
                  }else if(input.contains("my")){
                  	if(input.contains("restaurant") || input.contains("local") || input.contains("family restaurant") || input.contains("family business")){
                  		out+=("Of course! We support local business, If you are looking to add your business to our listing, reach out to us at https://google.com");
                          	isOutput=true;
                    	}
                  }
            }
        	if(input.contains("rate")){
              	if(input.contains("restaurant") || (input.contains("store"))){
                    	out+=("You can rate the restaurant you just ordered from here!");
                    	isOutput=true;
              	}else if(input.contains("company") || input.contains("seller")) {
              		out+=("You can rate the company you just ordered from here!");
                	isOutput=true;
                  }else if(input.contains("driver")){
                        out=("You can rate the driver here!");
                        isOutput=true;
                  }
            }else if(input.contains("contactless")){
        		out+=("Contactless delivery is available!");
                  isOutput=true;
      	}else if(input.contains("gift")){
        		if(input.contains("get") || input.contains("purchase")){
                  	out+=("You are able to purchase a gift card for your loved ones at https://google.com");
                    	isOutput=true;
                  }else if(input.contains("use") || (input.contains("spend"))){
                    	out+=("Make sure you put in the code of your gift card in the transaction page of your order to use your gift card.");
                    	isOutput=true;
                  }else if(input.contains("have") || (input.contains("left"))){
                  	out+=("You currently have " + data[9] + " left on your gift card.");
                    	isOutput=true;
                  }
      	}
  
  		if(input.contains("help")){
              	out+=("If you require any furthur assistance, feel free to reach out at https://google.com");
              	isOutput=true;
            }
  		
  		if(out.length() == 24) {
  			out+=" ";
  		}
  		
  		if(input.contains("wiki")) {
  			String temp = input;
  			if(input.contains("what is") || input.contains("who are")){
  				temp = temp.substring(8);
  			}else if(input.contains("search") || input.contains("who is")){
  				temp = temp.substring(7);
  			}else if(input.contains("what are")) {
  				temp = temp.substring(9);
  			}
  			if(input.contains("wikipedia")) {
  				temp = temp.substring(0,temp.length() -13);
  			}else {
  				temp = temp.substring(0,temp.length() -8);
  			}

  			Jwiki.Jwiki jwiki = new Jwiki.Jwiki(temp); 
  			String temp1 = jwiki.getExtractText();
  			if(jwiki.getExtractText().length() > 170) {
  				temp1 = jwiki.getExtractText().substring(0,170) + "...";
  	  			System.out.println(temp1);
  			}
  			out+= jwiki.getDisplayTitle() + ", " + temp1;
  			isOutput=true;
  		}
  		
  		// Outside bot focus
  		if(input.contains("sports") || input.contains("play")) {
          	out=("Sorry, I don't know much about sports.");
          	isOutput=true;
  		}else if(input.contains("games")) {
  			out=("Sorry, I don't know much about games.");
          	isOutput=true;
  		}else if(input.contains("history")) {
  			out=("Sorry, I don't know much about history.");
  			isOutput=true;
  		}else if(input.contains("song") || input.contains("music")) {
  			out=("Sorry, I don't know much about music.");
  			isOutput=true;
  		}else if(input.isEmpty()){
            out="Please ask a question.";
        }else if(!isOutput){
        	out="Sorry I don't understand your question. You can ask a question by putting 'on wikipedia' at the end!";
        }
  		
  	//Translating bot's output into French for the user to understand.
  		if(lang == 1) {
  			try {
  				out = translate("en", "fr", out);
  			} catch (IOException e1) {
  				e1.printStackTrace();
  			}
  		}
  		
		return out;	
      }
  
  	private String cleanInput(String input){
        	String out=input.toLowerCase();
      	return out;
    }
  	
  	public String[] getToken(){
  		return getTokenizer;
  	}
  	
  	public String[] getPOStag(){
  		return getPOStagger;
  	}
  	
  	public String[] getLemmatization(){
  		return getLemmatizer;
  	}
  	
  	private static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbyl2YVijacbt1DgzOtg7Qo6iay7_qq0bkQewXkVTWSqA2PSGifltnay9Pu9pMeVMyMf2A/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
  	
  	

}