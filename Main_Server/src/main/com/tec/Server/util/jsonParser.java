package main.com.tec.Server.util;

import org.json.JSONException;
import org.json.JSONObject;

public class jsonParser {
    /**
     * Selerialize json to Insert
     * @param json
     * @param ID
     * @return
     * @throws JSONException
     */
    public static String inserttoRaid(String json, String ID) throws JSONException{

        // Get img from input JSON
        JSONObject jsonObject = new JSONObject(json);
        String img64 = jsonObject.getString("img64");
        String username = jsonObject.getString("username");

        // Create new JSON to RAID
        JSONObject jsonOut = new JSONObject();
        jsonOut.put("ID",ID);
        jsonOut.put("img64",img64);
        jsonOut.put("username",username);

        return jsonOut.toString();
    }

    /**
     * Serialize Json to select
     * @param json
     * @return
     * @throws JSONException
     */
    public static String selecttoRaid(String json) throws JSONException{

        // Get img from input JSON
        JSONObject jsonObject = new JSONObject(json);
        String username = jsonObject.getString("username");
        String ID = jsonObject.getString("ID");

        // Get img from input JSON
        JSONObject jsonOut = new JSONObject();
        jsonOut.put("ID",ID);
        jsonOut.put("username",username);

        return jsonOut.toString();
    }

    /**
     * Serialize json to delete
     * @param json
     * @param ID
     * @return
     * @throws JSONException
     */
    public static String deleteRaid(String json,String ID) throws JSONException{

        // Get img from input JSON
        JSONObject jsonObject = new JSONObject(json);
        String username = jsonObject.getString("username");

        // Get img from input JSON
        JSONObject jsonOut = new JSONObject();
        jsonOut.put("ID",ID);
        jsonOut.put("username",username);

        return jsonOut.toString();
    }

    /**
     * Serialize response to IDE
     * @param json
     * @param idStack
     * @return
     * @throws JSONException
     */
    public static String raidtoIDE(String json, String idStack) throws JSONException {

        // Join jsons
        JSONObject jsonObject2 = new JSONObject(json);
        jsonObject2.put("IdStack",idStack);

        System.out.println("[JSON] "+jsonObject2.toString());
        return jsonObject2.toString();
    }
}
