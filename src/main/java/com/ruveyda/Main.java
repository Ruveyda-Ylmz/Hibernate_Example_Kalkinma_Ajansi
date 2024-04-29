package com.ruveyda;

import com.ruveyda.entity.Interest;
import com.ruveyda.entity.Name;
import com.ruveyda.entity.User;
import com.ruveyda.repository.InterestRepository;
import com.ruveyda.repository.UserRepository;
import com.ruveyda.utility.HibernateUtility;
import com.ruveyda.utility.enums.EGender;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.text.html.parser.Entity;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //1.yöntem
        //Create Read Update Delete
//        Session session = HibernateUtility.getSessionFunctory().openSession(); //Session -> oturum
//        Transaction transaction = session.beginTransaction(); //Transaction -> aktarım

        //2.yöntem
//         EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("CREATE");
//         EntityManager entityManager = entityManagerFactory.createEntityManager();
//         entityManager.getTransaction().begin();

        UserRepository userRepository = new UserRepository();
        InterestRepository interestRepository = new InterestRepository();

        Name name = Name.builder().name("Alperen").surname("İkinci").build();
        User user = User.builder()
                .username("alp")
                .password("12345678")
                .name(name)
                .gender(EGender.MALE)
                // .interests(List.of("boxing","hiking","reading"))
                .build();
        userRepository.save(user);
        Interest interest1 = Interest.builder().content("kitap okumak").user(user).build();
        Interest interest2 = Interest.builder().content("Oyun oynamak").user(user).build();


        //1.yöntemin devamı
//        session.save(user);
//        transaction.commit();
//        session.close();

        interestRepository.save(interest1);
        interestRepository.save(interest2);
        user.getInterests().addAll(List.of(interest1,interest2));
        userRepository.update(user);
        user.getInterests().add(Interest.builder().content("asdf").user(user).build());
        userRepository.update(user);
        //user = userRepository.findById(1L).get();
        //SELECT * FROM tbl_interests where user_id = ?; ? = user.getId()

//        Name name2 = Name.builder().name("ruveyda").surname("yilmaz").build();
//        User user2 = User.builder()
//                .username("alp")
//                .password("12345678")
//                .name(name2)
//                .gender(EGender.MALE)
//                .interests(List.of("boxing","hiking","reading"))
//                .build();
//
//        userRepository.saveAll(List.of(user,user2));

        //2.yöntemin devamı
//        entityManager.persist(user);
//        entityManager.getTransaction().commit();
//        entityManager.close();



    }
}