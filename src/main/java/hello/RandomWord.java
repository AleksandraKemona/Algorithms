import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {


    public static void main(String[] args) {
        int count = 1;
        String champion = "empty";
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            String newChampion = "";
            boolean result = StdRandom.bernoulli((double) 1/count);
            if (result==true){
                newChampion = word;
            }else{
                newChampion = champion;
            }
            champion = newChampion;
            count++;
        }
        System.out.println(champion);

    }
}