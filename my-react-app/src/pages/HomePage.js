import React, { useEffect, useState } from 'react';
import axios from 'axios';

const HomePage = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [cart, setCart] = useState([]);
    const [selectedProduct, setSelectedProduct] = useState(null);

    useEffect(() => {
        const fetchProducts = async () => {
            try {
                const token = localStorage.getItem('token'); // Предполагается, что токен хранится в localStorage
                const response = await axios.get('http://localhost:8080/api/products', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                setProducts(response.data);
                setLoading(false);
            } catch (error) {
                console.error('Error fetching products:', error);
                setError(error);
                setLoading(false);
            }
        };

        fetchProducts();
    }, []);

    const addToCart = (product) => {
        setCart([...cart, product]);
    };

    const handleProductClick = (product) => {
        setSelectedProduct(product);
    };

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error loading products: {error.message}</div>;
    }

    return (
        <div className="container">
            <header>
                <h1>Products</h1>
            </header>
            <nav>
                <a href="/">Home</a>
                <a href="/cart">Cart</a>
                <a href="/user">User</a>
            </nav>
            <h1>Products</h1>
            <ul>
                {products.length > 0 ? (
                    products.map(product => (
                        <li key={product.id} onClick={() => handleProductClick(product)}>
                            {product.name} - ${product.price}
                            <button onClick={() => addToCart(product)}>Add to Cart</button>
                        </li>
                    ))
                ) : (
                    <li>No products available</li>
                )}
            </ul>
            {selectedProduct && (
                <div className="product-details">
                    <h2>{selectedProduct.name}</h2>
                    <p>Price: ${selectedProduct.price}</p>
                    <p>Quantity: {selectedProduct.quantity}</p>
                    <p>Discount: {selectedProduct.discount * 100}%</p>
                </div>
            )}
            <footer>
                <p>&copy; полный кал</p>
            </footer>
        </div>
    );
};

export default HomePage;
