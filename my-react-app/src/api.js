// src/api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

export const fetchProducts = async () => {
    try {
        const response = await axios.get(`${API_BASE_URL}/products`);
        return response.data;
    } catch (error) {
        console.error('Error fetching products:', error);
        throw error;
    }
};

export const fetchProductById = async (id) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/products/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching product with id ${id}:`, error);
        throw error;
    }
};

export const fetchUser = async (username) => {
    try {
        const response = await axios.get(`${API_BASE_URL}/users/${username}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching user ${username}:`, error);
        throw error;
    }
};

