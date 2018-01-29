package com.perfectcodes;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Unit test for simple App.
 */
public class AppTest
{
//    /**
//     * Create the test case
//     *
//     * @param testName name of the test case
//     */
//    public AppTest( String testName )
//    {
//        super( testName );
//    }
//
//    /**
//     * @return the suite of tests being tested
//     */
//    public static Test suite()
//    {
//        return new TestSuite( AppTest.class );
//    }
//
//    /**
//     * Rigourous Test :-)
//     */
//    public void testApp()
//    {
//        assertTrue( true );
//    }
//
//    public void testForkJoin() throws ExecutionException, InterruptedException {
//        int [] array = new int[80];
//        for (int i=0;i<80;i++){
//            array[i] = i;
//        }
//        ForkJoinPool pool = new ForkJoinPool();
//        MyRecursiveTask task = new MyRecursiveTask(array);
//        Future<Integer> future = pool.submit(task);
//        System.out.println(future.get());
//    }
    public static void main(String [] args){
        String url = "https://weibo.cn/3952070245/fans?page=2";
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build();
             CloseableHttpResponse response = httpClient.execute(new HttpGetConfig(url))) {
            String result = EntityUtils.toString(response.getEntity());
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("获取失败");
        }
    }
}
