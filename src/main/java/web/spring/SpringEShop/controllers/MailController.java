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
        StringBuilder items =new StringBuilder();
        for (CartItem cartItem : cartItems) {
            items.append(" ").append(cartItem.getQuantity()).append("x ").append(cartItem.getItem().getName()).append(",");
        }
        StringBuilder message = new StringBuilder();
        message.append("Уважаемый ")
                .append(user.getUsername())
                .append(",")
                .append(" Ваш заказ:")
                .append(items)
                .append(" Номер телефона: ")
                .append(phoneNumber)
                .append(", Адрес: ")
                .append(address)
                .append(". Заказ обработан.")
                .append( "Сумма к оплате: ")
                .append(cart.getTotalPrice())
                .append(" ₽");

        mail.sendMail(email,message.toString(),"Номер заказа " +sessionValue);
        return "/order";

    }
}
