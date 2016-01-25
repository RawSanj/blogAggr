package com.rawsanj.blogaggr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.rawsanj.blogaggr.domain.Authority;
import com.rawsanj.blogaggr.domain.Blog;
import com.rawsanj.blogaggr.domain.User;
import com.rawsanj.blogaggr.repository.UserRepository;
import com.rawsanj.blogaggr.service.BlogService;
import com.rawsanj.blogaggr.service.UserService;
import com.rawsanj.blogaggr.web.rest.util.HeaderUtil;
import com.rawsanj.blogaggr.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing Blog.
 */
@RestController
@RequestMapping("/api")
public class BlogResource {

    private final Logger log = LoggerFactory.getLogger(BlogResource.class);
        
    @Inject
    private BlogService blogService;
    
    @Inject
    private UserService userService;
    
    /**
     * POST  /blogs -> Create a new blog.
     */
    @RequestMapping(value = "/blogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Blog> createBlog(@Valid @RequestBody Blog blog, Principal principal) throws URISyntaxException {
        log.debug("REST request to save Blog : {}", blog);
        if (blog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("blog", "idexists", "A new blog cannot already have an ID")).body(null);
        }
        String name = principal.getName();
        Blog result = blogService.save(blog, name);
        return ResponseEntity.created(new URI("/api/blogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("blog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /blogs -> Updates an existing blog.
     */
    @RequestMapping(value = "/blogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Blog> updateBlog(@Valid @RequestBody Blog blog, Principal principal) throws URISyntaxException {
        log.debug("REST request to update Blog : {}", blog);
        if (blog.getId() == null) {
            return createBlog(blog, principal);
        }
        Blog result = blogService.save(blog, principal.getName());
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("blog", blog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /blogs -> get all the blogs.
     */
    @RequestMapping(value = "/blogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Blog>> getAllBlogs(Pageable pageable, Principal principal)
        throws URISyntaxException {
        log.debug("REST request to get a page of Blogs of Logged in User");
        User user = userService.findUserByLogin(principal.getName());
        Set<Authority> authorities = user.getAuthorities();
        if (authorities.contains(new Authority("ROLE_ADMIN"))) {
        	log.debug("REST request to get a page of Blogs of All Users");
        	Page<Blog> page = blogService.findAll(pageable); 
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
		}
        Page<Blog> page = blogService.findBlogsByUser(pageable, user); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/blogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /blogs/:id -> get the "id" blog.
     */
    @RequestMapping(value = "/blogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Blog> getBlog(@PathVariable String id) {
        log.debug("REST request to get Blog : {}", id);
        Blog blog = blogService.findOne(id);
        return Optional.ofNullable(blog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /blogs/:id -> delete the "id" blog.
     */
    @RequestMapping(value = "/blogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBlog(@PathVariable String id) {
        log.debug("REST request to delete Blog : {}", id);
        Blog blog = blogService.findOne(id);
		blogService.delete(blog);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("blog", id.toString())).build();
    }
}
