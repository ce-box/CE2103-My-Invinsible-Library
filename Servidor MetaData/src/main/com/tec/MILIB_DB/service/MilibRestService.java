package main.com.tec.MILIB_DB.service;

// Libraries

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.com.tec.MILIB_DB.util.jsonParser;
import main.com.tec.MILIB_DB.domain.Metadata;

/**
 * Class that implements the Web Service for the MILIB project for the DATABASE
 * This Web Service run on Port 8080<br>
 *
 * <p><b>ADVICE: </b>Since with the other types of request, all the orders have been
 * replaced by @POST, which is the only verb with which it works</p>
 * @author Esteban Alvarado Vargas
 * @version alpha 3.5
 */
@Path("/database")
public class MilibRestService {

    String XMLPath = "/home/esteban/Documentos/TEC/1S 2019/Algoritmos y estructuras de datos II/4. Proyectos/" +
            "Proyecto #3/Source/MyInvensibleLibrary/Servidor MetaData/XML_Metadata/input.xml";

    /**
     * Converts the received inputStream to a String for handling the
     * received JSON file
     * @param incomingData Data sent by Client
     * @return Data in String type
     */
    private String inputToString(InputStream incomingData){

        // Setting an String Builder
        StringBuilder inputBuilder = new StringBuilder();

        // Try to read the incomingData in JSON format
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
            String line = null;
            while((line = in.readLine())!= null){
                inputBuilder.append(line);
            }
        } catch (Exception e){
            System.out.println("Error Parsing: -");
        }

