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

import modelo.Curso;
import service.CursoService;

@ViewScoped
@ManagedBean
public class CursoBean {
	
	@EJB
	private CursoService cursoService;
	
	private Curso curso = new Curso();
	private List<Curso> cursos = new ArrayList<>();
	private boolean editando = false;
	private String filtroNome = "";
	
	@PostConstruct
	public void init() {
		listar();
	}
	
	public void listar() {
		cursos = cursoService.listarOrdenadoPorArea();
		limpar();
	}
	
	public void gravar() {
		if (validarCampos()) {
			cursoService.create(curso);
			addMessage("Curso cadastrado com sucesso!", FacesMessage.SEVERITY_INFO);
			listar();
		}
	}
	
	public void carregar(Curso cursoSelecionado) {
		this.curso = cursoSelecionado;
		this.editando = true;
	}
	
	public void atualizar() {
		if (validarCampos()) {
			cursoService.merge(curso);
			addMessage("Curso atualizado com sucesso!", FacesMessage.SEVERITY_INFO);
			listar();
		}
	}
	
	public void excluir(Curso cursoSelecionado) {
		// Verificar se curso tem alunos vinculados
		if (cursoService.cursoTemAlunos(cursoSelecionado.getId())) {
			addMessage("Não é possível excluir um curso vinculado a alunos!", 
					FacesMessage.SEVERITY_ERROR);
			return;
		}
		
		cursoService.remove(cursoSelecionado);
		addMessage("Curso excluído com sucesso!", FacesMessage.SEVERITY_INFO);
		listar();
	}
	
	public void filtrar() {
		if (filtroNome == null || filtroNome.trim().isEmpty()) {
			listar();
		} else {
			cursos = cursoService.filtrarPorNome(filtroNome);
			limpar();
		}
	}
	
	private boolean validarCampos() {
		if (curso.getNome() == null || curso.getNome().trim().isEmpty()) {
			addMessage("O campo Nome é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (curso.getArea() == null || curso.getArea().trim().isEmpty()) {
			addMessage("O campo Área é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		if (curso.getDuracao() == null) {
			addMessage("O campo Duração é obrigatório!", FacesMessage.SEVERITY_ERROR);
			return false;
		}
		return true;
	}
	
	private void limpar() {
		curso = new Curso();
		editando = false;
		filtroNome = "";
	}
	
	private void addMessage(String msg, FacesMessage.Severity severity) {
		FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(severity, msg, null));
	}

	// Getters e Setters
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
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

	public String getFiltroNome() {
		return filtroNome;
	}

	public void setFiltroNome(String filtroNome) {
		this.filtroNome = filtroNome;
	}
}