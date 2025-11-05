public class Usuario{
    protected int id;
    protected String nome;
    protected String endereço;
    protected String status;

    public Usuario(int id, String nome, String endereço, String status) {
        this.id = id;
        this.nome = nome;
        this.endereço = endereço;
        this.status = status;
    }
}