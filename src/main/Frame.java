package main;

import java.io.File;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import objects.Stack;
import objects.Message;

public class Frame {
	
	private Stack list;
	private Ai ai;
	
	 public Frame() {
		 ai=new Ai();
		 list=new Stack();
		 list.addMessage(new Message("I am a AI designed to answer your questions. Select your language: English translation or French translation", 0));
	 }
	 
	 public Message[] getMessages() {
		 return list.getMessages();
	 }
	 
	 public void sendMessage(String text, int id) {
		 playPing();
		 list.addMessage(new Message(text, id));
		 if(id==1) {
			 String out=ai.getResponse(text);
			 sendMessage(out, 0);
		 }
	 }
	 
	 private static void playPing() {
		 try {
			 
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File("src/sounds/ping.WAV")));
			clip.start();
		 }catch(Exception e) {
			 System.out.println("No sound");
		}
	 }
}
