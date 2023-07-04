package com.ys.oa.result;


public class Result {

    private Integer code;
    private String message;
    private Object data;

    //构造方法 全为private
    private Result(){}

    private Result(Integer code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Result(ResultCodeEnum resultCodeEnum,Object data){
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
        this.data = data;
    }

    //成功
    public static Result ok(){
        return new Result(ResultCodeEnum.SUCCESS,null);
    }

    public static Result ok(String message){
        return new Result(ResultCodeEnum.SUCCESS.getCode(),message,null);
    }

    public static Result ok(Object data){
        return new Result(ResultCodeEnum.SUCCESS,data);
    }

    public static Result ok(String message,Object data){
        return new Result(ResultCodeEnum.SUCCESS.getCode(),message,data);
    }
    //失败
    public static Result fail(){
        return new Result(ResultCodeEnum.FAIL,null);
    }

    public static Result fail(String message){
        return new Result(ResultCodeEnum.FAIL.getCode(),message,null);
    }

    public static Result fail(ResultCodeEnum resultCodeEnum){
        return new Result(resultCodeEnum.getCode(),resultCodeEnum.getMessage(),null);
    }

    public static Result fail(ResultCodeEnum resultCodeEnum,String message){
        return new Result(resultCodeEnum.getCode(),message,null);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
