package com.example;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "programming_language")
@NamedQuery(name = "topProgrammingLanguages", query = "from ProgrammingLanguage pl where pl.rating > :rating")
public class ProgrammingLanguage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pl_id")
	private Integer id;

	@Column(name = "pl_name")
	private String name;

	@Column(name = "pl_rating")
	private Integer rating;

	public ProgrammingLanguage() {
	}

	public ProgrammingLanguage(String name, Integer rating) {
		this.name = name;
		this.rating = rating;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ProgrammingLanguage that = (ProgrammingLanguage) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

}