package com.ruveyda.repository;

import com.ruveyda.entity.Interest;
import com.ruveyda.entity.User;
import com.ruveyda.utility.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepository extends MyFactoryRepository<User,Long> {
    public UserRepository() {  //Constructor related/based Dependency Injection
        super(new User());
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager(); //bağımlılık
    }

    //NATIVE QUERY (doğal sql de yazdığımız query gibi)
    public List<Interest> findUsersInterests(User user) {
//        List<Interest> interestList = new ArrayList<>();
        String sql = "SELECT i.*\n" +
                "FROM tbl_interest i\n" +
                "JOIN tbl_user u ON i.userid = u.id\n" +
                "WHERE u.id = :userId";
        TypedQuery<Interest> interestTypedQuery = (TypedQuery<Interest>) entityManager.createNativeQuery(sql, Interest.class);
        interestTypedQuery.setParameter("userId", user.getId());
//        interestList = interestTypedQuery.getResultList();
        return interestTypedQuery.getResultList();
    }
}

//burada ÖZGÜN CRUD yazdık ama bu özgün bir durum değildir.bu yüzden MyFactoryRepository oluşturduk.
//public class UserRepository implements ICrud<User,Long> {
//
//    private Session session;
//
//    private Transaction transaction;
//    EntityManager entityManager;
//    CriteriaBuilder criteriaBuilder;
//    public UserRepository(User user){
//        this.entityManager = HibernateUtility.getSessionFunctory().createEntityManager();
//        this.criteriaBuilder = entityManager.getCriteriaBuilder();
//    }
//
//    public UserRepository() {
//
//    }
//
//    private void openSession(){
//        session = HibernateUtility.getSessionFunctory().openSession();
//        transaction = session.beginTransaction();
//    }
//
//    private void closeSession(){
//        transaction.commit();
//        session.close();
//    }
//    @Override
//    public User save(User entity) {
//        openSession();
//        session.save(entity);
//        closeSession();
//        return entity;
//    }
//
//    @Override
//    public User update(User entity) {
//        openSession();
//        session.update(entity);
//        closeSession();
//        return entity;
//    }
//
//    @Override
//    public Iterable<User> saveAll(Iterable<User> entities) {
//        openSession();
//        entities.forEach(t -> {
//            session.save(t);
//        });
//        closeSession();
//        return entities;
//    }
//
//    @Override
//    public void delete(User entity) {
//        openSession();
//        session.delete(entity);
//        closeSession();
//    }
//
//    @Override
//    public void deleteByID(Long id) {
//        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
//        Root<User> root = criteria.from(User.class);
//        criteria.select(root);
//        criteria.where(criteriaBuilder.equal(root.get("id"),id)); //dışarıdan alınan id ile kök dizinde ki id yi karşılaştır
//        User result = entityManager.createQuery(criteria).getSingleResult();
//        openSession();
//        session.delete(result);
//        closeSession();
//    }
//
//    @Override
//    public Optional<User> findById(Long id) {
//        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
//        Root<User> root = criteria.from(User.class);
//        criteria.select(root);
//        criteria.where(criteriaBuilder.equal(root.get("id"),id));
//        User result = entityManager.createQuery(criteria).getSingleResult();
//        return result! =null;
//
//    }
//
//    @Override
//    public Boolean existsById(Long id) {
//        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
//        Root<User> root = criteria.from(User.class);
//        criteria.select(root);
//        return result!=null;
//    }
//
//    @Override
//    public List<User> findAll() {
//        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
//        Root<User> root = criteria.from(User.class);
//        criteria.select(root);
//        return entityManager.createQuery(criteria).getResultList();
//    }
//
//    @Override
//    public List<User> findByColumnAndValue(String columnName, Objects value) {
//        CriteriaQuery<User> criteria = criteriaBuilder.createQuery(User.class);
//        Root<User> root = criteria.from(User.class);
//        criteria.select(root);
//        criteria.where(criteriaBuilder.equal(root.get(columnName),value));
//        criteria.where(criteriaBuilder.like(root.get(columnName),value));
//        return entityManager.createQuery(criteria).getResultList();
//    }
//
//    @Override
//    public List<User> findAllEntity(User entity) {
//        List<User>result=null;
//        Class cl=entity.getClass();
//        Field[] fl=cl.getDeclaredFields();
//        try {
//            CriteriaQuery<User>criteria = (CriteriaQuery<User>) criteriaBuilder.createQuery(User.class);
//            Root<User>root = (Root<User>) criteria.from(User.class));
//            criteria.select(root);
//            List<Predicate>predicateList=new ArrayList<>();
//            for(int i=0;i<fl.length;i++){
//                fl[i].setAccessible(true);
//
//                if(fl[i].get(entity.getClass())!=null && !fl[i].get(entity).equals("id")){
//
//                    if(fl[i].getType().isAssignableFrom(String.class)){
//                        predicateList.add(criteriaBuilder.like(root.get(fl[i].getName()),"%"+fl[i].get(entity)+"%")) ;
//                    }if(fl[i].getType().isAssignableFrom(Number.class)){
//                        predicateList.add(criteriaBuilder.equal(root.get(fl[i].getName()),fl[i].get(entity)));
//                    }
//                }
//            }
//
//            criteria.where(predicateList.toArray(new Predicate[]{}));
//            result=entityManager.createQuery(criteria).getResultList();
//        }catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return result;
//    }
//}
