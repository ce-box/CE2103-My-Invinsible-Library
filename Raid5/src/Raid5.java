import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    //ESTE METODO SIRVE PARA CREAR UN ARCHIVO  TXT EN UNA RESPECTIVA DIRRECCION CON UN CIERTO NOMBRE
    public void crearArchivo(String ruta,String informacionAmeter,String nombre) throws IOException {
        FileOutputStream out = new FileOutputStream(ruta+"/"+nombre+".txt");
        out.write(informacionAmeter.getBytes());
        out.close();
    }
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
                        System.out.println("File deleted successfully");
                    }
                    else
                    {
                        System.out.println("Failed to delete the file");
                    }

                }
            }

        }



    }
    //ESTE METODO LO QUE HACE ES GUARDAR LA INFORMACION CONTENIDA EN EL ARRAY DE STRING Y LO DISTRIBUYE ENTRE LOS DISCOS
    // DE MMANERA QUE LA PARIDAD QUEDE  DISTRIBUIDA EN MEDIO DE TODOS LOS DISCOS
    public void GuardarInfromacion(String[] data,String id) throws IOException {
        int parte=1;
int temporal=turno;
        for (int i = 0; i < Discos.length; i++) {
            if(turno==4){
                turno=0;
            }
            if(parte==4){
                crearArchivo(Discos[turno].getAbsolutePath(),data[i],id+"P");
            }
            else{
                crearArchivo(Discos[turno].getAbsolutePath(),data[i],id+parte);

            }
           parte=parte+1;
           turno++;
        }
        turno=temporal;
        turno=turno+1;
    }

}
