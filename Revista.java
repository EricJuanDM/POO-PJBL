public class Revista extends ItemDeAcervo {

    
    private String editora;
    private int volume;
    private String issn;

    
    public Revista(String codigo, String titulo, int anoPublicacao, String editora, int volume, String issn) {
        
        super(codigo, titulo, anoPublicacao);
        
        
        this.editora = editora;
        this.volume = volume;
        this.issn = issn;
    }

    

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }
}