package com.ho.ng.spi;

public class ServiceProviderImplB implements ServiceProvider {
    @Override
    public void execute() {
        System.out.println("ServiceProviderImplB executed.");
    }
}