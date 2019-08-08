package com.cg.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cg.daos.ProductDAO;
import com.cg.entities.Product;
import com.cg.exceptions.ApplicationException;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDAO dao;

	@Transactional(propagation=Propagation.REQUIRED)
	public void create(Product p) {
		if(dao.existsById(p.getProductId())) {
			throw new ApplicationException("Product Already exists!!");
		}
		dao.save(p);
	}

	public void update(Product p) {
		if(dao.existsById(p.getProductId()))
			dao.save(p);
		else
			throw new ApplicationException("Product "+p.getProductId()+" not found for update operation!");
	}

	public void delete(String productId) {
		if(dao.existsById(productId))
			dao.deleteById(productId);
		else
			throw new ApplicationException("Product "+productId+" not found for delete operation!");

	}
	
	@Transactional(readOnly = true)
	public List<Product> getAllProducts() {
		
		return dao.findAll();
	}
	
	@Transactional(readOnly = true)
	public Product find(String productId) {
		Optional<Product> product = dao.findById(productId);
		if(product.isPresent()) {
			return product.get();
		}
		else
			throw new ApplicationException("No Product found with Product Id "+productId);
	}

}
