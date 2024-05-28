document.addEventListener('DOMContentLoaded', async () => {
    const cartItemsDiv = document.getElementById('cart-items');

    // Fetch cart items
    const response = await fetch('/api/cart');
    const cartItems = await response.json();

    if (cartItems.length === 0) {
        cartItemsDiv.innerHTML = '<p>Your cart is empty</p>';
    } else {
        cartItems.forEach(item => {
            const cartItemDiv = document.createElement('div');
            cartItemDiv.className = 'cart-item';
            cartItemDiv.innerHTML = `
                <h3>${item.product.name}</h3>
                <p>Quantity: ${item.quantity}</p>
                <p>Price: $${item.product.price}</p>
                <button data-cart-item-id="${item.id}">Remove</button>
            `;
            cartItemsDiv.appendChild(cartItemDiv);
        });

        // Remove from cart
        document.querySelectorAll('.cart-item button').forEach(button => {
            button.addEventListener('click', async (e) => {
                const cartItemId = e.target.dataset.cartItemId;
                const response = await fetch(`/api/cart/${cartItemId}`, {
                    method: 'DELETE'
                });
                if (response.ok) {
                    window.location.reload();
                } else {
                    alert('Failed to remove item');
                }
            });
        });
    }
});
