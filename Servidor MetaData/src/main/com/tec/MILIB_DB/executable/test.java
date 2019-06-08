package main.com.tec.MILIB_DB.executable;

import main.com.tec.MILIB_DB.util.jsonParser;
import org.json.*;
import java.util.ArrayList;


public class test {
    public static void main(String[] args) throws JSONException{

        ArrayList<String> slotList = new ArrayList<>();
        slotList.add("name");
        slotList.add("author");
        slotList.add("date");
        slotList.add("size");
        slotList.add("description");

        ArrayList<String> valuesList = new ArrayList<>();
        valuesList.add("imagenChuza");
        valuesList.add("Esteban");
        valuesList.add("2012");
        valuesList.add("1000");
        valuesList.add("Beautiful pic");

        JSONObject json = new JSONObject();
        JSONArray slots = new JSONArray();
        JSONArray slotValues = new JSONArray();

        for( String slot:slotList) {
            slots.put(slot);
        }

        for( String value:valuesList) {
            slotValues.put(value);
        }

        json.put("slots",slots);
        json.put("slotsValues",slotValues);

        System.out.println(json.toString());

        //jsonParser.jsonInsertParser(json.toString());
    }
}
