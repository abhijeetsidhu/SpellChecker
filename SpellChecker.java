import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.HashMap;
/**
 * Created by Abhijeet Sidhu
 */
public class SpellChecker extends Object {

    private HashTableQuadratic<String> htq;
    private boolean isWord;

    public class MyStats
    {
        private boolean isWord;
        private ArrayList<Integer> arrayList = new ArrayList();

        public MyStats(boolean isWord)
        {
            this.isWord = isWord;
        }

        public int getOccurrences()
        {
            return arrayList.size();
        }

        public List<Integer> getLineNumbers()
        {
            return arrayList;
        }

        public boolean isWord()
        {
            return isWord;
        }

        private void UpdateLineNum(Integer a){
            arrayList.add(a);
        }
    }

    public SpellChecker()throws FileNotFoundException
    {
        try
        {
            Scanner sc = new Scanner(new File("dictionary.txt"));
            htq = new HashTableQuadratic<>(534238);
            while(sc.hasNext())
            {
                htq.add(sc.next());
            }
            //System.out.println("HASH TABLE SIZE: "+ htq.size());
        }

        catch(Exception e)
        {
            throw new FileNotFoundException();
        }
    }



    public SpellChecker(String fileName)throws FileNotFoundException{
        try
        {
            Scanner sc = new Scanner(new File(fileName));
            htq = new HashTableQuadratic<>(534238);
            while(sc.hasNext())
            {
                String s = sc.next();
                //System.out.println(s);
                htq.add(s);
            }
        }

        catch(Exception e)
        {
            throw new FileNotFoundException();
        }

    }

    public boolean isWord(String s)
    {



        return (htq.contains(s)|| htq.contains(s.toLowerCase()));

    }

    public HashTableQuadratic<String> getDictionary(){
        return htq;
    }

    public HashMap<String,SpellChecker.MyStats> indexFile(String fileName) throws FileNotFoundException
    {
        HashMap<String, MyStats> hm = new HashMap<>();

        Scanner sc = new Scanner(new File(fileName));


        int counter = 1;

        while(sc.hasNextLine()){
            String s = sc.nextLine();
            Scanner sc2 = new Scanner(s).useDelimiter("[^\\w-']+");
            while(sc2.hasNext()){

                String word = sc2.next();
                MyStats checkStat = hm.get(word);
                if(checkStat== null){
                    MyStats sa = new MyStats(isWord(word));
                    sa.UpdateLineNum(counter);
                    hm.put(word,sa);
                }
                else{

                    checkStat.UpdateLineNum(counter);

                }
            }
            counter++;
        }

        return hm;


    }



}
