
package web.spring.SpringEShop.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.spring.SpringEShop.models.Cart;
import web.spring.SpringEShop.models.User;
import web.spring.SpringEShop.services.CartService;
import web.spring.SpringEShop.services.UserService;

import javax.servlet.http.HttpSession;


@Controller
public class CartController {


    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;


    @GetMapping("/cart")
    public String showCart(@AuthenticationPrincipal User user, HttpSession session, Model model) {
        model.addAttribute("user", user);
        String sessionValue = (String) session.getAttribute("sessionValue");
        if (sessionValue == null||cartService.getCartBySessionValue(sessionValue)==null) {
            model.addAttribute("cart", new Cart());
        } else{
            Cart cart = cartService.getCartBySessionValue(sessionValue);
            model.addAttribute("cart", cart);
        }
        model.addAttribute("title", "Корзина");
        return "cart";
    }





    @PostMapping("/addtocart")
    public String addToCart(HttpSession session, Model model, @RequestParam("id") Long id,
                            @RequestParam("quantity") int quantity) {

        String sessionValue = (String) session.getAttribute("sessionValue");
        if (sessionValue == null) {
            sessionValue = session.getId();
            session.setAttribute("sessionValue", sessionValue);
            cartService.addCart(id, sessionValue, quantity);
        } else {
            cartService.addToShoppingCart(id, sessionValue, quantity);
        }


        return "redirect:/catalog";
    }


    @GetMapping("/removeCartItem/{id}")
    public String removeItem(@PathVariable("id") Long id, HttpSession session) {

        String sessionValue = (String) session.getAttribute("sessionValue");
        cartService.removeCartItemFromCart(id, sessionValue);
        return "redirect:/cart";
    }

    @GetMapping("/clearCart")
    public String clearCart(HttpSession session) {

        String sessionValue = (String) session.getAttribute("sessionValue");
        session.removeAttribute("sessionValue");
        cartService.clearCart(sessionValue);
        return "redirect:/cart";
    }

    @PostMapping("/updateCart")
    public String updateCartItem(@RequestParam("item_id") Long id, @RequestParam("quantity") int quantity) {

        cartService.updateCartItem(id, quantity);
        return "redirect:/cart";
    }


}
