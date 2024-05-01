package com.ruveyda.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "tbl_interest")
@Entity
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    // @ManyToOne //many interests can be owned by one user. -> birçok ilgi alanı tek bir user'a ait olabilir.
//    @ManyToOne(fetch = FetchType.LAZY)
//    private User user;

    private Long userId; //bağlantıyı elle kuruyorum yukarıda ki kod gereksiz olmuş oluyo.micro service yazarken bu yapı kullanılmalı

}

