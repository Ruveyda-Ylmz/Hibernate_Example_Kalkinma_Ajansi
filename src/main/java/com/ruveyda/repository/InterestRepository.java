package com.ruveyda.repository;

import com.ruveyda.entity.Interest;

//aldığı kalırımda <User,ID> yerine <Interest,Long> baz alarak işlemi gerçekleştir diyoruz.
public class InterestRepository extends MyFactoryRepository<Interest,Long>  {
    public InterestRepository() {

        super(new Interest()); //burada ki User T nin yerine geçiyo.tüm crud işlemi interest de çalışır hale geliyo
    }
}
