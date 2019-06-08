import java.util.ArrayList;

public class main {

    public static void main(String[] args){
        Metadata.Start();

        ArrayList<String> L = new ArrayList<String>();
        L.add("name");
        L.add("date");
        ArrayList<String> L2= new ArrayList<String>();
        L2.add("date");
        ArrayList<String> L3= new ArrayList<String>();
        L3.add("2000");
        ArrayList<String> L4= new ArrayList<String>();
        L4.add("2020");
        Metadata.Select(null,L2,L3,L4);

        Metadata.Close();

//        ArrayList<String> L4=Traductor.String2List("id,size,date");
//        Traductor.print(L4);
    }





}
