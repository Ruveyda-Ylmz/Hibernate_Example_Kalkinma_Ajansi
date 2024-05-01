package com.ruveyda.repository;
import com.ruveyda.entity.User;
import com.ruveyda.utility.HibernateUtility;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//burada ki amaç diğer sınıfların repository'lerini alıp,
// crud methodlarını içeride yazmak yerine bütün sınıfların kalıtım(extend) alacağı parent sınıf oluşturuduk.
/*bu sınıf MyFactoryRepository yinr içerisine bir entity ve id alıyo <T,ID> ,
  ICrud dan da implement alıyo yani crud methodları buray gelecek*/
public class MyFactoryRepository <T,ID> implements ICrud<T,ID>{
    private Session session;
    private Transaction transaction; //dosya aktarım veri işlemleri için

    //EntityManager ve CriteriaBuilder de custom sorgularımı yönetirken ve entity işlmelri için kullanılacak.
    EntityManager entityManager;
    CriteriaBuilder criteriaBuilder;
    T t; //T = entity

    //MyFactoryRepository in bir nesneis oluşturulduğunda içeriye (T entity)alsın demek.
    //yani User verirsem User olarak çalışır,Interest verirsem Interest ı-olarak çalışsın.
    public MyFactoryRepository(T entity){ //burada entity parametredir.
        this.t = entity;
        this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        this.criteriaBuilder = entityManager.getCriteriaBuilder(); //sorgu kriterleri
    }

    private void openSession(){ //veri göndermek için sessionu/transaction'u açmak lazım
        session = HibernateUtility.getSessionFactory().openSession();
        transaction = session.beginTransaction();
    }
    private void closeSession(){ //açılan sessionu/transaction'u kapatmak için
        transaction.commit();
        session.close();
    }

    //crud işlemlerinde save,update.. gibi işlemlerde entity genelde geri gönülür.
    @Override
    public T save(T entity) {
        openSession();
        session.save(entity);
        closeSession();
        return entity;
    }

    @Override
    public T update(T entity) {
        openSession();
        session.update(entity);
        closeSession();
        return entity;
    }

    @Override
    public void delete(T entity) {
        openSession();
        session.delete(entity);
        closeSession();
    }

