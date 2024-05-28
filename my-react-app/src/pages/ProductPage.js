import React, { useState } from 'react';
import axios from 'axios';

const ProductsPage = () => {
    const [products] = useState([
        { id: 1, name: 'Laptop', price: 999.99 },
        { id: 2, name: 'Smartphone', price: 499.99 },
    ]);

    const handleAddToCart = (productId) => {
        axios.post('/api/cart', { productId, quantity: 1 })
            .then(() => console.log('Product added to cart'))
            .catch(error => console.error('Error adding to cart:', error));
    };

    return (
        <div className="container">
            <h1>Products</h1>
            <ul>
                {products.map(product => (
                    <li key={product.id}>
                        {product.name} - ${product.price}
                        <button onClick={() => handleAddToCart(product.id)}>Add to Cart</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProductsPage;
