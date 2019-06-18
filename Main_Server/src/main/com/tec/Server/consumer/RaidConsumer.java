package main.com.tec.Server.consumer;

// Libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.URL;
import java.net.URLConnection;


public class RaidConsumer {

    // Network configuration: IP + URL
    private static String db_ip = "192.168.0.21";
    private static String db_default_Url = " http://"+db_ip+":9080/MILIB_RAID_war_exploded/api/raid";

    /* --------------------------------------------
                    CLIENT MANAGER
       --------------------------------------------*/

    public static void writeClient(String json){
        try {
            URL url = new URL(db_default_Url + "/write");
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
            System.out.println("\n[WRITE] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[WRITE] Data recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\n[WRITE] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }
    }

    public static String seekClient(String json){
        try {
            URL url = new URL(db_default_Url + "/select");
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
            System.out.println("\n[SEEK] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[SEEK] Data recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\n[SEEK] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }

        return "";
    }


    public static String deleteClient(String json){
        try {
            URL url = new URL(db_default_Url + "/delete");
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
            System.out.println("\n[DELETE] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[DELETE] Data recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\n[DELETE] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }

        return "";
    }

    public static void commitClient(String json){
        try {
            URL url = new URL(db_default_Url + "/commit");
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
            System.out.println("\n[COMMIT] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[COMMIT] Data recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\n[COMMIT] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }
    }

    public static void backClient(String json){
        try {
            URL url = new URL(db_default_Url + "/back");
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
            System.out.println("\n[BACK] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[BACK] Data recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\n[BACK] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }    }
}
