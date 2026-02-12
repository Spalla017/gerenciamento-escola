//Alunos: Vinicius e Victor Hugo

package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Aluno;
import modelo.Curso;
import modelo.Endereco;
import service.AlunoService;
import service.CursoService;

@ViewScoped
@ManagedBean
public class AlunoBean {
	
	@EJB
	private AlunoService alunoService;
	
	@EJB
	private CursoService cursoService;
	
	private Aluno aluno = new Aluno();
	private Endereco endereco = new Endereco();
	private List<Aluno> alunos = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();
	private boolean editando = false;
	private Long idCursoSelecionado;
	private String cpfOriginal;
	
	@PostConstruct
	public void init() {
		listar();
		carregarCursos();
		aluno.setEndereco(endereco);
	}
	
	public void listar() {
		alunos = alunoService.listarOrdenadoPorNome();
		limpar();
	}
	
	public void carregarCursos() {
		cursos = cursoService.listarOrdenadoPorArea();
	}
	
	public void gravar() {
		if (validarCampos()) {
			// Associar curso ao aluno
			if (idCursoSelecionado != null && idCursoSelecionado > 0) {
				Curso curso = cursoService.obtemPorId(idCursoSelecionado);
				aluno.setCurso(curso);
			}
			
			// Associar endereço ao aluno
			aluno.setEndereco(endereco);
			
			alunoService.create(aluno);
			addMessage("Aluno cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
			listar();
		}
	}
	
	public void carregar(Aluno alunoSelecionado) {
		this.aluno = alunoSelecionado;
		this.endereco = alunoSelecionado.getEndereco();
		this.editando = true;
		this.cpfOriginal = alunoSelecionado.getCpf();
		
		if (aluno.getCurso() != null) {
			this.idCursoSelecionado = aluno.getCurso().getId();
		}
	}
	
	public void atualizar() {
		// Validar que CPF não foi alterado
		if (!aluno.getCpf().equals(cpfOriginal)) {
			addMessage("O CPF não pode ser alterado!", FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		if (validarCampos()) {
			// Associar curso ao aluno
			if (idCursoSelecionado != null && idCursoSelecionado > 0) {
				Curso curso = cursoService.obtemPorId(idCursoSelecionado);
				aluno.setCurso(curso);
			}
			
			// Atualizar endereço
			aluno.setEndereco(endereco);
			
			alunoService.merge(aluno);
			addMessage("Aluno atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
			listar();
		}
	}
	
	public void excluir(Aluno alunoSelecionado) {
		alunoService.remove(alunoSelecionado);
		addMessage("Aluno excluído com sucesso!", FacesMessage.SEVERITY_INFO);
		listar();
	}
	
	private boolean validarCampos() {
		if (aluno.getNome() == null || aluno.getNome().trim().isEmpty()) {
			addMessage("O campo Nome é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (aluno.getIdade() == null) {
			addMessage("O campo Idade é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (aluno.getCpf() == null || aluno.getCpf().trim().isEmpty()) {
			addMessage("O campo CPF é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (aluno.getCpf().length() != 11) {
			addMessage("O CPF deve conter exatamente 11 dígitos!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (!aluno.getCpf().matches("\\d{11}")) {
			addMessage("O CPF deve conter apenas números!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (aluno.getEmail() == null || aluno.getEmail().trim().isEmpty()) {
			addMessage("O campo Email é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (idCursoSelecionado == null || idCursoSelecionado == 0) {
			addMessage("O campo Curso é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		// Validar endereço
		if (endereco.getRua() == null || endereco.getRua().trim().isEmpty()) {
			addMessage("O campo Rua é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (endereco.getNumero() == null) {
			addMessage("O campo Número é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (endereco.getBairro() == null || endereco.getBairro().trim().isEmpty()) {
			addMessage("O campo Bairro é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (endereco.getCidade() == null || endereco.getCidade().trim().isEmpty()) {
			addMessage("O campo Cidade é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		
		return true;
	}
	
	private void limpar() {
		aluno = new Aluno();
		endereco = new Endereco();
		aluno.setEndereco(endereco);
		editando = false;
		idCursoSelecionado = null;
		cpfOriginal = null;
	}
	
	private void addMessage(String msg, FacesMessage.Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(severity, msg, null));
	}
	
	// Método auxiliar para formatar CPF
	public String formatarCpf(String cpf) {
		if (cpf != null && cpf.length() == 11) {
			return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
				   cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
		}
		return cpf;
	}

	// Getters e Setters
	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public boolean isEditando() {
		return editando;
	}

	public void setEditando(boolean editando) {
		this.editando = editando;
	}

	public Long getIdCursoSelecionado() {
		return idCursoSelecionado;
	}

	public void setIdCursoSelecionado(Long idCursoSelecionado) {
		this.idCursoSelecionado = idCursoSelecionado;
	}
}