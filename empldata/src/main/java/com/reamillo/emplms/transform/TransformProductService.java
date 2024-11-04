package com.gabriel.emplms.transform;

import com.gabriel.emplms.entity.ProductData;
import com.gabriel.emplms.model.Product;

public interface TransformProductService {
	ProductData transform(Product product);
	Product transform(ProductData productData);
}
