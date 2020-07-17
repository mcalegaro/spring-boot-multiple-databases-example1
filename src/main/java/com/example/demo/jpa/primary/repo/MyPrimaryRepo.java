package com.example.demo.jpa.primary.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.jpa.primary.entity.MyPrimaryEntity;

@Repository
public interface MyPrimaryRepo extends PagingAndSortingRepository<MyPrimaryEntity, Long> {

}
