package com.oopclass.packageApp.repositories;

import com.oopclass.packageApp.models.Package;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PackageRepository extends CrudRepository<Package, Long>, JpaRepository<Package, Long> {
}

