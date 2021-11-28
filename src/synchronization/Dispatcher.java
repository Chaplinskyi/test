package synchronization;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dispatcher {
    public static void main(String[] args) {
        File file1 = new File("D:/IdeaProjects/synchronizedIsShit/src/synchronization/file1");
        File file2 = new File("D:/IdeaProjects/synchronizedIsShit/src/synchronization/file2");
        File file3 = new File("D:/IdeaProjects/synchronizedIsShit/src/synchronization/file3");
        Pattern digitNum = Pattern.compile("\\d+");

        Runnable r = () -> {

        };
    }
}

class Sum{
    static int sum = 0;
}
class FileHandler {
    static int result = 0;
    Scanner scanner = null;
    String line = "";
    Matcher m;
    public int numberSum(File input, Pattern regex){
        try {
            scanner = new Scanner(input);
            while(scanner.hasNextLine()) {
                line = scanner.nextLine();
                m = regex.matcher(line);
                while (m.find()) {
                    synchronized(this) {
                        result += Integer.parseInt(m.group());
                    }
                }
            }
        } catch (Exception e) {
        } finally {
            scanner.close();
        }
        return result;
    }

    public static synchronized int sumMultiThread(ExecutorService es, List<Callable<Integer>> lci) throws Exception {
        List<Future<Integer>> lfi = es.invokeAll(lci);
        es.shutdown();
        List<Integer> multiThreadList = new ArrayList<>();
        for(Future<Integer> temp : lfi){
            multiThreadList.add(temp.get());
        }
        int sum = 0;
        for (int temp : multiThreadList) {
            sum += temp;
        }
        System.out.println("File1(sum of numbers): " + multiThreadList.get(0) + ", File2(sum of numbers): "
                + multiThreadList.get(1) + ", File3(sum of numbers): " + multiThreadList.get(2));
        return sum;
    }
}