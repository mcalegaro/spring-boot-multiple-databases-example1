package com.example.demo.jpa.primary.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class MyPrimaryEntity {
	@Id
	private Long id;
}
