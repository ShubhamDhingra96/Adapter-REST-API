package snap.ib.personelLoan.adapter.common;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.snap.ib.personelLoan.common.provider.JsonProvider;
import com.snap.ib.personelLoan.common.provider.RequestLogger;
import com.snap.ib.personelLoan.common.provider.ResponseLogger;

/**
 * 
 * @author Harsh
 *
 */
public class AdapterConfig extends Application{

 
	    @Override
	    public Set<Class<?>> getClasses() {
	        Set<Class<?>> classes = new HashSet<Class<?>>();
	        classes.add(CommonAdapterResource.class);
	        classes.add(JsonProvider.class);
	        classes.add(RequestLogger.class);
	        classes.add(ResponseLogger.class);
	       
	        return classes;
	    }
}
