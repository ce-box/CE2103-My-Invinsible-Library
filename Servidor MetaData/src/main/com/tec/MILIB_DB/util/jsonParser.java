package main.com.tec.MILIB_DB.util;

// Libraries
import org.json.*;
import java.util.ArrayList;

/**
 * Class responsible for the translation of the JSON according to the method
 * by which the request was made
 * @author Esteban Alvarado Vargas
 * @version alpha 1.1
 */
public class jsonParser {

    /**
     * It is responsible for converting the entry json into the data required
     * for the metadata insertion
     * @param json Json file to parse
     * @throws JSONException
     */
    public static void jsonInsertParser(String json) throws JSONException{

        // Step 1: First a JSONObject is created and the JSONArray is extracted from it
        JSONObject jsonObject = new JSONObject(json);
        JSONArray slots = (JSONArray) jsonObject.getJSONArray("slots");
        JSONArray slotValues = (JSONArray) jsonObject.getJSONArray("slotsValues");

        // Step 2: Then, the necessary list is created and the data of the JSONArray is transferred to the lists
        ArrayList<String> slotsList =  new ArrayList<>();
        ArrayList<String> valuesList =  new ArrayList<>();

        for(int i = 0; i < slots.length(); i++){
            slotsList.add((String)slots.get(i));
            valuesList.add((String)slotValues.get(i));
        }

        // Step 3:
        System.out.println("Lista de Slots: "+ slotsList.toString());
        System.out.println("Lista de slotValues: "+ valuesList.toString());

    }

    /**
     * It is responsible for converting the entry json into the data required
     * for the select of metadata
     * @param json Json file to parse
     * @throws JSONException
     */
    public static void jsonSelectParser(String json) throws JSONException{

        // Step 1: First a JSONObject is created and the JSONArray is extracted from it
        JSONObject jsonObject = new JSONObject(json);
        JSONArray slots = (JSONArray) jsonObject.getJSONArray("slots"); // The slot to return
        JSONArray where = (JSONArray) jsonObject.getJSONArray("where"); // The condition
        JSONArray whereValues= (JSONArray) jsonObject.getJSONArray("whereValues");

        // Step 2: Then, the necessary list is created and the data of the JSONArray is transferred to the lists
        ArrayList<String> slotsList =  new ArrayList<>();
        ArrayList<String> whereList =  new ArrayList<>();
        ArrayList<String> valuesList =  new ArrayList<>();

        for(int i = 0; i < slots.length(); i++) {
            slotsList.add((String) slots.get(i));
        }

        for(int i = 0; i < where.length(); i++){
            whereList.add((String)where.get(i));
            valuesList.add((String)whereValues.get(i));
        }

        // Step 3:
        System.out.println("Lista de Slots: "+ slotsList.toString());
        System.out.println("Lista de Where: "+ whereList.toString());
        System.out.println("Lista de slotValues: "+ valuesList.toString());

    }

    /**
     * It is responsible for converting the entry json into the data required
     * for the update of metadata
     * @param json Json file to parse
     * @throws JSONException
     */
    public static void jsonUpdateParser(String json) throws JSONException{

        // Step 1: First a JSONObject is created and the JSONArray is extracted from it
        JSONObject jsonObject = new JSONObject(json);
        JSONArray slots = (JSONArray) jsonObject.getJSONArray("slots"); // The slot to return
        JSONArray slotsValues = (JSONArray) jsonObject.getJSONArray("slotsValues");
        JSONArray where = (JSONArray) jsonObject.getJSONArray("where");
        JSONArray whereValues = (JSONArray) jsonObject.getJSONArray("whereValues");

        // Step 2: Then, the necessary list is created and the data of the JSONArray is transferred to the lists
        ArrayList<String> slotsList =  new ArrayList<>();
        ArrayList<String> valuesList =  new ArrayList<>();
        ArrayList<String> whereList =  new ArrayList<>();
        ArrayList<String> whereValuesList =  new ArrayList<>();

        for(int i = 0; i < slots.length(); i++) {
            slotsList.add((String) slots.get(i));
            valuesList.add((String) slotsValues.get(i));
        }

        for(int i = 0; i < where.length(); i++){
            whereList.add((String)where.get(i));
            whereValuesList.add((String)whereValues.get(i));
        }

        // Step 3: Shows the ArrayList
        System.out.println("Lista de Slots: "+ slotsList.toString());
        System.out.println("Lista de SlotsValues: "+ valuesList.toString());
        System.out.println("Lista de Where: "+ whereList.toString());
        System.out.println("Lista de whereValues: "+ whereValuesList.toString());

    }

    /**
     * It is responsible for converting the entry json into the data required
     * for the deletion of metadata
     * @param json Json file to parse
     * @throws JSONException
     */
    public static void jsonDeleteParser(String json) throws JSONException {

        // Step 1: First a JSONObject is created and the JSONArray is extracted from it
        JSONObject jsonObject = new JSONObject(json);
        JSONArray where = (JSONArray) jsonObject.getJSONArray("where"); // The condition
        JSONArray whereValues= (JSONArray) jsonObject.getJSONArray("whereValues");

        // Step 2: Then, the necessary list is created and the data of the JSONArray is transferred to the lists
        ArrayList<String> whereList =  new ArrayList<>();
        ArrayList<String> whereValuesList =  new ArrayList<>();

        for(int i = 0; i < where.length(); i++){
            whereList.add((String)where.get(i));
            whereValuesList.add((String)whereValues.get(i));
        }

        // Step 3:
        System.out.println("Lista de Where: "+ whereList.toString());
        System.out.println("Lista de whereValues: "+ whereValuesList.toString());

    }


}
