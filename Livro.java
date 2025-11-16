public class Livro extends ItemDeAcervo {

    
    private String autor;
    private String isbn;
    private int edicao;

    
    public Livro(String codigo, String titulo, int anoPublicacao, String autor, String isbn, int edicao) {
        
        super(codigo, titulo, anoPublicacao);
        
        
        this.autor = autor;
        this.isbn = isbn;
        this.edicao = edicao;
    }

    

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
    }
}