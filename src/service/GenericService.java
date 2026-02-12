//Alunos: Vinicius e Victor Hugo

package service;

import java.util.List;

import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

@TransactionManagement(TransactionManagementType.CONTAINER)
public abstract class GenericService<T> {
	
	@PersistenceContext(unitName="punit")
    private EntityManager entityManager;
	
	private final Class<T> classe;

	public GenericService(Class<T> classe) {
		this.classe = classe;        
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void create(T entity) {
        getEntityManager().persist(entity); 
    }

    public void merge(T entity) {
		entity = getEntityManager().merge(entity);			
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));		  
    }
    
    public final T obtemPorId(Long id) {
		T entity = getEntityManager().find(classe, id);		
		return entity;
	}
	
    public List<T> listAll() {
    	final CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();	
    	final CriteriaQuery<T> cQuery = cb.createQuery(classe);
	
    	cQuery.select(cQuery.from(classe));

    	List<T> list = getEntityManager().createQuery(cQuery).getResultList();
    	return list;
    }	
}