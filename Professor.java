public class Professor extends Usuario{
    private String siape;
    private String departamento;
    private int limiteEmprestimo;
    
    public Professor(int id, String nome, String endereço, String status, String siape, String departamento) {
        super(id, nome, endereço, status);
        this.siape = siape;
        this.departamento = departamento;
        this.limiteEmprestimo = 5;
    }
}
