package net.huansi.retrofit.netmanage;

import net.huansi.retrofit.netapi.HttpApi;

/**
 * Created by Administrator on 2019/3/26 0026.
 */

abstract class BaseManager {
    protected HttpApi httpApi;

    protected BaseManager(HttpApi httpApi){
        this.httpApi=httpApi;
    }
}
