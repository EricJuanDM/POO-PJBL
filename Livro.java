public class Livro extends ItemDeAcervo{
    private String autor;
    private String isbn;
    private int edicao;

    public Livro(String codigo, String titulo, int anoPublicacao, String autor, String isbn, int edicao) {
        super(codigo, titulo, anoPublicacao);
        this.autor = autor;
        this.isbn = isbn;
        this.edicao = edicao;
    }
}
