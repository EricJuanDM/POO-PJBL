// Ficheiro: SistemaBiblioteca.java

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class SistemaBiblioteca {

    private List<Usuario> listaUsuarios;
    private List<ItemDeAcervo> acervo;
    private List<Emprestimo> historicoEmprestimos;

    private static final String ARQUIVO_USUARIOS = "usuarios.csv";
    private static final String ARQUIVO_ACERVO = "acervo.csv";
    private static final String ARQUIVO_EMPRESTIMOS = "emprestimos.csv";
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");

    public SistemaBiblioteca() {
        this.listaUsuarios = new ArrayList<>();
        this.acervo = new ArrayList<>();
        this.historicoEmprestimos = new ArrayList<>();
    }

    public void adicionarUsuario(Usuario usuario) {
        this.listaUsuarios.add(usuario);
        System.out.println("Usuário " + usuario.getNome() + " cadastrado com sucesso.");
    }

    public void adicionarItem(ItemDeAcervo item) {
        this.acervo.add(item);
        System.out.println("Item '" + item.getTitulo() + "' cadastrado com sucesso.");
    }
    
    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario u : this.listaUsuarios) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    public ItemDeAcervo buscarItemPorCodigo(String codigo) {
        for (ItemDeAcervo item : this.acervo) {
            if (item.getCodigo().equals(codigo)) {
                return item;
            }
        }
        return null;
    }

    public Emprestimo buscarEmprestimoPorId(String idEmprestimo) {
        for (Emprestimo e : this.historicoEmprestimos) {
            if (e.getIdEmprestimo().equals(idEmprestimo) && e.getDataDevolucaoReal() == null) {
                return e;
            }
        }
        return null;
    }
    
    public List<ItemDeAcervo> buscarItensPorTitulo(String titulo) {
        List<ItemDeAcervo> itensEncontrados = new ArrayList<>();
        
        for (ItemDeAcervo item : this.acervo) {
            if (item.getTitulo().toLowerCase().contains(titulo.toLowerCase())) {
                itensEncontrados.add(item);
            }
        }
        return itensEncontrados;
    }

    public ItemDeAcervo buscarItemPorIsbnIssn(String idUnico) {
        for (ItemDeAcervo item : this.acervo) {
            
            if (item instanceof Livro) {
                Livro livro = (Livro) item; 
                if (livro.getIsbn().equals(idUnico)) {
                    return livro;
                }
            } 
            else if (item instanceof Revista) {
                Revista revista = (Revista) item;
                if (revista.getIssn().equals(idUnico)) {
                    return revista;
                }
            }
        }
        return null;
    }

    public Emprestimo realizarEmprestimo(String idUsuario, String codItem) throws EmprestimoException {
        
        Usuario usuario = buscarUsuarioPorId(idUsuario);
        if (usuario == null) {
            throw new EmprestimoException("Usuário não encontrado.");
        }
        
        ItemDeAcervo item = buscarItemPorCodigo(codItem);
        if (item == null) {
            throw new EmprestimoException("Item não encontrado no acervo.");
        }

        if (!usuario.isAptoParaEmprestimo()) {
            throw new EmprestimoException("Usuário NÃO APTO para empréstimo (RN2 ou RN4). Limite excedido ou bloqueado.");
        }
        
        if (item.isEmprestado()) {
            throw new EmprestimoException("Item indisponível (RN1). Já está emprestado.");
        }
        
        item.emprestar();

        String idEmprestimo = "E" + (this.historicoEmprestimos.size() + 1);
        Emprestimo novoEmprestimo = new Emprestimo(idEmprestimo, usuario, item);
        
        this.historicoEmprestimos.add(novoEmprestimo);
        usuario.adicionarEmprestimo(novoEmprestimo);
        
        System.out.println("Empréstimo (ID: " + idEmprestimo + ") realizado com sucesso!");
        return novoEmprestimo;
    }

    
    public void realizarDevolucao(String idEmprestimo) throws EmprestimoException {
        
        Emprestimo emprestimo = buscarEmprestimoPorId(idEmprestimo);
        
        if (emprestimo == null) {
            throw new EmprestimoException("Empréstimo (ID: " + idEmprestimo + ") não encontrado ou já finalizado.");
        }
        
        ItemDeAcervo item = emprestimo.getItem();
        Usuario usuario = emprestimo.getUsuario();

        item.devolver();
        
        Date dataHoje = new Date();
        emprestimo.finalizarEmprestimo(dataHoje);
        
        usuario.removerEmprestimo(emprestimo);

        double multa = emprestimo.getMultaCobrada();
        
        System.out.println("Devolução (ID: " + idEmprestimo + ") realizada.");
        
        if (multa > 0) {
            usuario.setStatus("Bloqueado");
            System.out.println("Multa gerada: R$ " + multa + ". Usuário " + usuario.getNome() + " bloqueado (RN4).");
        } else {
            System.out.println("Item devolvido no prazo. Nenhuma multa gerada.");
        }
    }

    public void salvarDados() {
        try {
            salvarUsuarios();
            salvarAcervo();
            salvarEmprestimos();
            System.out.println("Dados salvos com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private void salvarUsuarios() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_USUARIOS))) {
            
            for (Usuario u : this.listaUsuarios) {
                if (u instanceof Aluno) {
                    Aluno a = (Aluno) u;
                    writer.write("ALUNO,");
                    writer.write(a.getId() + "," + a.getNome() + "," + a.getEndereco() + "," + a.getStatus() + ",");
                    writer.write(a.getMatricula() + "," + a.getCurso());
                } else if (u instanceof Professor) {
                    Professor p = (Professor) u;
                    writer.write("PROFESSOR,");
                    writer.write(p.getId() + "," + p.getNome() + "," + p.getEndereco() + "," + p.getStatus() + ",");
                    writer.write(p.getSiape() + "," + p.getDepartamento());
                }
                writer.newLine();
            }
        }
    }

    private void salvarAcervo() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ACERVO))) {
            for (ItemDeAcervo item : this.acervo) {
                if (item instanceof Livro) {
                    Livro l = (Livro) item;
                    writer.write("LIVRO,");
                    writer.write(l.getCodigo() + "," + l.getTitulo() + "," + l.getAnoPublicacao() + "," + l.isEmprestado() + ",");
                    writer.write(l.getAutor() + "," + l.getIsbn() + "," + l.getEdicao());
                } else if (item instanceof Revista) {
                    Revista r = (Revista) item;
                    writer.write("REVISTA,");
                    writer.write(r.getCodigo() + "," + r.getTitulo() + "," + r.getAnoPublicacao() + "," + r.isEmprestado() + ",");
                    writer.write(r.getEditora() + "," + r.getVolume() + "," + r.getIssn());
                }
                writer.newLine();
            }
        }
    }

    private void salvarEmprestimos() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_EMPRESTIMOS))) {
            for (Emprestimo e : this.historicoEmprestimos) {
                writer.write(e.getIdEmprestimo() + ",");
                writer.write(e.getUsuario().getId() + ",");
                writer.write(e.getItem().getCodigo() + ",");
                writer.write(DATE_FORMAT.format(e.getDataEmprestimo()) + ",");
                writer.write(DATE_FORMAT.format(e.getDataDevolucaoPrevista()) + ",");
                
                String dataRealStr = (e.getDataDevolucaoReal() != null) ? DATE_FORMAT.format(e.getDataDevolucaoReal()) : "null";
                writer.write(dataRealStr + ",");
                
                writer.write(String.valueOf(e.getMultaCobrada()));
                writer.newLine();
            }
        }
    }

    public void carregarDados() {
        try {
            this.listaUsuarios.clear();
            this.acervo.clear();
            this.historicoEmprestimos.clear();
            
            carregarUsuarios();
            carregarAcervo();
            carregarEmprestimos();
            
            System.out.println("Dados carregados com sucesso!");

        } catch (IOException | ParseException | EmprestimoException e) {
            System.err.println("Aviso: Não foi possível carregar dados salvos. Iniciando sistema vazio. Erro: " + e.getMessage());
            this.listaUsuarios.clear();
            this.acervo.clear();
            this.historicoEmprestimos.clear();
        }
    }
    
    private void carregarUsuarios() throws IOException {
        String linha;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_USUARIOS))) {
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String tipo = dados[0];
                
                if (tipo.equals("ALUNO")) {
                    Aluno a = new Aluno(dados[1], dados[2], dados[3], dados[5], dados[6]);
                    a.setStatus(dados[4]);
                    this.listaUsuarios.add(a);
                } else if (tipo.equals("PROFESSOR")) {
                    Professor p = new Professor(dados[1], dados[2], dados[3], dados[5], dados[6]);
                    p.setStatus(dados[4]);
                    this.listaUsuarios.add(p);
                }
            }
        }
    }

    private void carregarAcervo() throws IOException {
        String linha;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_ACERVO))) {
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String tipo = dados[0];
                
                ItemDeAcervo item = null;
                if (tipo.equals("LIVRO")) {
                    item = new Livro(dados[1], dados[2], Integer.parseInt(dados[3]), dados[5], dados[6], Integer.parseInt(dados[7]));
                } else if (tipo.equals("REVISTA")) {
                    item = new Revista(dados[1], dados[2], Integer.parseInt(dados[3]), dados[5], Integer.parseInt(dados[6]), dados[7]);
                }
                
                if (item != null) {
                    if (Boolean.parseBoolean(dados[4])) {
                        item.emprestar();
                    }
                    this.acervo.add(item);
                }
            }
        }
    }

    private void carregarEmprestimos() throws IOException, ParseException, EmprestimoException {
        String linha;
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_EMPRESTIMOS))) {
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                
                Usuario usuario = buscarUsuarioPorId(dados[1]);
                ItemDeAcervo item = buscarItemPorCodigo(dados[2]);
                
                if (usuario == null || item == null) {
                    throw new EmprestimoException("Erro ao carregar empréstimo: Usuário ou Item não encontrado.");
                }
                
                Emprestimo emprestimo = new Emprestimo(dados[0], usuario, item);
                
                emprestimo.setDataEmprestimo(DATE_FORMAT.parse(dados[3]));
                emprestimo.setDataDevolucaoPrevista(DATE_FORMAT.parse(dados[4]));
                
                if (!dados[5].equals("null")) {
                    emprestimo.setDataDevolucaoReal(DATE_FORMAT.parse(dados[5]));
                } else {
                    emprestimo.setDataDevolucaoReal(null);
                    usuario.adicionarEmprestimo(emprestimo);
                }
                
                emprestimo.setMultaCobrada(Double.parseDouble(dados[6]));
                
                this.historicoEmprestimos.add(emprestimo);
            }
        }
    }


    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public List<ItemDeAcervo> getAcervo() {
        return acervo;
    }

    public List<Emprestimo> getHistoricoEmprestimos() {
        return historicoEmprestimos;
    }
}