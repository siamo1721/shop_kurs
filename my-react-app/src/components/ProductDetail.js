import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { Card, CardContent, Typography, Button } from '@material-ui/core';
import { fetchProductById } from '../api';

const ProductDetail = () => {
    const { id } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        const getProduct = async () => {
            try {
                const product = await fetchProductById(id);
                setProduct(product);
            } catch (error) {
                console.error(`Error fetching product with id ${id}:`, error);
            }
        };

        getProduct();
    }, [id]);

    if (!product) return <div>Loading...</div>;

    return (
        <Card>
            <CardContent>
                <Typography variant="h4">{product.name}</Typography>
                <Typography variant="h5">${product.price}</Typography>
                <Typography variant="body1">{product.description}</Typography>
                <Button variant="contained" color="primary">
                    Add to Cart
                </Button>
            </CardContent>
        </Card>
    );
};

export default ProductDetail;
