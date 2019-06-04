package main.com.tec.MILIB_DB.Resources;

// Libraries
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Class that implements the Web Service for the MILIB project for the DATABASE
 * This Web Service run on Port 8080
 */
@Path("/database")
public class MilibRestService {

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
    @GET
    @Path("/select")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response SELECT(InputStream incomingData) throws JSONException{

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[SELECT] Data Received: "+ recvData);

        // In this part the select actions are performed
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
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response UPDATE(InputStream incomingData){

        // Convert the input in String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[UPDATE] Data Received: "+ recvData);

        // In this part the update actions are performed

        return Response.status(200).build();
    }

    /**
     * Method responsible for removing an image from the disk given its metadata
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/delete
     * @param incomingData Receive a json with the information of the image to delete
     * @return Indicates whether the image could be deleted
     * @throws JSONException
     */
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response DELETE(InputStream incomingData) throws JSONException{

        // Convert the input in String
        String recvData = inputToString(incomingData);

        // Show in console the received data
        System.out.println("[DELETE] Data Received: "+ recvData);

        // In this part the deletion is effected

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
    @PUT
    @Path("/commit")
    @Produces(MediaType.TEXT_PLAIN)
    public Response COMMIT(){

        //Commit stuff here

        System.out.println("[COMMIT] Commit Request");
        String ans = "Commit Successful!";

        return Response.status(200).entity(ans).build();
    }

    /**
     * Make rollback of all changes made to the database
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/back
     * @return Returns a text indicating if the rollback was successful
     */
    @PUT
    @Path("/back")
    @Produces(MediaType.TEXT_PLAIN)
    public Response BACK(){

        //Commit stuff here
        System.out.println("[BACK] Rollback Request");
        String ans = "Rollback Success!";

        return Response.status(200).entity(ans).build();
    }

}
