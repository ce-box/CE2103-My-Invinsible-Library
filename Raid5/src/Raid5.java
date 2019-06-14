import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Raid5 {
    private int turno=0;
    private Path currentRelativePath = Paths.get("");
    private String s = currentRelativePath.toAbsolutePath().toString();
    public File Disco1 = new File(s+"/src"+"/Disco1");
    public File Disco2 = new File(s+"/src"+"/Disco2");
    public File Disco3 = new File(s+"/src"+"/Disco3");
    public File Disco4 = new File(s+"/src"+"/Disco4");
    private File[] Discos={Disco1,Disco2,Disco3,Disco4};
    //######################################################################################################
    //ESTE METODO SIRVE PARA CREAR UN ARCHIVO  TXT EN UNA RESPECTIVA DIRRECCION CON UN CIERTO NOMBRE
    //######################################################################################################
    public void crearArchivo(String ruta,String informacionAmeter,String nombre) throws IOException {
        FileOutputStream out = new FileOutputStream(ruta+"/"+nombre+".txt");
        out.write(informacionAmeter.getBytes());
        out.close();
    }
    //##########################################
    public void crearImagen(String ruta, BufferedImage image, String nombre) throws IOException {
        File archivo = new File(ruta+"/"+nombre+".png");
        ImageIO.write(image, "png", archivo);
    }
    //##########333
    //######################################################################################################
    //ESTE METODO LO QUE HACE ES   BUSCAR ARCHIVOS  QUE CONTENGAN EL ID INGRESADO Y ELIMINARLOS DE LOS DISCOS
    //#######################################################################################################
    public void borrar(String id){
        //String input = "Android gave new life to Java";
       // boolean isFound = input.indexOf("id") !=-1? true: false;
        for (int i = 0; i <Discos.length ; i++) {
            File[] contents = this.Discos[i].listFiles();
            for (int j = 0; j < contents.length ; j++) {
                String archivo=contents[j].toString();
                boolean isFound = archivo.indexOf(id) !=-1? true: false;
                if(isFound){
                    File file = new File(archivo);
                    if(file.delete())
                    {
                        System.out.println("EL ARCHIVO SE ELIMINO CORRECTAMENTE");
                    }
                    else
                    {
                        System.out.println("EL ARCHIVO NO SE LOGRO ELIMINAR CORRECTAMENTE");
                    }
                }
            }
        }
    }

    public String obtenerImagen(String id) throws IOException {
        String imagen="";
        String data1="";
        String data2="";
        String data3="";
        String data[]=new String[3];
        for (int i = 0; i <Discos.length ; i++) {

            File[] contents = this.Discos[i].listFiles();
            for (int j = 0; j < contents.length ; j++) {
                String archivo=contents[j].toString();
                boolean parte1 = archivo.indexOf(id+"-1") !=-1? true: false;
                boolean parte2 = archivo.indexOf(id+"-2") !=-1? true: false;
                boolean parte3 = archivo.indexOf(id+"-3") !=-1? true: false;
                if(parte1){
                    File file = new File(archivo);
                    String path= file.toString();
                    data1 = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);

                }
                if(parte2){
                    File file = new File(archivo);
                    String path= file.toString();
                    data2 = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                }
                if(parte3){
                    File file = new File(archivo);
                    String path= file.toString();
                    data3 = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                }
            }
        }
        imagen=data1+data2+data3;
        return imagen;
    }
    //######################################################################################################
    //ESTE METODO LO QUE HACE ES   BUSCAR ARCHIVOS  QUE CONTENGAN EL ID INGRESADO
    //#######################################################################################################
    public boolean buscar(String id){
        int cuantasVecesLoEcontre=0;
        for (int i = 0; i <Discos.length ; i++) {
            File[] contents = this.Discos[i].listFiles();
            for (int j = 0; j < contents.length ; j++) {
                String archivo=contents[j].toString();
                boolean isFound = archivo.indexOf(id) !=-1? true: false;
                if(isFound){
                   cuantasVecesLoEcontre++;
                }
                if(cuantasVecesLoEcontre==4){
                    return true;
                }
            }
        }
        return false;
    }
    //#################################################################################################################
    //ESTE METODO LO QUE HACE ES GUARDAR LA INFORMACION CONTENIDA EN EL ARRAY DE STRING Y LO DISTRIBUYE ENTRE LOS DISCOS
    // DE MMANERA QUE LA PARIDAD QUEDE  DISTRIBUIDA EN MEDIO DE TODOS LOS DISCOS
    //#################################################################################################################
    public void GuardarInfromacion( BufferedImage partesDeLaImagen[] ,String id,String Paridad) throws IOException {

        int parte=1;
        int temporal=turno;
        for (int i = 0; i < Discos.length; i++) {
            if(turno==4){
                turno=0;
            }
            if(parte==4){
                crearArchivo(Discos[turno].getAbsolutePath(),Paridad,id+"-P");
            }
            else{
                crearImagen(Discos[turno].getAbsolutePath(),partesDeLaImagen[i],id+"-"+parte);
            }
           parte=parte+1;
           turno++;
        }
        turno=temporal;
        turno=turno+1;
    }

}
