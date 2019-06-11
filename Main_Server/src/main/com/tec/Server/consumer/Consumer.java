package main.com.tec.Server.consumer;

// Libraries
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 *
 */
public class Consumer {

    public static void main(String[] args) {
        startClient();
        insertClient("{}");
        selectClient("{}");
        updateClient("{}");
        deleteClient("{}");
        commitClient();
        backClient();
    }

    // Network configuration: IP + URL
    private static String db_ip = "192.168.0.21";
    private static String db_default_Url = " http://"+db_ip+":8080/MILIB_Servidor_war_exploded/api/database";

    /* --------------------------------------------
                    CLIENT MANAGER
       --------------------------------------------*/

    public static void startClient(){
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
            System.out.println("\n[START] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[START] Data recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\n[START] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }
    }

    public static String insertClient(String json){
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
            System.out.println("\n[INSERT] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[INSERT] Data recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\n[INSERT] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }

        return "";
    }


    public static String selectClient(String json){

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
            System.out.println("\n[SELECT] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[SELECT] Data recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\n[SELECT] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }

        return "";
    }



    public static String updateClient(String json){
        try {
            URL url = new URL(db_default_Url + "/update");
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
            System.out.println("\n[UPDATE] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[UPDATE] Data recieved: " + builder.toString());
            in.close();

            return builder.toString();


        } catch (Exception e) {
            System.out.println("\n[UPDATE] Error while calling MILIB DB REST Service");
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

    public static void commitClient(){
        try {
            URL url = new URL(db_default_Url + "/commit");
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
            System.out.println("\n[COMMIT] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[COMMIT] Data recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\n[COMMIT] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }
    }

    public static void backClient(){
        try {
            URL url = new URL(db_default_Url + "/back");
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
            System.out.println("\n[BACK] MILIB REST Service Invoked Successfully..");
            System.out.println("\n[BACK] Data recieved: " + builder.toString());
            in.close();


        } catch (Exception e) {
            System.out.println("\n[BACK] Error while calling MILIB DB REST Service");
            System.out.println(e);
        }    }
}