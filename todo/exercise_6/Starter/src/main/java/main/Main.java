package main;

import io.FileIO;
import jdk.jfr.Percentage;
import model.Medals;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Alan.Ryan
 */
public class Main {

    public static void main(String[] args) {
       //put your code here
        ArrayList<Medals> list = FileIO.readFile("medals.txt");

        //list.stream().forEach(System.out::println);

        double result = calculatePercRank(list, 2);

        //Method 1 output
        //System.out.println("Percentage of those countries whose rank is greater than their rank_by_total: " + result);

        //Method 2 method call
        //printRank(list);

        //Method 3 method call
        //printFilteredCountryName(list);

        //Method 4 method call
        //specifyListOrder(list, 5, 5);
    }

    //Method 1
    public static double calculatePercRank(List<Medals> medalList, int x){

        double percRank = medalList.stream()
                .filter(medals -> medals.getRank() > medals.getRankByTotal())
                .count();

        double result =  (percRank / medalList.size()) * 100;

         return Math.round(result * Math.pow(10, x)) / Math.pow(10, x);
    }

    //Method 2
    public static void printRank(List<Medals> medalList) {

        Random random = new Random();
        int randNum = random.nextInt(1,87);

            List<Medals> result =  medalList.stream()
                    .filter(medalRank -> medalRank.getRank() == randNum)
                    .toList();

            result.forEach(System.out::println);


            if(result.isEmpty()){
                System.out.println("Rank is not found");
            }
    }

    //Method 3
    public static void printFilteredCountryName(List<Medals> medalsList){

        List<Medals> sortListRank = medalsList.stream()
                .filter(medalCountry -> medalCountry.getTeam().endsWith("land") || medalCountry.getTeam().endsWith("stan"))
                .sorted(Comparator.comparingInt(Medals::getRankByTotal))
                .toList();

        sortListRank.forEach(System.out::println);
    }

    //Method 4
    public static void specifyListOrder(List<Medals> medalsList, int offset, int sortKey){

        List<Medals> sortedList = medalsList.stream()
                .sorted(Comparator.comparingInt(Medals::getGoldTotal).reversed())
                .limit(offset)
                .toList();

        sortedList.forEach(System.out::println);
    }
}
