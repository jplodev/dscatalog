package com.jpdev.dscatalog.services;

import com.jpdev.dscatalog.dto.CategoryDTO;
import com.jpdev.dscatalog.dto.ProductDTO;
import com.jpdev.dscatalog.entities.Category;
import com.jpdev.dscatalog.entities.Product;
import com.jpdev.dscatalog.repositories.CategoryRepository;
import com.jpdev.dscatalog.repositories.ProductRespository;
import com.jpdev.dscatalog.services.exceptions.DatabaseException;
import com.jpdev.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRespository respository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = respository.findAll(pageRequest);
        return list.map(x -> new ProductDTO(x));
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Optional<Product> obj = respository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Id not found " + id ));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        return new ProductDTO(respository.save(entity));
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        try{
            Product entity = respository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            return new ProductDTO(respository.save(entity));
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }

    @Transactional
    public void delete(Long id) {
        if(!respository.existsById(id)){
            throw new ResourceNotFoundException("Id not found " + id);
        } try{
            respository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation ");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO catDTO : dto.getCategories()){
            Category category = categoryRepository.getReferenceById(catDTO.getId());
            entity.getCategories().add(category);
        }
    }

}
