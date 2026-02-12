//Alunos: Vinicius e Victor Hugo

package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import modelo.Aluno;

@Stateless
public class AlunoService extends GenericService<Aluno> {

	public AlunoService() {
		super(Aluno.class);
	}
	
	// Listar alunos ordenados por nome
	public List<Aluno> listarOrdenadoPorNome() {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Aluno> cQuery = cb.createQuery(Aluno.class);
		
		final Root<Aluno> rootAluno = cQuery.from(Aluno.class);
		
		cQuery.select(rootAluno);
		cQuery.orderBy(cb.asc(rootAluno.get("nome")));
		
		return getEntityManager().createQuery(cQuery).getResultList();
	}
	
	// Filtrar alunos para relatório (combinado)
	public List<Aluno> filtrarAlunos(Long idCurso, String maioridade, String cidade) {
		final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Aluno> cQuery = cb.createQuery(Aluno.class);
		
		final Root<Aluno> rootAluno = cQuery.from(Aluno.class);
		
		List<Predicate> predicates = new ArrayList<>();
		
		// Filtro por curso (se não for "Todos")
		if (idCurso != null && idCurso > 0) {
			predicates.add(cb.equal(rootAluno.get("curso").get("id"), idCurso));
		}
		
		// Filtro por maioridade
		if (maioridade != null && !maioridade.isEmpty() && !maioridade.equals("TODOS")) {
			if (maioridade.equals("MAIOR")) {
				predicates.add(cb.ge(rootAluno.get("idade"), 18));
			} else if (maioridade.equals("MENOR")) {
				predicates.add(cb.lt(rootAluno.get("idade"), 18));
			}
		}
		
		// Filtro por cidade (like)
		if (cidade != null && !cidade.trim().isEmpty()) {
			predicates.add(cb.like(cb.lower(rootAluno.get("endereco").get("cidade")), 
					"%" + cidade.toLowerCase() + "%"));
		}
		
		cQuery.select(rootAluno);
		
		if (!predicates.isEmpty()) {
			cQuery.where(cb.and(predicates.toArray(new Predicate[0])));
		}
		
		cQuery.orderBy(cb.asc(rootAluno.get("nome")));
		
		return getEntityManager().createQuery(cQuery).getResultList();
	}
}