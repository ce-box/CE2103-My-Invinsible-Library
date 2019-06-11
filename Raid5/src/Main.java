import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        Raid5 test=new Raid5();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println(s);
        String input = "Android gave new life to Java";
        boolean isFound = input.indexOf("gavesa") !=-1? true: false;
        System.out.println("el valor de is found es "+isFound);
        String data[]={"Hola","como","Estas","Paridad"};
        test.GuardarInfromacion(data,"perrito");
      //test.GuardarInfromacion(data,"pajarito");
      //test.borrar("pajarito");
        System.out.println("El valor es "+ test.buscar("pajaritsoao"));
        System.out.println("la imagen es "+test.obtenerImagen("perrito"));
        File[] contents = test.Disco1.listFiles();
       // System.out.println(contents[1]);
        String container = "aBcDeFg";
        String content = "dE";
        test.borrar("perrito");
//        System.out.println("estsos son los archivos del disco 1 " +Arrays.toString(contents));
//        String camino =test.Disco1.getAbsolutePath();
//        System.out.println("Este es el camino"+ camino);
//        test.crearArchivo(camino,"ESTA ES LA INFROMACION INTERNA","Prueba");
    }
}
