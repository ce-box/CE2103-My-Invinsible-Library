package main.com.tec.MILIB.Consumer;

// Libraries
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;

public class MilibServiceClient {


    public static void main(String[] args) {

        try {

            // Step1: Create a JSON File to send

            // JSON MOCK for SELECT
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("request",2);
            jsonObject.put("columnas","id,date");
            jsonObject.put("valueA","1,2000");

            System.out.println(jsonObject);

            // Step2: Now pass JSON File Data to REST Service
            try {
                URL url = new URL("http://localhost:8080/MILIB_Servidor_war_exploded/api/insert");
                URLConnection connection = url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                out.write(jsonObject.toString());
                out.close();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while (in.readLine() != null) {
                }
                System.out.println("\nMILIB REST Service Invoked Successfully..");
                in.close();
            } catch (Exception e) {
                System.out.println("\nError while calling Crunchify REST Service");
                System.out.println(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
