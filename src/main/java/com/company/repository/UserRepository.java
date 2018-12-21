package com.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public List<User> findAllUserBylastNameContainingIgnoreCase(String lastName);
	
	public Optional<User> findUserBypesel(String pesel);
	
	public List<User> findAllUserBypeselNotLike(String pesel);
}
