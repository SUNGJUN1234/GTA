package com.jsj.GTA.domain.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * User 의 CRUD 를 책임짐
 * */
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**소셜 로그인으로 반환되는 값 중 email 을 통해 이미 생성된 사용자인지 처음 가입하는 사용자인지 판단하기 위한 메소드*/
    Optional<Users> findByEmail(String email);
}
