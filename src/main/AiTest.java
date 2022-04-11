//package main;
//
//import static org.junit.Assert.assertEquals;
//import org.junit.*;
//import org.junit.Assert;
//import org.junit.Test;
//
//
//public class AiTest {
//	
//	private Ai testAi;
//	
//	
//	//Intializing the test AI
//	public void initialize() {
//		testAi = new Ai();
//	
//	}
//	//The test is designed 
//	@Test
//	public void testTokenization() {
//		testAi.getResponse("Where is my order located at right now?");
//		String premade[] = {"where","is","my","order","located","at","right","now","?"};
//		Assert.assertArrayEquals(premade,testAi.getToken());
//	}
//	@Test
//	public void testPOStagging() {
//		
//		testAi.getResponse("Where is my order located at right now?");
//		String premade[] = {"WRB","VBZ","PRP$","NN","VBD","IN","RB","RB","."};
//		Assert.assertArrayEquals(premade,testAi.getPOStag());
//	}
//	@Test
//	public void testLemmatization() {
//		testAi.getResponse("Where is my order located at right now?");
//		String premade[] = {"where","be","my","0","locate","at","0","now","0"};
//		Assert.assertArrayEquals(premade,testAi.getLemmatization());
//	}
//}
