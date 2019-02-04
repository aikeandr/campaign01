package edu.isu.cs.cs3308;


import edu.isu.cs.cs3308.structures.CircularlyLinkedList;

import java.io.*;
import java.util.Scanner;

public class SolitaireEncrypt {

    private CircularlyLinkedList<String> deck = new CircularlyLinkedList<>();
    public SolitaireEncrypt(String path) {
        Scanner s = null;
        try{
            s = new Scanner(new BufferedReader(new FileReader(path)));
            while(s.hasNext()){
                deck.addLast(s.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally{
            if(s != null)
                s.close();
        }
    }

    public String keyGeneration(){
        deck.printList();
        System.out.println(": Deck");
        //Step 1:
        deck.swapNext(deck.indexOf("27"));
        deck.printList();
        System.out.println(": Step 1");
        //Step 2:
        deck.swapNext(deck.indexOf("28"));
        deck.swapNext(deck.indexOf("28"));
        deck.printList();
        System.out.println(": Step 2");
        //Step 3:
        deck.tripleCut(deck.indexOf("27"), deck.indexOf("28"));
        deck.printList();
        System.out.println(": Step 3");
        //Step 4:
        deck.cutFront(Integer.parseInt(deck.last()));
        deck.printList();
        System.out.println(": Step 4");
        //Step 5:
        String key;
        if(deck.first().equals("28"))
            key = deck.get(27);
        else
            key = deck.get(Integer.parseInt(deck.first()));
        System.out.println("Step 5:");
        System.out.println("key = " + key + "\n");
        if (key.equals("27") || key.equals("28"))
            return keyGeneration();
        return key;

    }
    public String encrypt(CircularlyLinkedList<String> encryp, CircularlyLinkedList<String> keys){
        CircularlyLinkedList<String> encoded = new CircularlyLinkedList<>();
        StringBuilder message = new StringBuilder(encryp.size());
        String sumString;
        int sumInt;
        for(int i = 0;i < keys.size();i++) {
            sumInt = Integer.parseInt(keys.get(i)) + Integer.parseInt(encryp.get(i));
            if(sumInt > 26)
                sumInt = sumInt - 26;
            sumString = Integer.toString(sumInt);
            encoded.addLast(sumString);
        }
        for(int i = 0; i < encryp.size(); i++)
            message.append((char) (int) (Integer.parseInt(encoded.get(i)) + 64));
        return message.toString();

    }
    public String execute(String message){
        CircularlyLinkedList<String> encrypted= new CircularlyLinkedList<>();
        CircularlyLinkedList<String> keyStream = new CircularlyLinkedList<>();
        message = message.replaceAll("[^a-zA-Z]+","");
        message = message.toUpperCase();
        int padding = 0;
        int length = message.length();
        while(length%5 > 0) {
            padding++;
            length++;
        }
        StringBuilder foo = new StringBuilder(length);
        for(int i = 0; i < message.length(); i++)
            foo.append(message.charAt(i));
        for(int i = 0; i < padding; i++)
            foo.append("X");
        for(int i = 0; i < foo.length();i++)
            encrypted.addLast(Integer.toString((int) foo.charAt(i) - 64));
        for(int i = 0;i < encrypted.size();i++) {
            System.out.println("Keystream index: " + i);
            keyStream.addLast(keyGeneration());
        }
        return encrypt(encrypted, keyStream);
    }
}
