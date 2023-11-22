package com.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author Miguel Castro
 */
public class ProductItemProcessor implements ItemProcessor<Product, Product> {

    private static final Logger log = LoggerFactory.getLogger(ProductItemProcessor.class);

    @Override
    public Product process(final Product product) {
        final String name = product.getName().toUpperCase();
        final Double price = product.getPrice();
        final Product transformedProduct = new Product(name, price);
        log.info("Converting (" + product + ") into (" + transformedProduct + ")");
        return transformedProduct;
    }

}
