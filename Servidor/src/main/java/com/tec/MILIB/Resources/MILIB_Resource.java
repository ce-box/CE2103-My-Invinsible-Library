package com.tec.MILIB.Resources;

// Libraries
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by @estalvgs1999 on 27/05/2019
 * version 1.0
 * last Update: 30/05/2019
 */
@Path("myresource")
public class MILIB_Resource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt(){

        JSONObject jsonObject = new JSONObject();
        return "Got it!";
    }
}

