public class Aluno extends Usuario{
    private int Matricula;
    private String Curso;
    private int limiteEmprestimo;

    public Aluno(int id, String nome, String endereço, String status, int Matricula, String Curso, int limiteEmprestimo) {
        super(id, nome, endereço, status);
        this.Matricula = Matricula;
        this.Curso = Curso;
        this.limiteEmprestimo = 3;
    }
    
}
