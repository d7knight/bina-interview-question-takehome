import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.String;
import java.lang.System;
import java.util.HashMap;
import java.util.Scanner;

public class Main {


    // Hi Sharon, here is my solution to the interview problem from Thursday


    //the $ character still indicates the end of a word in the trie data structure


    // So I changed my isExist solution from the interview on thursday, It is iterative versus recursive now.

    //A further optimization is to use a HashMap which maps a character to a child node
    //instead of using a fixed size array of child nodes

    public static class TrieNode{
        Character c;
        HashMap<Character, TrieNode> children;
        public TrieNode(Character c){
            this.c=c;
            this.children=new HashMap<>();
        }
    }
    public static HashMap<Character, TrieNode> characterTrieNodeHashMap=new HashMap<>();

    public static void addUsername(String username){
        if( username==null ||username.length()==0){
            return;
        }
        TrieNode cur=null;
        for(char c: username.toCharArray()){
            if(cur==null){
                if(!characterTrieNodeHashMap.containsKey(c)){
                    TrieNode newNode=new TrieNode(c);
                    //System.out.println("adding node " +c);
                    characterTrieNodeHashMap.put(c,newNode);
                    cur=newNode;

                }
                else cur=characterTrieNodeHashMap.get(c);
            }
            else{
                if(!cur.children.containsKey(c)){
                    TrieNode newNode=new TrieNode(c);
                    //System.out.println("adding node " +c);
                    cur.children.put(c, newNode);
                    cur=newNode;
                }
                else cur=cur.children.get(c);
            }

        }

    }



    public static void preProcess(String file) throws FileNotFoundException {
        FileInputStream inputStream=new FileInputStream(file);
        Scanner sc=new Scanner(inputStream);
        while (sc.hasNext())addUsername(sc.next()+"$");
    }

    // I am using iteration versus recursion while searching for a username
    // Runtime Complexity: O(1) since the height of the trie is at most the length of the longest username
    // in the file which is a constant
    // Space Complexity: When there are similar prefixes between the username strings, my trie data structures will
    // take less space than just using a set for example.
    public static boolean isExist(String username){
        TrieNode cur=null;
        for(char c: username.toCharArray()){
            if(cur==null){
                if(!characterTrieNodeHashMap.containsKey(c))return false;
                //System.out.println("found " + c);
                cur=characterTrieNodeHashMap.get(c);
            }
            else{
                if(!cur.children.containsKey(c))return false;
                //System.out.println("found " + c);
                cur=cur.children.get(c);
            }

        }

        return cur.children.containsKey('$');

    }



    public static void main(String[] args) {
        try {
            preProcess("usernames.txt");
            System.out.println(isExist("david1"));
            System.out.println(isExist("david2"));
            System.out.println(isExist("david3"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
