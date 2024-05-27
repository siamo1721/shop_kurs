// src/pages/UserPage.js
import React, { useState } from 'react';
import axios from 'axios';

const UserPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');

    const handleLogin = (e) => {
        e.preventDefault();
        axios.post('/api/auth/login', { username, password }) // Adjust URL to match your backend API
            .then(response => {
                console.log('Logged in:', response.data);
                // Handle successful login (e.g., store token, redirect)
            })
            .catch(error => {
                console.error('Login error:', error);
            });
    };

    return (
        <div>
            <h1>User Login</h1>
            <form onSubmit={handleLogin}>
                <div>
                    <label>Username</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label>Password</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default UserPage;
