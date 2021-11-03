package com.company;

import java.io.IOException;
import java.util.*;

public class WordOccurrences {
    public FileSystemDataService service;

    public WordOccurrences(){
        this.service = new FileReaderWrap();
    }
    public void setService(FileSystemDataService s){
        this.service = s;
    }
    public HashMap<String, Integer> main() throws IOException {

        System.out.println("Enter file path:");
        Scanner input = new Scanner(System.in);
        String filePath = input.nextLine();

        return process(filePath);
    }

    public HashMap<String, Integer> process(String path) throws IOException {
        String text = this.service.getData(path);
        String[] words = text.split(" ");

        Map<String, Integer> wordFreq =  calculateWordFreq(words);
        return groupWords(wordFreq);
    }

    public Map<String, Integer> calculateWordFreq(String[] tokens) {
        Map<String, Integer> wordFreq = new HashMap<>();

        for(String token : tokens) {
            String word = token.toLowerCase();
            if(word.length() > 30) {
                word = word.substring(0, 30);
            }
            word = word.replaceAll("[^A-Za-zА-Яа-я\\і\\І]", " ");

            if(wordFreq.containsKey(word)) {
                int count = wordFreq.get(word);
                wordFreq.put(word, count + 1);
            }
            else {
                wordFreq.put(word, 1);
            }
        }
        return wordFreq;
    }

    public HashMap<String, Integer> groupWords(Map<String, Integer> wordFreq) {
        Set<String> words = wordFreq.keySet();
        TreeSet<String> sortedWords = new TreeSet<>(words);

        HashMap<String, Integer> dict = new HashMap<>();

        int max = 30;
        for(String word : sortedWords) {
            max = Math.max(word.length(), max);
        }
        for(String word : sortedWords) {
            dict.put(word, wordFreq.get(word));
        }
        return dict;
    }
}
