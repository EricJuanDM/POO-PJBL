public class Revista extends ItemDeAcervo{
    private String issn;
    private int volume;
    private String editora;

    public Revista(String codigo, String titulo, int anoPublicacao, String issn, int volume, String editora) {
        super(codigo, titulo, anoPublicacao);
        this.issn = issn;
        this.volume = volume;
        this.editora = editora;
    }
    
}
