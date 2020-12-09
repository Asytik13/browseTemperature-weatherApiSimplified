import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.io.IOException;
import static org.testng.Assert.assertEquals;

public class EndpointTests extends BaseClass {

    @BeforeTest
    public void addCity() throws IOException{
        HttpPost post = new HttpPost(BASE_ENDPOINT + "/city");

        String json = "{\"id\":0,\"city\":\"Krakow\"}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
    }

    @Test
    public void getCitiesReturns200() throws IOException{
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/citiesList");
        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 200);
    }

    @AfterTest
    public void removeCity(){
        HttpDelete delete = new HttpDelete(BASE_ENDPOINT + "/deleteCity/Krakow/");
    }


    @Test
    public void addCityReturns200() throws IOException{

        HttpPost post = new HttpPost(BASE_ENDPOINT + "/city");

        String json = "{\"id\":0,\"city\":\"Krakow\"}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        response = client.execute(post);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 200);
    }

    @AfterTest
    public void removeCity1(){
        HttpDelete delete = new HttpDelete(BASE_ENDPOINT + "/deleteCity/Krakow/");
    }

    @Test
    public void getCitiesReturns404() throws IOException{
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/nonExistEndpoint");
        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 404);
    }

    @Test
    public void addCityReturns500() throws IOException{

        HttpPost post = new HttpPost(BASE_ENDPOINT + "/city");

        String json = "{\"id\":0,\"wrongBody\":\"Krakow\"}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");

        response = client.execute(post);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 500);
    }

    @BeforeTest
    public void addKrakow() throws IOException{
        HttpPost post = new HttpPost(BASE_ENDPOINT + "/city");

        String json = "{\"id\":0,\"city\":\"Krakow\"}";
        StringEntity entity = new StringEntity(json);
        post.setEntity(entity);
        post.setHeader("Accept", "application/json");
        post.setHeader("Content-type", "application/json");
    }

    @Test
    public void getCityTemperatureReturns200() throws IOException{

        HttpGet get = new HttpGet(BASE_ENDPOINT + "/temperature?city=Krakow");
        response = client.execute(get);

        int actualStatus = response.getStatusLine().getStatusCode();

        assertEquals(actualStatus, 200);
    }

    @AfterTest
    public void removeCity2(){
        HttpDelete delete = new HttpDelete(BASE_ENDPOINT + "/deleteCity/Krakow/");
    }
}
