package is.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import is.models.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

@RestController
class HomeController {

    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    @Value("${url}")
    private String url;

    @GetMapping("/test")
    public Product getMsgTesting() {
        Product product = new Product("getMsg", "lamp", "IEK", "E27, 11 watt", "200,00");
        LOGGER.info("In method getMsg(). Output: " + product);
        return product;
    }

    @GetMapping("/")
    public String getStringFromService() throws IOException {
        String urlGetSting = url + "getString";
        LOGGER.info("In method getStringFromService(). urlService: " + urlGetSting);
        StringBuilder urlBody = new StringBuilder();
        try {
            URL urlService = new URL(urlGetSting);
            URLConnection urlConnection = urlService.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            }
            assert connection != null;
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String current;
            while ((current = in.readLine()) != null) {
                urlBody.append(current);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("In method getStringFromService(). Output: " + urlBody);
        return urlBody.toString();
    }

    @GetMapping("/product")
    public Product getProductFromService() {
        String urlGetProduct = url + "getProduct";
        LOGGER.info("In method getProductFromService(). urlService: " + urlGetProduct);
        Product product = null;
        try {
            URL urlService = new URL(urlGetProduct);
            product = new ObjectMapper().readValue(urlService, Product.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("In method getProductFromService(). Successful: " + product);
        return product;
    }
}
