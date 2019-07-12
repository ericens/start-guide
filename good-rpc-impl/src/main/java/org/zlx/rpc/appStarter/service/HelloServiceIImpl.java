package org.zlx.rpc.appStarter.service;

/**
 * Created by @author linxin on 13/10/2017.  <br>
 */
public class HelloServiceIImpl implements HelloServiceI {

    @Override
    public String sayHi(String name) {
        return "Hi, " + name;
    }

}
