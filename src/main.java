import java.util.ArrayList;

public class main {

    public static void main(String[] args){
        Metadata.Start();

        ArrayList<String> L = new ArrayList<String>();
        L.add("name");
        L.add("date");
        ArrayList<String> L2= new ArrayList<String>();
        L2.add("Sahid1");
        L2.add("2012");
        ArrayList<String> L3= new ArrayList<String>();
        L3.add("name");
        ArrayList<String> L4= new ArrayList<String>();
        L4.add("Lucardiño");
        Metadata.Update(L,L2,L3,L4);
        Metadata.Select();

        Metadata.Close();
    }




}
