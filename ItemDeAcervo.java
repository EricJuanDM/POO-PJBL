public abstract class ItemDeAcervo {

    private String codigo;
    private String titulo;
    private int anoPublicacao;
    private boolean isEmprestado;

    public ItemDeAcervo(String codigo, String titulo, int anoPublicacao) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.anoPublicacao = anoPublicacao;
        this.isEmprestado = false; 
    }

    
    public void emprestar() {
        this.isEmprestado = true;
    }

    
    public void devolver() {
        this.isEmprestado = false;
    }

    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }

    public boolean isEmprestado() { 
        return isEmprestado;
    }

    
}