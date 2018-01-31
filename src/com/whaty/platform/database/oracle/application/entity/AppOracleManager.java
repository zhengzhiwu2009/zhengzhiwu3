/*
 * OracleManager.java
 *
 * Created on 2005��4��6��, ����8:30
 */

package com.whaty.platform.database.oracle.application.entity;
import com.whaty.platform.Exception.PlatformException;
/**
 *
 * @author  Administrator
 */
public class AppOracleManager extends com.whaty.platform.database.oracle.standard.entity.user.OracleManager {
    
    /** Creates a new instance of OracleManager */
    public AppOracleManager() {
        super();
    }
    
    public AppOracleManager(java.lang.String id) throws PlatformException {
        super(id);
    }
    
    
}
