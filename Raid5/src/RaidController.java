import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class RaidController {
    private  List<String> listaId = new ArrayList<String>();
    private  List<String> listaImagenes = new ArrayList<String>();
    private  Raid5 raid5=new Raid5();
    public void write(String imagen,String id){
        listaImagenes.add(imagen);
        listaId.add(id);
        System.out.println("YA SE INSERTO ALGO SIN COMMIT");
        System.out.println("ESTE ES EL ARRAY DE IMAGENES"+Arrays.toString(listaImagenes.toArray()));
        System.out.println("ESTE ES EL ARRAY DE ID"+Arrays.toString(listaId.toArray()));
        // adds 1 at 0 index
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
    public void delete(String idParaEliminar){

        int indiceDeImagenAEliminar=listaId.indexOf(idParaEliminar);
        listaImagenes.set(indiceDeImagenAEliminar,"0");
        System.out.println("YA SE ELIMINAR ALGO SIN COMMIT");
        System.out.println("ESTE ES EL ARRAY DE IMAGENES"+Arrays.toString(listaImagenes.toArray()));
        System.out.println("ESTE ES EL ARRAY DE ID"+Arrays.toString(listaId.toArray()));
    }


public String[] serializacion(String imagen){
        int size=imagen.length();
        String[] Respuesta = new String[4];
        String binario1=imagen.substring(0,size/3);
        String binario2=imagen.substring(size/3,size/3*2);
        String binario3=imagen.substring(size/3*2,size);
        byte[] bloque1=Base64.decode(binario1);
        byte[] bloque2=Base64.decode(binario2);
        byte[] bloque3=Base64.decode(binario3);
        byte[] paridad=calcularParidad(bloque1,bloque2,bloque3);
        String paridadString=Base64.encode(paridad);
        Respuesta[0]=binario1;
        Respuesta[1]=binario2;
        Respuesta[2]=binario3;
        Respuesta[3]=paridadString;
        return Respuesta;

}
    public void commit() throws IOException {
        Iterator<String> idActual = listaId.iterator();
        Iterator<String> imagenActual = listaImagenes.iterator();
        while(idActual.hasNext()){
            String id=idActual.next();
            String imagen=imagenActual.next();
            System.out.println("ESTA ES LA IMAGEN "+imagen+"ESTE ES EL ID"+id);
            if(imagen.equals("0")){
               //this.delete(id);
            }
            else{
                String[] data=serializacion(imagen);
                raid5.GuardarInfromacion(data,id);
                //System.out.println("este es el data"+Arrays.toString(listaImagenes.toArray()));

                //this.write(imagen,data);
            }
        }
    }
    public static byte [] XOR(byte array1[],byte array2[]){
        byte[] array2Lleno=rellenar(array1,array2);
        byte[] array_3 = new byte[array1.length];
        for(int i = 0; i < array1.length; i++)
        {
            array_3[i]= (byte) (array1[i] ^ array2Lleno[i]);
        }
        return array_3;
    }
    //ESTA FUNCION LO QUE HACE ES RECUPERAR UN BYTE ARRAY A PARTIR DE SU PARIDAD Y OTROS DOS ARRAYS
    public static  byte[] recuperacion(byte[] arrayX,byte[]arrayZ,byte paridad[],int sizeDelArrayParaRecuperar){
        byte[] temp=XOR(paridad,arrayZ);
        byte[]arrayRecuperado = XOR(temp,arrayX);
        return Arrays.copyOfRange(arrayRecuperado, 0, sizeDelArrayParaRecuperar);
    }
    //ESTA FUNCION LO QUE HACE ES COMPARAR DOS ARRAY PARA VER SI SON IGUALES
    public static void CompararArray(byte[] bloque1, byte[] bloque2){
        boolean Funciono=true;
        for(int i = 0; i < bloque1.length; i++)
        {
            if(bloque1[i]!=bloque2[i]){
                Funciono=false;
            }
        }
        System.out.println("EL VALOR DE LA PRUEBA ES "+Funciono);

    }
    public static byte[] calcularParidad( byte[] bloque1,byte[] bloque2,byte [] bloque3){
        byte[] temp=XOR(bloque1,bloque2);//AQUI LO QUE HACEMOS ES HACER UN XOR ENTRE LOS DOS PRIMEROS ARRAYS
        byte[] paridad=XOR(temp,bloque3);//CON EL RESULTADO LE HACEMOS UNA XOR AL RESTANTE PARA OBTENER EL QUE NOS FALTA
        return paridad;
    }
    public  static byte[]rellenar( byte[] bloque1,byte[] bloqueaRellenar){
        byte[]arraycompleto = new byte[bloque1.length];
        int sizeInicial=bloqueaRellenar.length;
        //AQUI LO QUE HACEMOS ES RELLENAR EL NUEVO ARRAY CON LOS ELEMENTOS DEL VIEJO
        for(int i = 0; i < sizeInicial; i++)
        {
            arraycompleto[i]= bloqueaRellenar[i];
        }
        //AQUI LO QUE HACEMOS ES PONER LOS CAMPOS RESTANTES CON LOS DEL PRIMER ARRAY
        for (int i=bloque1.length-1;i>=sizeInicial;i--) {
            System.out.println("No son del mismo size");
            arraycompleto[i] = 0 ;
        }
        return arraycompleto;
    }

}
