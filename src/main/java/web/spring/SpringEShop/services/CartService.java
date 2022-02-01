package web.spring.SpringEShop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.spring.SpringEShop.models.Cart;
import web.spring.SpringEShop.models.CartItem;
import web.spring.SpringEShop.models.Item;
import web.spring.SpringEShop.repo.CartItemRepository;
import web.spring.SpringEShop.repo.CartRepository;
import web.spring.SpringEShop.repo.ItemRepository;

import java.util.Set;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;


    public Cart addCart(Long id, String sessionValue, int quantity) {

        Cart cart = new Cart();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(quantity);
        cartItem.setItem(itemRepository.findById(id).orElseThrow());
        cart.getItems().add(cartItem);
        cart.setSessionValue(sessionValue);
        return cartRepository.save(cart);
    }


    public Cart addToShoppingCart(Long id, String sessionValue, int quantity) {

        Cart cart = cartRepository.findBySessionValue(sessionValue);
        Item t = itemRepository.findById(id).orElseThrow();
        boolean productIsInCart = false;
        if (cart != null) {
            Set<CartItem> items = cart.getItems();
            for (CartItem item : items) {
                if (item.getItem().equals(t)) {
                    productIsInCart = true;
                    item.setQuantity(item.getQuantity() + quantity);
                    cart.setItems(items);
                    return cartRepository.saveAndFlush(cart);
                }
            }
        }
        if (!productIsInCart && cart != null) {
            CartItem cartItem1 = new CartItem();
            cartItem1.setQuantity(quantity);
            cartItem1.setItem(t);
            cart.getItems().add(cartItem1);
            return cartRepository.saveAndFlush(cart);
        }

        return this.addCart(id, sessionValue, quantity);
    }

    public Cart getCartBySessionValue(String sessionValue) {
        return cartRepository.findBySessionValue(sessionValue);
    }



    public Cart removeCartItemFromCart(Long id, String sessionValue) {

        Cart cart = cartRepository.findBySessionValue(sessionValue);
        Set<CartItem> items = cart.getItems();
        CartItem cartItem = null;
        for (CartItem item : items) {
            if (item.getId().equals(id)) {
                cartItem = item;

            }
        }
        items.remove(cartItem);
        cartItemRepository.delete(cartItem);
        cart.setItems(items);
        return cartRepository.save(cart);
    }

    public void clearCart(String sessionValue) {

        Cart cart = cartRepository.findBySessionValue(sessionValue);
        cartRepository.delete(cart);
    }
}

