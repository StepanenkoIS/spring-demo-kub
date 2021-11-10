package is.controllers;


import is.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
class HomeController {

    @GetMapping("/")
    public Product setMsg() {
        return new Product("001", "lamp", "IEK", "E27, 11 watt", "200,00");
    }
}
