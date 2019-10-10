package Hardwar;

import com.Hardwar.Persistence.Entitys.CentralProcessingUnit;
import com.Hardwar.Persistence.Entitys.ComputerComponent;
import com.Hardwar.Persistence.Entitys.GraphicsCard;
import com.Hardwar.Persistence.Entitys.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    String apiHost;
    Gson gson = new Gson();
    CloseableHttpClient client;
    private final String PRODUCT_ENDPOINT = "/product/";
    private final String GRAPHICSCARD_ENDPOINT = "/graphicscard/";
    private final String CPU_ENDPOINT = "/cpu/";

    public Client(String apiHost) {
        this.apiHost = apiHost;
    }

    public List<Product> getAllByDomainNameAndType(String domainName,String hardWareType) {

        client = HttpClients.createDefault();
        List<Product> allProductsByDomainNameAndType = new ArrayList<>();
        try {
            HttpGet get = new HttpGet(apiHost + PRODUCT_ENDPOINT + domainName + "/" + hardWareType);
            System.out.println("Executing " + get.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();
                        String json = EntityUtils.toString(entity);
                        return json;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode);
                    }
                }
            };

            String response = client.execute(get, responseHandler);
            Product[] productList = gson.fromJson(response, Product[].class);
            allProductsByDomainNameAndType = Arrays.asList(productList);

        } catch (Exception e) {
            System.out.println("error");
        }
        return allProductsByDomainNameAndType;
    }

    public List<Product> getAllByDomainName(String domainName) {

        client = HttpClients.createDefault();
        List<Product> allProductsByDomain = new ArrayList<>();
        try {
            HttpGet get = new HttpGet(apiHost + PRODUCT_ENDPOINT + domainName);
            System.out.println("Executing " + get.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();
                        String json = EntityUtils.toString(entity);
                        return json;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode);
                    }
                }
            };

            String response = client.execute(get, responseHandler);
            Product[] productList = gson.fromJson(response, Product[].class);
            allProductsByDomain = Arrays.asList(productList);

        } catch (Exception e) {
            System.out.println("error");
        }
        return allProductsByDomain;
    }

    public List<GraphicsCard> getAllGraphicsCardsByDomainName(String domainName) {

        client = HttpClients.createDefault();
        List<GraphicsCard> allGraphicsCardsByDomain = new ArrayList<>();
        try {
            HttpGet get = new HttpGet(apiHost + GRAPHICSCARD_ENDPOINT + domainName);
            System.out.println("Executing " + get.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();
                        String json = EntityUtils.toString(entity);
                        return json;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode);
                    }
                }
            };

            String response = client.execute(get, responseHandler);
            GraphicsCard[] productList = gson.fromJson(response, GraphicsCard[].class);
            allGraphicsCardsByDomain = Arrays.asList(productList);

        } catch (Exception e) {
            System.out.println("error");
        }
        return allGraphicsCardsByDomain;
    }

    public List<Product> getAllNullAndDomain(String domainName) {

        CloseableHttpClient client = HttpClients.createDefault();
        List<Product> allNullValues = new ArrayList<>();
        try {
            HttpGet get = new HttpGet(apiHost + PRODUCT_ENDPOINT + domainName + "/nullvalues");
            System.out.println("Executing " + get.getRequestLine());
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();

                        String json = EntityUtils.toString(entity);

                        return json;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode);
                    }
                }
            };

            String response = client.execute(get, responseHandler);
            Product[] productList = gson.fromJson(response, Product[].class);
            allNullValues = Arrays.asList(productList);

        } catch (Exception e) {
            System.out.println("error");
        }
        return allNullValues;
    }

    public List<GraphicsCard> getAllGraphicsCards() {

        CloseableHttpClient client = HttpClients.createDefault();
        List<GraphicsCard> allGraphicCards = new ArrayList<>();
        try {
            HttpGet get = new HttpGet(apiHost + GRAPHICSCARD_ENDPOINT);
            System.out.println("Executing " + get.getRequestLine());
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();

                        String json = EntityUtils.toString(entity);

                        return json;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode);
                    }
                }
            };

            String response = client.execute(get, responseHandler);
            GraphicsCard[] productList = gson.fromJson(response, GraphicsCard[].class);
            allGraphicCards = Arrays.asList(productList);

        } catch (Exception e) {
            System.out.println("error");
        }
        return allGraphicCards;
    }

    public List<Product> saveProducts(List<Product> product) {
        CloseableHttpResponse response;
        CloseableHttpClient client;
        HttpPost post;
        String json;
        StringEntity entity;
        try {
            client = HttpClients.createDefault();
            post = new HttpPost(apiHost + PRODUCT_ENDPOINT);
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Accept", "application/json;charset=UTF-8");
            System.out.println("Executing " + post.getRequestLine());
            json = gson.toJson(product.toArray(), Product[].class);
            entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");
            System.out.println(entity);
            post.setEntity(entity);
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the post request!");
            e.printStackTrace();
        }
        return product;
    }

    public List<GraphicsCard> saveGraphicsCard(List<GraphicsCard> graphicsCards) {
        CloseableHttpResponse response;
        CloseableHttpClient client;
        HttpPost post;
        String json;
        StringEntity entity;
        try {
            client = HttpClients.createDefault();
            post = new HttpPost(apiHost + GRAPHICSCARD_ENDPOINT);
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Accept", "application/json;charset=UTF-8");
            System.out.println("Executing " + post.getRequestLine());
            json = gson.toJson(graphicsCards.toArray(), GraphicsCard[].class);
            entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");
            System.out.println(entity);
            post.setEntity(entity);
            response = client.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the post request!");
            e.printStackTrace();
        }
        return graphicsCards;
    }

    public List<GraphicsCard> updateGraphicsCard(List<GraphicsCard> listToBeUpdated) {
        HttpResponse response;
        String json;
        StringEntity entity;
        CloseableHttpClient client;
        Type listType = new TypeToken<List<GraphicsCard>>() {}.getType();
        try {
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(apiHost + GRAPHICSCARD_ENDPOINT);
            System.out.println("Executing " + put.getRequestLine());
            put.addHeader("Content-Type", "application/json");
            put.addHeader("Accept", "application/json;charset=UTF-8");
           json = gson.toJson(listToBeUpdated,listType);
            entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");

            put.setEntity(entity);
            response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the put request!");
            e.printStackTrace();
        }

        return listToBeUpdated;
    }

    public List<Product> updateProductTypes(List<Product> listToBeUpdated) {
        HttpResponse response;
        String json;
        StringEntity entity;
        CloseableHttpClient client;
        Type listType = new TypeToken<List<Product>>() {}.getType();
        try {
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(apiHost + PRODUCT_ENDPOINT);
            System.out.println("Executing " + put.getRequestLine());
            put.addHeader("Content-Type", "application/json");
            put.addHeader("Accept", "application/json;charset=UTF-8");
            json = gson.toJson(listToBeUpdated,listType);
            entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");

            put.setEntity(entity);
            response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the put request!");
            e.printStackTrace();
        }

        return listToBeUpdated;
    }

    public List<CentralProcessingUnit> updateCPUs(List<CentralProcessingUnit> listToBeUpdated) {
        HttpResponse response;
        String json;
        StringEntity entity;
        CloseableHttpClient client;
        Type listType = new TypeToken<List<CentralProcessingUnit>>() {}.getType();
        try {
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(apiHost + CPU_ENDPOINT);
            System.out.println("Executing " + put.getRequestLine());
            put.addHeader("Content-Type", "application/json");
            put.addHeader("Accept", "application/json;charset=UTF-8");
            json = gson.toJson(listToBeUpdated,listType);
            entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");

            put.setEntity(entity);
            response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the put request!");
            e.printStackTrace();
        }

        return listToBeUpdated;
    }

    public List<CentralProcessingUnit> getAllCPUs() {

        CloseableHttpClient client = HttpClients.createDefault();
        List<CentralProcessingUnit> allCPUs = new ArrayList<>();
        try {
            HttpGet get = new HttpGet(apiHost + CPU_ENDPOINT);
            System.out.println("Executing " + get.getRequestLine());
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();

                        String json = EntityUtils.toString(entity);

                        return json;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + statusCode);
                    }
                }
            };

            String response = client.execute(get, responseHandler);
            CentralProcessingUnit[] cpuList = gson.fromJson(response, CentralProcessingUnit[].class);
            allCPUs = Arrays.asList(cpuList);

        } catch (Exception e) {
            System.out.println("error");
        }
        return allCPUs;
    }


}
