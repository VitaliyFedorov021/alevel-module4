package ua.com.alevel.dao;

import ua.com.alevel.dto.Product;

import java.util.List;

public interface ProductDao {
    void addProduct(Product product);

    void updateProduct(String productSku);

    List<Product> selectAllProducts(int limit);

    void deleteProduct(String productSku);

}
