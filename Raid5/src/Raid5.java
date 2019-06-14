import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
    public  static byte[][] cualEsmasGrande(byte[]array1,byte[]array2,byte[]array3){
        byte[][]arraysOrdenados=new byte[3][];
        int size1=array1.length;
        int size2=array2.length;
        int size3=array3.length;

        if(size1>=size2&&size1>=size3){
            arraysOrdenados[0]=array1;
            if(size2>size3){
                arraysOrdenados[1]=array2;
                arraysOrdenados[2]=array3;
            }
            else{
                arraysOrdenados[2]=array2;
                arraysOrdenados[1]=array3;
            }
        }
        if(size2>=size1&&size2>=size3){
            arraysOrdenados[0]=array2;
            if(size1>size3){
                arraysOrdenados[1]=array1;
                arraysOrdenados[2]=array3;
            }
            else{
                arraysOrdenados[2]=array1;
                arraysOrdenados[1]=array3;
            }
        }
        if(size3>=size1&&size3>=size2){
            arraysOrdenados[0]=array3;
            if(size1>size2){
                arraysOrdenados[1]=array1;
                arraysOrdenados[2]=array2;
            }
            else{
                arraysOrdenados[2]=array1;
                arraysOrdenados[1]=array2;
            }
        }


        return arraysOrdenados;
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
    public int scanearDiscos(){
        for (int i = 0; i <Discos.length ; i++) {
            if(Discos[i].listFiles().length==0){
                return i;
            }
        }
        //Retorna esto si ningun disco esta vacio
        return 99;

    }

    public byte[][] obtenerInfromacionDisponible(String id) throws IOException {
        byte[][] infromacionDisponible = new byte[3][];
        int contador = 0;
        for (int i = 0; i < Discos.length; i++) {
            File[] contents = this.Discos[i].listFiles();
            for (int j = 0; j < contents.length; j++) {
                String archivo = contents[j].toString();
                boolean loEncontre = archivo.indexOf(id) != -1 ? true : false;
                boolean parte1 = archivo.indexOf(id + "-1") != -1 ? true : false;
                boolean parte2 = archivo.indexOf(id + "-2") != -1 ? true : false;
                boolean parte3 = archivo.indexOf(id + "-3") != -1 ? true : false;
                boolean parteP = archivo.indexOf(id + "-P") != -1 ? true : false;
                if (parte1) {
                    File file = new File(archivo);
                    String path = file.toString();
                    BufferedImage data = ImageIO.read(file);
                    ByteArrayOutputStream contenedor = new ByteArrayOutputStream();
                    ImageIO.write(data, "png", contenedor);
                    infromacionDisponible[contador] = contenedor.toByteArray();
                    contador++;

                }
                if (parte2) {
                    File file = new File(archivo);
                    String path = file.toString();
                    BufferedImage data = ImageIO.read(file);
                    ByteArrayOutputStream contenedor = new ByteArrayOutputStream();
                    ImageIO.write(data, "png", contenedor);
                    infromacionDisponible[contador] = contenedor.toByteArray();
                    contador++;
                }
                if (parte3) {
                    File file = new File(archivo);
                    String path = file.toString();
                    BufferedImage data = ImageIO.read(file);
                    ByteArrayOutputStream contenedor = new ByteArrayOutputStream();
                    ImageIO.write(data, "png", contenedor);
                    infromacionDisponible[contador] = contenedor.toByteArray();
                    contador++;
                }
                if (parteP) {
                    File file = new File(archivo);
                    String path = file.toString();
                    String data = new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
                    infromacionDisponible[contador] = Base64.decode(data);
                    contador++;


                }
                return infromacionDisponible;

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
                System.out.println(archivo.toString());
                boolean isFound = archivo.contains(id) ? true: false;
                if(isFound){
                    return true;

                }
            }
        }
        return false;
    }public String dameIdDelaImagen(String archivo{
        String nombre = "/raiz/feo/loca/12-1.png";
        String id = "";
        boolean inicio = false;
        for (int x = nombre.length() - 1; x >= 0; x--){
            if (nombre.charAt(x) == '/') {
                inicio = false;
            }
            if (inicio) {
                id = id + nombre.charAt(x);
            }
            if (nombre.charAt(x) == '-') {
                inicio = true;
            }
        }
        String idReal="";
        for (int x=id.length()-1;x>=0;x--)
            idReal = idReal + id.charAt(x);
        return idReal;
    }

    public void recuperarDiscoCompleto() throws IOException {
        int discoARecuperar =this.scanearDiscos();
        for (int i = 0; i <Discos.length ; i++) {
            if(i!=discoARecuperar){
                File[] contents = this.Discos[i].listFiles();
                for (int j = 0; j < contents.length ; j++) {
                    String archivo=contents[j].toString();
                    String idArchivo=dameIdDelaImagen(archivo);
                     byte[][]infromacionDisponible=obtenerInfromacionDisponible(idArchivo);
                     infromacionDisponible=cualEsmasGrande(infromacionDisponible[0],infromacionDisponible[2],infromacionDisponible[2]);

                }
            }

        }
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
