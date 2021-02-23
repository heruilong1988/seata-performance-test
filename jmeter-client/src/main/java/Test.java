/*
 * Copyright (c) 2021, TP-Link Co.,Ltd.
 * Author: heruilong <heruilong@tp-link.com.cn>
 * Created: 2021/2/22
 */

import java.net.URL;

public class Test {

    public static void main(String[] args) {
        Test t = new Test();
        URL url = t.getClass().getClassLoader().getResource("");
        System.out.println("url:" + url);

        String path = url.getPath();
        System.out.println("path:" + path);
    }

}
