public class Metadata {
    private String Nombre;
    private String Autor;
    private int AnoCreacion;
    private float Tamano;
    private String Descripcion;


    public Metadata( String Nombre,String Autor,int AnoCreacion,int Tamano, String Descripcion){
        this.Nombre=Nombre;
        this.Autor=Autor;
        this.AnoCreacion=AnoCreacion;
        this.Tamano=Tamano;
        this.Descripcion=Descripcion;
    }

    public void print(){
        String out="Nombre de la imagen: %s\n" +
                "Autor: %s\n" +
                "Año de creación: %d\n" +
                "Tamaño (KB): %f\n" +
                "Descripción: %s\n" +
                "============================\n";
        out=String.format(out,Nombre, Autor, AnoCreacion, Tamano, Descripcion);
        System.out.println(out);
    }


    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }

    public int getAnoCreacion() {
        return AnoCreacion;
    }

    public void setAnoCreacion(int anoCreacion) {
        AnoCreacion = anoCreacion;
    }

    public float getTamano() {
        return Tamano;
    }

    public void setTamano(int tamano) {
        Tamano = tamano;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }


}
