package com.example.demo.jpa.secondary.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.jpa.secondary.entity.MyEntity;

@Repository
public interface MyRepo extends PagingAndSortingRepository<MyEntity, Long> {

}
