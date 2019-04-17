package net.huansi.retrofit.listener;

/**
 * 回调的接口
 * @param <SuccessBean>
 */
public interface WebResponseListener<SuccessBean> {
    void success(SuccessBean bean);
    void error(WebResponseError error);


    class WebResponseError{
        public static final int CODE_ERROR_NORMAL=-1;

        private int code;
        private String msg;

        public WebResponseError(String msg) {
            code=CODE_ERROR_NORMAL;
            this.msg = msg;
        }

        public WebResponseError(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
