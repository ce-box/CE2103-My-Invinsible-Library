package main.com.tec.MILIB.Resources;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.print.attribute.standard.Media;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class MilibRestService {

    /**
     * POST
     * http://ip_addr:8080/MILIB_Servidor_war_exploded/api/insert
     */

    @POST
    @Path("/insert")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insert(InputStream incomingData) throws JSONException {

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

        // Show in console the received data
        System.out.println("Data Received: "+ inputBuilder.toString());

        JSONObject json = new JSONObject();
        json.put("valueA",1);
        json.put("valueB",2);

        System.out.println("Data sent: "+ json.toString());

        // Return HTTP response 200 in case of success
        return Response.status(200).entity(json.toString()).build();
    }

    /**
     * GET
     * http://ip_addr:8080/MILIB_Servidor_war_exploded/api/select
     */

    @GET
    @Path("/select")
    @Produces(MediaType.TEXT_PLAIN)
    public Response select(InputStream incomingData){
        String result = "MILIB Rest Service Successfully started...";

        return Response.status(200).entity(result).build();
    }
}
