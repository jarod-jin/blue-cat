package cn.jarod.bluecat.auth.model;


/**
 * @author Jarod Jin E-mail:kira277@163.com
 * @version 创建时间：2020/3/16
 */
public class IntegrationAuthenticationContext {

    private static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

    public static void set(IntegrationAuthentication integrationAuthentication) {
        holder.set(integrationAuthentication);
    }

    public static IntegrationAuthentication get(){
        return holder.get();
    }

    public static void clear() {
        holder.remove();
    }
}
