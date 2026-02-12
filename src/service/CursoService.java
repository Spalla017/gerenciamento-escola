//Alunos: Vinicius e Victor Hugo

package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import modelo.Aluno;
import modelo.Curso;

@Stateless
public class CursoService extends GenericService<Curso> {

	public CursoService() {
		super(Curso.class);
	}
	
	// Listar cursos ordenados por área (crescente)
	public List<Curso> listarOrdenadoPorArea() {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Curso> cQuery = cb.createQuery(Curso.class);
		
		final Root<Curso> rootCurso = cQuery.from(Curso.class);
		
		cQuery.select(rootCurso);
		cQuery.orderBy(cb.asc(rootCurso.get("area")));
		
		return getEntityManager().createQuery(cQuery).getResultList();
	}
	
	// Filtrar curso por nome (like) ordenado por área
	public List<Curso> filtrarPorNome(String nome) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Curso> cQuery = cb.createQuery(Curso.class);
		
		final Root<Curso> rootCurso = cQuery.from(Curso.class);
		
		Expression<String> expNome = rootCurso.get("nome");
		
		cQuery.select(rootCurso);
		cQuery.where(cb.like(cb.lower(expNome), "%" + nome.toLowerCase() + "%"));
		cQuery.orderBy(cb.asc(rootCurso.get("area")));
		
		return getEntityManager().createQuery(cQuery).getResultList();
	}
	
	// Verificar se curso está vinculado a algum aluno
	public boolean cursoTemAlunos(Long idCurso) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Long> cQuery = cb.createQuery(Long.class);
		
		final Root<Aluno> rootAluno = cQuery.from(Aluno.class);
		
		cQuery.select(cb.count(rootAluno));
		cQuery.where(cb.equal(rootAluno.get("curso").get("id"), idCurso));
		
		Long count = getEntityManager().createQuery(cQuery).getSingleResult();
		
		return count > 0;
	}
}