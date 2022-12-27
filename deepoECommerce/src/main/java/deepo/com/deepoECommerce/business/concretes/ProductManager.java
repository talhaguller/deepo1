package deepo.com.deepoECommerce.business.concretes;

import deepo.com.deepoECommerce.business.abstracts.ProductService;
import deepo.com.deepoECommerce.business.requests.CreateProductRequest;
import deepo.com.deepoECommerce.business.responses.GetAllProductResponse;
import deepo.com.deepoECommerce.dataAccess.abstracts.ProductRepository;
import deepo.com.deepoECommerce.entities.concretes.Product;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductManager implements ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductManager(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    //İş Kuralları
    @Override
    public List<GetAllProductResponse> getAll() {

        List<Product> products = productRepository.findAll();
        List<GetAllProductResponse> productResponse = new ArrayList<GetAllProductResponse>();

        for (Product product: products) {
            GetAllProductResponse responseItem = new GetAllProductResponse();
            responseItem.setProductId(product.getProductId());
            responseItem.setProduct_title(product.getProduct_title());
            responseItem.setProduct_main_image_url(product.getProduct_main_image_url());
            responseItem.setApp_sale_price(product.getApp_sale_price());
            responseItem.setApp_sale_price_currency(product.getApp_sale_price_currency());
            responseItem.setProduct_detail_url(product.getProduct_detail_url());
            responseItem.setEvaluate_rate(product.getEvaluate_rate());
            responseItem.setOriginal_price(product.getOriginal_price());

            productResponse.add(responseItem);
        }

        return productResponse;
    }

    @Override
    public void add(CreateProductRequest createProductRequest) throws IOException, InterruptedException, JSONException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://amazon24.p.rapidapi.com/api/product?categoryID=aps&keyword=iphone&country=US&page=1"))
                .header("X-RapidAPI-Key", "22e834f73fmsh4f455d0e2a47d81p1184eajsn040a0991ca32")
                .header("X-RapidAPI-Host", "amazon24.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.toString());

        File file = new File("dosya.json");
        if (!file.exists()) { file.createNewFile();
        } FileWriter fileWriter = new FileWriter(file, false);
        BufferedWriter bWriter = new BufferedWriter(fileWriter);
        bWriter.write(response.body()); bWriter.close();

        JSONObject myResponse = new JSONObject(response.toString());
        System.out.println("result after Reading JSON Response");
        System.out.println("isBestSeller- "+myResponse.getBoolean("isBestSeller"));
        System.out.println("product_title- "+myResponse.getString("product_title"));
        System.out.println("product_main_image_url- "+myResponse.getString("product_main_image_url"));
        System.out.println("app_sale_price- "+myResponse.getString("app_sale_price"));
        System.out.println("app_sale_price_currency- "+myResponse.getString("app_sale_price_currency"));
        System.out.println("isPrime- "+myResponse.getBoolean("isPrime"));
        System.out.println("product_detail_url- "+myResponse.getString("product_detail_url"));
        System.out.println("product_id- "+myResponse.getString("product_id"));
        System.out.println("evaluate_rate- "+myResponse.getString("evaluate_rate"));
        System.out.println("original_price- "+myResponse.getString("original_price"));

        Product product = new Product();
        product.setProduct_title(String.valueOf(myResponse.getString("product_title")));
        product.setProduct_main_image_url(String.valueOf(myResponse.getString("product_main_image_url")));
        product.setApp_sale_price(String.valueOf(myResponse.getString("app_sale_price")));
        product.setApp_sale_price_currency(String.valueOf(myResponse.getString("app_sale_price_currency")));
        product.setProduct_detail_url(String.valueOf(myResponse.getString("product_detail_url")));
        product.setEvaluate_rate(String.valueOf(myResponse.getString("product_id")));
        product.setOriginal_price(String.valueOf(myResponse.getString("evaluate_rate")));
        product.setOriginal_price(String.valueOf(myResponse.getString("original_price")));



        this.productRepository.save(product);
    }


}
