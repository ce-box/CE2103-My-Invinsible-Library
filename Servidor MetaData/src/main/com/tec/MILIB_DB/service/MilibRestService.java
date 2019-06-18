package main.com.tec.MILIB_DB.service;

// Libraries

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

    private String XMLPath = "/home/esteban/Documentos/TEC/1S 2019/Algoritmos y estructuras de datos II/4. Proyectos/" +
            "Proyecto #3/Source/MyInvensibleLibrary/Servidor MetaData/XML_Metadata/input.xml";

    // Dictionary that contains the metadata instances by username
    private static  Map<String,Metadata> users = new HashMap<>();


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
    public Response START(InputStream incomingData) throws JSONException{

        String recvData = inputToString(incomingData);
        String username = jsonParser.getUsername(recvData);

        System.out.println("[START] Username: "+username);

        // If the username exists it just initialized else creates a new Metadata instance
        if(!users.containsKey(username)){
            users.put(username,new Metadata());
            System.out.println("Se crea un nuevo usuario!");
            System.out.println("[START] Users:: "+users.toString());
        }

        users.get(username).setFile_path(XMLPath);
        users.get(username).Start();

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

        Response resp = dataManager.insert(recvData,users);

        System.out.println("[INSERT] Data sent: "+ resp.getEntity());

        // Return HTTP response 200 in case of success
        return Response.status(201).entity(resp.getEntity()).build();
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

        Response res = dataManager.select(recvData,users);

        System.out.println("[SELECT] Data sent: "+ res.getEntity());

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(res.getEntity()).build();
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

        Response resp = dataManager.update(recvData,users);

        System.out.println("[UPDATE] Data sent: "+ resp.getEntity());

        return Response.status(200).entity(resp.getEntity()).build();
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

        Response resp = dataManager.delete(recvData,users);

        System.out.println("[DELETE] Data sent: "+ resp.getEntity());

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp.getEntity()).build();
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response COMMIT(InputStream incomingData) throws JSONException{

        String recvData = inputToString(incomingData);

        String username = jsonParser.getUsername(recvData);

        //Commit stuff here
        users.get(username).setFile_path(XMLPath);
        users.get(username).Close();

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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response BACK(InputStream incomingData) throws JSONException{

        String recvData = inputToString(incomingData);

        String username = jsonParser.getUsername(recvData);

        //Rollback stuff here
        System.out.println("[BACK] Rollback Request");

        users.get(username).setFile_path(XMLPath);
        users.get(username).Start();

        String ans = "Rollback Success!";

        return Response.status(200).entity(ans).build();
    }

    /**
     * Returns the ID of the last insert/select request
     * @param incomingData Receive a json with the information of the image to delete
     * @return Returns the ID of the last request
     * @throws JSONException
     */
    @POST
    @Path("/getId")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response GET_ID(InputStream incomingData) throws JSONException{

        String recvData = inputToString(incomingData);
        String username = jsonParser.getUsername(recvData);

        // Show in console the received data
        System.out.println("[GET ID] Data Received: "+ recvData);
        System.out.println("[GET ID] Username: "+ username);

        // Get the ID of the last request
        String resp = users.get(username).getID();

        System.out.println("[GET ID] Id: " + resp);

        return Response.status(200).entity(resp).build();

    }

}
