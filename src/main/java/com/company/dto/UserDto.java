package com.company.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserDto {
	
	@Min(0)
	private Long id;
	@NotBlank(message="Imię nie może być puste")
	private String firstName;
	@NotBlank(message="Nazwisko nie może być puste")
	private String lastName;
	@Pattern(regexp="[0-9]{11}",message="Pesel musi się składać z 11 znaków liczbowych")
	private String pesel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPesel() {
		return pesel;
	}

	public void setPesel(String pesel) {
		this.pesel = pesel;
	}

}
