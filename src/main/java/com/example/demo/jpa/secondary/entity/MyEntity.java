package com.example.demo.jpa.secondary.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MyEntity {
	@Id
	private Long id;
}
