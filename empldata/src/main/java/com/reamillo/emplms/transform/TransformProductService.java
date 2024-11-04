package com.reamillo.emplms.transform;

import com.reamillo.emplms.entity.ProductData;
import com.reamillo.emplms.model.Product;

public interface TransformProductService {
	ProductData transform(Product product);
	Product transform(ProductData productData);
}
