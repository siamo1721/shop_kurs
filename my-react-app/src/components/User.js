import React, { useEffect, useState } from 'react';
import { fetchUser } from '../api';

const User = ({ username }) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const getUser = async () => {
            try {
                const userData = await fetchUser(username);
                setUser(userData);
            } catch (error) {
                console.error(`Error fetching user ${username}:`, error);
            }
        };

        getUser();
    }, [username]);

    if (!user) return <div>Loading...</div>;

    return (
        <div>
            <h1>{user.username}</h1>
            <p>{user.email}</p>
        </div>
    );
};

export default User;
