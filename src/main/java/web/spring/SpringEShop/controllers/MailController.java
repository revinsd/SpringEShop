package web.spring.SpringEShop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.spring.SpringEShop.models.Cart;
import web.spring.SpringEShop.models.CartItem;
import web.spring.SpringEShop.models.User;
import web.spring.SpringEShop.repo.CartItemRepository;
import web.spring.SpringEShop.services.CartService;
import web.spring.SpringEShop.services.MailService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {
    @Autowired
    private CartService cartService;
    @Autowired
    private MailService mail;
    @Autowired
    private CartItemRepository cartItemRepository;

    @PostMapping("/order")
    public String sendMail(@RequestParam("phoneNumber") String phoneNumber,
                           @RequestParam("email") String email,
                           @RequestParam("address") String address,
                           @AuthenticationPrincipal User user,
                           HttpSession session,
                           Model model){
        String sessionValue = (String) session.getAttribute("sessionValue");
        Cart cart = cartService.getCartBySessionValue(sessionValue);
        List<CartItem> cartItems= new ArrayList<>(cart.getItems());
        String items="";
        for (CartItem cartItem : cartItems) {
        items=items+" "+cartItem.getQuantity()+"x "+cartItem.getItem().getName()+",";
        }
        String message = "Уважаемый "+user.getUsername()
                +", Ваш заказ:"+items
                +" Номер телефона: "+ phoneNumber
                +", Адрес: "+address
                +". Заказ обработан. Сумма к оплате: "+ cart.getTotalPrice()+" ₽";

        mail.sendMail(email,message,"Номер заказа: " +sessionValue);
        model.addAttribute("sessionValue", sessionValue);
        cartService.clearCart(sessionValue);
        return "/order";

    }
}
