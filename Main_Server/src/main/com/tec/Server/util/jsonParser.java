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
    public static String inserttoRaid(String json, String ID) throws JSONException{

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

    /**
     *
     * @param ID
     * @return
     * @throws JSONException
     */
    public static String selecttoRaid(String ID) throws JSONException{

        // Get img from input JSON
        JSONObject jsonOut = new JSONObject();
        jsonOut.put("ID",ID);

        return jsonOut.toString();
    }


    public static String raidtoIDE(String json, String img) throws JSONException {

        // Get img from input JSON
        JSONObject jsonObject1 = new JSONObject(img);
        String img64 = "";

        // Join jsons
        JSONObject jsonObject2 = new JSONObject(json);
        jsonObject2.put("img64",img64);

        System.out.println("[JSON] "+jsonObject2.toString());
        return jsonObject2.toString();
    }
}
