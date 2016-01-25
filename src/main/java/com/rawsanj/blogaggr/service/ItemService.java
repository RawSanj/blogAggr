package com.rawsanj.blogaggr.service;

import com.rawsanj.blogaggr.domain.Blog;
import com.rawsanj.blogaggr.domain.Item;
import com.rawsanj.blogaggr.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Item.
 */
@Service
public class ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemService.class);
    
    @Inject
    private ItemRepository itemRepository;
    
    /**
     * Save a item.
     * @return the persisted entity
     */
    public Item save(Item item) {
        log.debug("Request to save Item : {}", item);
        Item result = itemRepository.save(item);
        return result;
    }

    /**
     *  get all the items.
     *  @return the list of entities
     */
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public Page<Item> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        Page<Item> result = itemRepository.findAll(pageable); 
        return result;
    }

    /**
     *  get one item by id.
     *  @return the entity
     */
    public Item findOne(String id) {
        log.debug("Request to get Item : {}", id);
        Item item = itemRepository.findOne(id);
        return item;
    }

    /**
     *  delete the  item by id.
     */
    public void delete(String id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.delete(id);
    }

	public void deleteAllItemBy(Blog blog) {
		List<Item> items = itemRepository.findByBlog(blog);
		for (Item item : items) {
			itemRepository.delete(item);	
		}			
	}
	@PreAuthorize("#blog.user.login == authentication.name or hasRole('ROLE_ADMIN')")
	public Page<Item> findItemByBlog(Pageable pageable, @P("blog") Blog blog) {
		Page<Item> result = itemRepository.findItemByBlog(pageable, blog);
		return result;
	}
}
