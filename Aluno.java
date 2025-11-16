import java.util.Date;       
import java.util.Calendar;   


public class Aluno extends Usuario {

    
    private String matricula;
    private String curso;
    private final int limiteEmprestimo = 3; 

    public Aluno(String id, String nome, String endereco, String matricula, String curso) {
        super(id, nome, endereco); 
        this.matricula = matricula;
        this.curso = curso;
    }
    
    
    @Override 
    public boolean isAptoParaEmprestimo() {
        if (!super.isAptoParaEmprestimo()) {
            return false;
        }
        return this.getItensEmprestados().size() < this.limiteEmprestimo;
    }


    
    @Override
    public Date calculaPrazoDevolucao() {
        Calendar calendario = Calendar.getInstance(); 
        calendario.add(Calendar.DAY_OF_YEAR, 7); 
        return calendario.getTime(); 
    }
    
    
    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }
    
}