import java.util.Date;
import java.util.concurrent.TimeUnit; 


public class Emprestimo {

    private String idEmprestimo;
    private Usuario usuario; 
    private ItemDeAcervo item; 
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private double multaCobrada;

    
    public Emprestimo(String idEmprestimo, Usuario usuario, ItemDeAcervo item) {
        this.idEmprestimo = idEmprestimo;
        this.usuario = usuario;
        this.item = item;
        
        this.dataEmprestimo = new Date(); 
        this.dataDevolucaoPrevista = usuario.calculaPrazoDevolucao();
        
        this.dataDevolucaoReal = null; 
        this.multaCobrada = 0.0;
    }

    
    public double calcularMulta(Date dataDevolucaoReal) {
        if (dataDevolucaoReal.after(this.dataDevolucaoPrevista)) {
            
            long diffEmMillis = Math.abs(dataDevolucaoReal.getTime() - this.dataDevolucaoPrevista.getTime());
            
            long diasDeAtraso = TimeUnit.DAYS.convert(diffEmMillis, TimeUnit.MILLISECONDS);
            
            return (double) diasDeAtraso * 1.00;
        } else {
            return 0.0;
        }
    }

    
    public void finalizarEmprestimo(Date dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
        this.multaCobrada = this.calcularMulta(dataDevolucaoReal);
        
        if (this.multaCobrada > 0) {
        }
    }


    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ItemDeAcervo getItem() {
        return item;
    }

    public void setItem(ItemDeAcervo item) {
        this.item = item;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(Date dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public double getMultaCobrada() {
        return multaCobrada;
    }

    public void setMultaCobrada(double multaCobrada) {
        this.multaCobrada = multaCobrada;
    }
}