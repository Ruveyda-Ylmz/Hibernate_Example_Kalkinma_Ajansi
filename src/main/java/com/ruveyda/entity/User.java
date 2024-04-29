package com.ruveyda.entity;

import com.ruveyda.utility.enums.EGender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


//lmbk (anatasyon9 -> code+sava as live template
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Name name;

    //unique =true -> özgün değer, tabloda bu değerden sadece bir tane olabilir.
    //nullable = false -> boş bırakılamaz.Oluşturma ve DB ye göndermeden önce mutlaka ama mutlaka içerisine bir değer  yerleştirilmeli.
    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false,length = 32)
    private String password;

    /*
  @Enumerated anotasyonu enum'ların DB tablosunda nasıl görüntüleneceğini belirlemek için kullanılır.
  2 farklı parametre alabilir;
      1- EnumType.ORDINAL -> Enum'ın ordinal değerini(indexini) baz alarak kayıt işlemi gerçekleştirir. (0,1,2,3,4,5...)
      2- EnumType.String -> Enum'ın String değerini baz alarak kayıt işlemi gerçekleştirir. (MALE, FEMALE)
   Eğer @Enumerated anotasyonu ile işaretleme yapmazsak, default değer olarak ORDINAL'i alır.
   */
//    @Enumerated(EnumType.ORDINAL)
    @Enumerated(EnumType.STRING)
    private EGender gender;

//@ElementCollection içerisinde Wrapper class saklayan collection yapılarını
// DB  de ayrı bir tablo olarak tutmak için kullanılır.
//    @ElementCollection //
//    private List<String> interests;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Interest> interests = new ArrayList<>();


}
