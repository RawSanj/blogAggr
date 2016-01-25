package com.rawsanj.blogaggr.service;

import com.rawsanj.blogaggr.domain.Blog;
import com.rawsanj.blogaggr.domain.Item;
import com.rawsanj.blogaggr.domain.User;
import com.rawsanj.blogaggr.exception.RssException;
import com.rawsanj.blogaggr.repository.BlogRepository;
import com.rawsanj.blogaggr.repository.ItemRepository;
import com.rawsanj.blogaggr.repository.UserRepository;

import org.apache.xml.resolver.apps.resolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.xml.bind.JAXBException;

import java.util.List;

/**
 * Service Implementation for managing Blog.
 */
@Service
public class BlogService {

    private final Logger log = LoggerFactory.getLogger(BlogService.class);
    
    @Inject
    private BlogRepository blogRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private RssService rssService;
    
    @Inject
    private ItemService itemService;
    
    @Inject
    private ItemRepository itemRepository;
    
	public void saveItems(Blog blog) {
		
		try {
			List<Item> items = rssService.getItems(blog.getUrl());
			for (Item item : items) {
				Item savedItem = itemRepository.findByBlogAndLink(blog, item.getLink());
				if(savedItem == null) {
					item.setBlog(blog);
					itemRepository.save(item);
				}
			}
			log.debug("Request to save Items of : {}", blog);
		} catch (JAXBException e) {
			e.printStackTrace();
			log.warn("Could not save Items for '{}', exception is: {}", blog.getName(), e.getMessage());
		}
	}
	
	// 1 hour = 60 seconds * 60 minutes * 1000
	@Scheduled(fixedDelay=3600000)
	public void reloadBlogs() {
		List<Blog> blogs = blogRepository.findAll();
		for (Blog blog : blogs) {
			saveItems(blog);
		}
		log.info("Reloading All Blogs....");
	}
    
    /**
     * Save a blog.
     * @return the persisted entity
     */
    public Blog save(Blog blog, String name) {
    	User user = userRepository.findUserByLogin(name);
		blog.setUser(user);
        log.debug("Request to save Blog : {}", blog);
        Blog result = blogRepository.save(blog);
        saveItems(blog);
        return result;
    }

    /**
     *  get all the blogs.
     *  @return the list of entities
     */
    public Page<Blog> findAll(Pageable pageable) {
        log.debug("Request to get all Blogs");
        Page<Blog> result = blogRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one blog by id.
     *  @return the entity
     */
    public Blog findOne(String id) {
        log.debug("Request to get Blog : {}", id);
        Blog blog = blogRepository.findOne(id);
        return blog;
    }

    /**
     *  delete the  blog by id. 
     */
    @PreAuthorize("#blog.user.login == authentication.name or hasRole('ROLE_ADMIN')")
    public void delete(@P("blog") Blog blog) {
		blogRepository.delete(blog);
		itemService.deleteAllItemBy(blog);
	}

	public Page<Blog> findBlogsByUser(Pageable pageable, User user) {
		Page<Blog> result = blogRepository.findBlogByUser(pageable, user);
		return result;
	}
}
