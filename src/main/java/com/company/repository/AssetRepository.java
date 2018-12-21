package com.company.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.company.model.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long>{
	
	public List<Asset> findAllAssetBynameContainingIgnoreCase(String name);

	@Query("select a from Asset a where lower(a.name) like lower(concat('%', :search, '%')) " +
            "or lower(a.serialNumber) like lower(concat('%', :search, '%'))")
	public List<Asset> findAllAssetByNameOrSerialNumber(@Param("search") String search);
	
	public Optional<Asset> findOneAssetBySerialNumberIgnoreCase(String serialNumber);
}
