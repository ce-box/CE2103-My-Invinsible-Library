package com.tec.MILIB.Resources;

// Libraries
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by @estalvgs1999 on 27/05/2019
 * version 1.0
 * last Update: 30/05/2019
 */
@Path("serverSource")
public class MILIB_Resource {

    // SELECT
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String select(){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","Esteban");

        ArrayList<String> myList = new ArrayList<String>();
        myList.add("a");
        myList.add("b");
        myList.add("c");

        jsonObject.put("list",myList);

        return jsonObject.toString();
    }

    // CREATE / INSERT

    @POST
    public String insert(){

        JSONObject jsonObject = new JSONObject();

        return "INSERT";
    }

    // UPDATE
    @PUT
    public String update(){

        JSONObject jsonObject = new JSONObject();

        return "UPDATE";
    }

    // DELETE
    @DELETE
    public String delete(){

        JSONObject jsonObject = new JSONObject();

        return "DELETE";
    }
}

