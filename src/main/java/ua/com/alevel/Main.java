package ua.com.alevel;

import ua.com.alevel.dao.ProductDao;
import ua.com.alevel.dao.impl.ProductDaoImpl;
import ua.com.alevel.dto.Product;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductDao dao = new ProductDaoImpl();
        List<Product> productList = dao.selectAllProducts(10);
        for (Product pr:
                productList) {
            System.out.println(pr.toString());
        }
        dao.updateProduct("abcIQXBjhY");

    }
}
