package com.ruveyda.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder //Name sınıfı için özleştirme
@NoArgsConstructor
@AllArgsConstructor //constructor oluşturabilmek için
@Embeddable //name in çağrıldığı yerde Name içinde ki fieldları çağrıldığı yere aktar(gömülü)

public class Name {
    //database ile ilişkilendirilmemiş taraf
    private String name;
    private String surname;
}
