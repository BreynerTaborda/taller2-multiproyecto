package co.com.poli.usersservice.helpers;



public class Response {

    private Integer code;
    private Object data;

    public static class Builder{
        private Integer code;
        private Object data;

        public Builder(){

        }

        public Builder code(Integer code){
            this.code = code;

            return this;
        }

        public Builder data(Object data){
            this.data = data;

            return this;
        }

        public Response build(){
            Response response = new Response();
            response.setCode(this.code);
            response.setData(this.data);

            return response;
        }
    }

    public Response(){

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
