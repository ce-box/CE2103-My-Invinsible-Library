package main.com.tec.Server.resources;

// Libraries
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.com.tec.Server.consumer.*;


/**
 * Class that implements the main Web Service for the MILIB project connections
 * This Web Service run on Port 8081
 * @author Esteban Alvarado Vargas
 * @version alpha 1.5
 */
@Path("/server")
public class MainRestService {

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

    /**
     * Url : http://localhost:8081/Main_Server_war_exploded/api/server/start
     * @param incomingData
     * @return
     */
    @POST
    @Path("/start")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response START(InputStream incomingData){

        Consumer.START();
        return Response.status(200).build();
    }

    /**
     * It is responsible for inserting new images in the RAID and in the
     * metadata Database
     * url: http://localhost:8081/Main_Server_war_exploded/api/server/database/insert
     *
     * @param incomingData Receive a JSON that contains the metadata of the image
     * @return Respond with the status of the request
     */
    @POST
    @Path("database/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response DB_INSERT(InputStream incomingData){

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = Consumer.DB_INSERT(recvData);

        // Return HTTP response 200 in case of success
        return Response.status(201).entity(resp).build();
    }

    /**
     * It is responsible for inserting new images in the RAID and in the
     * metadata Database
     * url: http://localhost:8081/Main_Server_war_exploded/api/server/raid/insert
     *
     * @param incomingData Receive a JSON that contains the metadata of the image
     * @return Respond with the status of the request
     */
    @POST
    @Path("raid/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response RAID_INSERT(InputStream incomingData){

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = Consumer.RAID_INSERT(recvData);

        // Return HTTP response 200 in case of success
        return Response.status(201).entity(resp).build();
    }

    /**
     * Method in charge of returning a JSON with the requested image from the client, under the
     * criterion of the parameters of the metadata
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/select
     * @param incomingData  Receive a json with the information of the requested image
     * @return Returns the requested image and metadata in JSON format
     */

    @GET
    @Path("database/select")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response DB_SELECT(InputStream incomingData){

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = Consumer.DB_SELECT(recvData);

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp).build();
    }

    /**
     * Method in charge of returning a JSON with the requested image from the client, under the
     * criterion of the parameters of the metadata
     * url: http://ip_addr:port/MILIB_Servidor_war_exploded/api/database/select
     * @param incomingData  Receive a json with the information of the requested image
     * @return Returns the requested image and metadata in JSON format
     */

    @GET
    @Path("raid/select")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response RAID_SELECT(InputStream incomingData){

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = Consumer.RAID_SELECT(recvData);

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp).build();
    }
}
