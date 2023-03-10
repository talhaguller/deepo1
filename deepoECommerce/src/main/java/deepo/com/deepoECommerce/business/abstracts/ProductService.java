package deepo.com.deepoECommerce.business.abstracts;

import deepo.com.deepoECommerce.business.requests.CreateProductRequest;
import deepo.com.deepoECommerce.business.responses.GetAllProductResponse;
import deepo.com.deepoECommerce.entities.concretes.Product;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<GetAllProductResponse> getAll();
    void add(CreateProductRequest createProductRequest) throws IOException, InterruptedException, JSONException;


}
