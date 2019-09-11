package cn.jarod.bluecat.core.component;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

/**
 * @auther jarod.jin 2019/8/14
 */
@Slf4j
@Service
public class JaxWsDynamicClient {

    public Object[] callWebService(String url, String methodName, Object... params) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        return clientInvoke(methodName,client,params);
    }


    public Object[] callWebServiceWithUserInfo(String url, String methodName, String userName, String password, Object... params) {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        // 需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new ClientLoginInterceptor(userName,password));
        return clientInvoke(methodName, client, params);
    }

    private Object[] clientInvoke(String methodName, Client client, Object[] params) {
        Object[] objects = new Object[0];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            objects = client.invoke(methodName, params);
            log.info("WebService返回数据:{}", objects[0]);
        } catch (Exception e) {
            log.error("WebService调用出错：{}", e.getMessage());
        }
        return objects;
    }
}
