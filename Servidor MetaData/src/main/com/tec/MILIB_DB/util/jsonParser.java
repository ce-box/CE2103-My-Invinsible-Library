package main.com.tec.MILIB_DB.util;

// Libraries
import jdk.nashorn.internal.parser.JSONParser;
import main.com.tec.MILIB_DB.domain.Metadata;
import org.json.*;
import java.util.ArrayList;

/**
 *
 */
public class jsonParser {

    public static void jsonInsertParser(String json) throws JSONException{

        JSONObject jsonObject = new JSONObject(json);
        JSONArray slots = (JSONArray) jsonObject.getJSONArray("slots");
        JSONArray slotValues = (JSONArray) jsonObject.getJSONArray("slotsValues");

        System.out.println("slots: "+ slots.toString());
        System.out.println("slotsValues: "+ slotValues.toString());

        ArrayList<String> slotsList =  new ArrayList<>();
        ArrayList<String> valuesList =  new ArrayList<>();

        for(int i = 0; i < slots.length(); i++){
            slotsList.add((String)slots.get(i));
        }

        for(int i = 0; i < slotValues.length(); i++){
            valuesList.add((String)slotValues.get(i));
        }

        System.out.println("Lista de Slots: "+ slotsList.toString());
        System.out.println("Lista de slotValues: "+ valuesList.toString());

    }
}
