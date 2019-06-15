package main.com.tec.MILIB_DB.service;

import main.com.tec.MILIB_DB.domain.Metadata;
import main.com.tec.MILIB_DB.util.jsonParser;
import org.json.JSONException;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Map;

/**
 * This class works as an interface between the Web Service and the metadata
 * @author Esteban Alvarado
 * @version 1.0
 */
public class dataManager {

    /**
     * It is responsible for the insertion of the metadata of the images
     * @param json Json file w/request
     * @param users Users of MILIB
     * @return Response
     * @throws JSONException
     */
    public static Response insert(String json, Map<String,Metadata> users) throws JSONException {

        // Set the JSON data into the list
        ArrayList<String> slotList = new ArrayList<>();
        ArrayList<String> valuesList = new ArrayList<>();

        jsonParser.jsonInsertParser(json,slotList,valuesList);
        String username = jsonParser.getUsername(json);

        System.out.println("[INSERT]:: Slots: " + slotList.toString());
        System.out.println("[INSERT]:: Slots Values: " + valuesList.toString());
        System.out.println("[INSERT] Username: "+username);

        // If the username exists it just initialized else creates a new Metadata instance
        if(!users.containsKey(username)){
            users.put(username,new Metadata());
            System.out.println("[START] Users:: "+users.toString());
        }

        // Validate no ID insert

        if(!users.get(username).verifySlotsNoID(slotList).equals("")){
            JSONObject jsonError = new JSONObject();
            jsonError.put("Status", users.get(username).verifySlots(slotList));
            return Response.status(200).entity(jsonError.toString()).build();
        }

        // Validate that slots are valid
        if(!slotList.isEmpty()) {

            String isAvailableSlot = users.get(username).verifySlots(slotList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        // Insert the metadata
        users.get(username).Insert(slotList,valuesList);

        // --------------------------------------------------------------------

        // Response
        JSONObject respJson = new JSONObject();
        respJson.put("Status","Done");
        return Response.status(200).entity(respJson.toString()).build();
    }


    /**
     * It is responsible for the selection of the metadata of the images
     * @param json Json file w/request
     * @param users Users of MILIB
     * @return Response
     * @throws JSONException
     */
    public static Response select(String json,Map<String,Metadata> users) throws JSONException{
        // --------------------------------------------------------------------
        ArrayList<String> slotList = new ArrayList<>();
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> whereValuesAList = new ArrayList<>();
        ArrayList<String> whereValuesBList = new ArrayList<>();

        jsonParser.jsonSelectParser(json,slotList,whereList,whereValuesAList,whereValuesBList);
        String username = jsonParser.getUsername(json);

        System.out.println("[SELECT]::" + slotList.toString()); // Return values
        System.out.println("[SELECT]::" + whereList.toString()); // Where condition
        System.out.println("[SELECT]::" + whereValuesAList.toString()); // Values condition
        System.out.println("[SELECT]::" + whereValuesBList.toString()); // Between values
        System.out.println("[SELECT]:: Username:" + username);

        // Do the validation to determine: what is it that comes to SELECT?
        // Note: It still needs to carry out the return of the information

        // If the username exists it just initialized else creates a new Metadata instance
        if(!users.containsKey(username)){
            users.put(username,new Metadata());
            System.out.println("[START] Users:: "+users.toString());
        }

        // Validate that slots or whereSlots are correct!

        if(!slotList.isEmpty()) {

            String isAvailableSlot = users.get(username).verifySlots(slotList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        if(!whereList.isEmpty()) {

            String isAvailableSlot = users.get(username).verifySlots(whereList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        // If came an SELECT with an BETWEEN
        if(!whereValuesBList.isEmpty()){

            String isAvailableSlot = users.get(username).verifySlotsRange(whereList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        // Select wich Select method will be called
        if(slotList.isEmpty() && whereList.isEmpty()){

            System.out.println("[SELECT]:: Se solicita toda la galeria");
            users.get(username).Select();

        } else if(whereList.isEmpty() && whereValuesAList.isEmpty()){

            System.out.println("[SELECT]:: Se solicitan algunas columnas de toda la galeria");
            users.get(username).Select(slotList);

        } else if(!whereValuesAList.isEmpty() && whereValuesBList.isEmpty()){

            System.out.println("[SELECT]:: Se solicitan las columnas de las imagenes que cumplan con la condicion");
            users.get(username).Select(slotList,whereList,whereValuesAList);

        } else {
            System.out.println("[SELECT]:: Se solicitan las columnas de las imagenes que cumplan con la condicion en un Between");
            users.get(username).Select(slotList,whereList,whereValuesAList,whereValuesBList);
        }

        String metadataMatrix = users.get(username).getSelectList();


        // --------------------------------------------------------------------

        // Create a new Response w/ the selected data ready to send
        JSONObject respJson = new JSONObject();
        respJson.put("Status","Done");
        respJson.put("MetadataStack",metadataMatrix);
        respJson.put("imgStack","b");

        return Response.status(200).entity(respJson.toString()).build();
    }

    /**
     * It is responsible of update the metadata of the images
     * @param json Json file w/request
     * @param users Users of MILIB
     * @return Response
     * @return
     */
    public static Response update(String json, Map<String,Metadata> users) throws JSONException{
        // --------------------------------------------------------------------
        ArrayList<String> slotList = new ArrayList<>();
        ArrayList<String> valuesList = new ArrayList<>();
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> whereValuesList = new ArrayList<>();

        jsonParser.jsonUpdateParser(json,slotList,valuesList,whereList,whereValuesList);
        String username = jsonParser.getUsername(json);

        System.out.println("[Desde el REST]:: Slots: " + slotList.toString()); // Return values
        System.out.println("[Desde el REST]:: Where slots: " + whereList.toString()); // Where condition
        System.out.println("[Desde el REST]:: slot values: " + valuesList.toString()); // Values of each slot
        System.out.println("[Desde el REST]:: Where values: " + whereValuesList.toString()); // Where Values condition

        // If the username exists it just initialized else creates a new Metadata instance
        if(!users.containsKey(username)){
            users.put(username,new Metadata());
            System.out.println("[START] Users:: "+users.toString());
        }

        // Validate no ID update

        if(!users.get(username).verifySlotsNoID(slotList).equals("")){
            JSONObject jsonError = new JSONObject();
            jsonError.put("Status", users.get(username).verifySlots(slotList));
            return Response.status(200).entity(jsonError.toString()).build();
        }

        // Validate that slots and where are valid

        if(!slotList.isEmpty()) {

            String isAvailableSlot = users.get(username).verifySlots(slotList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        if(!whereList.isEmpty()) {

            String isAvailableSlot = users.get(username).verifySlots(whereList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        users.get(username).Update(slotList,valuesList,whereList,whereValuesList);

        // --------------------------------------------------------------------

        // Response
        JSONObject respJson = new JSONObject();
        respJson.put("Status","Done");

        return Response.status(200).entity(respJson.toString()).build();

    }


    /**
     * It is responsible to delete metadata of the images
     * @param json Json file w/request
     * @param users Users of MILIB
     * @return Response
     * @return
     */
    public static Response delete(String json, Map<String,Metadata> users) throws JSONException {
        // --------------------------------------------------------------------
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> whereValuesList = new ArrayList<>();

        jsonParser.jsonDeleteParser(json,whereList,whereValuesList);
        String username = jsonParser.getUsername(json);

        System.out.println("[DELETE]:: Where slots: " + whereList.toString()); // Where condition
        System.out.println("[DELETE]:: Where values: " + whereValuesList.toString()); // Where Values condition
        System.out.println("[DELETE]:: username: " + username);

        // If the user doesnt exist creates a new Metadata instance
        if(!users.containsKey(username)){
            users.put(username,new Metadata());
            System.out.println("[START] Users:: "+users.toString());
        }

        // Validate that where are valid

        if(!whereList.isEmpty()) {

            String isAvailableSlot = users.get(username).verifySlots(whereList);

            if (!isAvailableSlot.equals("")) {
                JSONObject jsonError = new JSONObject();
                jsonError.put("Status", isAvailableSlot);
                return Response.status(200).entity(jsonError.toString()).build();
            }
        }

        users.get(username).Delete(whereList,whereValuesList);

        // --------------------------------------------------------------------

        // If deletion done, the send Status: OK else Status: FAIL
        JSONObject respJson = new JSONObject();
        respJson.put("Status","Done");

        return Response.status(200).entity(respJson.toString()).build();
    }

}
