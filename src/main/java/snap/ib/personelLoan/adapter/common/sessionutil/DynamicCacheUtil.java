package snap.ib.personelLoan.adapter.common.sessionutil;

/*import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.DistributedObjectCache;
//import com.ibm.ws.cache.EntryInfo;
import com.ibm.websphere.cache.EntryInfo;*/

public class DynamicCacheUtil {
/*	private static Logger logger = LoggerFactory.getLogger(DynamicCacheUtil.class);
	private HashMap<String, CacheInformation> dynamicCacheinformation;
	public static final String KEY_LOGGED_IN = "DynaCache";
	public static final String KEY_ACCOUNT_OPENING = "accountOpening";
	
	private static final String JNDI_LOGGED_IN = "DynaCache";
	private static final int EXP_LOGGED_IN = 10 * 60;
	@Resource
    (name="com.ibm.websphere.cache.DistributedMap",lookup="DynaCache",authenticationType=Resource.AuthenticationType.CONTAINER, shareable=true)
    DistributedMap dynaCaheInstance ;
	public DistributedMap initCache(String jndiName) {
		if(null == dynamicCacheinformation){
			dynamicCacheinformation = new HashMap<String, CacheInformation>();
			dynamicCacheinformation.put(KEY_LOGGED_IN, new CacheInformation(JNDI_LOGGED_IN, EXP_LOGGED_IN));
//			dynamicCacheinformation.put(KEY_ACCOUNT_OPENING, new CacheInformation(JNDI_AO_LOGGED_IN, EXP_AO_LOGGED_IN));
		}
		logger.info("init started");
//		DistributedMap dynaCaheInstance = null;
//		DistributedObjectCache cache  =null;
		try {
			InitialContext context = new InitialContext();
			if(null != jndiName && dynamicCacheinformation.containsKey(jndiName)){
				dynaCaheInstance = (DistributedMap) context.lookup(dynamicCacheinformation.get(jndiName).getJndiCompleteName());
				dynaCaheInstance.setTimeToLive(dynamicCacheinformation.get(jndiName).getExpirationTime());
				
				logger.info("init success");
//				int type = dynaCaheInstance.getMapType();
//				logger.info("init success type;"+type);
				
			}
			else{
				logger.info("initCache --> No key present in dynacache or cache object is null");
			}

		} catch (NamingException e) {
			e.printStackTrace();
			logger.info(
					"Dynacache is not configured on server object cache context =" + dynamicCacheinformation.get(jndiName).getJndiCompleteName());
		}catch (Exception e) {
			e.printStackTrace();
			logger.info(
					"Dynacache is not configured on server object cache context =" + dynamicCacheinformation.get(jndiName).getJndiCompleteName());
		}
		return dynaCaheInstance;
	}

	public void clearCache(String cacheKey, String jndiName) {
		DistributedMap cache = initCache(jndiName);
		if (cache != null && cache.containsKey(cacheKey)) {
			cache.remove(cacheKey);
		} else {
			logger.info("clearCache --> No key present in dynacache or cache object is null");
		}
	}

	public void putInCacheForSession(String cacheKey, Object objectToBeStoredIncache, String jndiName) throws Exception{
		DistributedMap cache = initCache(jndiName);
		try {
		
		
		if (cache != null) {
			if (cache.containsKey(cacheKey)) {
				cache.remove(cacheKey);
				if(null != jndiName && dynamicCacheinformation.containsKey(jndiName)){
					cache.put(cacheKey, objectToBeStoredIncache, 1, dynamicCacheinformation.get(jndiName).getExpirationTime(), EntryInfo.SHARED_PUSH_PULL,
							new String[] { dynamicCacheinformation.get(jndiName).getJndiName() });
				}
			} else {
				cache.put(cacheKey, objectToBeStoredIncache);
			}

		} else {
			logger.info("putValueInDynaCacheForSession --> No key present in dynacache or cache object is null");
		}
		
		} catch (Exception e) {
		   e.printStackTrace();
		   logger.info("Except ;"+e.getMessage());
		}
	}

	public Object getFromCache(String cacheKey, String jndiName)  throws Exception{
		DistributedMap cache = initCache(jndiName);
		Object cachedValue = null;
		try {
		
		if (cache != null) {
			if (cache.containsKey(cacheKey)) {
				cachedValue = cache.get(cacheKey);
				logger.info("get from cache object ;"+cachedValue);
				// Insert in session again, so as to refresh values, as Dynacache consider time-out from write only
				putInCacheForSession(cacheKey, cachedValue, jndiName);
			}
		}
		else {
			logger.info("putValueInDynaCacheForSession --> No key present in dynacache or cache object is null");
		}
		
		} catch (Exception e) {
		   e.printStackTrace();
		   logger.info("getFromCache ;"+e.getMessage());
		}
		return cachedValue;
	}
}
final class CacheInformation{
	private String jndiName;
	private int expirationTime;
	private String jndiCompleteName;
	
	public CacheInformation(String jndiName, int expirationTime) {
		super();
		this.jndiName = jndiName;
		this.expirationTime = expirationTime;
//		this.jndiCompleteName = "services/cache/" + jndiName;
		this.jndiCompleteName = jndiName;
	}
	
	public String getJndiCompleteName() {
		return jndiCompleteName;
	}
	public void setJndiCompleteName(String jndiCompleteName) {
		this.jndiCompleteName = jndiCompleteName;
	}
	public String getJndiName() {
		return jndiName;
	}
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}
	public int getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(int expirationTime) {
		this.expirationTime = expirationTime;
	}*/
}
