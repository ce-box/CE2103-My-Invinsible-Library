import java.util.ArrayList;

public class main {

    public static void main(String[] args){
        ArrayList<String> L = new ArrayList<String>();
        L.add("name");
        L.add("date");
        ArrayList<String> L2= new ArrayList<String>();
        L2.add("date");
        ArrayList<String> L3= new ArrayList<String>();
        L3.add("2019");
        Metadata.Delete(L2,L3);
    }




}
