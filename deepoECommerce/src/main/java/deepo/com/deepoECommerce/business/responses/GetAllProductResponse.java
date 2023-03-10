package deepo.com.deepoECommerce.business.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAllProductResponse {

    private int productId;
    private boolean isBestSeller;
    private String product_title;
    private String product_main_image_url;
    private String app_sale_price;
    private String app_sale_price_currency;
    private boolean isPrime;
    private String product_detail_url;
    private String evaluate_rate;
    private String original_price;
}