        // Returns the Data in String format
        return inputBuilder.toString();
    }


    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response START(InputStream incomingData){

        Metadata.setFile_path(XMLPath);
        Metadata.Start();
        return Response.status(200).build();
    }

    /* -----------------------------------------------------------------------------------
                               REGULAR SYNTAX - METADATA DATA BASE
        ----------------------------------------------------------------------------------*/

    /**
     * It is responsible for inserting new images in the RAID and in the
     * metadata Database
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/insert
     *
     * @param incomingData Receive a JSON that contains the metadata of the image
     * @return Respond with the status of the request
     * @throws JSONException
     */
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response INSERT(InputStream incomingData) throws JSONException {

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[INSERT] Data Received: "+ recvData);

        // In this part the insert actions are performed

        // --------------------------------------------------------------------

        // Set the JSON data into the list
        ArrayList<String> slotList = new ArrayList<>();
        ArrayList<String> valuesList = new ArrayList<>();

        jsonParser.jsonInsertParser(recvData,slotList,valuesList);

        System.out.println("[INSERT]:: Slots: " + slotList.toString());
        System.out.println("[INSERT]:: Slots Values: " + valuesList.toString());

        // Insert the metadata

        Metadata.Insert(slotList,valuesList);

        // --------------------------------------------------------------------

        // Response
        JSONObject json = new JSONObject();
        json.put("Status","OK");

        System.out.println("[INSERT] Data sent: "+ json.toString());

        // Return HTTP response 200 in case of success
        return Response.status(201).entity(json.toString()).build();
    }


    /**
     * Method in charge of returning a JSON with the requested image from the client, under the
     * criterion of the parameters of the metadata
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/select
     * @param incomingData  Receive a json with the information of the requested image
     * @return Returns the requested image and metadata in JSON format
     * @throws JSONException
     */
    @POST
    @Path("/select")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response SELECT(InputStream incomingData) throws JSONException{

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[SELECT] Data Received: "+ recvData);

        // In this part the select actions are performed

        // --------------------------------------------------------------------
        ArrayList<String> slotList = new ArrayList<>();
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> whereValuesAList = new ArrayList<>();
        ArrayList<String> whereValuesBList = new ArrayList<>();

        jsonParser.jsonSelectParser(recvData,slotList,whereList,whereValuesAList,whereValuesBList);

        System.out.println("[Desde el REST]::" + slotList.toString()); // Return values
        System.out.println("[Desde el REST]::" + whereList.toString()); // Where condition
        System.out.println("[Desde el REST]::" + whereValuesAList.toString()); // Values condition
        System.out.println("[Desde el REST]::" + whereValuesBList.toString()); // Between values

        // Do the validation to determine: what is it that comes to SELECT?
        // Note: It still needs to carry out the return of the information

        if(slotList.isEmpty() && whereList.isEmpty()){

            System.out.println("[SELECT]:: Se solicita toda la galeria");
            Metadata.Select();

        } else if(whereList.isEmpty() && whereValuesAList.isEmpty()){

            System.out.println("[SELECT]:: Se solicitan algunas columnas de toda la galeria");
            Metadata.Select(slotList);

        } else if(!whereValuesAList.isEmpty() && whereValuesBList.isEmpty()){

            System.out.println("[SELECT]:: Se solicitan las columnas de las imagenes que cumplan con la condicion");
            Metadata.Select(slotList,whereList,whereValuesAList);

        } else {
            System.out.println("[SELECT]:: Se solicitan las columnas de las imagenes que cumplan con la condicion en un Between");
            Metadata.Select(slotList,whereList,whereValuesAList,whereValuesBList);
        }

        // --------------------------------------------------------------------

        // Response
        JSONObject json = new JSONObject();
        json.put("valueC","a");
        json.put("valueD","b");

        System.out.println("[SELECT] Data sent: "+ json.toString());

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * Update the metadata of an image or several images
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/update
     * @param incomingData Receive a json with the information of the requested image
     * @return Respond with the status of the request
     */
    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UPDATE(InputStream incomingData) throws JSONException{

        // Convert the input in String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[UPDATE] Data Received: "+ recvData);

        // In this part the update actions are performed
        // --------------------------------------------------------------------
        ArrayList<String> slotList = new ArrayList<>();
        ArrayList<String> valuesList = new ArrayList<>();
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> whereValuesList = new ArrayList<>();

        jsonParser.jsonUpdateParser(recvData,slotList,valuesList,whereList,whereValuesList);

        System.out.println("[Desde el REST]:: Slots: " + slotList.toString()); // Return values
        System.out.println("[Desde el REST]:: Where slots: " + whereList.toString()); // Where condition
        System.out.println("[Desde el REST]:: slot values: " + valuesList.toString()); // Values of each slot
        System.out.println("[Desde el REST]:: Where values: " + whereValuesList.toString()); // Where Values condition

        Metadata.Update(slotList,valuesList,whereList,whereValuesList);

        // --------------------------------------------------------------------

        // Response

        return Response.status(200).build();
    }

    /**
     * Method responsible for removing an image from the disk given its metadata
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/delete
     * @param incomingData Receive a json with the information of the image to delete
     * @return Indicates whether the image could be deleted
     * @throws JSONException
     */
    @POST
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response DELETE(InputStream incomingData) throws JSONException{

        // Convert the input in String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[DELETE] Data Received: "+ recvData);

        // In this part the deletion is effected
        // --------------------------------------------------------------------
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<String> whereValuesList = new ArrayList<>();

        jsonParser.jsonDeleteParser(recvData,whereList,whereValuesList);

        System.out.println("[Desde el REST]:: Where slots: " + whereList.toString()); // Where condition
        System.out.println("[Desde el REST]:: Where values: " + whereValuesList.toString()); // Where Values condition

        Metadata.Delete(whereList,whereValuesList);
        // --------------------------------------------------------------------

        // If deletion done, the send Status: OK else Status: FAIL
        JSONObject json = new JSONObject();
        json.put("Status","OK");

        System.out.println("[DELETE] Data sent: "+ json.toString());

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(json.toString()).build();
    }

    /* -----------------------------------------------------------------------------------
                          COMMIT & ROLLBACK - METADATA DATA BASE
        ----------------------------------------------------------------------------------*/

    /**
     * Make commit of all changes made to the database
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/commit
     * @return Returns a text indicating if the commit was successful
     */
    @POST
    @Path("/commit")
    @Produces(MediaType.TEXT_PLAIN)
    public Response COMMIT(){

        //Commit stuff here
        Metadata.setFile_path(XMLPath);
        Metadata.Close();

        System.out.println("[COMMIT] Commit Request");
        String ans = "Commit Successful!";

        return Response.status(200).entity(ans).build();
    }

    /**
     * Make rollback of all changes made to the database
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/back
     * @return Returns a text indicating if the rollback was successful
     */
    @POST
    @Path("/back")
    @Produces(MediaType.TEXT_PLAIN)
    public Response BACK(){

        //Rollback stuff here
        System.out.println("[BACK] Rollback Request");

        Metadata.setFile_path(XMLPath);
        Metadata.Start();

        String ans = "Rollback Success!";

        return Response.status(200).entity(ans).build();
    }

}
