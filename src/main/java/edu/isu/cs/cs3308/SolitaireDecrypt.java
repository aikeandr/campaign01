package edu.isu.cs.cs3308;

import edu.isu.cs.cs3308.structures.CircularlyLinkedList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class SolitaireDecrypt {

    private CircularlyLinkedList<String> deck = new CircularlyLinkedList<>();
    public SolitaireDecrypt(String path) {
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
    private String keyGeneration(){
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
    private String decrypt(CircularlyLinkedList<String> decryp, CircularlyLinkedList<String> keys){
        CircularlyLinkedList<String> decoded = new CircularlyLinkedList<>();
        StringBuilder message = new StringBuilder(decryp.size());
        String sumString;
        int diffInt;
        for(int i = 0;i < keys.size();i++) {
            if(Integer.parseInt(decryp.get(i)) <= Integer.parseInt(keys.get(i)))
                diffInt = Integer.parseInt(decryp.get(i)) + 26 - Integer.parseInt(keys.get(i));
            else
                diffInt = Integer.parseInt(decryp.get(i)) - Integer.parseInt(keys.get(i));
            sumString = Integer.toString(diffInt);
            decoded.addLast(sumString);
        }
        for(int i = 0;i < decoded.size();i++)
            message.append((char) (int) (Integer.parseInt(decoded.get(i)) + 64));
        return message.toString();

    }
    public String execute(String message){
        CircularlyLinkedList<String> decrypted= new CircularlyLinkedList<>();
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
            decrypted.addLast(Integer.toString((int) foo.charAt(i) - 64));
        for(int i = 0;i < decrypted.size();i++) {
            System.out.println("Keystream index: " + i);
            keyStream.addLast(keyGeneration());
        }
        return decrypt(decrypted, keyStream);
    }
}
