import React, { useEffect, useState } from 'react';
import { Grid, Card, CardContent, Typography, Button } from '@material-ui/core';
import { Link } from 'react-router-dom';
import { fetchProducts } from '../api';

const ProductList = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const getProducts = async () => {
            try {
                const products = await fetchProducts();
                setProducts(products);
            } catch (error) {
                console.error('Error fetching products:', error);
            }
        };

        getProducts();
    }, []);

    return (
        <Grid container spacing={3}>
            {products.map(product => (
                <Grid item key={product.id} xs={12} sm={6} md={4}>
                    <Card>
                        <CardContent>
                            <Typography variant="h5">{product.name}</Typography>
                            <Typography variant="body2">${product.price}</Typography>
                            <Button component={Link} to={`/product/${product.id}`} color="primary">
                                View Details
                            </Button>
                        </CardContent>
                    </Card>
                </Grid>
            ))}
        </Grid>
    );
};

export default ProductList;
