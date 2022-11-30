package com.example.demo.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
@Entity
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer productId;
	private String productName;
	private double price;
	private String color;
	private String dimenson;
	private String specification;
	private String manufacturar;
	private Integer quantity;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Category catogry;
	
	
	@JsonIgnore
	@ManyToOne(cascade = CascadeType.ALL)
	private Orders order;
}
