package com.ruveyda.repository;

import com.ruveyda.entity.Interest;

public class InterestRepository extends MyFactoryRepository<Interest,Long>  {
    public InterestRepository() {
        super(new Interest());
    }
}
