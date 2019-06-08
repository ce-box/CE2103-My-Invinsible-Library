import java.util.ArrayList;

public class Traductor {
    public static ArrayList<String> String2List(String stringList){
        ArrayList<String> List=new ArrayList<>();
        char c;
        String tmp="";

        for (int i=0;i<stringList.length();i++){
            c=stringList.charAt(i);
            if (c==','){
                List.add(tmp);
                tmp="";
            }
            else{
                tmp+=c;
            }
        }
        List.add(tmp);

        return List;
    }



    public static void print(ArrayList<String> A){
        for (String s: A){
            System.out.print(s+"-");
        }
        System.out.println();
    }

}
