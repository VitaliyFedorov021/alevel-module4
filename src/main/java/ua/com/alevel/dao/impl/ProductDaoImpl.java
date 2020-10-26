package ua.com.alevel.dao.impl;

import ua.com.alevel.DataSource;
import ua.com.alevel.dao.ProductDao;
import ua.com.alevel.dto.Product;
import ua.com.alevel.impl.DataSourceImpl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductDaoImpl implements ProductDao {
    private final DataSource dataSource = new DataSourceImpl();

    @Override
    public void addProduct(Product product) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("INSERT INTO product (sku, name, price, description " +
                     "categoryName, supplierId) values (?, ?, ?, ?, (SELECT name FROM category " +
                     "ORDER BY RAND() LIMIT 1), SELECT id FROM supplier ORDER BY RAND() LIMIT 1)")) {
            connection.setAutoCommit(false);
            ps.setString(1, product.getProductSku());
            ps.setString(2, product.getName());
            ps.setInt(3, product.getPrice());
            ps.setString(4, product.getDescription());
            ps.executeQuery();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateProduct(String productSku) {
        String SQL = "UPDATE product SET name = ?, price = ?, description = ?, categoryName = ?, supplierId = ? WHERE sku = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(SQL)) {
            Scanner sc = new Scanner(System.in);
            connection.setAutoCommit(false);
            System.out.println("enter the new product name");
            sc.hasNext();
            ps.setString(1, sc.nextLine());
            System.out.println("enter the new product price");
            sc.hasNext();
            ps.setInt(2, sc.nextInt());
            System.out.println("enter the new product description");
            sc.hasNext();
            ps.setString(3, sc.nextLine());
            System.out.println("enter the new category name");
            sc.hasNext();
            ps.setString(4, sc.nextLine());
            System.out.println("enter the new supplier id");
            sc.hasNext();
            ps.setInt(5, sc.nextInt());
            sc.hasNext();
            ps.setString(6, productSku);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Product> selectAllProducts(int limit) {
        List<Product> products = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM product LIMIT ? OFFSET ?")) {
            Scanner sc = new Scanner(System.in);
            int flag = 0;
            int offset = 0;
            ps.setInt(1, limit);
            ps.setInt(2, offset);
            ResultSet set = ps.executeQuery();
            while (set.next()) {
                Product product = mapProduct(set);
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public void deleteProduct(String productSku) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM product WHERE sku = ?")) {
            ps.setString(1, productSku);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Product selectProductToUpdate(String productSku) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM product WHERE sku = ?")) {
            connection.setAutoCommit(false);
            ps.setString(1, productSku);
            ResultSet res = ps.executeQuery();
            Product product = mapProduct(res);
            return product;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Product mapProduct(ResultSet res) {
        Product product = new Product();
        try {
            product.setName(res.getString("name"));
            product.setCategoryName(res.getString("categoryName"));
            product.setDescription(res.getString("description"));
            product.setPrice(res.getInt("price"));
            product.setProductSku(res.getString("sku"));
            product.setSupplierId(res.getInt("supplierId"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return product;
    }


}
