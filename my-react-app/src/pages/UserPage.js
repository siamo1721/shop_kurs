import React from 'react';

const UserPage = () => {
    return (
        <div className="container">
            <header>
                <h1>User Info</h1>
            </header>
            <nav>
                <a href="/">Home</a>
                <a href="/cart">Cart</a>
                <a href="/user">User</a>
            </nav>
            <h1>User Info</h1>
            {/* Здесь будет отображение информации о пользователе */}
        </div>
    );
};

export default UserPage;
