package main.com.tec.Server.resources;

// Libraries
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.com.tec.Server.consumer.*;
import main.com.tec.Server.util.jsonParser;
import org.json.JSONException;
import org.json.JSONObject;


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

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        MilibConsumer.startClient(recvData);

        return Response.status(200).build();
    }

    /**
     * It is responsible for inserting new images in the RAID and in the
     * metadata Database
     * url: http://localhost:8081/Main_Server_war_exploded/api/server/insert
     *
     * @param incomingData Receive a JSON that contains the metadata of the image
     * @return Respond with the status of the request
     */
    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverInsert(InputStream incomingData) throws JSONException {

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = MilibConsumer.insertClient(recvData);

        // Create and Send json file to insert an image on RAID disk
        String ID = MilibConsumer.getInsertedId(recvData);
        String toRaidJson = jsonParser.inserttoRaid(recvData,ID);
        RaidConsumer.writeClient(toRaidJson);

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp).build();
    }



    /**
     * Method in charge of returning a JSON with the requested image from the client, under the
     * criterion of the parameters of the metadata
     * url: http://localhost:8081/Main_Server_war_exploded/api/server/select
     * @param incomingData  Receive a json with the information of the requested image
     * @return Returns the requested image and metadata in JSON format
     */

    @GET
    @Path("/select")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverSelect(InputStream incomingData) throws JSONException{

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String json = MilibConsumer.selectClient(recvData);

        // Create and Send json file to select an image from RAID disk
        String ID = MilibConsumer.getInsertedId(recvData);
        String toRaidJson = jsonParser.selecttoRaid(recvData,ID);

        System.out.println("[JSON] :: "+toRaidJson);
        String img = RaidConsumer.seekClient(toRaidJson); // { img64 = imagen}

        System.out.println(json + "/n" + img);
        String resp = jsonParser.raidtoIDE(json,img);

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp).build();
    }

    /**
     * Url : http://localhost:8081/Main_Server_war_exploded/api/server/update
     * @param incomingData
     * @return
     */
    @PUT
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverUpdate(InputStream incomingData){

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = MilibConsumer.updateClient(recvData);

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp).build();
    }

    /**
     * Url : http://localhost:8081/Main_Server_war_exploded/api/server/delete
     * @param incomingData
     * @return
     */
    @DELETE
    @Path("/delete")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverDelete(InputStream incomingData) throws JSONException{

        // Convert the input in to an String
        String recvData = inputToString(incomingData);

        String resp = MilibConsumer.deleteClient(recvData);

        // Create and Send json file to select an image from RAID disk
        String ID = MilibConsumer.getInsertedId(recvData);
        String toRaidJson = jsonParser.selecttoRaid(recvData,ID);

        System.out.println("[JSON] :: "+toRaidJson);
        RaidConsumer.deleteClient(toRaidJson);

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(resp).build();
    }

    /**
     * Url : http://localhost:8081/Main_Server_war_exploded/api/server/commit
     * @param incomingData
     * @return
     */
    @PUT
    @Path("/commit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverCommit(InputStream incomingData){

        String recvData = inputToString(incomingData);

        MilibConsumer.commitClient(recvData);
        RaidConsumer.commitClient();

        // Return HTTP response 200 in case of success
        return Response.status(200).build();
    }

    /**
     * Url : http://localhost:8081/Main_Server_war_exploded/api/server/back
     * @param incomingData
     * @return
     */
    @PUT
    @Path("/back")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response serverBack(InputStream incomingData){

        String recvData = inputToString(incomingData);

        MilibConsumer.backClient(recvData);
        RaidConsumer.backClient();

        // Return HTTP response 200 in case of success
        return Response.status(200).build();
    }


}
