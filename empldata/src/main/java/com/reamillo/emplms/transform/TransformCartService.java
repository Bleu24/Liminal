package com.gabriel.emplms.transform;

import com.gabriel.emplms.entity.CartData;
import com.gabriel.emplms.model.Cart;
public interface TransformCartService {
	CartData transform(Cart cart);
	Cart transform(CartData cartData);
}
