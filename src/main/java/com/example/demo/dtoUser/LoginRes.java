package com.example.demo.dtoUser;
import com.example.demo.Model.Supervisor;

public class LoginRes{
    private String message;
    private boolean success;
    private String jwt;
    private Supervisor supervisor;

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "LoginRes{" +
                "message='" + message + '\'' +
                ", success=" + success +
                ", jwt='" + jwt + '\'' +
                ", user=" + supervisor +
                '}';
    }
}