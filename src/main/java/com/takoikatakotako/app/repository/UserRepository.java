package com.takoikatakotako.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}



//
//public interface NewsRepository extends JpaRepository<News, Long> {
//    Optional<News> findByNewsId(Long title);
//    List<News> findByTitle(String title);
//
//    @Transactional
//    void deleteByNewsId(Long newsId);
//}