    @Override
    public void deleteByID(ID id) { //burada kriterlerin hangi tablo üzerinden oluşacağını belirliyoruz.
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass()); //kök dizini hangi source entity ile eşleme işlemini gerçekleştirmeyi belirtir
        criteria.select(root); //root ve criteria yı birbirine bağlar
        criteria.where(criteriaBuilder.equal(root.get("id"),id));
        T result = entityManager.createQuery(criteria).getSingleResult();
        openSession();
        session.delete(result);
        closeSession();
    }

    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        openSession();
        entities.forEach(t -> {
            session.save(t);
        });
        closeSession();
        return entities;
    }

    @Override
    public Optional<T> findById(ID id) {
        T result = null;
        try {
            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T> root  = (Root<T>) criteria.from(User.class);
            criteria.select(root);
            criteria.where(criteriaBuilder.equal(root.get("id"),id));
            result = entityManager.createQuery(criteria).getSingleResult();

        } catch (Exception e){

        } finally {
            return Optional.ofNullable(result);
        }

    }

    @Override
    public Boolean existsById(ID id) {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root  = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get("id"),id));
        T result = entityManager.createQuery(criteria).getSingleResult();
        return result!=null;
    }

    @Override
    public List<T> findAll() {
        CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<T> findByColumnAndValue(String columnName, Objects value) {
        return null;
    }

    @Override
    public List<T> findByColumnNameAndValue(String columnName, Object value) {
        CriteriaQuery<T>criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T>root = (Root<T>) criteria.from(t.getClass());
        criteria.select(root);
        criteria.where(criteriaBuilder.equal(root.get(columnName),value));
        criteria.where(criteriaBuilder.like(root.get(columnName),"%"+value+"%"));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<T> findAllEntity(T entity) {
        List<T>result=null;
        Class cl=entity.getClass();
        Field[] fl=cl.getDeclaredFields();
        try {
            CriteriaQuery<T>criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T>root = (Root<T>) criteria.from(t.getClass());
            criteria.select(root);
            List<Predicate>predicateList=new ArrayList<>();
            for(int i=0;i<fl.length;i++){
                fl[i].setAccessible(true);

                if(fl[i].get(entity.getClass())!=null && !fl[i].get(entity).equals("id")){

                    if(fl[i].getType().isAssignableFrom(String.class)){
                        predicateList.add(criteriaBuilder.like(root.get(fl[i].getName()),"%"+fl[i].get(entity)+"%")) ;
                    }if(fl[i].getType().isAssignableFrom(Number.class)){
                        predicateList.add(criteriaBuilder.equal(root.get(fl[i].getName()),fl[i].get(entity)));
                    }
                }
            }

            criteria.where(predicateList.toArray(new Predicate[]{}));
            result=entityManager.createQuery(criteria).getResultList();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
//        private Session session;
//        private Transaction transaction;
//        EntityManager entityManager;
//        CriteriaBuilder criteriaBuilder;
//        T t;
//        public MyFactoryRepository(T entity){
//            this.t = entity;
//            this.entityManager = HibernateUtility.getSessionFactory().createEntityManager();
//            this.criteriaBuilder = entityManager.getCriteriaBuilder();
//        }
//
//        private void openSession(){
//            session = HibernateUtility.getSessionFactory().openSession();
//            transaction = session.beginTransaction();
//        }
//        private void closeSession(){
//            transaction.commit();
//            session.close();
//        }
//
//        @Override
//        public T save(T entity) {
//            openSession();
//            session.save(entity);
//            closeSession();
//            return entity;
//        }
//
//        @Override
//        public T update(T entity) {
//            openSession();
//            session.update(entity);
//            closeSession();
//            return entity;
//        }
//
//        @Override
//        public void delete(T entity) {
//            openSession();
//            session.delete(entity);
//            closeSession();
//        }
//
//        @Override
//        public void deleteByID(ID id) {
//            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
//            Root<T> root = (Root<T>) criteria.from(t.getClass());
//            criteria.select(root);
//            criteria.where(criteriaBuilder.equal(root.get("id"),id));
//            T result = entityManager.createQuery(criteria).getSingleResult();
//            openSession();
//            session.delete(result);
//            closeSession();
//        }
//
//        @Override
//        public Iterable<T> saveAll(Iterable<T> entities) {
//            openSession();
//            entities.forEach(t -> {
//                session.save(t);
//            });
//            closeSession();
//            return entities;
//        }
//
//        @Override
//        public Optional<T> findById(ID id) {
//            T result = null;
//            try {
//                CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
//                Root<T> root  = (Root<T>) criteria.from(User.class);
//                criteria.select(root);
//                criteria.where(criteriaBuilder.equal(root.get("id"),id));
//                result = entityManager.createQuery(criteria).getSingleResult();
//
//            } catch (Exception e){
//
//            } finally {
//                return Optional.ofNullable(result);
//            }
//
//        }
//
//        @Override
//        public Boolean existsById(ID id) {
//            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
//            Root<T> root  = (Root<T>) criteria.from(t.getClass());
//            criteria.select(root);
//            criteria.where(criteriaBuilder.equal(root.get("id"),id));
//            T result = entityManager.createQuery(criteria).getSingleResult();
//            return result!=null;
//        }
//
//        @Override
//        public List<T> findAll() {
//            CriteriaQuery<T> criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
//            Root<T> root = (Root<T>) criteria.from(t.getClass());
//            criteria.select(root);
//            return entityManager.createQuery(criteria).getResultList();
//        }
//
//        @Override
//        public List<T> findByColumnNameAndValue(String columnName, Object value) {
//            CriteriaQuery<T>criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
//            Root<T>root = (Root<T>) criteria.from(t.getClass());
//            criteria.select(root);
//            criteria.where(criteriaBuilder.equal(root.get(columnName),value));
//            criteria.where(criteriaBuilder.like(root.get(columnName),"%"+value+"%"));
//            return entityManager.createQuery(criteria).getResultList();
//        }
//
//        @Override
//        public List<T> findAllEntity(T entity) {
//            List<T>result=null;
//            Class cl=entity.getClass();
//            Field[] fl=cl.getDeclaredFields();
//            try {
//                CriteriaQuery<T>criteria = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
//                Root<T>root = (Root<T>) criteria.from(t.getClass());
//                criteria.select(root);
//                List<Predicate>predicateList=new ArrayList<>();
//                for(int i=0;i<fl.length;i++){
//                    fl[i].setAccessible(true);
//
//                    if(fl[i].get(entity.getClass())!=null && !fl[i].get(entity).equals("id")){
//
//                        if(fl[i].getType().isAssignableFrom(String.class)){
//                            predicateList.add(criteriaBuilder.like(root.get(fl[i].getName()),"%"+fl[i].get(entity)+"%")) ;
//                        }if(fl[i].getType().isAssignableFrom(Number.class)){
//                            predicateList.add(criteriaBuilder.equal(root.get(fl[i].getName()),fl[i].get(entity)));
//                        }
//                    }
//                }
//
//                criteria.where(predicateList.toArray(new Predicate[]{}));
//                result=entityManager.createQuery(criteria).getResultList();
//            }catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//            return result;
//        }




