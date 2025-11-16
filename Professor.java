import java.util.Date;       
import java.util.Calendar;   

public class Professor extends Usuario {

    private String siape;
    private String departamento;
    private final int limiteEmprestimo = 5; 

    public Professor(String id, String nome, String endereco, String siape, String departamento) {
        super(id, nome, endereco);
        this.siape = siape;
        this.departamento = departamento;
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
        calendario.add(Calendar.DAY_OF_YEAR, 15); 
        return calendario.getTime(); 
    }
    

    public String getSiape() {
        return siape;
    }

    public void setSiape(String siape) {
        this.siape = siape;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getLimiteEmprestimo() {
        return limiteEmprestimo;
    }
    
    
}