
package com.web.curation.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.curation.model.User;

public interface UserDao extends JpaRepository<User, String> {
	User getUserByEmail(String email);

	User getUserById(int id);

	User getUserByNickname(String nickname);

	Optional<User> findUserByEmailAndPassword(String email, String password);

	User findUserByEmail(String email);

	User getUserByEmailAndAccountType(String email, int accountType);

	User getUserByEmailAndNicknameAndId(String email, String nickName, int id);

}
