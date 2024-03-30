package com.parking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.parking.model.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {
	List<User> findAllById(Long id);
	// JPQL query
	@Query(value = "select u from User u where u.userEmail = :email")
	Optional<User> findUserByEmail(@Param("email") String email);



	@Query(value = "select u from User u where u.id = :id")
	Optional<User> findUserById(@Param("id") Long id);

	@Query(value = "select u from User u where u.id = :id")
	User findUserByIdUser(@Param("id") Long id);
	
	@Query(value = "select u from User u where u.userPhoneNumber = :phoneNumber")
	Optional<User> findUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);


	@Transactional
	@Modifying
	@Query("update User u set u.userFullName = ?1,u.userEmail= ?2,u.userPhoneNumber= ?3,u.userPassword= ?4 where u.id = ?5")
	int updateUserById(String full_name,String email,String phone,String password, Long id);


}
