package com.rawsanj.blogaggr.repository;

import com.rawsanj.blogaggr.domain.Blog;
import com.rawsanj.blogaggr.domain.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Blog entity.
 */
public interface BlogRepository extends MongoRepository<Blog,String> {

	Page<Blog> findBlogByUser(Pageable pageable, User user);

}
