package com.company.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.company.model.Assignment;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long>{

	public Optional<Assignment> findFirstAssignmentByAsset_idAndEndIsNull(Long assetId);
}
