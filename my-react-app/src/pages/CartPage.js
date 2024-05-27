import React from 'react';

const CartPage = () => {
    return (
        <div className="container">
            <header>
                <h1>Your Cart</h1>
            </header>
            <nav>
                <a href="/">Home</a>
                <a href="/cart">Cart</a>
                <a href="/user">User</a>
            </nav>
            <h1>Cart</h1>
            {/* Здесь будет отображение товаров в корзине */}
        </div>
    );
};

export default CartPage;
