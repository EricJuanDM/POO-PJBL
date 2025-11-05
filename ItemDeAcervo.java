public class ItemDeAcervo {
    protected String codigo;
    protected String titulo;
    protected int anoPublicacao;
    protected boolean isEmprestado;

    public ItemDeAcervo(String codigo, String titulo, int anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.isEmprestado = false;
    }
}
