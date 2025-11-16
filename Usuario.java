
import java.util.ArrayList;
import java.util.List;
import java.util.Date; 


public abstract class Usuario {

    private String id;
    private String nome;
    private String endereco;
    private String status; 
    private List<Emprestimo> itensEmprestados;
    
    public Usuario(String id, String nome, String endereco) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.status = "Ativo";
        this.itensEmprestados = new ArrayList<>();
    }

    
    
    public void adicionarEmprestimo(Emprestimo emprestimo) {
        this.itensEmprestados.add(emprestimo);
    }
    
    public boolean isAptoParaEmprestimo() {
        if (this.status.equals("Bloqueado")) {
            return false;
        }
        return true; 
    }
    
    public void removerEmprestimo(Emprestimo emprestimo) {
        this.itensEmprestados.remove(emprestimo);
    }

    public abstract Date calculaPrazoDevolucao();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Emprestimo> getItensEmprestados() {
        return itensEmprestados;
    }
}