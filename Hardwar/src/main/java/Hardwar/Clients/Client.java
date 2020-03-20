package Hardwar.Clients;


import com.Hardwar.Persistence.Entitys.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.text.html.parser.Entity;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client {
    String json;
    StringEntity entity;
    HttpPut put;
    String apiHost;
    Gson gson = new Gson();
    HttpGet get;
    CloseableHttpResponse response;
    CloseableHttpClient client;
    HttpPost post;

    public Client(String apiHost) {
        this.apiHost = apiHost;
    }

    public List<Product> getAllByDomainNameAndType(String domainName, String hardWareType, String endpoint) {

        client = HttpClients.createDefault();
        List<Product> allProductsByDomainNameAndType = new ArrayList<>();

        try {
            get = new HttpGet(apiHost + "/" + endpoint + "/" + domainName + "/" + hardWareType);
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            System.out.println("Executing " + get.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();
                         json = EntityUtils.toString(entity);
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

    public List<Product> getAllByDomainName(String domainName,String endpoint) {

        client = HttpClients.createDefault();
        List<Product> allProductsByDomain = new ArrayList<>();
        try {
            get = new HttpGet(apiHost + "/" + endpoint + "/" + domainName);
            System.out.println("Executing " + get.getRequestLine());
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();
                         json = EntityUtils.toString(entity);
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


    public List<Product> getAllNullAndDomain(String domainName,String endpoint) {

        client = HttpClients.createDefault();
        List<Product> allNullValues = new ArrayList<>();
        try {
            get = new HttpGet(apiHost + "/" + endpoint + "/" + domainName + "/nullvalues");
            System.out.println("Executing " + get.getRequestLine());
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (statusCode >= 200 && statusCode < 300) {
                        HttpEntity entity = httpResponse.getEntity();


                         json = EntityUtils.toString(entity);

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


    public List<Product> saveProducts(List<Product> product,String endpoint) {

        try {
            client = HttpClients.createDefault();
            post = new HttpPost(apiHost + "/" + endpoint);
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



    public List<Product> updateProductTypes(List<Product> listToBeUpdated,String endpoint) {

        Type listType = new TypeToken<List<Product>>() {}.getType();
        try {
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(apiHost + "/" + endpoint);
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

    public List<? extends ComputerComponent> saveComponent(List<? extends ComputerComponent> components, String endpoint) {
        Type component = new TypeToken<List<ComputerComponent>>(){}.getType();
        try {
            client = HttpClients.createDefault();
            post = new HttpPost(apiHost + "/" + endpoint);
            post.addHeader("Content-Type", "application/json");
            post.addHeader("Accept", "application/json;charset=UTF-8");
            System.out.println("Executing " + post.getRequestLine());
            json = gson.toJson(components, component);
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
        return components;
    }

    public List<? extends ComputerComponent> getAllComponentsByDomainName(String domainName) {
        Type component = new TypeToken<List<ComputerComponent>>(){}.getType();
        List<ComputerComponent> allComponents = new ArrayList<>();
        GraphicsCard[] graphicsCards = null;
        CentralProcessingUnit[] cpus = null;
        Chassi[] chassis = null;
        PowerSupplyUnit[] psus = null;
        MotherBoard[] motherBoards = null;
        RandomAccessMemory[] ram = null;
        Storage[] storages = null;

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/graphicscard/" + domainName );
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
             graphicsCards = gson.fromJson(json,GraphicsCard[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/storage/" + domainName);
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
            storages = gson.fromJson(json,Storage[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/ram/" + domainName);
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
            ram = gson.fromJson(json,RandomAccessMemory[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/cpu/" + domainName);
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
            cpus = gson.fromJson(json,CentralProcessingUnit[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/motherboards/"+ domainName);
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
            motherBoards = gson.fromJson(json,MotherBoard[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/psu/" + domainName);
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
            psus = gson.fromJson(json,PowerSupplyUnit[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + "/chassi/" + domainName);
            System.out.println("Executing " + get.getRequestLine());
            response = client.execute(get);
            HttpEntity httpEntity = response.getEntity();
            json = EntityUtils.toString(httpEntity);
            chassis = gson.fromJson(json,Chassi[].class);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the get request!");
            e.printStackTrace();
        }

        allComponents.addAll(Arrays.asList(storages));
        allComponents.addAll(Arrays.asList(graphicsCards));
        allComponents.addAll(Arrays.asList(cpus));
        allComponents.addAll(Arrays.asList(chassis));
        allComponents.addAll(Arrays.asList(motherBoards));
        allComponents.addAll(Arrays.asList(psus));
        allComponents.addAll(Arrays.asList(ram));
        return allComponents;
    }
    public List<? extends ComputerComponent> updateComponents(List<? extends ComputerComponent> listToBeUpdated, String endpoint) {
        Type component = new TypeToken<List<ComputerComponent>>(){}.getType();
        try {
            client = HttpClients.createDefault();
            HttpPut put = new HttpPut(apiHost + endpoint);
            System.out.println("Executing " + put.getRequestLine());
            put.addHeader("Content-Type", "application/json");
            put.addHeader("Accept", "application/json;charset=UTF-8");
            json = gson.toJson(listToBeUpdated,component);
            entity = new StringEntity(json,"UTF-8");
            entity.setContentEncoding("UTF-8");
            System.out.println(entity);
            System.out.println(listToBeUpdated);

            put.setEntity(entity);
            response = client.execute(put);
            int statusCode = response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            System.out.println("Error in the put request!");
            e.printStackTrace();
        }

        return listToBeUpdated;
    }


    public Product deleteProduct(Product product,String endpoint){
        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + endpoint + "/delete/" + product.getId());
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            client.execute(get);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
        return product;
    }
    public void deleteComponent(ComputerComponent component,String endpoint){
        try {
            client = HttpClients.createDefault();
            get = new HttpGet(apiHost + endpoint + "/delete/" + component.getId());
            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "application/json;charset=UTF-8");
            client.execute(get);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Error");
        }
    }
}
