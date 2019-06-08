package main.com.tec.Server.consumer;

// Libraries
import sun.net.www.protocol.http.HttpURLConnection;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class Consumer {

    private static String db_ip = "192.168.100.20";
    private static String rd_ip = "192.168.100.20";
    private static String db_default_Url = " http://"+db_ip+":8080/MILIB_Servidor_war_exploded/api/database";
    private static String rd_default_Url = " http://"+rd_ip+":9080/MILIB_Servidor_war_exploded/api/database";

    public static void START(){
        try {
            URL url = new URL(db_default_Url + "/start");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write("");
            out.close();

            // Step 3: Receive the Data sent from Server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            System.out.println("\nMILIB REST Service Invoked Successfully..");
            System.out.println("\nData recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\nError while calling MILIB DB REST Service");
            System.out.println(e);
        }
    }

    public static String DB_INSERT(String json){
        try {
            URL url = new URL(db_default_Url + "/insert");
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            out.write(json);
            out.close();

            // Step 3: Receive the Data sent from Server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            System.out.println("\nMILIB REST Service Invoked Successfully..");
            System.out.println("\nData recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\nError while calling MILIB DB REST Service");
            System.out.println(e);
        }

        return "";
    }

    public static String RAID_INSERT(String json){
        return "{:)}";
    }

    public static String DB_SELECT(String json){

        try {
            URL url = new URL(db_default_Url + "/select");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            //connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
            //out.write("{}");
            out.close();

            // Step 3: Receive the Data sent from Server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;

            while ((line = in.readLine()) != null) {
                builder.append(line);
            }
            System.out.println("\nMILIB REST Service Invoked Successfully..");
            System.out.println("\nData recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\nError while calling MILIB DB REST Service");
            System.out.println(e);
        }

        return "";
    }

    public static String RAID_SELECT(String json){
        return "{:)}";
    }
}
