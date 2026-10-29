package src;


import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.*;
import java.util.concurrent.*;

import org.w3c.dom.ls.LSOutput;
import src.model.Person;


public class Main {

    //I'm going to read the data from the file into a List<Person>
    static List<Person> list = new ArrayList();
    static String file = "census.txt";

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        parseFile(file);

        //list.forEach(System.out::println);

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> future = executorService.submit(new MaleAndFemale(list));
        Future<Integer> future1 = executorService.submit(new MaleOnly(list));
        Future<Integer> future2 = executorService.submit(new FemaleOnly(list));

        //executorService.submit(new UsedAsLastName(list));
        //executorService.submit(new NotUsedAsLastName(list));

        //Task 2
        Future<List<String>> future3 = executorService.submit(new LongestName(list));

        System.out.println("MF (Male and Female only): " + future.get());
        System.out.println("MO (Male only): " + future1.get());
        System.out.println("FO (Female only): " + future2.get());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("\n--------------Task 2------------------");

        List<String> longest = future3.get();

        System.out.println("Longest name(s)\nLongest:" + longest.get(0).length());
        System.out.println(future3.get());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println("\n--------------Task 3------------------");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter users name in the system");
        String input = scanner.nextLine();

        Future<Optional<Person>> future4 = executorService.submit(new SearchName(list,input));

        executorService.shutdown();

        Optional<Person> found = future4.get();

        if(found.isPresent()){
            Person p = found.get();

            System.out.println("Name: " + p.getName());
            System.out.println("Code: " + p.getCode());
            System.out.println("Indicator: " + p.getIndicator());
        }else {
            System.out.println("User not found: " + input);
        }


    }//end main

    private static void parseFile(String file) {
        FileReader frs;
        StreamTokenizer in;

        String code = "";
        String indicator = "";
        String name = "";

        try {
            frs = new FileReader(file);
            in = new StreamTokenizer(frs);

            //read headers
            in.nextToken();
            in.nextToken();
            in.nextToken();

            in.nextToken(); //first token of first record

            while (in.ttype != StreamTokenizer.TT_EOF) {

                //read code
                if (in.ttype == StreamTokenizer.TT_WORD) {
                    code = in.sval.trim();
                } else {
                    System.out.println("Bad file format reading code");
                }

                //read indicator
                if (in.nextToken() == StreamTokenizer.TT_WORD) {
                    indicator = in.sval.trim();
                } else {
                    System.out.println("Bad file format reading indicator");
                }

                //read the name (dont need it for this exercise, but I'll read it anyway)
                if (in.nextToken() == StreamTokenizer.TT_WORD) {
                    name = in.sval.trim();
                } else {
                    System.out.println("Bad file format reading name");
                }
                //create a new Record and add it to the list
                list.add(new Person(code, indicator, name));

                in.nextToken();
            }//end while

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }//end parseFile()
}//end class


class MaleAndFemale implements Callable<Integer>{

    //Declare list of persons passed
    private List<Person> list;

    //Constructor
    public MaleAndFemale(List<Person> list){
        this.list = list;
    }

    //In order for the callable method to work you need an override call method
    @Override
    public Integer call(){

        int count = 0;

        for(Person p : list){

            if(p.getCode().equals("MF")){
                count++;
            }
        }

        return count;
    }
}

class MaleOnly implements Callable<Integer>{

    private List<Person> list;

    public MaleOnly(List<Person> list) {
        this.list = list;
    }

    @Override
    public Integer call(){

        int count = 0;

        for(Person p : list){

            if(p.getCode().equals("MO")){
                count++;
            }
        }
        return count;
    }
}

class FemaleOnly implements Callable<Integer>{

    private List<Person> list;

    public FemaleOnly(List<Person> list) {
        this.list = list;
    }

    @Override
    public Integer call(){

        int count = 0;

        for(Person p : list){

            if(p.getCode().equals("FO")){
                count++;
            }
        }
        return count;
    }
}

class UsedAsLastName implements Runnable{

    private List<Person> list;

    public UsedAsLastName(List<Person> list) {
        this.list = list;
    }

    @Override
    public void run(){

        int count = 0;

        for(Person p : list){

            if(p.getIndicator().equals("LY")){
               count++;
            }
        }

        System.out.println("LY (also used as last name): " + count);
    }
}

class NotUsedAsLastName implements Runnable{

    private List<Person> list;

    public NotUsedAsLastName(List<Person> list) {
        this.list = list;
    }

    @Override
    public void run(){

        int count = 0;

        for(Person p : list){

            if(p.getIndicator().equals("LN")){
                count++;
            }
        }

        System.out.println("LN (not used as last name): " + count);
    }
}

class LongestName implements Callable<List<String>>{

    private List<Person> list;

    public LongestName(List<Person> list) {
        this.list = list;
    }

    @Override
    public List<String> call(){

        //Get the longest names
        int maxLength = list.stream().mapToInt(p -> p.getName().length()).max().getAsInt();

        //Keep the longest name and keep it and add it to a list
        return list.stream().filter(p -> p.getName().length() == maxLength).map(Person::getName).toList();
    }
}

class SearchName implements Callable<Optional<Person>>{

    private List<Person> list;
    private String search;

    public SearchName(List<Person> list, String search) {
        this.list = list;
        this.search = search;
    }

    @Override
    public Optional<Person> call(){

        return list.stream().filter(p -> p.getName().equalsIgnoreCase(search)).findFirst();
    }
}