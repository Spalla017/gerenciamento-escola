//Alunos: Vinicius e Victor Hugo

package controle;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Aluno;
import modelo.Curso;
import service.AlunoService;
import service.CursoService;

@ViewScoped
@ManagedBean
public class RelatorioBean {
	
	@EJB
	private AlunoService alunoService;
	
	@EJB
	private CursoService cursoService;
	
	private List<Aluno> alunos = new ArrayList<>();
	private List<Curso> cursos = new ArrayList<>();
	
	private Long idCursoFiltro = 0L;
	private String maioridadeFiltro = "TODOS";
	private String cidadeFiltro = "";
	
	@PostConstruct
	public void init() {
		carregarCursos();
	}
	
	public void carregarCursos() {
		cursos = cursoService.listarOrdenadoPorArea();
	}
	
	public void filtrar() {
		// Preparar parâmetros
		Long idCurso = (idCursoFiltro != null && idCursoFiltro > 0) ? idCursoFiltro : null;
		String maioridade = (maioridadeFiltro != null && !maioridadeFiltro.equals("TODOS")) 
				? maioridadeFiltro : null;
		String cidade = (cidadeFiltro != null && !cidadeFiltro.trim().isEmpty()) 
				? cidadeFiltro : null;
		
		// Buscar alunos com filtros combinados
		alunos = alunoService.filtrarAlunos(idCurso, maioridade, cidade);
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

	public Long getIdCursoFiltro() {
		return idCursoFiltro;
	}

	public void setIdCursoFiltro(Long idCursoFiltro) {
		this.idCursoFiltro = idCursoFiltro;
	}

	public String getMaioridadeFiltro() {
		return maioridadeFiltro;
	}

	public void setMaioridadeFiltro(String maioridadeFiltro) {
		this.maioridadeFiltro = maioridadeFiltro;
	}

	public String getCidadeFiltro() {
		return cidadeFiltro;
	}

	public void setCidadeFiltro(String cidadeFiltro) {
		this.cidadeFiltro = cidadeFiltro;
	}
}