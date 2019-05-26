public class Imagen {
    private Metadata MiMetadata;

    public Metadata getMiMetadata() {
        return MiMetadata;
    }

    public void setMiMetadata(Metadata miMetadata) {
        MiMetadata = miMetadata;
    }

    public Imagen(Metadata M){
        this.MiMetadata=M;
    }

}
