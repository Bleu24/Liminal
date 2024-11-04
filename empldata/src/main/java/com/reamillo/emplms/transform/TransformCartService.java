package com.reamillo.emplms.transform;

import com.reamillo.emplms.entity.CartData;
import com.reamillo.emplms.model.Cart;

public interface TransformCartService {
	CartData transform(Cart cart);
	Cart transform(CartData cartData);
}
