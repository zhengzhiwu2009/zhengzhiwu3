/**
 * GatewayOrderQueryServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com._99bill.sandbox.apipay.services.gatewayOrderQuery;

public class GatewayOrderQueryServiceLocator extends org.apache.axis.client.Service implements com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQueryService {

    public GatewayOrderQueryServiceLocator() {
    }


    public GatewayOrderQueryServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GatewayOrderQueryServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for gatewayOrderQuery
    //Lee sandbox为测试地址www为生产地址
    private java.lang.String gatewayOrderQuery_address = "http://www.99bill.com/apipay/services/gatewayOrderQuery";

    public java.lang.String getgatewayOrderQueryAddress() {
        return gatewayOrderQuery_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String gatewayOrderQueryWSDDServiceName = "gatewayOrderQuery";

    public java.lang.String getgatewayOrderQueryWSDDServiceName() {
        return gatewayOrderQueryWSDDServiceName;
    }

    public void setgatewayOrderQueryWSDDServiceName(java.lang.String name) {
        gatewayOrderQueryWSDDServiceName = name;
    }

    public com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuery getgatewayOrderQuery() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(gatewayOrderQuery_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getgatewayOrderQuery(endpoint);
    }

    public com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuery getgatewayOrderQuery(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuerySoapBindingStub _stub = new com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuerySoapBindingStub(portAddress, this);
            _stub.setPortName(getgatewayOrderQueryWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setgatewayOrderQueryEndpointAddress(java.lang.String address) {
        gatewayOrderQuery_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuery.class.isAssignableFrom(serviceEndpointInterface)) {
                com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuerySoapBindingStub _stub = new com._99bill.sandbox.apipay.services.gatewayOrderQuery.GatewayOrderQuerySoapBindingStub(new java.net.URL(gatewayOrderQuery_address), this);
                _stub.setPortName(getgatewayOrderQueryWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("gatewayOrderQuery".equals(inputPortName)) {
            return getgatewayOrderQuery();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    //Lee sandbox为测试地址www为生产地址
    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/gatewayOrderQuery", "GatewayOrderQueryService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            //Lee sandbox为测试地址www为生产地址
            ports.add(new javax.xml.namespace.QName("http://www.99bill.com/apipay/services/gatewayOrderQuery", "gatewayOrderQuery"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("gatewayOrderQuery".equals(portName)) {
            setgatewayOrderQueryEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
