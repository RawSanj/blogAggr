package com.rawsanj.blogaggr.repository;

import com.rawsanj.blogaggr.domain.Blog;
import com.rawsanj.blogaggr.domain.Item;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Item entity.
 */
public interface ItemRepository extends MongoRepository<Item,String> {

	Item findByBlogAndLink(Blog blog, String link);
	//Item findByLink(String link);

	List<Item> findByBlog(Blog blog);

	Page<Item> findItemByBlog(Pageable pageable, Blog blog);
	
}
