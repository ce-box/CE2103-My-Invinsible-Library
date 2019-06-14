package main.com.tec.Server.util;

import org.json.JSONException;
import org.json.JSONObject;

public class jsonParser {
    /**
     *
     * @param json
     * @param ID
     * @return
     * @throws JSONException
     */
    public static String jsontoRaid(String json, String ID) throws JSONException{

        // Get img from input JSON
        JSONObject jsonObject = new JSONObject(json);
        String img64 = "";
        try {
            img64 = jsonObject.getString("img64");
        }catch (JSONException e){

        }
        // Create new JSON to RAID
        JSONObject jsonOut = new JSONObject();
        jsonOut.put("ID",ID);
        jsonOut.put("img64",img64);

        return jsonOut.toString();
    }
}
