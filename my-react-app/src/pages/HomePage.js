// src/pages/HomePage.js
import React, { useEffect, useState } from 'react';
import axios from 'axios';

const HomePage = () => {
    const [products, setProducts] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('http://localhost:8080/api/products') // URL должен совпадать с вашим backend
            .then(response => {
                setProducts(response.data);
                setLoading(false);
            })
            .catch(error => {
                console.error('Error fetching products:', error);
                setError(error);
                setLoading(false);
            });
    }, []);

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error loading products: {error.message}</div>;
    }
    console.log('Products type:', typeof products);

    return (
        <div>
            <h1>Products</h1>
            <ul>

                {products.length > 0 ? (

                    products.map(product => (
                        <li key={product.id}>{product.name} - ${product.price}</li>
                    ))
                ) : (
                    <li>No products available</li>
                )}
            </ul>

        </div>
    );
};

export default HomePage;
